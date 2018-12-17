package us.ahududu.ahududu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    DesignTools designTools;
    Activity activity;

    EditText edtUsername, edtPassword;
    Button btnGoLogin, btnGoRegister;
    ProgressBar pbLogin;

    Map<String, String> loginInfos;

    String tokenURL = "http://31.210.91.130/token";

    Switch swRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        activity = this;

        designTools = new DesignTools();
        designTools.setStatusBarColor(activity,R.color.loginStatusBarColor);

        connectUI(); // connecting edittext and passwords to backend
    }

    private void connectUI (){
        edtUsername = findViewById(R.id.edtUsernameLogin);
        edtPassword = findViewById(R.id.edtPasswordLogin);
        swRemember = findViewById(R.id.swRemember);

        setEditTexts();

        btnGoLogin = findViewById(R.id.btnGoLogin);
        btnGoLogin.setOnClickListener(this);

        btnGoRegister = findViewById(R.id.btnGoRegister);
        btnGoRegister.setOnClickListener(this);

        pbLogin = findViewById(R.id.pbLogin);
    }

    @Override
    public void onClick(View view) {
        int itemId = view.getId();
        switch (itemId) {
            case R.id.btnGoLogin :
                clickedLogin();
                break;
            case R.id.btnGoRegister :
                clickedRegister();
                break;
            default:

        }

    }

    private void clickedLogin(){

         loginInfos = new HashMap<>();
         loginInfos.put("username",edtUsername.getText().toString());
         loginInfos.put("password",edtPassword.getText().toString());

        if (checkLoginFields()){
            loginRequest(loginInfos);
        }
        else {
            return;
        }
    }

    private boolean checkLoginFields() {
        if (loginInfos.get("username").toString().length() > 3)
            if (loginInfos.get("password").toString().length() > 3)
                return true;

        if (loginInfos.get("username").toString().length() <= 3){
            edtUsername.setError("Kullanıcı adı alanını doldurunuz.");
            return false;
        }

        if (loginInfos.get("password").toString().length() <= 3) {
            edtPassword.setError("Şifre alanını doldurunuz.");
            return false;
        }

        return true;
    }

    private void loginRequest (Map<String, String> loginInfos) {
        pbLogin.setVisibility(View.VISIBLE);
        final String strUsername = loginInfos.get("username");
        final String strPassword = loginInfos.get("password");

        Log.e("username:",strUsername);
        Log.e("password",strPassword);

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest jsonForGetRequest = new StringRequest(
                Request.Method.POST,tokenURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            designTools.hideProgressBar(pbLogin);
                            JSONObject jsBodyResponse = new JSONObject(response);
                            String accessToken = jsBodyResponse.getString("access_token");
                            int numberOfLogin = jsBodyResponse.getInt("numberOfLogin");
                            numberOfLogin--;
                            Intent intent;
                            if (swRemember.isChecked()){
                                setRememberMe();
                            }
                            if (numberOfLogin == 0){
                                Log.d("numberOfLogin :", numberOfLogin + "");
                                intent = new Intent(getApplicationContext(), FirstLogin.class);
                            }else{
                                Log.d("numberOfLogin :", numberOfLogin + "");
                                intent = new Intent(getApplicationContext(), HomepageActivity.class);
                            }
                            storeToToken(accessToken);
                            startActivity(intent);
                            LoginActivity.this.finish();
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
                param.put("username",strUsername);
                param.put("password",strPassword);

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

    private void setRememberMe() {
        SharedPreferences settings = getApplicationContext().getSharedPreferences("tokenizer", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("isRemember", true);
        editor.putString("username", edtUsername.getText().toString());
        editor.putString("password", edtPassword.getText().toString());
        editor.commit();
    }

    private void clickedRegister(){
        startActivity(new Intent(activity, RegisterActivity.class));
        activity.finish();
    }

    private void setEditTexts () {
        edtUsername.setText("");
        edtPassword.setText("");
    }

    private void parseVolleyError(VolleyError error) throws UnsupportedEncodingException, JSONException {
        designTools.hideProgressBar(pbLogin);
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

    private void storeToToken(String token){
        SharedPreferences settings = getApplicationContext().getSharedPreferences("tokenizer", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("access_token", token);
        editor.commit();
    }
}
