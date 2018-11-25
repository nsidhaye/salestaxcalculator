package com.nss.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item implements ItemIF{

    private String name;

    private boolean imported = false;

    private boolean exempt = false;

    // Need to remove afterwords
    //private BigDecimal price = BigDecimal.ZERO;

    private BigDecimal originalPrice = BigDecimal.ZERO;

    public Item(Item anotherItem) {
        this.name = anotherItem.name;
        this.imported = anotherItem.imported;
        this.exempt = anotherItem.exempt;
        //this.price = anotherItem.price;
        this.originalPrice = anotherItem.originalPrice;
    }

    public String toString() {
        return this.name;
    }

    // This will execute in case of no import nor Sales Tax.
    public BigDecimal getPrice() {
        return originalPrice;
    }
}
