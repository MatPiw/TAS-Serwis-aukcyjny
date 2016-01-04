package pl.edu.amu.rest.model;

import java.math.BigDecimal;

/**
 * Created by Altenfrost on 2016-01-02.
 */
public class Prices {
    private BigDecimal best_price;
    private BigDecimal minimal_price;
    private BigDecimal buy_now_price;
    private String currency;

    public BigDecimal getBest_price() {
        return best_price;
    }

    public void setBest_price(BigDecimal best_price) {
        this.best_price = best_price;
    }

    public BigDecimal getMinimal_price() {
        return minimal_price;
    }

    public void setMinimal_price(BigDecimal minimal_price) {
        this.minimal_price = minimal_price;
    }

    public BigDecimal getBuy_now_price() {
        return buy_now_price;
    }

    public void setBuy_now_price(BigDecimal buy_now_price) {
        this.buy_now_price = buy_now_price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Prices(BigDecimal best_price, BigDecimal minimal_price, BigDecimal buy_now_price, String currency) {
        this.best_price = best_price;
        this.minimal_price = minimal_price;
        this.buy_now_price = buy_now_price;

        this.currency = (currency==null)?"PLN":currency;
    }

    @Override
    public String toString() {
        return "Prices{" +
                "best_price=" + best_price +
                ", minimal_price=" + minimal_price +
                ", buy_now_price=" + buy_now_price +
                ", currency='" + currency + '\'' +
                '}';
    }

    public Prices(BigDecimal buy_now_price, String currency) {

        this.buy_now_price = buy_now_price;
        this.currency = currency;
    }

    public Prices(BigDecimal minimal_price, String currency, BigDecimal buy_now_price) {

        this.minimal_price = minimal_price;
        this.currency = currency;
        this.buy_now_price = buy_now_price;
    }

    public Prices() {
    }
}
