package com.development.task3.entity;

public class GreetingCard extends AbstractPostalCard {
    private HolidayType holidayType;

    public GreetingCard() {
        super();
    }

    public HolidayType getHolidayType() {
        return holidayType;
    }

    public void setHolidayType(HolidayType holidayType) {
        this.holidayType = holidayType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        GreetingCard that = (GreetingCard) o;
        return holidayType == that.holidayType;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 31 + holidayType.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append("{").append(super.toString());
        sb.append("holidayType=").append(holidayType);
        sb.append('}');
        return sb.toString();
    }
}
