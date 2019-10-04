package baremetrics;

import org.junit.Assert;
import org.junit.Test;

public class AmountTest {
    @Test
    public void testGetDefaultAmountCurrency() {
        Assert.assertEquals(new Amount().getDefaultAmount(10).getCurrency(), "USD");
    }

    @Test
    public void testGetDefaultAmountSymbol() {
        Assert.assertEquals(new Amount().getDefaultAmount(10).getSymbol(), "$");
    }

    @Test
    public void testGetDefaultAmountSymbolRight() {
        Assert.assertEquals(new Amount().getDefaultAmount(10).getSymbolRight(), null);
    }

    @Test
    public void testGetDefaultAmountIntAmount() {
        Assert.assertEquals(new Amount().getDefaultAmount(10).getAmount(),10);
    }
}
