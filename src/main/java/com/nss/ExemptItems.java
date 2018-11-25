package com.nss;


public enum ExemptItems {
    BOOKS("books"),
    PILLS("pills"),
    CHOCOLATES("chocolates"),
    CHOCOLATE("chocolate bar");

    private String description;

    ExemptItems(String description) {
        this.description = description;
    }

    //TODO: Backed by Hashset for better performance
    public static boolean contains(final String exemptItem) {
        String itemDescription;

        if (exemptItem != null) {
            itemDescription = exemptItem.toLowerCase().trim();
            for (ExemptItems items : ExemptItems.values()) {
                if (items.description.contains(itemDescription)) {
                    return true;
                }
            }
        }

        return false;
    }

}
