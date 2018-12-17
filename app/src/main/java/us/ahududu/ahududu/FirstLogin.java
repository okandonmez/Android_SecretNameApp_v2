package us.ahududu.ahududu;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class FirstLogin extends AppCompatActivity {
   // private static final String TAG = "FirstLogin";
    private static final String PREFS_NAME = "preferenceName";
    private static final String postProfileURL = "http://31.210.91.130/api/Account/UpdateUserInfo";
    private static final String postProfilePhoto = "http://31.210.91.130/api/Account/PostProfilePhoto";
    private EditText edtName, edtMotto;
    private TextView edtDate;
    private TextView edtGender;
    private ImageView imgGoNext;
    int typeGender;
    private Button btnGetPhoto;
    private ImageView imgProfile;
    Uri imageURI;
    Calendar cr;
    DatePickerDialog dpd;
    private static final int PICK_IMAGE = 100;
    CircleImageView crcProfile;
    String token;
    ProgressBar pbFirstLogin;
    DesignTools designTools;
    Activity activity = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);
        designTools = new DesignTools();
        designTools.setStatusBarColor(activity,R.color.splashStatusBarColor);
        connectUI();
        getToken();
    }
    private void connectUI(){
        pbFirstLogin = findViewById(R.id.pbFirstLogin);
        imgGoNext = findViewById(R.id.imgNext);
        imgGoNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkEmptyFields()){
                    pbFirstLogin.setVisibility(View.VISIBLE);
                    postProfileDetails();
                }

            }
        });
        edtName = findViewById(R.id.edtName);
        edtMotto = findViewById(R.id.edtMotto);
        crcProfile = findViewById(R.id.imgCircleProfile);
        imgProfile = findViewById(R.id.imgProfile);
        btnGetPhoto = findViewById(R.id.btnGetPhotos);
        btnGetPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        edtGender = findViewById(R.id.txtGender);
        edtGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRadioButtonDialog();
            }
        });
        edtDate = findViewById(R.id.txtDate);
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cr = Calendar.getInstance();
                int day = cr.get(Calendar.DAY_OF_MONTH);
                int month = cr.get(Calendar.MONTH);
                int year = cr.get(Calendar.YEAR);

                dpd = new DatePickerDialog(FirstLogin.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        mMonth++;
                        edtDate.setText(mDay + "." + mMonth + "." + mYear);
                    }
                },year,month,day);
                dpd.show();
            }
        });
    }


    public void showRadioButtonDialog() {
        AlertDialog levelDialog = null;
// Strings to Show In Dialog with Radio Buttons
        final CharSequence[] items = {" Erkek "," Kadın "};
        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cinsiyet :");
        final AlertDialog finalLevelDialog = levelDialog;
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch(item)
                {
                    case 0:
                        setGender(0);
                        break;
                    case 1:
                        setGender(1);
                        break;
                }
                dialog.dismiss();
            }
        });
        levelDialog = builder.create();
        levelDialog.show();
    }

    private void setGender(int gender){
        if (gender == 0){
            typeGender = 0;
            edtGender.setText("Erkek");
        }else{
            typeGender = 1;
            edtGender.setText("Kadın");
        }
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageURI = data.getData();
            //imgProfile.setImageURI(imageURI);
            crcProfile.setImageURI(imageURI);
        }
    }

    private void postProfileDetails(){
        final String strName = edtName.getText().toString();
        final String strDate = edtDate.getText().toString();
        final String strMotto = edtMotto.getText().toString();
        final String strGender;

        if (typeGender == 0){
            strGender = "Erkek";
        }
        else{
            strGender = "Kadın";
        }
        final String strCity = "Istanbul";

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest jsonForGetRequest = new StringRequest(
                Request.Method.POST,postProfileURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pbFirstLogin.setVisibility(View.INVISIBLE);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean isSuccess = jsonObject.getBoolean("sonuc:");
                            if (isSuccess){
                                Intent intent = new Intent(getApplicationContext(), SelectCategories.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                pbFirstLogin.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Bir hata meydana geldi !", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("Name",strName);
                param.put("Birthday",strDate);
                param.put("Motto", strMotto);
                param.put("Gender", strGender);
                param.put("City", strCity);
                param.put("PhoneNumber", "00000000");
                param.put("Hobby", "Developer");
                param.put("Job", "Student");

                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("Authorization", "Bearer "+token);
                param.put("Content-Type","application/x-www-form-urlencoded");
                return param;
            }
        };
        queue.add(jsonForGetRequest);
    }

    private void getToken(){
        SharedPreferences settings = getApplicationContext().getSharedPreferences("tokenizer", Context.MODE_PRIVATE);
        token = settings.getString("access_token", "no_token");
    }

    private boolean checkEmptyFields(){
        int lenght = edtName.getText().toString().length();
        if (lenght == 0){
            Toast.makeText(getApplicationContext(), "Lütfen boş alanları doldurunuz.1",Toast.LENGTH_LONG).show();
            return false;
        }
        if (!edtDate.getText().toString().contains(".")){
            Toast.makeText(getApplicationContext(), "Lütfen boş alanları doldurunuz.2",Toast.LENGTH_LONG).show();
            return false;
        }
        if (edtGender.getText().toString().contains("Cin")){
            Toast.makeText(getApplicationContext(), "Lütfen boş alanları doldurunuz.3",Toast.LENGTH_LONG).show();
            return false;
        }
        if (edtMotto.getText().toString().contains("İnsanlara Kendinden Bahset")){
            Toast.makeText(getApplicationContext(), "Kendin hakkında kısa bir yazı yazabilir misin ?",Toast.LENGTH_LONG).show();
            edtMotto.setError("");
            return false;
        }
        return true;
    }




}
