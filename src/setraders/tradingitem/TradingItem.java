
package setraders.tradingitem;

/**
 *
 * @author Josh Da Silva
 */
public abstract class TradingItem {

    private double itemPrice;

    public TradingItem() {
        this.itemPrice = 0;
    }

    public double getTradingItemPrice() {
        return itemPrice;
    }

    public void setTradingPrice(double price) {
        itemPrice = price;
    }



}