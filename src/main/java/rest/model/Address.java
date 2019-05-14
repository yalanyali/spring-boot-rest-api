package rest.model;

// TODO: toString FULL ADDRESS

import javax.persistence.*;

@Entity
public class Address {

    private String state;
    private String city;
    private String street;
    private String buildingNumber;
    private String zip;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @OneToOne(mappedBy = "address")
    private Patient patient;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuildingNumber() {
        return buildingNumber;
    }

    public void setBuildingNumber(String buildingNumber) {
        this.buildingNumber = buildingNumber;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

}