package baremetrics;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Random;

public class Plan {
    String OID;
    String sourceID;
    String source;
    String name;
    String interval;
    int intervalCount;
    String trialDuration;
    String trialDurationUnit;
    String created;
    boolean active;
    ArrayList<Amount> amounts;

    public Plan withNewOID() {
        Random rand = new Random(this.hashCode());

        Integer OID = rand.nextInt();

        return this.withOID(OID.toString());
    }

    public Plan withOID(String OID) {
        this.OID = OID;

        return this;
    }

    public Plan withSourceID(String sourceID) {
        this.sourceID = sourceID;

        return this;
    }

    public Plan withSource(String source) {
        this.source = source;

        return this;
    }

    public Plan withName(String name) {
        this.name = name;

        return this;
    }

    public Plan withInterval(String interval) {
        this.interval = interval;

        return this;
    }

    public Plan withIntervalCount(int intervalCount) {
        this.intervalCount = intervalCount;

        return this;
    }

    public Plan withTrialDuration(String trialDuration) {
        this.trialDuration = trialDuration;

        return this;
    }

    public Plan withTrialDurationUnit(String trialDurationUnit) {
        this.trialDurationUnit = trialDurationUnit;

        return this;
    }

    public Plan withCreated(String created) {
        this.created = created;

        return this;
    }

    public Plan withActive(boolean active) {
        this.active = active;

        return this;
    }

    public Plan withAmounts(ArrayList<Amount> amounts) {
        this.amounts = amounts;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public int getIntervalCount() {
        return intervalCount;
    }

    public void setIntervalCount(int intervalCount) {
        this.intervalCount = intervalCount;
    }

    public String getTrialDuration() {
        return trialDuration;
    }

    public void setTrialDuration(String trialDuration) {
        this.trialDuration = trialDuration;
    }

    public String getTrialDurationUnit() {
        return trialDurationUnit;
    }

    public void setTrialDurationUnit(String trialDurationUnit) {
        this.trialDurationUnit = trialDurationUnit;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ArrayList<Amount> getAmounts() {
        return amounts;
    }

    public void setAmounts(ArrayList<Amount> amounts) {
        this.amounts = amounts;
    }

    public String getJson() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }
}
