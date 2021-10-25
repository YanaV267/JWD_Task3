package com.development.task3.entity;

public class GreetingCard extends AbstractPostalCard {
    private HolidayType holiday;

    public GreetingCard() {
        super();
    }

    public HolidayType getHoliday() {
        return holiday;
    }

    public void setHoliday(HolidayType holiday) {
        this.holiday = holiday;
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
        return holiday == that.holiday;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 31 + holiday.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName());
        sb.append("{").append(super.toString());
        sb.append("holiday=").append(holiday);
        sb.append('}');
        return sb.toString();
    }
}
