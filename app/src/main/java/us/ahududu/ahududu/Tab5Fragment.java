package us.ahududu.ahududu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Tab5Fragment extends Fragment implements View.OnClickListener {
    TextView txtName, txtMotto, txtCtg1, txtCtg2, txtCtg3, txtCtg4, txtCtg5;
    String token, strName, strMotto;
    private static final String getProfileDetailsURL = "http://31.210.91.130/api/Account/GetUserInfos";
    private static final String getHobbiesURL = "http://31.210.91.130/api/Account/GetHobbies";
    private static final String getProfilePicURL = "http://31.210.91.130/api/Account/GetProfilePhoto";
    CircleImageView imgProfile;
    ImageView imgSetProfile;
    Button btnLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab5_fragment,container,false);
        connectUI(view);
        getToken();
        getProfilePic();
        getProfileDetails();
        getHobbies();
        return view;
    }

    private void getProfilePic(){
        GlideUrl glideUrl = new GlideUrl(getProfilePicURL, new LazyHeaders.Builder()
                .addHeader("Authorization", "Bearer "+token).build());

        Glide.with(getActivity()).load(glideUrl).into(imgProfile);

    }

    private void connectUI(View view){
        imgProfile = view.findViewById(R.id.profilePic);
        imgSetProfile = view.findViewById(R.id.imgSetProfile);
        txtName = view.findViewById(R.id.profileName);
        txtMotto = view.findViewById(R.id.profileMotto);

        txtCtg1 = view.findViewById(R.id.txtCtg1);
        txtCtg2 = view.findViewById(R.id.txtCtg2);
        txtCtg3 = view.findViewById(R.id.txtCtg3);
        txtCtg4 = view.findViewById(R.id.txtCtg4);
        txtCtg5 = view.findViewById(R.id.txtCtg5);

        btnLogout = view.findViewById(R.id.btnLogout);

        txtName.setText("deneme");
        imgSetProfile.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    private void getToken(){
        SharedPreferences settings = getActivity().getApplicationContext().getSharedPreferences("tokenizer", Context.MODE_PRIVATE);
        token = settings.getString("access_token", "no_token");
    }

    private void getProfileDetails(){
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        StringRequest jsonForGetRequest = new StringRequest(
                Request.Method.GET,getProfileDetailsURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            strName = jsonObject.getString("Name:");
                            strMotto = jsonObject.getString("Motto:");

                            txtName.setText(strName);
                            txtMotto.setText(strMotto);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Bir hata meydana geldi !", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("Authorization", "Bearer "+token);
                return param;
            }
        };
        queue.add(jsonForGetRequest);
    }

    private void getHobbies(){
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        StringRequest jsonForGetRequest = new StringRequest(
                Request.Method.GET,getHobbiesURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String responseHobbies = jsonObject.getString("Name:");
                            //Log.e("responseHobbies",responseHobbies);
                            String[] parts = responseHobbies.split(",");

                            txtCtg1.setText(parts[0]);
                            txtCtg2.setText(parts[1]);
                            txtCtg3.setText(parts[2]);
                            txtCtg4.setText(parts[3]);
                            txtCtg5.setText(parts[4]);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Bir hata meydana geldi !", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("Authorization", "Bearer "+token);
                return param;
            }
        };
        queue.add(jsonForGetRequest);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.imgSetProfile:
                Intent intent = new Intent(getActivity().getApplicationContext(), SetProfile.class);
                startActivity(intent);
                break;
            case R.id.btnLogout:
                falseRemember();
                break;
        }
    }

    private void falseRemember() {
        SharedPreferences settings = getActivity().getApplicationContext().getSharedPreferences("tokenizer", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("isRemember", false);
        editor.commit();
    }
}
