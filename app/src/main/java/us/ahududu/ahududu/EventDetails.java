package us.ahududu.ahududu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class EventDetails extends AppCompatActivity {

    String token;
    String eventId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        getToken();
        Bundle extras = getIntent().getExtras();
        eventId = extras.getString("eventId");
        Log.e("eventId", eventId);
    }

    private void getToken(){
        SharedPreferences settings = getApplicationContext().getSharedPreferences("tokenizer", Context.MODE_PRIVATE);
        token = settings.getString("access_token", "no_token");
    }
}
