package com.development.task3.entity;

public class PromotionalCard extends AbstractPostalCard {
    private String companyName;

    public PromotionalCard() {
        super();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        PromotionalCard that = (PromotionalCard) o;
        return companyName != null ? companyName.equals(that.companyName) : that.companyName == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 31 + companyName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append("{").append(super.toString());
        sb.append("companyName=").append(companyName);
        sb.append('}');
        return sb.toString();
    }
}
