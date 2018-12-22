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

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SponsoredDetails extends AppCompatActivity {

    String strUrl, strName, strCategory, strTime, strDate, strPrice, strLocation, strCapacity, strDescription;
    String sponsoredURL = "http://31.210.91.130/api/Sponsored/GetSponsoredEventsDetails?id=";
    int eventId;
    String strToken;

    ImageView imgTitle;
    TextView txtCategory, txtName, txtDate, txtPrice, txtTime, txtDescription;

    DesignTools designTools;
    EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsored_details);
        connectUI();
        getToken();
        getEventsId();
        getEventDetails();
    }

    private void connectUI(){
        imgTitle = findViewById(R.id.imgSponsored);
        txtCategory = findViewById(R.id.txtCategory);
        txtName = findViewById(R.id.txtName);
        txtDate = findViewById(R.id.txtDate);
        txtPrice = findViewById(R.id.txtPrice);
        txtTime = findViewById(R.id.txtTime);
        txtDescription = findViewById(R.id.txtDescription);

        designTools = new DesignTools();
        designTools.setStatusBarColor(this, R.color.splashStatusBarColor);

        setSearchBar();
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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, sponsoredURL + eventId , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    strUrl = jsonObject.getString("url");
                    strName = jsonObject.getString("name");
                    strCategory = jsonObject.getString("category");
                    strTime = jsonObject.getString("time");
                    strDate = jsonObject.getString("date");
                    strPrice = jsonObject.getString("price");
                    strLocation = jsonObject.getString("location");
                    strCapacity = jsonObject.getString("capacity");
                    strDescription = jsonObject.getString("description");

                    Picasso.get().load(strUrl).into(imgTitle);
                    txtCategory.setText(strCategory);
                    txtName.setText(strName);
                    txtDate.setText(setDate(strDate));
                    txtTime.setText(setTime(strTime));
                    txtDescription.setText(strDescription);
                    if (Integer.parseInt(strPrice) == 0)
                        txtPrice.setText(getResources().getString(R.string.free));
                    else
                        txtPrice.setText(strPrice + " ₺");

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

    private String setTime(String time){
        String[] parts = time.split(":");
        return parts[0] + "." + parts[1];
    }

    private String setDate(String date){
        String[] parts = date.split("T");
        String onlyDate = parts[0];

        String[] dateDetails = parts[0].split("-");

        Date dtDate = new Date(Integer.parseInt(dateDetails[0]),Integer.parseInt(dateDetails[1]),Integer.parseInt(dateDetails[2]));
        Calendar c = Calendar.getInstance();
        c.setTime(dtDate);

        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        Log.e("deneme",dayOfWeek+"");
        String lastDate = dateDetails[2] + "." + dateDetails[1] + "." + dateDetails[0];

        switch (dayOfWeek){
            case 1:
                lastDate = lastDate + " Çarşamba";
                break;
            case 2:
                lastDate = lastDate + " Perşembe";
                break;
            case 3:
                lastDate = lastDate + " Cuma";
                break;
            case 4:
                lastDate = lastDate + " Cumartesi";
                break;
            case 5:
                lastDate = lastDate + " Pazar";
                break;
            case 6:
                lastDate = lastDate + " Pazartesi";
                break;
            case 7:
                lastDate = lastDate + " Salı";
                break;
        }

        return lastDate;
    }
}
