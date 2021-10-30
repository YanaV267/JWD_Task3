package test.development.task3.builder;

import com.development.task3.builder.AbstractCardBuilder;
import com.development.task3.builder.CardBuilderFactory;
import com.development.task3.exception.CardException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CardBuilderFactoryTest {
    private AbstractCardBuilder cardDomBuilder;
    private AbstractCardBuilder cardSaxBuilder;
    private AbstractCardBuilder cardStaxBuilder;

    @BeforeTest
    public void init() throws CardException {
        cardDomBuilder = CardBuilderFactory.createBuilder("dom");
        cardSaxBuilder = CardBuilderFactory.createBuilder("sax");
        cardStaxBuilder = CardBuilderFactory.createBuilder("stax");
    }

    @Test
    public void domBuildSetCards() {
        int expected = 16;
        cardDomBuilder.buildSetCards("data/cards.xml");
        int actual = cardDomBuilder.getPostalCards().size();
        Assert.assertEquals(actual, expected, "data reading with SAX builder is invalid");
    }

    @Test
    public void saxBuildSetCards() {
        int expected = 16;
        cardSaxBuilder.buildSetCards("data/cards.xml");
        int actual = cardSaxBuilder.getPostalCards().size();
        Assert.assertEquals(actual, expected, "data reading with SAX builder is invalid");
    }

    @Test
    public void staxBuildSetCards() {
        int expected = 16;
        cardStaxBuilder.buildSetCards("data/cards.xml");
        int actual = cardStaxBuilder.getPostalCards().size();
        Assert.assertEquals(actual, expected, "data reading with SAX builder is invalid");
    }
}
