package us.ahududu.ahududu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Tab2Fragment extends Fragment {
    private static final String TAG = "tab2Fragment";

    final List<Discover> events = new ArrayList<Discover>();
    ListView lsFlow;
    DiscoverAdapter discoverAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_fragment,container,false);
        connectUI(view);
        setDiscoveryPage();
        return view;
    }

    private void connectUI(View view){
        lsFlow = view.findViewById(R.id.lsDiscover);
    }

    private void setDiscoveryPage(){
        String title1 = "Şirketlerde Aile Anayasası";
        String title2 = "İngilizce Konuşma Kulübü";
        String title3 = "DIY Takı Kursu";

        String detail = "Ahududu'ya özel %20 indirim";
        String[] details = setDetail(detail);
        events.add(new Discover(details[0], details[2], details[3], title1,"http://31.210.91.130/ActivityPhoto/18.jpg"));

        detail = "Ahududu'ya özel %10 indirim";
        details = setDetail(detail);
        events.add(new Discover(details[0], details[2], details[3], title2,"http://31.210.91.130/ActivityPhoto/41.jpg"));

        detail = "Ahududu'ya özel %30 indirim";
        details = setDetail(detail);
        events.add(new Discover(details[0], details[2], details[3], title3,"http://31.210.91.130/ActivityPhoto/43.jpg"));

        discoverAdapter = new DiscoverAdapter(getActivity(), events);
        lsFlow.setAdapter(discoverAdapter);
    }

    private String[] setDetail(String text){
        String[] detailParts =  text.split(" ");
        detailParts[0] = detailParts[0] + " " + detailParts[1];

        return detailParts;
    }
}
