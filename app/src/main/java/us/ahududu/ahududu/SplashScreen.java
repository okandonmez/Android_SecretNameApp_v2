package us.ahududu.ahududu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class SplashScreen extends AppCompatActivity {

    private Activity activity = this;
    private DesignTools designTools;
    String username;
    String password;
    Boolean isRemember;

    String tokenURL = "http://31.210.91.130/token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getRememberStatus();
        designTools = new DesignTools(getApplicationContext());
        designTools.setStatusBarColor(activity,R.color.splashStatusBarColor);
    }

    // Go to login page
    private void setSplashLogic(int splashTime){    // Splash time is milisecond
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashScreen.this,LoginActivity.class);
                startActivity(mainIntent);
                SplashScreen.this.finish();
            }
        }, splashTime);
    }

    // Check Remember me is checked or not
    private void getRememberStatus(){
        SharedPreferences settings = getApplicationContext().getSharedPreferences("tokenizer", Context.MODE_PRIVATE);
        isRemember = settings.getBoolean("isRemember", false);
        username = settings.getString("username", "1");
        password = settings.getString("password", "1");
        if (isRemember){
            loginRequest();
        }
        else {
            setSplashLogic(1500);
        }
    }

    private void loginRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest jsonForGetRequest = new StringRequest(
                Request.Method.POST,tokenURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsBodyResponse = new JSONObject(response);
                            String accessToken = jsBodyResponse.getString("access_token");
                            int numberOfLogin = jsBodyResponse.getInt("numberOfLogin");
                            numberOfLogin--;
                            Intent intent;

                            if (numberOfLogin == 0){
                                Log.d("numberOfLogin :", numberOfLogin + "");
                                intent = new Intent(getApplicationContext(), FirstLogin.class);
                            }else{
                                Log.d("numberOfLogin :", numberOfLogin + "");
                                intent = new Intent(getApplicationContext(), HomepageActivity.class);
                            }
                            storeToToken(accessToken);
                            startActivity(intent);
                            SplashScreen.this.finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    parseVolleyError(error);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("grant_type","password");
                param.put("username",username);
                param.put("password",password);

                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("Content-Type","application/x-www-form-urlencoded");
                return param;
            }
        };

        queue.add(jsonForGetRequest);
    }

    private void storeToToken(String token){
        SharedPreferences settings = getApplicationContext().getSharedPreferences("tokenizer", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("access_token", token);
        editor.commit();
    }

    private void parseVolleyError(VolleyError error) throws UnsupportedEncodingException, JSONException {
        try{
            String responseBody = new String(error.networkResponse.data, "utf-8");
            JSONObject jsResponseBody = new JSONObject(responseBody);
            String errorDescription = jsResponseBody.getString("error_description");
            if (errorDescription.equals("The user name or password is incorrect.")) {
                Toast.makeText(getApplicationContext(), "Kullanıcı adı veya şifre hatalı", Toast.LENGTH_LONG).show();
            } else if (errorDescription.equals("You need to confirm email")) {
                Toast.makeText(getApplicationContext(), "Eposta adresinizden hesabınızı onaylamalısınız", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Server ayağa kaldırılıyor",Toast.LENGTH_LONG).show();
        }
    }
}
