package com.nss.parser;

import com.nss.ExemptItems;
import com.nss.exception.ItemParsingException;
import com.nss.vo.Item;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemParser {

    private static final String REGEX = "(\\d+)\\s((\\w+\\s)+)at\\s(\\d+.\\d+)";

    private static Pattern pattern = Pattern.compile(REGEX);

    public static Item parse(String input) throws ItemParsingException {
        Matcher matcher = pattern.matcher(input);

        matcher.matches();

        String name = matcher.group(2).trim();
        String type = matcher.group(3).trim();

        String price = matcher.group(4).trim();

        if (name == null) {
            throw new ItemParsingException("Item name should be present");
        }

        Item item = new Item();
        item.setName(name);
        if (name.contains("imported")) {
            item.setImported(true);
        }

        if (ExemptItems.contains(type) || ExemptItems.contains(name)) {
            item.setExempt(true);
        }

        //May be we can use DecimalFormat to parse BigDecimal...
        item.setOriginalPrice(new BigDecimal(price));

        return item;
    }

    public static int getCount(String input) throws ItemParsingException {
        Matcher matcher = pattern.matcher(input);
        matcher.matches();
        String count = matcher.group(1).trim();

        if (count == null || "".equals(count)) {
            throw new ItemParsingException("Invalid count of item...");
        }

        return Integer.valueOf(count);
    }

    public static boolean matches(String description) {
        return Pattern.matches(REGEX, description);
    }
}
