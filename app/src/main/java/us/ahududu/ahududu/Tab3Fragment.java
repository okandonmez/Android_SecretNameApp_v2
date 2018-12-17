package us.ahududu.ahududu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class Tab3Fragment extends Fragment {
    private static final String TAG = "tab3Fragment";
    View vwQuick, vwLocation, vwEvent, vwAdvanced;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3_fragment,container,false);
       // connectUI(view);
        return view;
    }
/*
    private void connectUI(View view){
        vwQuick = view.findViewById(R.id.dudulaQuickEvent);
        vwLocation = view.findViewById(R.id.dudulaSearchByLocation);
        vwEvent = view.findViewById(R.id.dudulaEvent);
        vwAdvanced = view.findViewById(R.id.dudulaSearchAdvanced);

        vwQuick.setOnClickListener(this);
        vwLocation.setOnClickListener(this);
        vwEvent.setOnClickListener(this);
        vwAdvanced.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.dudulaQuickEvent:
                Intent intent2 = new Intent(getActivity().getApplicationContext(), AddQuickEvent.class);
                startActivity(intent2);
                break;
            case R.id.dudulaSearchByLocation:
                // Do sth
                break;
            case R.id.dudulaEvent:
                Intent intent = new Intent(getActivity().getApplicationContext(),addEvents.class);
                startActivity(intent);
                break;
            case R.id.dudulaSearchAdvanced:
                // Do sth
                break;
        }
    } */
}
