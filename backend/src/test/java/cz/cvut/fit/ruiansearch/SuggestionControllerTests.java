package cz.cvut.fit.ruiansearch;

import cz.cvut.fit.ruiansearch.controller.SuggestionController;
import cz.cvut.fit.ruiansearch.service.AddressService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

@WebMvcTest(SuggestionController.class)
public class SuggestionControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    @Test
    public void cityEndpoitSupportsOnlyGetTest() throws Exception {
        String endpoint = "/api/suggestions/city?city=";
        onlyGetMethodAllowedTest(endpoint);
    }

    @Test
    public void districtEndpoitSupportsOnlyGetTest() throws Exception {
        String endpoint = "/api/suggestions/district?district=";
        onlyGetMethodAllowedTest(endpoint);
    }

    @Test
    public void streetEndpoitSupportsOnlyGetTest() throws Exception {
        String endpoint = "/api/suggestions/street?street=";
        onlyGetMethodAllowedTest(endpoint);
    }

    @Test
    public void houseNumberEndpoitSupportsOnlyGetTest() throws Exception {
        String endpoint = "/api/suggestions/houseNumber?houseNumber=";
        onlyGetMethodAllowedTest(endpoint);
    }

    @Test
    public void citEndpointReturnsBadRequestWithoutParamTest() throws Exception {
        String endpoint = "/api/suggestions/city";
        mockMvc.perform(get(endpoint))
        .andExpect(status().is4xxClientError());

    }

    @Test
    public void cityEndpointReturnsEmptyPageWithEmptyParamTest() throws Exception {
        List<String> list = Collections.emptyList();

        String endpoint = "/api/suggestions/city?city=";
        mockMvc.perform(get(endpoint))
                .andExpect(status().isOk())
                .andExpect(content().string(mapToJson(list)));
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

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}
