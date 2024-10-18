package nws.mc.cores.amlib.config.the_world;

public class TheWorldConfigData {
    private boolean enable;
    private int type;
    private int stopTime;

    public TheWorldConfigData(boolean enable, int type, int stopTime) {
        this.enable = enable;
        this.type = type;
        this.stopTime = stopTime;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isEnable() {
        return enable;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setStopTime(int stopTime) {
        this.stopTime = stopTime;
    }

    public int getStopTime() {
        return stopTime;
    }
}
