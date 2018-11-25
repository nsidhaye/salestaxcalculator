package com.nss.decorator;

import com.nss.vo.Item;
import com.nss.vo.ItemIF;

public class SalesTaxDecorator extends TaxDecorator {

    final double rate = 10;

    public SalesTaxDecorator(ItemIF item) {
        super(item);
    }

    @Override
    public double getRate() {
        return this.rate;
    }
}
