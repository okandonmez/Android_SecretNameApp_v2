package us.ahududu.ahududu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SetProfile extends AppCompatActivity {
    DesignTools designTools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);
        setStatusBar();
    }

    private void setStatusBar(){
        designTools = new DesignTools(getApplicationContext());
        designTools.setStatusBarColor(this, R.color.splashStatusBarColor);
    }
}
