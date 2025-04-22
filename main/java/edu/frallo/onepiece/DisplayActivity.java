package edu.frallo.onepiece;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity implements PostExecuteActivity<OnepieceCharacter>, CallbackActivity, Clickable {
    private final String apiUrl = "http://edu.info06.net/onepiece/characters.json";
    private final List<OnepieceCharacter> characters = new ArrayList<>();
    private CharacterAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        adapter = new CharacterAdapter(this, getApplicationContext(), characters);
        ListView list = findViewById(R.id.listView);
        list.setAdapter(adapter);

        new HttpAsyncGet<>(apiUrl, OnepieceCharacter.class, this, new ProgressDialog(DisplayActivity.this));
    }

    @Override
    public void onPostExecute(List<OnepieceCharacter> newCharacters) {
        this.characters.addAll(newCharacters);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void filter(int value) {
        this.adapter.setCurrentFilter(value);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onClicItem(int itemIndex) {
        OnepieceCharacter clickedCharacter = (OnepieceCharacter) this.adapter.getItem(itemIndex);
        Intent intent = new Intent(DisplayActivity.this, CharacterActivity.class);
        intent.putExtra("character", clickedCharacter);
        startActivity(intent);
    }

    @Override
    public void onRatingBarChange(int itemIndex, float value) {
        this.characters.get(itemIndex).setValue(value);
    }
}
