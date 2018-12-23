package us.ahududu.ahududu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;

public class DesignTools {
    Context mContext;
    public DesignTools(Context mContext){
        this.mContext = mContext;
    }
    public void setStatusBarColor (Activity activity, int colorPath) {            // Changing Status Bar Color
        Window window = activity.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(activity,colorPath));
    }

    public void closeKeyboard (Activity mActivity) {                            // Close the keyboard when focused to item
        try {
            // use application level context to avoid unnecessary leaks.
            InputMethodManager inputManager = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            assert inputManager != null;
            inputManager.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideProgressBar(ProgressBar pb){                         // Change visibility yo INVISIBLE for Progress Bar
        pb.setVisibility(View.INVISIBLE);
    }

    public String setDate(String date){
        String[] parts = date.split("T");
        String onlyDate = parts[0];

        String[] dateDetails = parts[0].split("-");

        Date dtDate = new Date(Integer.parseInt(dateDetails[0]),Integer.parseInt(dateDetails[1]),Integer.parseInt(dateDetails[2]));
        Calendar c = Calendar.getInstance();
        c.setTime(dtDate);

        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        Log.e("deneme",dayOfWeek+"");
        String lastDate = dateDetails[2] + "." + dateDetails[1] + "." + dateDetails[0];

        switch (dayOfWeek){
            case 1:
                lastDate = lastDate + " Çrşmb";
                break;
            case 2:
                lastDate = lastDate + " Prşmb";
                break;
            case 3:
                lastDate = lastDate + " Cuma";
                break;
            case 4:
                lastDate = lastDate + " Cmrts";
                break;
            case 5:
                lastDate = lastDate + " Pazar";
                break;
            case 6:
                lastDate = lastDate + " Pzrts";
                break;
            case 7:
                lastDate = lastDate + " Salı";
                break;
        }

        return lastDate;
    }

    public String setTime(String time){
        String[] parts = time.split(":");
        return parts[0] + "." + parts[1];
    }

    public Typeface getTypeFace (String path){
        return Typeface.createFromAsset(mContext.getAssets(), path);
    }
}
