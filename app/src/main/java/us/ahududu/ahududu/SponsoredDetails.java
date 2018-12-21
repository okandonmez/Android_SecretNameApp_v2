package us.ahududu.ahududu;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class SponsoredDetails extends AppCompatActivity {

    String sponsoredURL = "http://31.210.91.130/api/Sponsored/GetSponsoredEventsDetails?id=";
    int eventId;
    String strToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsored_details);
        getEventsId();
        getToken();
        getEventDetails();
    }

    private void getEventsId(){
        Bundle extras = getIntent().getExtras();
        eventId = extras.getInt("eventId");
        Toast.makeText(getApplicationContext(),eventId+"",Toast.LENGTH_LONG).show();
    }

    private void getToken(){
        SharedPreferences settings = getApplicationContext().getSharedPreferences("tokenizer", Context.MODE_PRIVATE);
        strToken = settings.getString("access_token", "no_token");
    }

    private void getEventDetails(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, sponsoredURL + Integer.toString(eventId) , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("GetEventsResponse", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("GetEventsErrorResponse", error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("Authorization","Bearer " + strToken);
                return param;
            }
        };

        requestQueue.add(stringRequest);
    }
}
