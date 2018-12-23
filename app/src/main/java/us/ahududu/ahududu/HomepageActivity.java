package us.ahududu.ahududu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class HomepageActivity extends AppCompatActivity {

    private SectionPageAdapter mSectionPageAdapter;
    Button btnDudula;
    ViewPager mViewPager;
    DesignTools designTools;
    Activity mActivity;
    EditText edtSearch;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        mActivity = this;
        connectUI();
        setTabLayout();
        designTools.setStatusBarColor(mActivity,R.color.splashStatusBarColor);
        sad();
    }

    private void sad(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void connectUI () {
        mSectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        btnDudula = findViewById(R.id.btnDudula);

        mViewPager = findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(4);
        setupViewPager(mViewPager);

        designTools = new DesignTools(getApplicationContext());

        edtSearch = findViewById(R.id.edtSearchEvent);
        edtSearch.setFocusable(false);
        edtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                overridePendingTransition(0,0);
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    private void setTabLayout(){
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.setTabTextColors(ColorStateList.valueOf(R.color.colorPrimary));

        tabLayout.getTabAt(0).setIcon(R.drawable.kafadengi);
        tabLayout.getTabAt(0).setText("Kafa Dengi");

        tabLayout.getTabAt(1).setIcon(R.drawable.nerdenevaricon);
        tabLayout.getTabAt(1).setText("Nerde Ne Var?");

        tabLayout.getTabAt(3).setIcon(R.drawable.notification);
        tabLayout.getTabAt(3).setText("Bildirimler");

        tabLayout.getTabAt(4).setIcon(R.drawable.avatar);
        tabLayout.getTabAt(4).setText("Profil");

        btnDudula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(2);
            }
        });

    }

    private void setupViewPager(ViewPager viewPager){
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(),"TAB1");
        adapter.addFragment(new Tab2Fragment(),"TAB2");
        adapter.addFragment(new Tab3Fragment(),"TAB3");
        adapter.addFragment(new Tab4Fragment(),"TAB4");
        adapter.addFragment(new Tab5Fragment(), "TAB5");

        viewPager.setAdapter(adapter);
    }

}
