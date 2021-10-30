package com.development.task3.builder;

import com.development.task3.entity.*;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.time.Year;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import static com.development.task3.builder.CardXmlTag.*;

public class CardHandler extends DefaultHandler {
    private static final char UNDERSCORE = '_';
    private static final char HYPHEN = '-';

    private final Set<AbstractPostalCard> postalCards;
    private final EnumSet<CardXmlTag> withText;
    private AbstractPostalCard currentCard;
    private CardXmlTag currentTag;

    public CardHandler() {
        postalCards = new HashSet<>();
        withText = EnumSet.range(CardXmlTag.THEME, CardXmlTag.COMPANY_NAME);
    }

    public Set<AbstractPostalCard> getPostalCards() {
        return postalCards;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        String greetingCardTag = GREETING_CARD.getTagName();
        String promotionalCardTag = PROMOTIONAL_CARD.getTagName();
        if (!qName.toUpperCase().replace(HYPHEN, UNDERSCORE).equals(POSTAL_CARDS)) {
            if (greetingCardTag.equals(qName) || promotionalCardTag.equals(qName)) {
                currentCard = greetingCardTag.equals(qName) ? new GreetingCard() : new PromotionalCard();
                currentCard.setId(attributes.getValue("id"));
                if (attributes.getValue("title") != null) {
                    currentCard.setTitle(attributes.getValue("title"));
                }
            } else {
                CardXmlTag temp = valueOf(qName.toUpperCase().replace(HYPHEN, UNDERSCORE));
                if (withText.contains(temp)) {
                    currentTag = temp;
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        String greetingCardTag = GREETING_CARD.getTagName();
        String promotionalCardTag = PROMOTIONAL_CARD.getTagName();
        if (greetingCardTag.equals(qName) || promotionalCardTag.equals(qName)) {
            postalCards.add(currentCard);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String data = new String(ch, start, length).trim();
        if (currentTag != null) {
            switch (currentTag) {
                case THEME -> currentCard.setTheme(ThemeType.valueOf(data.toUpperCase()));
                case ORIGIN_COUNTRY -> currentCard.setCountry(data);
                case YEAR -> currentCard.setYear(Year.parse(data));
                case AUTHOR -> currentCard.setAuthor(data);
                case SENT -> currentCard.setSent(Boolean.parseBoolean(data));
                case COUNTRY -> currentCard.getDestinationAddress().setCountry(data);
                case TOWN -> currentCard.getDestinationAddress().setTown(data);
                case STREET -> currentCard.getDestinationAddress().setStreet(data);
                case HOLIDAY -> {
                    GreetingCard greetingCard = (GreetingCard) currentCard;
                    greetingCard.setHoliday(HolidayType.valueOf(data.toUpperCase().replace(HYPHEN, UNDERSCORE)));
                }
                case COMPANY_NAME -> {
                    PromotionalCard promotionalCard = (PromotionalCard) currentCard;
                    promotionalCard.setCompanyName(data);
                }
                default -> throw new EnumConstantNotPresentException(currentTag.getDeclaringClass(), currentTag.name());
            }
            currentTag = null;
        }
    }
}
