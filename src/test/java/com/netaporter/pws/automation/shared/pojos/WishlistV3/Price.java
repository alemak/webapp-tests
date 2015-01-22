package com.netaporter.pws.automation.shared.pojos.WishlistV3;

/**
 * Equals overriden whilst the webapp still has rounding issues
 *
 * User: jgchristian
 * Date: 13/11/2013
 * Time: 16:13
 */
public class Price {

    private static final int MAX_PRICE_DIFFERENCE = 1;

    private String currencySymbol;
    private Double value;

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price price = (Price) o;

        if (currencySymbol != null ? !currencySymbol.equals(price.currencySymbol) : price.currencySymbol != null)
            return false;
        if (value != null ? !value.equals(price.value) : price.value != null) {
            // Values don't match
            // Awful hack to allow 1 cent/penny difference between prices
            // TODO Remove this once bugs on the website fixed
            if ((value != null) && (price.value != null)) {
                int v1 = (int) (Math.round(value * 100));
                int v2 = (int) (Math.round(price.value * 100));
                //System.out.println(v1 + "-" + v2 + "=" + (Math.abs(v1 - v2)));
                return (Math.abs(v1 - v2)) <= MAX_PRICE_DIFFERENCE;
            }

            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = currencySymbol != null ? currencySymbol.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }


    public static void main(String[] args) {
        Price a = new Price();
        a.setCurrencySymbol("£");
        a.setValue(321.71);

        Price b = new Price();
        b.setCurrencySymbol("£");
        b.setValue(321.72);

        Price c = new Price();
        c.setCurrencySymbol("£");
        c.setValue(321.73);

        System.out.println(a.equals(b)); //true
        System.out.println(a.equals(c)); //false
        System.out.println(c.equals(b)); //true
    }

}
