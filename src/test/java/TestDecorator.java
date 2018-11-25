import com.nss.decorator.ImportTaxDecorator;
import com.nss.decorator.SalesTaxDecorator;
import com.nss.decorator.TaxDecorator;
import com.nss.vo.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class TestDecorator {

    @Test
    public void testSalesTaxCalculation() {
        Item item = new Item();
        item.setName("TestItem");
        item.setOriginalPrice(new BigDecimal(11.11));


        SalesTaxDecorator salesTaxDecorator = new SalesTaxDecorator(item);

        BigDecimal tax = salesTaxDecorator.getTax();

        //With 0.05 round up
        Assertions.assertEquals(new BigDecimal("1.15"), tax, "Tax....");
    }

    @Test
    public void testImportTaxDecorator() {
        Item item = new Item();
        item.setName("TestItem");
        item.setOriginalPrice(new BigDecimal(11.11));

        TaxDecorator importTax = new ImportTaxDecorator(item);

        BigDecimal tax = importTax.getTax();


        //Nearest .05 round up
        Assertions.assertEquals(new BigDecimal("0.60"), tax, "Tax....");
    }

    @Test
    public void testBothTaxDecorators() {
        Item item = new Item();
        item.setName("TestItem");
        item.setOriginalPrice(new BigDecimal(11.11));

        TaxDecorator importTax = new ImportTaxDecorator(item);

        TaxDecorator salesTax = new SalesTaxDecorator(importTax);

        BigDecimal tax = importTax.getTax();

        //Nearest .05 round up
        Assertions.assertEquals(new BigDecimal("0.60"), tax, "Tax....");


        //With Sales + Import
        tax = salesTax.getTax();
        Assertions.assertEquals(new BigDecimal("1.70"), tax, "Tax....");
    }


}
