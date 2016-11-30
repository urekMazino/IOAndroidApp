package com.carbajal.danniel.ioapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.models.programacionlineal.FuncionObjetivo;
import com.carbajal.danniel.ioapp.views.input.ModelViewPager;
import com.carbajal.danniel.ioapp.views.output.CustomPagerAdapter;


public class ModelInputFragment extends Fragment{

    private onCaptureModelListener mListener;
    private CustomPagerAdapter customPagerAdapter;
    private int maxVariables = Integer.MAX_VALUE;
    public ModelInputFragment() {
        // Required empty public constructor
    }
    public static ModelInputFragment newInstance(int maxVariables) {
        ModelInputFragment fragment = new ModelInputFragment();
        Bundle args = new Bundle();
        args.putInt("maxVariables",maxVariables);
        fragment.setArguments(args);

        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        maxVariables = getArguments().getInt("maxVariables",Integer.MAX_VALUE);
        customPagerAdapter = new CustomPagerAdapter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.model_view_pager_layout, container, false);
        ModelViewPager modelViewPager = (ModelViewPager)root.findViewById(R.id.model_view_pager);
        modelViewPager.setMaxVariables(maxVariables);
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
//        outState.putStringArray("coeficients", objectiveFunctionInputView.getCoeficients());
    }

    public interface onCaptureModelListener{
        void onCaptureModel(FuncionObjetivo funcionObjetivoa);
    }
}
