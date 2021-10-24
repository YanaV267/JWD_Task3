package com.development.task3.builder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class CardSaxBuilder extends AbstractCardBuilder{
    private static final Logger LOGGER = LogManager.getLogger();
    private final SAXParserFactory factory;

    public CardSaxBuilder(){
        factory = SAXParserFactory.newInstance();
    }

    @Override
    public void buildSetCards(String filename){
        try {
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            CardHandler handler = new CardHandler();
            reader.setContentHandler(handler);
            reader.setErrorHandler(new CardErrorHandler());
            reader.parse(filename);
            postalCards = handler.getPostalCards();
        } catch (ParserConfigurationException exception) {
            LOGGER.error("File parser configuration failed" + exception);
        } catch (SAXException exception) {
            LOGGER.error("File parser configuration failed" + exception);
        } catch (IOException exception) {
            LOGGER.error("Reading of file failed" + exception);
        }
    }
}
