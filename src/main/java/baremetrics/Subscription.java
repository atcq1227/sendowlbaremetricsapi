package baremetrics;

import org.ietf.jgss.GSSException;
import org.ietf.jgss.Oid;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.Random;

public class Subscription {
    String OID;
    String sourceID;
    String source;
    Customer customer;
    String cancelledAt;
    String startedAt;
    boolean processed;
    String active;
    int quantity;
    int discount;
    Plan plan;

    public Subscription withNewOID() {
        Random rand = new Random(this.hashCode());

        Integer OID = Math.abs(rand.nextInt());

        System.out.println("New subscription OID: " + OID);

        return this.withOID(OID.toString());
    }

    public Subscription withOID(String OID) {
        this.OID = OID;

        return this;
    }

    public Subscription withActive(String active) {
        this.active = active;

        return this;
    }

    public Subscription withCustomer(Customer customer) {
        this.customer = customer;

        return this;
    }

    public Subscription startedNow() {
        Long timestamp = (System.currentTimeMillis() / 1000L);

        return this.withStartedAt(timestamp.toString());
    }

    private Subscription withStartedAt(String startedAt) {
        this.startedAt = startedAt;

        return this;
    }

    public Subscription withPlan(Plan plan) {
        this.plan = plan;

        return this;
    }

    public String getOID() {
        return OID;
    }

    public void setOID(String OID) {
        this.OID = OID;
    }

    public String getSourceID() {
        return sourceID;
    }

    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getCancelledAt() {
        return cancelledAt;
    }

    public void setCancelledAt(String cancelledAt) {
        this.cancelledAt = cancelledAt;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public String isActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
}
