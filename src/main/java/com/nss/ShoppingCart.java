package com.nss;

import com.nss.decorator.ImportTaxDecorator;
import com.nss.decorator.SalesTaxDecorator;
import com.nss.exception.ItemParsingException;
import com.nss.parser.ItemParser;
import com.nss.vo.Item;
import com.nss.vo.ItemIF;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ShoppingCart {

    private final Map<ItemIF, Integer> shoppingCart = new HashMap<>();

    DecimalFormat df = new DecimalFormat("###.00");

    public static void main(String[] args) {
        if(args.length!= 0) {
            for (String fileName: args)  {
                System.out.println(fileName);
                readFromFile(fileName);
            }

        }else {
            System.out.println("File Name should be give in argument...");
            System.out.println("Usage: java ShoppingCart Input1.txt");
        }
    }


    //TODO : We can move this to any utility
    public static void readFromFile(String fileName) {
        ShoppingCart shoppingCart = new ShoppingCart();
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String str;
            while ((str = in.readLine()) != null) {
                if (ItemParser.matches(str) && !str.isEmpty()) {
                    shoppingCart.put(ItemParser.parse(str), ItemParser.getCount(str));
                } else {
                    if (!str.isEmpty()) {
                        System.out.println("unknown line format: " + str);
                    }
                }
            }
            in.close();
        } catch (IOException e) {
            System.out.println("error:" + e.getMessage());
            return;
        } catch (ItemParsingException e1) {
            System.out.println("error:" + e1.getMessage());
        }
        shoppingCart.printInput();
        shoppingCart.printResults();
    }



    public void put(ItemIF item, int count) {
        if (item.isImported()) {
            item = new ImportTaxDecorator(item);
        }

        if (!item.isExempt()) {
            item = new SalesTaxDecorator(item);
        }

        //TODO if same line repeated then we should update count in Shopping Cart.
        this.shoppingCart.put(item, count);
    }

    public int getQuantity(ItemIF item) {
        return shoppingCart.get(item);
    }

    public double getTaxtotal() {
        double taxtotal = 0.0;
        for (ItemIF item : shoppingCart.keySet()) {
            double subTotal = item.getPrice().doubleValue() * (getQuantity(item));
            double subOriginalTotal = item.getOriginalPrice().doubleValue() * getQuantity(item);
            taxtotal += subTotal - subOriginalTotal;
        }
        return taxtotal;
    }

    public double getTotal() {
        double total = 0.0;
        for (ItemIF item : shoppingCart.keySet()) {
            double subTotal = item.getPrice().doubleValue() * (getQuantity(item));
            total += subTotal;
        }
        return total;
    }

    public void printInput() {
        System.out.println("Input: ");
        for (ItemIF item : shoppingCart.keySet()) {
            System.out.println(shoppingCart.get(item) + " " + item.getName() + " at " + df.format(item.getOriginalPrice()));
        }
        System.out.println();
    }

    public void printResults() {
        double taxtotal = 0;
        double total = 0;
        System.out.println("Output: ");
        Set<ItemIF> taxedItems = shoppingCart.keySet();
        for (ItemIF item : taxedItems) {
            double subTotal = item.getPrice().doubleValue() * getQuantity(item);
            double subInitTotal = item.getOriginalPrice().doubleValue() * getQuantity(item);
            taxtotal += subTotal - subInitTotal;
            total += subTotal;
            System.out.println(getQuantity(item) + " " + item.getName() + ": " + df.format(subTotal));
        }
        total = new BigDecimal(total).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println("Sales Taxes: " + df.format(taxtotal));
        System.out.println("Total: " + df.format(total));
        System.out.println();
    }


}
