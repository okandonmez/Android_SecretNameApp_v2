package us.ahududu.ahududu;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class addEvents extends AppCompatActivity implements View.OnClickListener {
    Button btnBack;
    DesignTools designTools;
    EditText edtName, edtCategories, edtPrice;
    TextView txtDate, txtIsPriced;
    int addedEventId;
    private static final int PICK_IMAGE = 100;
    Boolean isPriced = false;
    String strPrice;
    TextView txtCategorySelect;
    Calendar cr;
    DatePickerDialog dpd;
    Button btnPublish;
    Uri imageURI;
    CircleImageView crcProfile;



    private final String postEventURL = "http://31.210.91.130/api/Activity/Create";
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_events);
        getToken();
        setStatusBar();
        connectUI();
    }

    private void connectUI(){
        edtName = findViewById(R.id.edtEventName);
        edtCategories = findViewById(R.id.edtCtg);
        btnBack = findViewById(R.id.btnBack);
        txtCategorySelect = findViewById(R.id.txtCtgClickable);
        txtDate = findViewById(R.id.edtDate);
        txtIsPriced = findViewById(R.id.txtIsPriced);
        edtPrice = findViewById(R.id.edtPrice);
        btnPublish = findViewById(R.id.btnShare);
        crcProfile = findViewById(R.id.crcEventName);

        btnBack.setOnClickListener(this);
        edtCategories.setOnClickListener(this);
        txtCategorySelect.setOnClickListener(this);
        txtDate.setOnClickListener(this);
        txtIsPriced.setOnClickListener(this);
        btnPublish.setOnClickListener(this);
        crcProfile.setOnClickListener(this);
    }

    private void setStatusBar() {
        designTools = new DesignTools();
        designTools.setStatusBarColor(this, R.color.splashStatusBarColor);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.txtCtgClickable:
                showRadioButtonDialog();
                break;
            case R.id.edtDate:
                setDate();
                break;
            case R.id.txtIsPriced:
                setIsPriced();
                break;
            case R.id.btnShare:
                postEvent();
                break;
            case R.id.crcEventName:
                openGallery();
                break;


        }
    }

    private void setIsPriced(){
        AlertDialog levelDialog = null;
        final CharSequence[] items = {"Evet", "Hayır"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Kategori :");
        final AlertDialog finalLevelDialog = levelDialog;
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch(item)
                {
                    case 0:
                        txtIsPriced.setText(items[0]);
                        edtPrice.setEnabled(true);
                        isPriced = true;
                        break;
                    case 1:
                        txtIsPriced.setText(items[1]);
                        edtPrice.setText("");
                        edtPrice.setEnabled(false);
                        isPriced = false;
                        break;
                }
                dialog.dismiss();
            }
        });
        levelDialog = builder.create();
        levelDialog.show();
    }


    private void setDate(){
        cr = Calendar.getInstance();
        int day = cr.get(Calendar.DAY_OF_MONTH);
        int month = cr.get(Calendar.MONTH);
        int year = cr.get(Calendar.YEAR);

        dpd = new DatePickerDialog(addEvents.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                mMonth++;
                txtDate.setText(mDay + "." + mMonth + "." + mYear);
            }
        },year,month,day);
        dpd.show();
    }

    private void showRadioButtonDialog() {
        AlertDialog levelDialog = null;
        final CharSequence[] items = {" Doğa "," Spor", "Bilim & Öğrenme", "Kariyer", "E-Spor", "Aile & Çocuk", "Müzik", "Festival", "Kültür Sanat", "Üniversite", "Ders", "Araçlar", "Evcil Hayvan", "Yeme İçme", "Seyahat"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Kategori :");
        final AlertDialog finalLevelDialog = levelDialog;
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch(item)
                {
                    case 0:
                        txtCategorySelect.setText(items[0]);
                        break;
                    case 1:
                        txtCategorySelect.setText(items[1]);
                        break;
                    case 2:
                        txtCategorySelect.setText(items[2]);
                        break;
                    case 3:
                        txtCategorySelect.setText(items[3]);
                        break;
                    case 4:
                        txtCategorySelect.setText(items[4]);
                        break;
                    case 5:
                        txtCategorySelect.setText(items[5]);
                        break;
                    case 6:
                        txtCategorySelect.setText(items[6]);
                        break;
                    case 7:
                        txtCategorySelect.setText(items[7]);
                        break;
                    case 8:
                        txtCategorySelect.setText(items[8]);
                        break;
                    case 9:
                        txtCategorySelect.setText(items[9]);
                        break;
                    case 10:
                        txtCategorySelect.setText(items[10]);
                        break;
                    case 11:
                        txtCategorySelect.setText(items[11]);
                        break;
                    case 12:
                        txtCategorySelect.setText(items[12]);
                        break;
                    case 13:
                        txtCategorySelect.setText(items[13]);
                        break;
                    case 14:
                        txtCategorySelect.setText(items[14]);
                        break;
                }
                dialog.dismiss();
            }
        });
        levelDialog = builder.create();
        levelDialog.show();
    }

    private void postEvent(){
        if (isAnyEmpty())
            return;

        Log.e("PostEvent","Post Event Scene");
        final String name = edtName.getText().toString();
        final String category = txtCategorySelect.getText().toString();
        final String date = txtDate.getText().toString();
        final String time = "19:00";
        final String strIsPriced2 = isPriced.toString();
        final String location = "Istanbul";
        final String capacity = "Sınırsız";
        final String description = "description";
        final String url = "deneme";

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest jsonForGetRequest = new StringRequest(
                Request.Method.POST,postEventURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Log.e("PostEventResponse", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean isSuccess = jsonObject.getBoolean("Sonuc");
                            addedEventId = jsonObject.getInt("id");

                            if (isSuccess){
                                Toast.makeText(getApplicationContext(),"Tebrikler, Başarılı bir şekilde, etkinlik oluşturdunuz !",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), HomepageActivity.class);
                                startActivity(intent);
                                addEvents.this.finish();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Bir hata meydana geldi, daha sonra tekrar deneyiniz.",Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("Name", name);
                param.put("Category",category);
                param.put("Date", parseDate(date));
                param.put("Time", time);
                param.put("IsPriced", strIsPriced2);
                param.put("Location", location);
                param.put("Capacity", capacity);
                param.put("Description", description);
                param.put("Url", url);

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

    private boolean isAnyEmpty(){
        if (edtName.getText().toString().length() < 3){
            edtName.setError("Boş bırakmayınız");
            return true;
        }
        if (txtCategorySelect.getText().toString().contains("Kategori")){
            Toast.makeText(getApplicationContext(), "Lütfen kategori seçiniz.", Toast.LENGTH_LONG).show();
            return true;
        }
        if (!txtDate.getText().toString().contains("2")){
            Toast.makeText(getApplicationContext(), "Lütfen tarih seçimi yapınız.", Toast.LENGTH_LONG).show();
            return true;
        }
        if (txtIsPriced.getText().toString().contains("/")){
            Toast.makeText(getApplicationContext(),"Etkinliğin ücret seçeneğini giriniz",Toast.LENGTH_LONG).show();
            return true;
        }

        return false;

    }

    private void getToken(){
        SharedPreferences settings = getApplicationContext().getSharedPreferences("tokenizer", Context.MODE_PRIVATE);
        token = settings.getString("access_token", "no_token");
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

    private String parseDate(String date){
        String result = "";
        Log.e("date",date);

        String[] parts = date.split("\\.");

        result = parts[1] + "." + parts[0] + "." + parts[2];
        return result;
    }

}
