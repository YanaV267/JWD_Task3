package test.development.task3.builder;

import com.development.task3.builder.CardDomBuilder;
import com.development.task3.entity.AbstractPostalCard;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CardDomBuilderTest {
    private CardDomBuilder cardDomBuilder;

    @BeforeTest
    public void init() {
        cardDomBuilder = new CardDomBuilder();
    }

    @Test
    public void buildSetCards() {
        int expected = 16;
        cardDomBuilder.buildSetCards("data/cards.xml");
        int actual = cardDomBuilder.getPostalCards().size();
        Assert.assertEquals(actual, expected, "data reading from XML file is invalid");
    }
}


