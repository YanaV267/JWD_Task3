package com.development.task3.builder;

public enum CardXmlTag {
    OLD_CARDS,
    POSTAL_CARD,
    GREETING_CARD,
    PROMOTIONAL_CARD,
    DESTINATION_ADDRESS,
    THEME,
    ORIGIN_COUNTRY,
    YEAR,
    AUTHOR,
    SENT,
    COUNTRY,
    TOWN,
    STREET,
    HOLIDAY,
    COMPANY_NAME;

    private static final char UNDERSCORE = '_';
    private static final char HYPHEN = '-';

    public String getTagName() {
        String tageName = this.name();
        tageName = tageName.toLowerCase();
        tageName = tageName.replace(UNDERSCORE, HYPHEN);
        return tageName;
    }
}
