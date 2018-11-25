package com.nss.decorator;

import com.nss.vo.Item;
import com.nss.vo.ItemIF;
import lombok.Data;

import java.math.BigDecimal;

public abstract class TaxDecorator implements ItemIF {

    ItemIF item;

    protected double rate;

    TaxDecorator(ItemIF item) {
        //super(item);
        this.item = item;
    }


    public String getName() {
        return item.getName();
    }

    public BigDecimal getOriginalPrice(){
        return item.getOriginalPrice();
    }



    public boolean isImported() {
       return item.isImported();
    }

    public boolean isExempt() {
        return item.isExempt();
    }



    abstract double getRate();

    public BigDecimal getTax() {
        double tax = 0.0;

        double rate = getRate();

        if(item instanceof TaxDecorator) {
            TaxDecorator taxDecorator = (TaxDecorator) item;
            rate += taxDecorator.getRate();
        }

        if(getRate() > 0) {
            tax = item.getOriginalPrice().doubleValue() * (rate / 100);
        }
        return new BigDecimal(Math.ceil(tax * 20)/20).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getPrice() {
        BigDecimal tax = getOriginalPrice().add(getTax()).setScale(2, BigDecimal.ROUND_HALF_UP);
        return tax;
    }
}
