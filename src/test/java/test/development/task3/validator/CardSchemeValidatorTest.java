package test.development.task3.validator;

import com.development.task3.exception.CardException;
import com.development.task3.validator.CardSchemeValidator;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CardSchemeValidatorTest {

    @Test
    public void checkXMLFile() throws CardException {
        boolean actual = CardSchemeValidator.getInstance().checkXMLFile("data/cards.xml");
        Assert.assertTrue(actual, "validation of XML scheme failed.");
    }
}
