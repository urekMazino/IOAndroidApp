package com.carbajal.danniel.ioapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carbajal.danniel.ioapp.views.input.transporte.TransporteController;


public class TransporteInputFragment extends Fragment{

//    private onCaptureModelListener mListener;
    private TransporteController transporteController;

    public TransporteInputFragment() {
        // Required empty public constructor
    }
    public static TransporteInputFragment newInstance(int index) {
        TransporteInputFragment fragment = new TransporteInputFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transporteController = new TransporteController(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return transporteController.getCustomViewPager();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof onCaptureModelListener) {
//            mListener = (onCaptureModelListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//            + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putStringArray("coeficients", objectiveFunctionInputView.getCoeficients());
    }

}
