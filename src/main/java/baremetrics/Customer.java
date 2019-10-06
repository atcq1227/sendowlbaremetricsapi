package baremetrics;

import com.google.gson.Gson;

import java.util.Date;
import java.util.Random;

public class Customer {
    private String OID;
    private String sourceID;
    private String source;
    private String created;
    private String email;
    private String name;
    private String displayImagePath;
    private String displayName;
    private String notes;
    private int LTV;
    private boolean isActive;
    private boolean isCancelled;
    private String currentMRR;
    private Plan currentPlan;

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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayImagePath() {
        return displayImagePath;
    }

    public void setDisplayImagePath(String displayImagePath) {
        this.displayImagePath = displayImagePath;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getLTV() {
        return LTV;
    }

    public void setLTV(int LTV) {
        this.LTV = LTV;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public String getCurrentMRR() {
        return currentMRR;
    }

    public void setCurrentMRR(String currentMRR) {
        this.currentMRR = currentMRR;
    }

    public Plan getCurrentPlan() {
        return currentPlan;
    }

    public void setCurrentPlan(Plan currentPlan) {
        this.currentPlan = currentPlan;
    }
    
    public Customer withOID(String OID) {
        this.OID = OID;

        return this;
    }

    public Customer withSourceID(String sourceID) {
        this.sourceID = sourceID;

        return this;
    }

    public Customer withSource(String source) {
        this.source = source;

        return this;
    }

    public Customer withCreated(String created) {
        this.created = created;

        return this;
    }

    public Customer withEmail(String email) {
        this.email = email;

        return this;
    }

    public Customer withName(String name) {
        this.name = name;

        return this;
    }

    public Customer withDisplayImagePath(String displayImagePath) {
        this.displayImagePath = displayImagePath;

        return this;
    }

    public Customer withDisplayName(String displayName) {
        this.displayName = displayName;

        return this;
    }

    public Customer withNotes(String notes) {
        this.notes = notes;

        return this;
    }

    public Customer withLTV(int LTV) {
        this.LTV = LTV;

        return this;
    }

    public Customer withActive(boolean active) {
        isActive = active;

        return this;
    }

    public Customer withCancelled(boolean cancelled) {
        isCancelled = cancelled;

        return this;
    }

    public Customer withCurrentMRR(String currentMRR) {
        this.currentMRR = currentMRR;

        return this;
    }

    public Customer withCurrentPlan(Plan currentPlan) {
        this.currentPlan = currentPlan;

        return this;
    }

    public Customer withNewOID() {
        Random rand = new Random(this.hashCode());

        Integer OID = rand.nextInt();

        return this.withOID(OID.toString());
    }

    public Customer createdNow() {
        Long timestamp = new Date().getTime();

        return this.withCreated(timestamp.toString());
    }

    public String getJson() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }
}
