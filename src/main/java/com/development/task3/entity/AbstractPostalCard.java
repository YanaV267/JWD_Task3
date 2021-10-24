package com.development.task3.entity;

import java.time.Year;

public abstract class AbstractPostalCard {
    protected ThemeType themeType;
    protected String country;
    protected Year year;
    protected String author;
    protected boolean isSent;
    protected Address destinationAddress;

    protected AbstractPostalCard() {
        author = "неизвестен";
        isSent = false;
        destinationAddress = null;
    }

    public ThemeType getThemeType() {
        return themeType;
    }

    public void setThemeType(ThemeType themeType) {
        this.themeType = themeType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean sent) {
        isSent = sent;
    }

    public Address getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(Address destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AbstractPostalCard that = (AbstractPostalCard) o;
        return isSent == that.isSent && themeType == that.themeType &&
                (country != null ? country.equals(that.country) : that.country == null) &&
                (year != null ? year.equals(that.year) : that.country == null) &&
                (author != null ? author.equals(that.author) : that.author == null) &&
                (destinationAddress != null ? destinationAddress.equals(that.destinationAddress) : that.destinationAddress == null);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = result * 31 + themeType.hashCode();
        result = result * 31 + (country != null ? country.hashCode() : 0);
        result = result * 31 + (year != null ? year.hashCode() : 0);
        result = result * 31 + (author != null ? author.hashCode() : 0);
        result = result * 31 + (destinationAddress != null ? destinationAddress.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append("{");
        sb.append("themeType=").append(themeType);
        sb.append(", country=").append(country).append('\'');
        sb.append(", year=").append(year);
        sb.append(", author=").append(author).append('\'');
        sb.append(", isSent=").append(isSent);
        sb.append(", destinationAddress=").append(destinationAddress);
        sb.append("}");
        return sb.toString();
    }
}
