package com.nss.vo;

import java.math.BigDecimal;

public interface ItemIF {
    String getName();

    BigDecimal getOriginalPrice();

    BigDecimal getPrice();

    boolean isImported();

    boolean isExempt();
}
