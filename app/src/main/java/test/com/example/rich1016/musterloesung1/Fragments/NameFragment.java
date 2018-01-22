package test.com.example.rich1016.musterloesung1.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.jar.Attributes;

import test.com.example.rich1016.musterloesung1.R;

/**
 * Created by wech1025 on 17.01.2018.
 */

public class NameFragment extends DialogFragment implements View.OnClickListener {


    public String trackname;
    private OnFragmentInteractionListener listener;
    View view;

    public String getTrackname() {
        return trackname;
    }

    public void setTrackname(String trackname) {
        this.trackname = trackname;
    }

    private NameFragment.OnFragmentInteractionListener nListener;

    @Override
    public void onStart() {
        super.onStart();

        if (getDialog() == null)
            return;

        int dialogWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        int dialogHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.name_fragment, container, false);

        EditText trackName = (EditText) view.findViewById(R.id.track_name);
        trackName.setOnClickListener(this);
        Button speichern = (Button) view.findViewById(R.id.speichern);
        speichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO setName somehow...??
                listener.onFragmentInteraction(view.);
            }
        });
        Button abbrechen = (Button) view.findViewById(R.id.abbrechen);
        abbrechen.setOnClickListener(this);

        view.setBottom(2);


        return view;

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NameFragment.OnFragmentInteractionListener) {
            nListener = (NameFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        nListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Drawable background);
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.abbrechen:
                nListener.onFragmentInteraction(v.getBackground());
                dismiss();
                //kein Track abspeichern
                break;
            case R.id.speichern:
                nListener.onFragmentInteraction(v.getBackground());
                dismiss();
                trackname = trackname;
                break;

        }

    }
}

