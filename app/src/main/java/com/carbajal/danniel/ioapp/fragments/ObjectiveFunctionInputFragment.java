package com.carbajal.danniel.ioapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.models.programacionlineal.FuncionObjetivo;
import com.carbajal.danniel.ioapp.views.customViews.ModelInputAdapter;


public class ObjectiveFunctionInputFragment extends Fragment{

    private onCaptureModelListener mListener;
    private ModelInputAdapter modelInputAdapter;

    public ObjectiveFunctionInputFragment() {
        // Required empty public constructor
    }
    public static ObjectiveFunctionInputFragment newInstance(int index) {
        ObjectiveFunctionInputFragment fragment = new ObjectiveFunctionInputFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modelInputAdapter = new ModelInputAdapter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.model_view_pager_layout, container, false);
        return view;
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
//        outState.putStringArray("coeficients", objectiveFunctionInputView.getCoeficients());
    }

    public interface onCaptureModelListener{
        void onCaptureModel(FuncionObjetivo funcionObjetivoa);
    }
}
