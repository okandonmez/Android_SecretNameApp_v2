package us.ahududu.ahududu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SponsoredDetails extends AppCompatActivity {
    int eventId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsored_details);

        Bundle extras = getIntent().getExtras();
        eventId = extras.getInt("eventId");
        Toast.makeText(getApplicationContext(),eventId+"",Toast.LENGTH_LONG).show();
    }
}
