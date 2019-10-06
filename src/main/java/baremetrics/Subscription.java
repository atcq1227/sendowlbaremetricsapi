package baremetrics;

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
    boolean active;
    int quantity;
    int discount;
    Plan plan;

    public Subscription withNewOID() {
        Random rand = new Random(this.hashCode());

        Integer OID = rand.nextInt();

        return this.withOID(OID.toString());
    }

    public Subscription withOID(String OID) {
        this.OID = OID;

        return this;
    }

    public Subscription withCustomer(Customer customer) {
        this.customer = customer;

        return this;
    }

    public Subscription startedNow() {
        Long timestamp = new Date().getTime();

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
}
