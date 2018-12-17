package us.ahududu.ahududu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class Tab3Fragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "tab3Fragment";
    CardView vwQuick, vwLocation, vwEvent, vwAdvanced;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3_fragment,container,false);
        connectUI(view);
        return view;
    }

    private void connectUI(View view){
        vwQuick = view.findViewById(R.id.cvQuick);
        vwLocation = view.findViewById(R.id.cvLocation);
        vwEvent = view.findViewById(R.id.cvEvent);
        vwAdvanced = view.findViewById(R.id.cvAdcanced);

        vwQuick.setOnClickListener(this);
        vwLocation.setOnClickListener(this);
        vwEvent.setOnClickListener(this);
        vwAdvanced.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.cvQuick:
                Intent intent2 = new Intent(getActivity().getApplicationContext(), AddQuickEvent.class);
                startActivity(intent2);
                break;
            case R.id.cvLocation:
                // Do sth
                break;
            case R.id.cvEvent:
                Intent intent = new Intent(getActivity().getApplicationContext(),addEvents.class);
                startActivity(intent);
                break;
            case R.id.cvAdcanced:
                // Do sth
                break;
        }
    }
}
