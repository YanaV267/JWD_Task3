package com.development.task3.entity;

import java.time.Year;

public abstract class AbstractPostalCard {
    protected String id;
    protected String title;
    protected ThemeType theme;
    protected String country;
    protected Year year;
    protected String author;
    protected boolean sent;
    protected Address destinationAddress;

    protected AbstractPostalCard() {
        title = "unknown";
        destinationAddress = new Address();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ThemeType getTheme() {
        return theme;
    }

    public void setTheme(ThemeType theme) {
        this.theme = theme;
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
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
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
        return sent == that.sent && theme == that.theme &&
                (id != null ? id.equals(that.id) : that.id == null) &&
                (title != null ? title.equals(that.title) : that.title == null) &&
                (country != null ? country.equals(that.country) : that.country == null) &&
                (year != null ? year.equals(that.year) : that.country == null) &&
                (author != null ? author.equals(that.author) : that.author == null) &&
                (destinationAddress != null ? destinationAddress.equals(that.destinationAddress) : that.destinationAddress == null);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = result * 31 + (id != null ? id.hashCode() : 0);
        result = result * 31 + (title != null ? title.hashCode() : 0);
        result = result * 31 + theme.hashCode();
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
        sb.append("id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", theme=").append(theme);
        sb.append(", country=").append(country).append('\'');
        sb.append(", year=").append(year);
        sb.append(", author=").append(author).append('\'');
        sb.append(", sent=").append(sent);
        sb.append(", destinationAddress=").append(destinationAddress);
        sb.append("}");
        return sb.toString();
    }
}
