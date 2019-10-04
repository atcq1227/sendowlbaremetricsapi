package baremetrics;

public class Amount {
    String currency;
    String symbol;
    String symbolRight;
    int amount;

    public String getCurrency() {
        return currency;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getSymbolRight() {
        return symbolRight;
    }

    public int getAmount() {
        return amount;
    }

    public Amount getDefaultAmount(int amount) {
        return this.withCurrency("USD")
                    .withSymbol("$")
                    .withSymbolRight(null)
                    .withAmount(amount);
    }

    public Amount withCurrency(String currency) {
        this.currency = currency;

        return this;
    }

    public Amount withSymbol(String symbol) {
        this.symbol = symbol;

        return this;
    }

    public Amount withSymbolRight(String symbolRight) {
        this.symbolRight = symbolRight;

        return this;
    }

    public Amount withAmount(int amount) {
        this.amount = amount;

        return this;
    }
}
