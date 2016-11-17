package com.carbajal.danniel.ioapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.carbajal.danniel.ioapp.models.programacionlineal.FuncionObjetivo;
import com.carbajal.danniel.ioapp.views.input.restrictions.RestrictionsInputView;


public class RestrictionsInputFragment extends Fragment{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "index";

    private int index;

    private onCaptureModelListener mListener;
    private RestrictionsInputView restrictionsInputView;

    public RestrictionsInputFragment() {
        // Required empty public constructor
    }


    public static RestrictionsInputFragment newInstance(int index) {
        RestrictionsInputFragment fragment = new RestrictionsInputFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            index = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FrameLayout root = new FrameLayout(getActivity());
        if (savedInstanceState != null) {
            restrictionsInputView = new RestrictionsInputView(getActivity(),index,savedInstanceState.getStringArray("coeficients"));
            root.addView(restrictionsInputView);
        } else {
            restrictionsInputView = new RestrictionsInputView(getActivity(),index);
            root.addView(restrictionsInputView);
        }
        restrictionsInputView.getCaptureButton().setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                try{
                    //FuncionObjetivo funcionObjetivoa = new FuncionObjetivo(restrictionsInputView.getCoeficientsValues());
                    //mListener.onCaptureModel(funcionObjetivoa);
                } catch (Exception e){

                }

            }
        });
        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof onCaptureModelListener) {
            mListener = (onCaptureModelListener) context;
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
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArray("coeficients", restrictionsInputView.getCoeficients());
    }

    public interface onCaptureModelListener{
        void onCaptureModel(FuncionObjetivo funcionObjetivoa);
    }
}
