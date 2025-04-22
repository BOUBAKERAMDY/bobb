package edu.frallo.onepiece;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class FilterFragment extends Fragment {
    private CallbackActivity callbackActivity;

    public FilterFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callbackActivity = (CallbackActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceBundle) {
        View rootView = inflater.inflate(R.layout.filter_fragment, container, false);

        SeekBar seekBar = rootView.findViewById(R.id.seekBar2);
        TextView seekBarValue = rootView.findViewById(R.id.seekBarValue);
        seekBarValue.setText(String.valueOf(seekBar.getProgress()));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                callbackActivity.filter(progress);
                seekBarValue.setText(String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return rootView;
    }
}
