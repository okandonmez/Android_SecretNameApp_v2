package us.ahududu.ahududu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

import java.util.HashMap;
import java.util.Map;

public class EventDetails extends AppCompatActivity {

    String strName, strCategory, strURL, strTime, strDate, strPrice, strLocation, strCapacity, strDescription;

    String strToken;
    String eventId;
    String eventDetailsURL = "http://31.210.91.130/api/Activity/GetSpecActDetails?id=";

    DesignTools designTools;

    EditText edtSearch;

    ImageView imgEventDetail;
    TextView txtCategory, txtName, txtDate, txtPrice, txtTime, txtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        setSplashColor();
        setSearchBar();
        connectUI();
        getToken();
        getEventId();
        getEventDetails();
    }

    private void setSearchBar(){
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

    private void connectUI(){
        imgEventDetail = findViewById(R.id.imgEventDetail);
        txtCategory = findViewById(R.id.txtCategory);
        txtName = findViewById(R.id.txtName);
        txtDate = findViewById(R.id.txtDate);
        txtPrice = findViewById(R.id.txtPrice);
        txtTime = findViewById(R.id.txtTime);
        txtDescription = findViewById(R.id.txtDescription);
    }

    private void setSplashColor(){
        designTools = new DesignTools(getApplicationContext());
        designTools.setStatusBarColor(this, R.color.splashStatusBarColor);
    }

    private void getEventId(){
        Bundle extras = getIntent().getExtras();
        eventId = extras.getString("eventId");
        Log.e("eventId", eventId);
    }

    private void getToken(){
        SharedPreferences settings = getApplicationContext().getSharedPreferences("tokenizer", Context.MODE_PRIVATE);
        strToken = settings.getString("access_token", "no_token");
    }

    private void getEventDetails(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, eventDetailsURL + eventId , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("GetEventsResponse", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    strName = jsonObject.getString("Name");
                    strCategory = jsonObject.getString("Category");
                    strURL = jsonObject.getString("Url");
                    strDate = jsonObject.getString("Date");
                    strTime = jsonObject.getString("Time");
                    strPrice = jsonObject.getString("Price");
                    strLocation = jsonObject.getString("Location");
                    strCapacity = jsonObject.getString("Capacity");
                    strDescription = jsonObject.getString("Description");

                    if (Integer.parseInt(strPrice) == 0)
                        txtPrice.setText("Ücretsiz");
                    else
                        txtPrice.setText(strPrice + " ₺");

                    Picasso.get().load("http://" + strURL).into(imgEventDetail);
                    txtCategory.setText(strCategory);
                    txtName.setText(strName);
                    txtDate.setText(designTools.setDate(strDate));
                    txtTime.setText(designTools.setTime(strTime));
                    txtDescription.setText(strDescription);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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