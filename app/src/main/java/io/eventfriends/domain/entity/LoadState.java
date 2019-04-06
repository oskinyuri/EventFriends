package io.eventfriends.domain.entity;


public class LoadState {
    public enum Status{
        RUNNING,
        SUCCESS,
        FAILED
    }
    private final Status status;
    private final String msg;

    public static final LoadState LOADED;
    public static final LoadState LOADING;
    public static final LoadState FAILED;

    public LoadState(Status status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    static {
        LOADED = new LoadState(Status.SUCCESS,"Success");
        LOADING = new LoadState(Status.RUNNING,"Running");
        FAILED = new LoadState(Status.FAILED,"No internet connection");
    }

    public Status getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

}
