package com.development.task3.builder;

import com.development.task3.entity.*;
import com.development.task3.exception.CardException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
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
        try (FileInputStream inputStream = new FileInputStream(new File(getClass().getClassLoader().getResource(filename).toURI()))) {
            XMLStreamReader reader = inputFactory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    String name = reader.getLocalName();
                    if (name.equals(CardXmlTag.GREETING_CARD.getTagName()) || name.equals(CardXmlTag.PROMOTIONAL_CARD.getTagName())) {
                        AbstractPostalCard postalCard = buildPostalCard(reader);
                        postalCards.add(postalCard);
                    }
                }
            }
        } catch (IOException | URISyntaxException exception) {
            LOGGER.error("Reading of file failed. " + exception);
        } catch (XMLStreamException exception) {
            LOGGER.error("Reading of xml data failed. " + exception);
        } catch (CardException exception) {
            LOGGER.error("XML file contains unknown tag. " + exception);
        }
    }

    private AbstractPostalCard buildPostalCard(XMLStreamReader reader) throws XMLStreamException, CardException {
        AbstractPostalCard postalCard = reader.getLocalName().equals(GREETING_CARD.getTagName()) ? new GreetingCard() : new PromotionalCard();
        postalCard.setId(reader.getAttributeValue(null, "id"));
        if (reader.getAttributeValue(null, "title") != null) {
            postalCard.setTitle(reader.getAttributeValue(null, "title"));
        }
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
            }
        }
        return postalCard;
    }

    private void getStartElement(XMLStreamReader reader, AbstractPostalCard postalCard) throws XMLStreamException, CardException {
        String name = reader.getLocalName();
        String data = getXMLText(reader);
        CardXmlTag currentTag = CardXmlTag.valueOf(name.toUpperCase().replace(HYPHEN, UNDERSCORE));
        switch (currentTag) {
            case THEME -> postalCard.setTheme(ThemeType.valueOf(data.toUpperCase()));
            case ORIGIN_COUNTRY -> postalCard.setCountry(data);
            case YEAR -> postalCard.setYear(Year.parse(data));
            case AUTHOR -> postalCard.setAuthor(data);
            case SENT -> postalCard.setSent(Boolean.parseBoolean(data));
            case DESTINATION_ADDRESS -> getAddressFromXML(reader, postalCard.getDestinationAddress());
            case HOLIDAY -> {
                GreetingCard greetingCard = (GreetingCard) postalCard;
                greetingCard.setHoliday(HolidayType.valueOf(data.toUpperCase().replace(HYPHEN, UNDERSCORE)));
            }
            case COMPANY_NAME -> {
                PromotionalCard promotionalCard = (PromotionalCard) postalCard;
                promotionalCard.setCompanyName(data);
            }
            default -> {
                LOGGER.error("Unknown tag: " + currentTag);
                throw new CardException("Unknown tag: " + currentTag);
            }
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
                    switch (currentTag) {
                        case COUNTRY -> address.setCountry(data);
                        case TOWN -> address.setTown(data);
                        case STREET -> address.setStreet(data);
                        default -> {
                            LOGGER.error("Unknown tag: " + currentTag);
                            throw new CardException("Unknown tag: " + currentTag);
                        }
                    }
                }
                case XMLStreamConstants.END_ELEMENT -> {
                    if (name.equals(DESTINATION_ADDRESS.getTagName())) {
                        return;
                    }
                }
                default -> {
                    LOGGER.error("Unknown element: " + type);
                    throw new CardException("Unknown element: " + type);
                }
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
