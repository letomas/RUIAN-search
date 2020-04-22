package cz.cvut.fit.ruiansearch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.fit.ruiansearch.controller.AddressController;
import cz.cvut.fit.ruiansearch.model.Address;
import cz.cvut.fit.ruiansearch.service.AddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddressController.class)
public class AddressControlerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    @Test
    public void getAddressesByCodeShouldUseServiceTest() throws Exception {
        String admCode = "555";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Address> page = getAddressPage();

        when(addressService.findByAdmCodeStartsWith(admCode, pageable)).thenReturn(page);
        mockMvc.perform(get("/api/addresses?admCode=" + admCode))
                .andExpect(status().isOk())
                .andExpect(content().string(mapToJson(page)));
    }

    @Test
    public void searchReturnsEmptyPageWithoutParametersTest() throws Exception {
        Page<Address> emptyPage = Page.empty();
        mockMvc.perform(get("/api/addresses/search"))
                .andExpect(status().isOk())
                .andExpect(content().string(mapToJson(emptyPage)));
    }

    @Test
    public void nearbyEndpointReturnsBadRequestWithoutParametersTest() throws Exception {
        mockMvc.perform(get("/api/addresses/nearby"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void searchEndpointSupportsOnlyGetTest() throws Exception {
        String endpoint = "/api/addresses/search";
        onlyGetMethodAllowedTest(endpoint);
    }

    @Test
    public void nearbyEndpointSupportsOnlyGetTest() throws Exception {
        String endpoint = "/api/addresses/nearby?x=0&y=0";
        onlyGetMethodAllowedTest(endpoint);
    }

    @Test
    public void detailEndpointSupportsOnlyGetTest() throws Exception {
        String endpoint = "/api/addresses/555";
        onlyGetMethodAllowedTest(endpoint);
    }

    private void onlyGetMethodAllowedTest(String endpoint) throws Exception {
        mockMvc.perform(post(endpoint))
                .andExpect(status().is4xxClientError());
        mockMvc.perform(put(endpoint))
                .andExpect(status().is4xxClientError());
        mockMvc.perform(patch(endpoint))
                .andExpect(status().is4xxClientError());
        mockMvc.perform(delete(endpoint))
                .andExpect(status().is4xxClientError());
        mockMvc.perform(get(endpoint))
                .andExpect(status().isOk());
    }

    private Page<Address> getAddressPage() {
        Address address = new Address();
        List<Address> addressList = new ArrayList<>();
        addressList.add(address);

        return new PageImpl<>(addressList);
    }

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
