package us.ahududu.ahududu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectCategories extends AppCompatActivity implements View.OnClickListener {

    DesignTools designTools;
    Activity activity = this;
    ImageView imgNature, imgSport, imgScience, imgCareer, imgESport, imgFamily, imgMusic, imgFest, imgCulturArt,
            imgUniversity, imgCourse, imgCars, imgPets, imgFood, imgTravel;
    ImageView imgNextPage;
    int ctrCategory = 0;
    List<Integer> param;
    ProgressBar pbCategory;
    private static final String postCtgURL = "http://31.210.91.130/api/Category/SaveCategories?";
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_categories);
        param = new ArrayList<Integer>();
        getToken();
        setStatusColor();
        connectUI();
    }

    private void connectUI(){
        pbCategory = findViewById(R.id.pbCategory);

        imgNature = findViewById(R.id.imgCtgNature);
        imgNature.setTag(0);
        imgNature.setImageResource(R.drawable.ctgnature);
        imgNature.setOnClickListener(this);

        imgSport = findViewById(R.id.imgCtgSport);
        imgSport.setTag(0);
        imgSport.setImageResource(R.drawable.ctgsport);
        imgSport.setOnClickListener(this);

        imgScience = findViewById(R.id.imgCtgScience);
        imgScience.setTag(0);
        imgScience.setImageResource(R.drawable.ctgscience);
        imgScience.setOnClickListener(this);

        imgCareer = findViewById(R.id.imgCtgCareer);
        imgCareer.setTag(0);
        imgCareer.setImageResource(R.drawable.ctgcareer);
        imgCareer.setOnClickListener(this);

        imgESport = findViewById(R.id.imgCtgESport);
        imgESport.setTag(0);
        imgESport.setImageResource(R.drawable.ctgesport);
        imgESport.setOnClickListener(this);

        imgFamily = findViewById(R.id.imgCtgFamily);
        imgFamily.setTag(0);
        imgFamily.setImageResource(R.drawable.ctgfamily);
        imgFamily.setOnClickListener(this);

        imgMusic = findViewById(R.id.imgCtgMusic);
        imgMusic.setTag(0);
        imgMusic.setImageResource(R.drawable.ctgmusic);
        imgMusic.setOnClickListener(this);

        imgFest = findViewById(R.id.imgCtgFest);
        imgFest.setTag(0);
        imgFest.setImageResource(R.drawable.ctgfest);
        imgFest.setOnClickListener(this);

        imgCulturArt = findViewById(R.id.imgCtgCulture);
        imgCulturArt.setTag(0);
        imgCulturArt.setImageResource(R.drawable.ctgcultureart);
        imgCulturArt.setOnClickListener(this);

        imgUniversity = findViewById(R.id.imgCtgUniversity);
        imgUniversity.setTag(0);
        imgUniversity.setImageResource(R.drawable.ctguniversity);
        imgUniversity.setOnClickListener(this);

        imgCourse = findViewById(R.id.imgCtgCourse);
        imgCourse.setTag(0);
        imgCourse.setImageResource(R.drawable.ctgcourse);
        imgCourse.setOnClickListener(this);

        imgCars = findViewById(R.id.imgCtgCars);
        imgCars.setTag(0);
        imgCars.setImageResource(R.drawable.ctgcars);
        imgCars.setOnClickListener(this);

        imgPets = findViewById(R.id.imgCtgPet);
        imgPets.setTag(0);
        imgPets.setImageResource(R.drawable.ctgpet);
        imgPets.setOnClickListener(this);

        imgFood = findViewById(R.id.imgCtgFood);
        imgFood.setTag(0);
        imgFood.setImageResource(R.drawable.ctgfood);
        imgFood.setOnClickListener(this);

        imgTravel = findViewById(R.id.imgCtgTravel);
        imgTravel.setTag(0);
        imgTravel.setImageResource(R.drawable.ctgtravel);
        imgTravel.setOnClickListener(this);

        imgNextPage = findViewById(R.id.imgNextCategory);
        imgNextPage.setOnClickListener(this);
    }

    private void setStatusColor(){
        designTools = new DesignTools();
        designTools.setStatusBarColor(activity, R.color.splashStatusBarColor);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.imgCtgNature :
                setSelected(imgNature, R.drawable.ctgnature, R.drawable.ctgnatureselected, 1);
                break;
            case R.id.imgCtgSport :
                setSelected(imgSport, R.drawable.ctgsport, R.drawable.ctgsportselected, 2);
                break;
            case R.id.imgCtgScience :
                setSelected(imgScience, R.drawable.ctgscience, R.drawable.ctgscienceselected, 3);
                break;
            case R.id.imgCtgCareer :
                setSelected(imgCareer, R.drawable.ctgcareer, R.drawable.ctgcareerselected,4);
                break;
            case R.id.imgCtgESport :
                setSelected(imgESport, R.drawable.ctgesport, R.drawable.ctgesportselected,5);
                break;
            case R.id.imgCtgFamily :
                setSelected(imgFamily, R.drawable.ctgfamily, R.drawable.ctgfamilyselected,6);
                break;
            case R.id.imgCtgMusic :
                setSelected(imgMusic, R.drawable.ctgmusic, R.drawable.ctgmusicselected,7);
                break;
            case R.id.imgCtgFest :
                setSelected(imgFest, R.drawable.ctgfest, R.drawable.ctgfestselected,8);
                break;
            case R.id.imgCtgCulture :
                setSelected(imgCulturArt, R.drawable.ctgcultureart, R.drawable.ctgcultureartselected,9);
                break;
            case R.id.imgCtgUniversity :
                setSelected(imgUniversity, R.drawable.ctguniversity, R.drawable.ctguniversityselected,10);
                break;
            case R.id.imgCtgCourse :
                setSelected(imgCourse, R.drawable.ctgcourse, R.drawable.ctgcourseselected,11);
                break;
            case R.id.imgCtgCars :
                setSelected(imgCars, R.drawable.ctgcars, R.drawable.ctgcarsselected,12);
                break;
            case R.id.imgCtgPet :
                setSelected(imgPets, R.drawable.ctgpet, R.drawable.ctgpetselected,13);
                break;
            case R.id.imgCtgFood :
                setSelected(imgFood, R.drawable.ctgfood, R.drawable.ctgfoodselected,14);
                break;
            case R.id.imgCtgTravel :
                setSelected(imgTravel, R.drawable.ctgtravel, R.drawable.ctgtravelselected,15);
                break;
            case R.id.imgNextCategory :
                goNextPage();
                break;
        }
    }

    private void setSelected(ImageView imgCategory, int categoryPic, int selectedCategoryPic, int idCtr){
        if (Integer.parseInt(imgCategory.getTag().toString()) == 0 && ctrCategory < 5) {
            imgCategory.setImageResource(selectedCategoryPic);
            imgCategory.setTag(1);
            param.add(idCtr);
            ctrCategory++;

        } else if (ctrCategory < 5 || Integer.parseInt(imgCategory.getTag().toString()) == 1){
            imgCategory.setImageResource(categoryPic);
            imgCategory.setTag(0);
            int temp;
            for (int i = 0; i < 5; i++){
                temp = param.get(i);
                if(temp == idCtr){
                    param.remove(i);
                    break;
                }
            }
            ctrCategory--;
        }
    }

    private void goNextPage(){
        if (ctrCategory != 5){
            Toast.makeText(getApplicationContext(),"Lütfen 5 adet ilgi alanı seçiniz.", Toast.LENGTH_LONG).show();
            return;
        }

        /*Log.e("error",param.get(0)+"");
        Log.e("error",param.get(1)+"");
        Log.e("error",param.get(2)+"");
        Log.e("error",param.get(3)+"");
        Log.e("error",param.get(4)+"");*/

        String lastURL = postCtgURL + "cid1=" + param.get(0) + "&" + "cid2=" + param.get(1) + "&" + "cid3=" + param.get(2) + "&" +
                "cid4=" + param.get(3) + "&" + "cid5=" + param.get(4);

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest jsonForGetRequest = new StringRequest(
                Request.Method.POST,lastURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("sonuc:")){
                                Intent intent = new Intent(getApplicationContext(),HomepageActivity.class);
                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Bir hata meydana geldi !", Toast.LENGTH_LONG).show();
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

        System.gc();

    }

    private void getToken(){
        SharedPreferences settings = getApplicationContext().getSharedPreferences("tokenizer", Context.MODE_PRIVATE);
        token = settings.getString("access_token", "no_token");
    }

}
