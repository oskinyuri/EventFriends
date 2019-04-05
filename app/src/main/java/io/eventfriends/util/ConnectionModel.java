package io.eventfriends.util;

public class ConnectionModel {

    public static final int WIFI_DATA = 1;
    public static final int MOBILE_DATA = 2;

    private int type;
    private boolean isConnected;

    public ConnectionModel(int type, boolean isConnected) {
        this.type = type;
        this.isConnected = isConnected;
    }

    public int getType() {
        return type;
    }

    public boolean getIsConnected() {
        return isConnected;
    }
}
