package us.ahududu.ahududu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Tab1Fragment extends Fragment {

    int[] images = {R.drawable.addeventad1, R.drawable.addeventad2, R.drawable.addeventad3};
    String[] names = {"Deneme1", "Deneme2", "Deneme3"};
    String[] categories = {"category1", "category2", "category3"};
    String[] prices = {"price1", "price2", "price3"};
    String[] remainingTimes = {"remainingTime1", "remainingTime2", "remainingTime3"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment,container,false);
        return view;
    }


}
