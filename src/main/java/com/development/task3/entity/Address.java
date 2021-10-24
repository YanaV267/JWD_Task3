package com.development.task3.entity;

public class Address {
    private String country;
    private String town;
    private String street;
    private String houseNumber;

    public Address() {
    }

    public Address(String country, String town, String street, String houseNumber) {
        this.country = country;
        this.town = town;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Address address = (Address) o;
        return (country != null ? country.equals(address.country) : address.country == null) &&
                (town != null ? town.equals(address.town) : address.town == null) &&
                (street != null ? street.equals(address.street) : address.street == null) &&
                (houseNumber != null ? houseNumber.equals(address.houseNumber) : address.houseNumber == null);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (town != null ? town.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (houseNumber != null ? houseNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append("{");
        sb.append("country='").append(country).append('\'');
        sb.append(", town='").append(town).append('\'');
        sb.append(", street='").append(street).append('\'');
        sb.append(", houseNumber='").append(houseNumber).append('\'');
        sb.append("}");
        return sb.toString();
    }
}
