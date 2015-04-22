package id.ac.ukdw.ti.yuanlukito.wifidatacollector.model;

/**
 * Created by Yuan Lukito on 22-Apr-15.
 */
public class LocationData {
    private Location location;
    private int morningCount;
    private int noonCount;
    private int afternoonCount;
    private boolean isComplete;

    public LocationData(Location location, int mc, int nc, int ac, boolean complete){
        this.setLocation(location);
        this.setMorningCount(mc);
        this.setNoonCount(nc);
        this.setAfternoonCount(ac);
        this.setComplete(complete);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getMorningCount() {
        return morningCount;
    }

    public void setMorningCount(int morningCount) {
        this.morningCount = morningCount;
    }

    public int getNoonCount() {
        return noonCount;
    }

    public void setNoonCount(int noonCount) {
        this.noonCount = noonCount;
    }

    public int getAfternoonCount() {
        return afternoonCount;
    }

    public void setAfternoonCount(int afternoonCount) {
        this.afternoonCount = afternoonCount;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }
}
