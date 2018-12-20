package us.ahududu.ahududu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tab1Fragment extends Fragment implements View.OnClickListener {
    final List<Event> events = new ArrayList<Event>();
    ListView lsFlow;
    EventAdapter eventAdapter;
    String eventsURL = "http://31.210.91.130/api/Activity/GetSpecializedActivity";
    String sponsoredURL = "http://31.210.91.130/api/Sponsored/GetSponsoredEvents";
    String strToken;
    int[] spnIDs = new int[4];

    ImageView imgSpn1, imgSpn2, imgSpn3, imgSpn4;
    ImageView[] imgSpns;

    ImageView imgCtg1, imgCtg2, imgCtg3, imgCtg4, imgCtg5, imgCtg6, imgCtg7, imgCtg8, imgCtg9,
            imgCtg10, imgCtg11, imgCtg12, imgCtg13, imgCtg14, imgCtg15;                             // Spagetti code but
                                                                                                    // deadline is so close

    TextView txtSpn1, txtSpn2, txtSpn3, txtSpn4;
    TextView[] txtSpns;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment,container,false);
        connectUI(view);

        getToken();
        getEvents();
        getSponsoredEvents();
        lsFlow = view.findViewById(R.id.lsLastAdded);

        setListViewHeightBasedOnChildren(lsFlow);

       /* events.add(new Event("deneme1","deneme1","deneme1","deneme1"));
        events.add(new Event("deneme2","deneme2","deneme2","deneme2"));
        events.add(new Event("deneme3","deneme3","deneme3","deneme3"));
        */

       /* Intent intent = new Intent(getActivity().getApplicationContext(), SponsoredDetails.class);
                intent.putExtra("eventId", id);
                startActivity(intent);
        */
        return view;
    }

    private void connectUI(View view){
        imgSpn1 = view.findViewById(R.id.spnImage1);
        imgSpn2 = view.findViewById(R.id.spnImage2);
        imgSpn3 = view.findViewById(R.id.spnImage3);
        imgSpn4 = view.findViewById(R.id.spnImage4);

        txtSpn1 = view.findViewById(R.id.txtSpn1);
        txtSpn2 = view.findViewById(R.id.txtSpn2);
        txtSpn3 = view.findViewById(R.id.txtSpn3);
        txtSpn4 = view.findViewById(R.id.txtSpn4);

        imgCtg1 = view.findViewById(R.id.ctgImage1);
        imgCtg2 = view.findViewById(R.id.ctgImage2);
        imgCtg3 = view.findViewById(R.id.ctgImage3);
        imgCtg4 = view.findViewById(R.id.ctgImage4);
        imgCtg5 = view.findViewById(R.id.ctgImage5);
        imgCtg6 = view.findViewById(R.id.ctgImage6);
        imgCtg7 = view.findViewById(R.id.ctgImage7);
        imgCtg8 = view.findViewById(R.id.ctgImage8);
        imgCtg9 = view.findViewById(R.id.ctgImage9);
        imgCtg10 = view.findViewById(R.id.ctgImage10);
        imgCtg11 = view.findViewById(R.id.ctgImage11);
        imgCtg12 = view.findViewById(R.id.ctgImage12);
        imgCtg13 = view.findViewById(R.id.ctgImage13);
        imgCtg14 = view.findViewById(R.id.ctgImage14);
        imgCtg15 = view.findViewById(R.id.ctgImage15);

        imgCtg1.setOnClickListener(this);
        imgCtg2.setOnClickListener(this);
        imgCtg3.setOnClickListener(this);
        imgCtg4.setOnClickListener(this);
        imgCtg5.setOnClickListener(this);
        imgCtg6.setOnClickListener(this);
        imgCtg7.setOnClickListener(this);
        imgCtg8.setOnClickListener(this);
        imgCtg9.setOnClickListener(this);
        imgCtg10.setOnClickListener(this);
        imgCtg11.setOnClickListener(this);
        imgCtg12.setOnClickListener(this);
        imgCtg13.setOnClickListener(this);
        imgCtg14.setOnClickListener(this);
        imgCtg15.setOnClickListener(this);
    }

    private void getSponsoredEvents(){
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        StringRequest jsonForGetRequest = new StringRequest(
                Request.Method.GET, sponsoredURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            imgSpns = new ImageView[jsonArray.length()];
                            imgSpns[0] = imgSpn1;
                            imgSpns[1] = imgSpn2;
                            imgSpns[2] = imgSpn3;
                            imgSpns[3] = imgSpn4;

                            for (int i = 0; i < jsonArray.length(); i++){
                                spnIDs[i] = jsonArray.getJSONObject(i).getInt("id");
                                Log.e("sponsored IDs", spnIDs[i]+"");
                                setSponsoredClicks(spnIDs[i], imgSpns[i]);
                            }

                            String [] url = new String[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++){
                                url[i] = jsonArray.getJSONObject(i).getString("url");
                            }


                            for (int i = 0; i < jsonArray.length(); i++){
                                Picasso.get().load(url[i]).into(imgSpns[i]);
                            }

                            txtSpn1.setText(jsonArray.getJSONObject(0).getString("name"));
                            txtSpn2.setText(jsonArray.getJSONObject(1).getString("name"));
                            txtSpn3.setText(jsonArray.getJSONObject(2).getString("name"));
                            txtSpn4.setText(jsonArray.getJSONObject(3).getString("name"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("Authorization","Bearer " + strToken);
                return param;
            }
        };

        queue.add(jsonForGetRequest);
    }

    private void setSponsoredClicks(final int id, ImageView img){
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), SponsoredDetails.class);
                intent.putExtra("eventId", id);
                startActivity(intent);
            }
        });
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, WindowManager.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void getEvents(){
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        StringRequest jsonForGetRequest = new StringRequest(
                Request.Method.GET, eventsURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response", response);
                        try {
                            final JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++){
                                if (jsonArray.getJSONObject(i).getInt("price") == 0){
                                    events.add(new Event("Ücretsiz",
                                            jsonArray.getJSONObject(i).getString("category"),
                                            jsonArray.getJSONObject(i).getString("name"),
                                            jsonArray.getJSONObject(i).getString("datediff"),
                                            jsonArray.getJSONObject(i).getString("url")));
                                }
                                else{
                                    events.add(new Event(jsonArray.getJSONObject(i).getString("price") + "  ₺",
                                            jsonArray.getJSONObject(i).getString("category"),
                                            jsonArray.getJSONObject(i).getString("name"),
                                            jsonArray.getJSONObject(i).getString("datediff"),
                                            jsonArray.getJSONObject(i).getString("url")));
                                    }
                            }
                            eventAdapter = new EventAdapter(getActivity(), events);
                            lsFlow.setAdapter(eventAdapter);

                            lsFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    try {
                                        String eventId = jsonArray.getJSONObject(position).getString("id");
                                        Intent intent = new Intent(getActivity().getApplicationContext(), EventDetails.class);
                                        intent.putExtra("eventId", eventId);
                                        startActivity(intent);
                                        Toast.makeText(getActivity(),jsonArray.getJSONObject(position).getString("id"),Toast.LENGTH_LONG).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("Authorization","Bearer " + strToken);
                return param;
            }
        };

        queue.add(jsonForGetRequest);
    }

    private void getToken(){
        SharedPreferences settings = getActivity().getApplicationContext().getSharedPreferences("tokenizer", Context.MODE_PRIVATE);
        strToken = settings.getString("access_token", "no_token");
    }

    private void getEventsByCtg(int ctgId){
        String id = Integer.toString(ctgId);
        Toast.makeText(getActivity().getApplicationContext(),id,Toast.LENGTH_LONG).show();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ctgImage1:
                getEventsByCtg(1);
                break;
            case R.id.ctgImage2:
                getEventsByCtg(2);
                break;
            case R.id.ctgImage3:
                getEventsByCtg(3);
                break;
            case R.id.ctgImage4:
                getEventsByCtg(4);
                break;
            case R.id.ctgImage5:
                getEventsByCtg(5);
                break;
            case R.id.ctgImage6:
                getEventsByCtg(6);
                break;
            case R.id.ctgImage7:
                getEventsByCtg(7);
                break;
            case R.id.ctgImage8:
                getEventsByCtg(8);
                break;
            case R.id.ctgImage9:
                getEventsByCtg(9);
                break;
            case R.id.ctgImage10:
                getEventsByCtg(10);
                break;
            case R.id.ctgImage11:
                getEventsByCtg(11);
                break;
            case R.id.ctgImage12:
                getEventsByCtg(12);
                break;
            case R.id.ctgImage13:
                getEventsByCtg(13);
                break;
            case R.id.ctgImage14:
                getEventsByCtg(14);
                break;
            case R.id.ctgImage15:
                getEventsByCtg(15);
                break;
        }

    }
}
