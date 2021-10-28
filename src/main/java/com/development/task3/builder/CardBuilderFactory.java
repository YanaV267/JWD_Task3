package com.development.task3.builder;

import com.development.task3.exception.CardException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CardBuilderFactory {
    private static final Logger LOGGER = LogManager.getLogger();

    private enum TypeParser {
        DOM, SAX, STAX
    }

    private CardBuilderFactory() {
    }

    public static AbstractCardBuilder createBuilder(String typeParser) throws CardException {
        try {
            return switch (TypeParser.valueOf(typeParser.toUpperCase())) {
                case DOM -> new CardDomBuilder();
                case SAX -> new CardSaxBuilder();
                case STAX -> new CardStaxBuilder();
            };
        } catch (IllegalArgumentException exception) {
            LOGGER.error("Invalid type of parser was provided: " + typeParser.toUpperCase());
            throw new CardException("Invalid type of parser was provided: " + typeParser.toUpperCase(), exception);
        }
    }
}