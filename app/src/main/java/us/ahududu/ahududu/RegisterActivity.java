package us.ahududu.ahududu;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    Activity activity;
    DesignTools designTools;

    Button btnGoLogin, btnGoRegister;
    EditText edtUsername, edtPassword, edtConfirmPassword;
    CheckBox chIsAccepted;
    ProgressBar pbRegister;
    String registerURl = "http://31.210.91.130/api/Account/Register";
    TextView txtPolicy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        activity = this;
        designTools = new DesignTools();
        designTools.setStatusBarColor(activity, R.color.registerStatusBarColor);

        connectUI();

    }

    private void connectUI () {
        btnGoLogin = findViewById(R.id.btnRegBack);
        btnGoLogin.setOnClickListener(this);

        btnGoRegister = findViewById(R.id.btnRegister);
        btnGoRegister.setOnClickListener(this);

        edtUsername = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtAgainPassword);

        chIsAccepted = findViewById(R.id.chIsAccepted);
        txtPolicy = findViewById(R.id.txtPolicyText);
        pbRegister = findViewById(R.id.pbRegister);

        txtPolicy.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(activity, LoginActivity.class));
        overridePendingTransition(R.anim.enter, R.anim.exit);  // Left to right activity passing.
        activity.finish();
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        int itemId = view.getId();
        switch (itemId){
            case R.id.btnRegBack :
                onBackPressed();
                break;
            case R.id.btnRegister :
                if (chIsAccepted.isChecked()){          // Check policy is accepted or not
                    registerRequest();
                }
                /* if (isAnyEmptyField()){                 // Check is there any empty field
                    return;    //  Do nothing
                }*/
                else {
                    Toast.makeText(getApplicationContext(),"Kullanıcı sözleşmesini okuyup onaylamalısınız.",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.txtPolicyText:
                Intent intent = new Intent(getApplicationContext(), PolicyShow.class);
                startActivity(intent);

            default :

        }
    }

    public void registerRequest(){
        pbRegister.setVisibility(View.VISIBLE);

        final String strUsername = edtUsername.getText().toString();
        final String strPassword = edtPassword.getText().toString();
        final String strConfPassword = edtConfirmPassword.getText().toString();

        if (!strPassword.equals(strConfPassword)) {
            edtConfirmPassword.setError("Girilen şifreler aynı olmalıdır.");
            designTools.hideProgressBar(pbRegister);
            return;
        }

        Log.e("username",strUsername);
        Log.e("password",strPassword);
        Log.e("confPassword",strConfPassword);

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest jsonForGetRequest = new StringRequest(
                Request.Method.POST,registerURl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("Sonuc").equals("true")){
                                Toast.makeText(getApplicationContext(),"Kayıt Başarılı ! Eposta adresiniz ile hesabınızı onaylayınız.", Toast.LENGTH_LONG).show();
                                designTools.hideProgressBar(pbRegister);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse.statusCode == 500){   // Check server error
                    Toast.makeText(getApplicationContext(),"Server Hatası. Sistem yöneticisi ile iletişime geçiniz.",Toast.LENGTH_LONG).show();
                    designTools.hideProgressBar(pbRegister);
                    return;
                }
                try {
                    String responseBody = new String(error.networkResponse.data, "utf-8");
                    Log.e("errorBody",responseBody);
                    if (responseBody.contains("invalid") && responseBody.contains("Email") && responseBody.contains("already taken")) {
                        Toast.makeText(getApplicationContext(), "Eposta adresi zaten sistemde kayıtlı !", Toast.LENGTH_LONG).show();
                        designTools.hideProgressBar(pbRegister);
                    }
                    else if (responseBody.contains("invalid") && responseBody.contains("Email")){
                        Toast.makeText(getApplicationContext(),"Eposta adresi geçerli bir formatta değil !",Toast.LENGTH_LONG).show();
                        designTools.hideProgressBar(pbRegister);
                    }
                    if(responseBody.contains("Password")){
                        Toast.makeText(getApplicationContext(),"Şifre en az 8 karakter olmalıdır !.",Toast.LENGTH_LONG).show();
                        designTools.hideProgressBar(pbRegister);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("email", strUsername);
                param.put("password",strPassword);
                param.put("ConfirmPassword",strConfPassword);

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

    private Boolean isAnyEmptyField(){
        int lengtOfStr = edtUsername.getText().toString().length();
        if (lengtOfStr == 0){
            edtUsername.setError("Bu alan boş bırakılamaz.");
            return true;
        }
        lengtOfStr = edtPassword.getText().toString().length();
        if (lengtOfStr == 0){
            edtPassword.setError("Bu alan boş bırakılamaz.");
            return true;
        }
        lengtOfStr = edtConfirmPassword.getText().toString().length();
        if (lengtOfStr == 0){
            edtConfirmPassword.setError("Bu alan boş bırakılamaz.");
            return true;
        }
        return false;
    }
}
