package sendowl;

public abstract class Order {
    private String orderBody;

    public Order(String orderBody) {
        this.orderBody = orderBody;
    }
    

    public String getOrderBody() { 
        return orderBody;
    }

    public void setOrderBody(String orderBody) {
        this.orderBody = orderBody;
    }

    public abstract String getState();

    public abstract String getCurrency(); 

    public abstract boolean getForSubscription(); 

    public abstract String getProductName(); 

    public abstract String getPrice(); 

    public abstract String getRecurringPrice(); 

    public abstract String getFrequencyInterval(); 

    public abstract String getBuyerName(); 

    public abstract String getBuyerEmail(); 

    public abstract String getProductID();

    public abstract String getBackloadProductID();

    public abstract String getCompletedCheckoutAt();

    public abstract String getSettledGross();
}
