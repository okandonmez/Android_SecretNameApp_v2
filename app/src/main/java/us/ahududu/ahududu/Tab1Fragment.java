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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tab1Fragment extends Fragment {
    final List<Event> events = new ArrayList<Event>();
    ListView lsFlow;
    EventAdapter eventAdapter;
    String eventsURL = "http://31.210.91.130/api/Activity/GetSpecializedActivity";
    String strToken;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment,container,false);
        getToken();
        getEvents();
        lsFlow = view.findViewById(R.id.lsLastAdded);

        setListViewHeightBasedOnChildren(lsFlow);

       /* events.add(new Event("deneme1","deneme1","deneme1","deneme1"));
        events.add(new Event("deneme2","deneme2","deneme2","deneme2"));
        events.add(new Event("deneme3","deneme3","deneme3","deneme3"));

        */

        return view;
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


}
