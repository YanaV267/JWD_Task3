package com.development.task3.validator;

import com.development.task3.builder.CardErrorHandler;
import com.development.task3.exception.CardException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class CardSchemeValidator {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final CardSchemeValidator instance = new CardSchemeValidator();
    private static final String SCHEMA_NAME = "data/cards.xsd";

    private CardSchemeValidator() {
    }

    public static CardSchemeValidator getInstance() {
        return instance;
    }

    public boolean checkXMLFile(String filename) throws CardException {
        String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
        SchemaFactory factory = SchemaFactory.newInstance(language);
        String schemaPath = new File(SCHEMA_NAME).getPath();
        File schemaLocation = new File(schemaPath);
        try {
            Schema schema = factory.newSchema(schemaLocation);
            Validator validator = schema.newValidator();
            Source source = new StreamSource(filename);
            validator.setErrorHandler(new CardErrorHandler());
            validator.validate(source);
        } catch (SAXException exception) {
            LOGGER.warn("XML-file validation failed.", exception);
            return false;
        } catch (IOException exception) {
            LOGGER.error("File " + filename + " doesn't exist. ");
            throw new CardException("File " + filename + " doesn't exist. ", exception);
        }
        return true;
    }
}
