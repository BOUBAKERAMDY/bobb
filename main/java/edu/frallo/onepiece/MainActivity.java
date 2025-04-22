package edu.frallo.onepiece;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume() {
        super.onResume();

        Button button = findViewById(R.id.main_button);
        button.setOnClickListener(v -> {
            Animation tvoff = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.tv_off);
            tvoff.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            button.startAnimation(tvoff);
        });
        ImageView logo = findViewById(R.id.skull);
        Animation welcome = AnimationUtils.loadAnimation(this,R.anim.welcome);
        welcome.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                logo.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        logo.startAnimation(welcome);
        Button bouton = findViewById(R.id.main_button);
        Animation reapparition = AnimationUtils.loadAnimation(this,R.anim.reapparition);
        bouton.startAnimation(reapparition);
    }
}
