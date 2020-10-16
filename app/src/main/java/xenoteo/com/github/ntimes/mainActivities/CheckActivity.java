package xenoteo.com.github.ntimes.mainActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.ntimes.R;
import xenoteo.com.github.ntimes.mainActivities.timeActivity.TimeActivity;

public class CheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
    }

    public void reset(View v){
        startActivity(new Intent(this, RoundNumberActivity.class));
    }

    public void back(View v){
        super.onBackPressed();
    }

    public void start(View v){
        startActivity(new Intent(this, TimeActivity.class));
    }
}