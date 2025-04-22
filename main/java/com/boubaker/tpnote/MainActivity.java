package com.boubaker.tpnote;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "frallo "+getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = new Intent(getApplicationContext(), ControlActivity.class);

        // Get the image view and button
        ImageView image = findViewById(R.id.imageView);
        Button evelButton = findViewById(R.id.evelButton);

        // Initially hide the button
        evelButton.setVisibility(View.INVISIBLE);

        // Load and start the new animation
        Animation fondAgrandissement = AnimationUtils.loadAnimation(this, R.anim.fondu_agrandissement);

        // Make button visible after animation completes
        fondAgrandissement.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Nothing to do on start
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Show the button when animation ends
                evelButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Nothing to do on repeat
            }
        });

        // Start the animation
        image.startAnimation(fondAgrandissement);


        evelButton.setOnClickListener(clic -> {




            intent.putExtra(getString(R.string.index), 3);

            // Log the action
            Log.d(TAG, "Sending menu#3 to ControlActivity");

            // Start the activity
            startActivity(intent);
        });
    }
}