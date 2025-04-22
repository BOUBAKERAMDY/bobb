package edu.frallo.onepiece;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class CharacterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        OnepieceCharacter character = getIntent().getParcelableExtra("character");

        TextView tvName = findViewById(R.id.characterName);
        tvName.setText(character.getName());

        TextView tvDesc = findViewById(R.id.characterDescription);
        tvDesc.setText(character.getDescription());

        ImageView tvPicture = findViewById(R.id.characterPicture);
        Picasso.get().load(character.getPictureLowDefinition()).into(tvPicture);
    }
}
