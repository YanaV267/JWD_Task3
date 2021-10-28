package test.development.task3.builder;

import com.development.task3.builder.CardSaxBuilder;
import com.development.task3.entity.AbstractPostalCard;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CardSaxBuilderTest {
    private CardSaxBuilder cardSaxBuilder;

    @BeforeTest
    public void init() {
        cardSaxBuilder = new CardSaxBuilder();
    }

    @Test
    public void buildSetCards() {
        int expected = 16;
        cardSaxBuilder.buildSetCards("data/cards.xml");
        int actual = cardSaxBuilder.getPostalCards().size();
        Assert.assertEquals(actual, expected, "data reading from XML file is invalid");
    }
}
