package com.boubaker.tpnote;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class ControlActivity extends AppCompatActivity implements Menuable, Notifiable, PostExecuteActivity<Pokemon> {
    private final String TAG = "frallo " + getClass().getSimpleName();
    private static final String POKEMON_API_URL = "https://raw.githubusercontent.com/fanzeyi/pokemon.json/17d33dc111ffcc12b016d6485152aa3b1939c214/pokedex.json";

    private int seekBarValue = 60;

    private List<Pokemon> pokemonList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        int menuNumber = 3;

        // Get index from MainActivity
        Intent intent = getIntent();
        if (intent != null) {
            menuNumber = intent.getIntExtra(getString(R.string.index), 1);
            Log.d(TAG, "received menu#" + menuNumber);
        }

        // Index to start menu
        Bundle args = new Bundle();
        args.putInt(getString(R.string.index), menuNumber);
        MenuFragment menu = new MenuFragment();
        menu.setArguments(args);
        Log.d(TAG, "send to MenuFragment menu#" + menuNumber);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_menu, menu);
        transaction.commit();
    }

    @Override
    public void onMenuChange(int index) {
        Fragment fragment = null;
        switch (index) {
            case 0:
                fragment = new Screen1Fragment();
                break;
            case 1: {
                fragment = new Screen2Fragment();
                Bundle args = new Bundle();
                args.putInt(getString(R.string.seekbarvalue), seekBarValue);
                fragment.setArguments(args);
            } break;
            case 2:
                fragment = new Screen3Fragment();
                break;
            case 3: {
                fragment = new Screen4Fragment();
                Bundle args = new Bundle();

            } break;
            case 4:
                fragment = new Screen5Fragment();
                break;
            case 5:
                fragment = new Screen6Fragment();
                break;
            default:
                fragment = new Screen1Fragment();  // Default case
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_main, fragment);
        transaction.addToBackStack(null); // To be able to browse with back button
        transaction.commit();
    }

    @Override
    public void onClick(int numFragment) {
        Log.d(TAG, "Menu " + numFragment + " has clicked!");
    }

    @Override
    public void onDataChange(int numFragment, Object data) {
        Log.d(TAG, "received " + data + " data from fragment#" + numFragment);
        switch (numFragment) {
            case 1:
                break;
            case 2:
                seekBarValue = (Integer) data;
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:  // PokemonFragment
                if (data instanceof Integer) {
                    seekBarValue = (Integer) data;
                }
                break;
        }
    }

    @Override
    public void requestPokemonData() {
        Log.d(TAG, "Pokemon data requested from Screen3Fragment");

        // If we already have Pokemon data, show it directly
        if (!pokemonList.isEmpty()) {
            showPokemonFragment();
            return;
        }

        // Otherwise, load the data first
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Pokemon data...");
        progressDialog.setCancelable(false);

        // Start the async HTTP request
        new PokemonHttpAsyncGet<>(POKEMON_API_URL, Pokemon.class, this, progressDialog);
    }

    @Override
    public void onPostExecute(List<Pokemon> itemList) {
        // Save the Pokemon list
        pokemonList.clear();
        if (itemList != null && !itemList.isEmpty()) {
            pokemonList.addAll(itemList);
        }

        Log.d(TAG, "Loaded " + pokemonList.size() + " Pokemon");

        // Show the Pokemon fragment with the loaded data
        showPokemonFragment();
    }

    @Override
    public void runOnUiThread(Runnable runnable) {
        runOnUiThread(runnable);
    }

    // Show the Pokemon fragment with current data
    private void showPokemonFragment() {
        // Create a bundle with the Pokemon list and SeekBar value
        Bundle args = new Bundle();
        args.putInt("strengthFilter", seekBarValue);
        args.putParcelableArrayList("pokemonList", new ArrayList<Parcelable>(pokemonList));

        // Create and configure the fragment
        PokemonFragment fragment = new PokemonFragment();
        fragment.setArguments(args);

        // Replace the current fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_main, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        Log.d(TAG, "Showing Pokemon fragment with " + pokemonList.size() + " Pokemon");
    }
}