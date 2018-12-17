package us.ahududu.ahududu;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {
    DesignTools designTools;
    Activity mActivity = this;
    EditText edtSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        designTools = new DesignTools();
        designTools.setStatusBarColor(mActivity,R.color.splashStatusBarColor);
        edtSearch = findViewById(R.id.edtSearchEvent);
        edtSearch.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}
