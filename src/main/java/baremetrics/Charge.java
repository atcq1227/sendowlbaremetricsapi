package baremetrics;

public class Charge {
    String OID;
    String sourceID;
    String source;
    String status;
    int created;
    Customer customer;
    String subscription;
    String amount;
    String currency;
    int originalAmount;
    int fee;
    int originalFee;
    String startedAt;
    String endedAt;

    public Charge startedNow() {
        Long timestamp = (System.currentTimeMillis() / 1000L);

        return this.withStartedAt(timestamp.toString());
    }

    public Charge endedNow() {
        Long timestamp = (System.currentTimeMillis() / 1000L);

        return this.withStartedAt(timestamp.toString());
    }
    
    public String getOID() {
        return OID;
    }

    public String getSourceID() {
        return sourceID;
    }

    public String getSource() {
        return source;
    }

    public String getStatus() {
        return status;
    }

    public int getCreated() {
        return created;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getSubscription() {
        return subscription;
    }

    public String getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public int getOriginalAmount() {
        return originalAmount;
    }

    public int getFee() {
        return fee;
    }

    public int getOriginalFee() {
        return originalFee;
    }
    
    public Charge withOID(String OID) {
        this.OID = OID;

        return this;
    }

    public Charge withSourceID(String sourceID) {
        this.sourceID = sourceID;

        return this;
    }

    public Charge withSource(String source) {
        this.source = source;

        return this;
    }

    public Charge withStatus(String status) {
        this.status = status;

        return this;
    }

    public Charge withCreated(int created) {
        this.created = created;

        return this;
    }

    public Charge withCustomer(Customer customer) {
        this.customer = customer;

        return this;
    }

    public Charge withSubscription(String subscription) {
        this.subscription = subscription;

        return this;
    }

    public Charge withAmount(String amount) {
        this.amount = amount;

        return this;
    }

    public Charge withCurrency(String currency) {
        this.currency = currency;

        return this;
    }

    public Charge withOriginalAmount(int originalAmount) {
        this.originalAmount = originalAmount;

        return this;
    }

    public Charge withFee(int fee) {
        this.fee = fee;

        return this;
    }

    public Charge withOriginalFee(int originalFee) {
        this.originalFee = originalFee;

        return this;
    }

    public Charge withStartedAt(String startedAt) {
        this.startedAt = startedAt;

        return this;
    }

    public Charge withEndedAt(String endedAt) {
        this.endedAt = endedAt;

        return this;
    }

}
