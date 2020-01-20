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
    @Indexed(name="Kód_ADM")
    private long admCode;

    @Indexed(name="Kód_obce")
    private long cityCode;

    @Indexed(name="Název_obce")
    private String cityName;

    /*
     * MOMC is czech abbreviation for city borough
     */
    @Indexed(name="Kód_MOMC")
    private long boroughCode;

    @Indexed(name="Název_MOMC")
    private String boroughName;

    /*
     *  MOP is czech abbreviation for Prague's (Czech capital city) city borough
     */
    @Indexed(name="Kód_MOP")
    private long pragueBoroughCode;

    @Indexed(name="Název_MOP")
    private long pragueBoroughName;

    /*
     * Unlike boroughs city disctricts aren't self-governed
     */
    @Indexed(name="Kód_části_obce")
    private long disctrictCode;

    @Indexed(name="Název_části_obce")
    private String districtName;

    @Indexed(name="Kód_ulice")
    private long streetCode;

    @Indexed(name="Název_ulice")
    private String streetName;

    /*
     * SO stands for Stavební objekt (building)
     * buildingType can be either "č.p." (číslo popisné) or "č.ev" (číslo evidenční)
     */
    @Indexed(name="Typ_SO")
    private String buildingType;

    @Indexed(name="Číslo_domovní")
    private long houseNumber;

    @Indexed(name="Číslo_orientační")
    private long orientationalNumber;

    @Indexed(name="Znak_čísla_orientačního")
    private String orientationalNumberLetter;

    @Indexed(name="PSČ")
    private long postalCode;

    @Indexed(name="Souřadnice_X")
    private double coordinationX;

    @Indexed(name="Souřadnice_Y")
    private double coordinationY;

    @Indexed(name="Platí_od")
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
