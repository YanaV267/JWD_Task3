package test.development.task3.builder;

import com.development.task3.builder.CardStaxBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CardStaxBuilderTest {
    private CardStaxBuilder cardStaxBuilder;

    @BeforeTest
    public void init() {
        cardStaxBuilder = new CardStaxBuilder();
    }

    @Test
    public void buildSetCards() {
        int expected = 16;
        cardStaxBuilder.buildSetCards("data/cards.xml");
        int actual = cardStaxBuilder.getPostalCards().size();
        Assert.assertEquals(actual, expected, "data reading from XML file is invalid");
    }
}
