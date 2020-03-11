package cz.cvut.fit.ruiansearch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.time.LocalDate;
import java.util.List;

@SolrDocument(collection = "ruian")
public class Address {
    @Id
    @Indexed
    private String id;

    /*
     * ADM stands for Adresní místo
     */
    @Indexed(name="K_d_ADM")
    private String admCode;

    @Indexed(name="K_d_obce")
    private String cityCode;

    @Indexed(name="N_zev_obce")
    private String cityName;

    /*
     * MOMC is czech abbreviation for city borough
     */
    @Indexed(name="K_d_MOMC")
    private String boroughCode;

    @Indexed(name="N_zev_MOMC")
    private String boroughName;

    /*
     *  MOP is czech abbreviation for Prague's (Czech capital city) city borough
     */
    @Indexed(name="K_d_MOP")
    private String pragueBoroughCode;

    @Indexed(name="N_zev_MOP")
    private String pragueBoroughName;

    /*
     * Unlike boroughs city disctricts aren't self-governed
     */
    @Indexed(name="K_d___sti_obce")
    private String disctrictCode;

    @Indexed(name="N_zev___sti_obce")
    private String districtName;

    @Indexed(name="K_d_ulice")
    private String streetCode;

    @Indexed(name="N_zev_ulice")
    private String streetName;

    /*
     * SO stands for Stavební objekt (building)
     * buildingType can be either "č.p." (číslo popisné) or "č.ev" (číslo evidenční)
     */
    @Indexed(name="Typ_SO")
    private String buildingType;

    @Indexed(name="__slo_domovn_")
    private String houseNumber;

    @Indexed(name="__slo_orienta_n_")
    private String orientationalNumber;

    @Indexed(name="Znak___sla_orienta_n_ho")
    private String orientationalNumberLetter;

    @Indexed(name="PS_")
    private String postalCode;

    @Indexed(name="Sou_adnice_X")
    private Double coordinateX;

    @Indexed(name="Sou_adnice_Y")
    private Double coordinateY;

    @Indexed(name="Plat__Od")
    private LocalDate validFrom;

    @Indexed(name="Search_field")
    private List<String> searchField;

    public String getId() {
        return id;
    }

    public String getAdmCode() {
        return admCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public String getBoroughCode() {
        return boroughCode;
    }

    public String getBoroughName() {
        return boroughName;
    }

    public String getPragueBoroughCode() {
        return pragueBoroughCode;
    }

    public String getPragueBoroughName() {
        return pragueBoroughName;
    }

    public String getDisctrictCode() {
        return disctrictCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public String getStreetCode() {
        return streetCode;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public String getOrientationalNumber() {
        return orientationalNumber;
    }

    public String getOrientationalNumberLetter() {
        return orientationalNumberLetter;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Double getCoordinateX() {
        return coordinateX;
    }

    public Double getCoordinateY() {
        return coordinateY;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public List<String> getSearchField() {
        return searchField;
    }
}
