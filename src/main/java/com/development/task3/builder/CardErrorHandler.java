package com.development.task3.builder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class CardErrorHandler implements ErrorHandler {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void warning(SAXParseException exception) throws SAXException {
        LOGGER.warn(getErrorPosition(exception) + ": " + exception.getMessage());
    }

    @Override
    public void error(SAXParseException exception) throws SAXException {
        LOGGER.error(getErrorPosition(exception) + ": " + exception.getMessage());
    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {
        LOGGER.fatal(getErrorPosition(exception) + ": " + exception.getMessage());
    }

    private String getErrorPosition(SAXParseException exception) {
        return "line " + exception.getLineNumber() + " | column " + exception.getColumnNumber();
    }
}
