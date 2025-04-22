package com.boubaker.tpnote;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PokemonFragment extends Fragment {
    private static final String TAG = "bob PokemonFragment";
    private static final int NUM_FRAGMENT = 7; // Arbitrary number for this fragment

    private List<Pokemon> allPokemons = new ArrayList<>();
    private List<Pokemon> filteredPokemons = new ArrayList<>();
    private PokemonAdapter adapter;
    private Notifiable notifiable;
    private int strengthFilter = 60; // Default value

    public PokemonFragment() {
        // Required empty public constructor
        Log.d(TAG, "PokemonFragment created");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (requireActivity() instanceof Notifiable) {
            notifiable = (Notifiable) requireActivity();
        } else {
            throw new AssertionError("Class " + requireActivity().getClass().getName() + " does not implement Notifiable.");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get arguments passed from the activity
        if (getArguments() != null) {
            strengthFilter = getArguments().getInt("strengthFilter", 60);

            // Get the Pokemon list if available
            if (getArguments().containsKey("pokemonList")) {
                allPokemons = getArguments().getParcelableArrayList("pokemonList");
                Log.d(TAG, "Received " + allPokemons.size() + " Pokemon from arguments");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon, container, false);

        // Initialize the filtered Pokemon list
        filterPokemons();

        // Set up the ListView
        ListView listView = view.findViewById(R.id.pokemonListView);
        adapter = new PokemonAdapter(requireContext(), filteredPokemons);
        listView.setAdapter(adapter);

        // Set up SeekBar
        SeekBar seekBar = view.findViewById(R.id.strengthSeekBar);
        TextView seekBarValueText = view.findViewById(R.id.seekBarValueText);

        // Set initial values
        seekBar.setProgress(strengthFilter);
        seekBarValueText.setText(String.valueOf(strengthFilter));

        // SeekBar listener
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                strengthFilter = progress;
                seekBarValueText.setText(String.valueOf(progress));

                // Update filtered list
                filterPokemons();
                adapter.notifyDataSetChanged();

                // Notify the activity of the value change
                notifiable.onDataChange(NUM_FRAGMENT, strengthFilter);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }
        });

        // Set item click listener to show details
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Pokemon selectedPokemon = filteredPokemons.get(position);
            showPokemonDetails(selectedPokemon);

            // Notify activity of the click
            notifiable.onClick(NUM_FRAGMENT);
        });

        return view;
    }

    // Filter Pokemon based on strength
    private void filterPokemons() {
        filteredPokemons.clear();

        if (allPokemons != null && !allPokemons.isEmpty()) {
            // Filter Pokemon that have strength greater than the filter value
            filteredPokemons.addAll(
                    allPokemons.stream()
                            .filter(pokemon -> pokemon.getStrength() * 20 > strengthFilter) // Scale to match SeekBar
                            .collect(Collectors.toList())
            );
        }

        Log.d(TAG, "Filtered to " + filteredPokemons.size() + " Pokemon");
    }

    // Show a dialog with Pokemon details
    private void showPokemonDetails(Pokemon pokemon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

        // Create custom view for dialog
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_pokemon_details, null);
        TextView nameText = dialogView.findViewById(R.id.detailName);
        TextView typeText = dialogView.findViewById(R.id.detailType);
        TextView strengthText = dialogView.findViewById(R.id.detailStrength);

        // Set Pokemon details
        nameText.setText(pokemon.getName());
        typeText.setText("Type: " + pokemon.getType());
        strengthText.setText("Strength: " + pokemon.getStrength());

        // Set up the dialog
        builder.setTitle("Pokemon Details")
                .setView(dialogView)
                .setPositiveButton("Close", (dialog, which) -> dialog.dismiss());

        // Show the dialog
        builder.create().show();
    }

    // Method to update Pokemon list
    public void updatePokemonList(List<Pokemon> newPokemons) {
        allPokemons.clear();
        if (newPokemons != null) {
            allPokemons.addAll(newPokemons);
        }

        // Update filtered list
        filterPokemons();

        // Update UI
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}