package com.boubaker.tpnote;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Screen3Fragment extends Fragment {
    private final String TAG = "frallo "+getClass().getSimpleName();
    private Notifiable notifiable;

    public Screen3Fragment() {
        Log.d(TAG,"screenFragment type 3 created");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (requireActivity() instanceof Notifiable) {
            notifiable = (Notifiable) requireActivity();
            //Log.d(TAG, "Class " + requireActivity().getClass().getSimpleName() + " implements Notifiable.");
        } else {
            throw new AssertionError("Classe " + requireActivity().getClass().getName() + " ne met pas en œuvre Notifiable.");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_screen3, container, false);

        Button catchEmUpButton = view.findViewById(R.id.button);

        // Set button text to "Catchem up"
        catchEmUpButton.setText("Catchem up");

        // Set click listener
        catchEmUpButton.setOnClickListener(v -> {
            // Notify the activity (for logging purposes)
            notifiable.onClick(NUM_FRAGMENT);

            // Launch the ListPokemonFragment
            ListPokemonFragment pokemonListFragment = new ListPokemonFragment();

            // Replace current fragment with the Pokemon list fragment
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_main, ListPokemonFragment);
            transaction.addToBackStack(null); // Add to back stack so we can go back
            transaction.commit();

            Log.d(TAG, "Launching Pokemon list fragment");
    }
}