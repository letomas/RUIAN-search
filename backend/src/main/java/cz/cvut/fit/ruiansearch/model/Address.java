package cz.cvut.fit.ruiansearch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.time.LocalDate;

@SolrDocument(collection = "ruian")
public class Address {
    @Id
    @Indexed
    private String id;

    /*
     * ADM stands for Adresní místo
     */
    @Indexed(name="K_d_ADM")
    private long admCode;

    @Indexed(name="K_d_obce")
    private long cityCode;

    @Indexed(name="N_zev_obce")
    private String cityName;

    /*
     * MOMC is czech abbreviation for city borough
     */
    @Indexed(name="K_d_MOMC")
    private long boroughCode;

    @Indexed(name="N_zev_MOMC")
    private String boroughName;

    /*
     *  MOP is czech abbreviation for Prague's (Czech capital city) city borough
     */
    @Indexed(name="K_d_MOP")
    private long pragueBoroughCode;

    @Indexed(name="N_zev_MOP")
    private long pragueBoroughName;

    /*
     * Unlike boroughs city disctricts aren't self-governed
     */
    @Indexed(name="K_d___sti_obce")
    private long disctrictCode;

    @Indexed(name="N_zev___sti_obce")
    private String districtName;

    @Indexed(name="K_d_ulice")
    private long streetCode;

    @Indexed(name="N_zev_ulice")
    private String streetName;

    /*
     * SO stands for Stavební objekt (building)
     * buildingType can be either "č.p." (číslo popisné) or "č.ev" (číslo evidenční)
     */
    @Indexed(name="Typ_SO")
    private String buildingType;

    @Indexed(name="__slo_domovn_")
    private long houseNumber;

    @Indexed(name="__slo_orienta_n_")
    private long orientationalNumber;

    @Indexed(name="Znak___sla_orienta_n_ho")
    private String orientationalNumberLetter;

    @Indexed(name="PS_")
    private long postalCode;

    @Indexed(name="Sou_adnice_X")
    private double coordinationX;

    @Indexed(name="Sou_adnice_Y")
    private double coordinationY;

    @Indexed(name="Plat__Od")
    private LocalDate validFrom;

    public String getId() {
        return id;
    }

    public long getAdmCode() {
        return admCode;
    }

    public long getCityCode() {
        return cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public long getBoroughCode() {
        return boroughCode;
    }

    public String getBoroughName() {
        return boroughName;
    }

    public long getPragueBoroughCode() {
        return pragueBoroughCode;
    }

    public long getPragueBoroughName() {
        return pragueBoroughName;
    }

    public long getDisctrictCode() {
        return disctrictCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public long getStreetCode() {
        return streetCode;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public long getHouseNumber() {
        return houseNumber;
    }

    public long getOrientationalNumber() {
        return orientationalNumber;
    }

    public String getOrientationalNumberLetter() {
        return orientationalNumberLetter;
    }

    public long getPostalCode() {
        return postalCode;
    }

    public double getCoordinationX() {
        return coordinationX;
    }

    public double getCoordinationY() {
        return coordinationY;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }
}
