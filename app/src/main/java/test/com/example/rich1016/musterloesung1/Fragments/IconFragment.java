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
import android.widget.ImageButton;

import test.com.example.rich1016.musterloesung1.DbHelper;
import test.com.example.rich1016.musterloesung1.R;

/**
 * Created by rich1016 on 20.12.2017.
 */

public class IconFragment extends DialogFragment implements View.OnClickListener {


    public String mode;
    View view;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    private OnFragmentInteractionListener mListener;

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
        view = inflater.inflate(R.layout.icon_fragment, container, false);
        ImageButton buttonBus = (ImageButton) view.findViewById(R.id.button_bus);
        buttonBus.setOnClickListener(this);
        ImageButton buttonFahrrad = (ImageButton) view.findViewById(R.id.button_fahrrad);
        buttonFahrrad.setOnClickListener(this);
        ImageButton buttonTram = (ImageButton) view.findViewById(R.id.button_tram);
        buttonTram.setOnClickListener(this);
        ImageButton buttonGehen = (ImageButton) view.findViewById(R.id.button_gehen);
        buttonGehen.setOnClickListener(this);
        ImageButton buttonAuto = (ImageButton) view.findViewById(R.id.button_auto);
        buttonAuto.setOnClickListener(this);

        view.setBottom(5);


        return view;

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IconFragment.OnFragmentInteractionListener) {
            mListener = (IconFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Drawable background);
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.button_bus:
                mListener.onFragmentInteraction(v.getBackground());
                dismiss();
                mode = "Bus";
                break;
            case R.id.button_auto:
                mListener.onFragmentInteraction(v.getBackground());
                dismiss();
                mode = "Auto";
                break;
            case R.id.button_fahrrad:
                mListener.onFragmentInteraction(v.getBackground());
                dismiss();
                mode = "Fahrrad";
                break;
            case R.id.button_gehen:
                mListener.onFragmentInteraction(v.getBackground());
                dismiss();
                mode = "Gehen";
                break;
            case R.id.button_tram:
                mListener.onFragmentInteraction(v.getBackground());
                dismiss();
                mode = "Tram";
                break;


        }

    }
}
