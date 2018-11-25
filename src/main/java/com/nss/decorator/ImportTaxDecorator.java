package com.nss.decorator;

import com.nss.vo.Item;
import com.nss.vo.ItemIF;

public class ImportTaxDecorator extends TaxDecorator {

    final double rate = 5;

    public ImportTaxDecorator(ItemIF item) {
        super(item);
    }

    @Override
    public double getRate() {
        return this.rate;
    }
}
