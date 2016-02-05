package cn.edu.bit.szw.bitunion.network;

/**
 * Created by szw on 16-2-5.
 */
public class BuError extends Exception{
    public static final int TYPE_SESSION = 1;
    public static final int TYPE_UNKNOWN = 2;

    private int type;
    private String msg;

    public BuError(int type, String msg) {
        super(msg);
        this.type = type;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
    public static BuError SESSION = new BuError(TYPE_SESSION, "session过期");

    public static BuError UNKNOWN = new BuError(TYPE_UNKNOWN, "unknown");
}
