package android.example.pathcrex;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {
    private static final int StartupDelay = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);

                finish();

            }
        },  StartupDelay);
    }
}
