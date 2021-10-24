package com.development.task3.builder;

import com.development.task3.exception.CardException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CardBuilderFactory {
    private static final Logger LOGGER = LogManager.getLogger();

    private enum TypeParser{
        DOM, SAX,STAX
    }

    private CardBuilderFactory(){
    }

    public static AbstractCardBuilder createBuilder(String typeParser) throws CardException {
        TypeParser type = TypeParser.valueOf(typeParser.toUpperCase());
        switch (type){
            case DOM -> {
                return new CardDomBuilder();
            }
            case SAX -> {
                return new CardSaxBuilder();
            }
            case STAX -> {
                return new CardStaxBuilder();
            }
            default -> {
                LOGGER.error("Invalid type of parser was provided.");
                throw new CardException("Invalid type of parser was provided.");
            }
        }
    }
}