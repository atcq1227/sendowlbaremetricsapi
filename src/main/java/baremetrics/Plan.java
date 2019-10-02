package baremetrics;

import java.util.ArrayList;

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
}
