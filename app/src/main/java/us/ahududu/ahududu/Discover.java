package us.ahududu.ahududu;

import android.content.Context;

public class Discover {

    String  detail1;
    String  detail2;
    String  detail3;
    String  title;
    String  url;
    Context mContext;

    public Discover(String detail1, String detail2, String detail3, String title, String url, Context mContext) {
        super();
        this.title = title;
        this.url = url;
        this.detail1 = detail1;
        this.detail2 = detail2;
        this.detail3 = detail3;
        this.mContext = mContext;
    }
}
