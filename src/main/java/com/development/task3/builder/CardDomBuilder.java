package com.development.task3.builder;

import com.development.task3.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.Year;

import static com.development.task3.builder.CardXmlTag.*;

public class CardDomBuilder extends AbstractCardBuilder {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final char UNDERSCORE = '_';
    private static final char HYPHEN = '-';

    private DocumentBuilder documentBuilder;

    public CardDomBuilder() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException exception) {
            LOGGER.error("Parser configuration failed. " + exception);
        }
    }

    @Override
    public void buildSetCards(String filename) {
        try {
            Document document = documentBuilder.parse(getClass().getClassLoader().getResource(filename).getPath());
            Element root = document.getDocumentElement();
            NodeList cardList = root.getElementsByTagName(GREETING_CARD.getTagName());
            for (int i = 0; i < cardList.getLength(); i++) {
                Element cardElement = (Element) cardList.item(i);
                AbstractPostalCard postalCard = buildCard(cardElement);
                postalCards.add(postalCard);
            }
            cardList = root.getElementsByTagName(PROMOTIONAL_CARD.getTagName());
            for (int i = 0; i < cardList.getLength(); i++) {
                Element cardElement = (Element) cardList.item(i);
                AbstractPostalCard postalCard = buildCard(cardElement);
                postalCards.add(postalCard);
            }
        } catch (SAXException exception) {
            LOGGER.error("File parsing failed. " + exception);
        } catch (IOException exception) {
            LOGGER.error("Reading of file failed. " + exception);
        }
    }

    public AbstractPostalCard buildCard(Element cardElement) {
        AbstractPostalCard postalCard = cardElement.getTagName().equals(GREETING_CARD.getTagName()) ? new GreetingCard() : new PromotionalCard();

        postalCard.setId(cardElement.getAttribute("id"));
        if (cardElement.getAttribute("title") != null) {
            postalCard.setTitle(cardElement.getAttribute("title"));
        }

        String data = getElementTextContent(cardElement, THEME.getTagName());
        postalCard.setTheme(ThemeType.valueOf(data.toUpperCase()));
        data = getElementTextContent(cardElement, ORIGIN_COUNTRY.getTagName());
        postalCard.setCountry(data);
        data = getElementTextContent(cardElement, YEAR.getTagName());
        postalCard.setYear(Year.parse(data));
        data = getElementTextContent(cardElement, AUTHOR.getTagName());
        postalCard.setAuthor(data);
        data = getElementTextContent(cardElement, SENT.getTagName());
        postalCard.setSent(Boolean.parseBoolean(data));

        Address address = postalCard.getDestinationAddress();
        data = getElementTextContent(cardElement, COUNTRY.getTagName());
        address.setCountry(data);
        data = getElementTextContent(cardElement, STREET.getTagName());
        address.setStreet(data);
        data = getElementTextContent(cardElement, TOWN.getTagName());
        address.setTown(data);

        if (postalCard instanceof GreetingCard greetingCard) {
            data = getElementTextContent(cardElement, HOLIDAY.getTagName());
            greetingCard.setHoliday(HolidayType.valueOf(data.toUpperCase().replace(HYPHEN, UNDERSCORE)));
        } else {
            data = getElementTextContent(cardElement, COMPANY_NAME.getTagName());
            PromotionalCard promotionalCard = (PromotionalCard) postalCard;
            promotionalCard.setCompanyName(data);
        }
        return postalCard;
    }

    private static String getElementTextContent(Element element, String elementName) {
        NodeList nList = element.getElementsByTagName(elementName);
        Node node = nList.item(0);
        return node.getTextContent();
    }
}


