package com.development.task3.builder;

import com.development.task3.entity.*;
import com.development.task3.exception.CardException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Year;

import static com.development.task3.builder.CardXmlTag.*;

public class CardStaxBuilder extends AbstractCardBuilder {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final char UNDERSCORE = '_';
    private static final char HYPHEN = '-';

    private final XMLInputFactory inputFactory;

    public CardStaxBuilder() {
        inputFactory = XMLInputFactory.newInstance();
    }


    @Override
    public void buildSetCards(String filename) {
        try (FileInputStream inputStream = new FileInputStream(filename)) {
            XMLStreamReader reader = inputFactory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    String name = reader.getLocalName();
                    if (name.equals(CardXmlTag.GREETING_CARD.getTagName())
                            || name.equals(CardXmlTag.PROMOTIONAL_CARD.getTagName())) {
                        AbstractPostalCard postalCard = buildPostalCard(reader);
                        postalCards.add(postalCard);
                    }
                }
            }
        } catch (IOException exception) {
            LOGGER.error("Reading of file failed " + exception);
        } catch (XMLStreamException exception) {
            LOGGER.error("Reading of xml data failed " + exception);
        } catch (CardException exception) {
            LOGGER.error("Unknown tag " + exception);
        }
    }

    private AbstractPostalCard buildPostalCard(XMLStreamReader reader) throws XMLStreamException, CardException {
        AbstractPostalCard postalCard = reader.getLocalName().equals(GREETING_CARD.getTagName()) ? new GreetingCard() : new PromotionalCard();
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT -> getStartElement(reader, postalCard);
                case XMLStreamConstants.END_ELEMENT -> {
                    String name = reader.getLocalName();
                    if (name.equals(GREETING_CARD.getTagName())
                            || name.equals(PROMOTIONAL_CARD.getTagName())) {
                        return postalCard;
                    }
                }
                default -> throw new CardException("Unknown tag");
            }
        }
        return postalCard;
    }

    private void getStartElement(XMLStreamReader reader, AbstractPostalCard postalCard) throws XMLStreamException, CardException {
        String name = reader.getLocalName();
        String data = getXMLText(reader);
        CardXmlTag currentTag = CardXmlTag.valueOf(name.toUpperCase().replace(HYPHEN, UNDERSCORE));
        switch (currentTag) {
            case THEME -> postalCard.setThemeType(ThemeType.valueOf(data.toUpperCase()));
            case ORIGIN_COUNTRY -> postalCard.setCountry(data);
            case YEAR -> postalCard.setYear(Year.parse(data));
            case AUTHOR -> postalCard.setAuthor(data);
            case SENT -> postalCard.setSent(Boolean.parseBoolean(data));
            case COUNTRY -> getAddressFromXML(reader, postalCard.getDestinationAddress());
            case HOLIDAY -> {
                GreetingCard greetingCard = (GreetingCard) postalCard;
                greetingCard.setHolidayType(HolidayType.valueOf(data.toUpperCase().replace(HYPHEN, UNDERSCORE)));
                postalCard = greetingCard;
            }
            case COMPANY_NAME -> {
                PromotionalCard promotionalCard = (PromotionalCard) postalCard;
                promotionalCard.setCompanyName(data);
                postalCard = promotionalCard;
            }
            default -> throw new CardException("Unknown tag");
        }
    }

    private void getAddressFromXML(XMLStreamReader reader, Address address) throws XMLStreamException, CardException {
        while (reader.hasNext()) {
            int type = reader.next();
            String name = reader.getLocalName();
            String data = getXMLText(reader);
            switch (type) {
                case XMLStreamConstants.START_ELEMENT -> {
                    CardXmlTag currentTag = CardXmlTag.valueOf(name.toUpperCase().replace(HYPHEN, UNDERSCORE));
                    switch(currentTag) {
                        case COUNTRY -> address.setCountry(data);
                        case TOWN -> address.setTown(data);
                        case STREET -> address.setStreet(data);
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    if (name.equals(DESTINATION_ADDRESS.getTagName())) {
                        return;
                    }
                }
                default -> throw new CardException("Unknown tag");
            }
        }
    }

    private String getXMLText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}
