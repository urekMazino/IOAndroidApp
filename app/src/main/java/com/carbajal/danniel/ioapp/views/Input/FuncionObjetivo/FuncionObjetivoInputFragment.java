package com.carbajal.danniel.ioapp.views.input.funcionObjetivo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.carbajal.danniel.ioapp.models.programacionlineal.FuncionObjetivo;


public class FuncionObjetivoInputFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private onCaptureModelListener mListener;

    private FuncionObjetivoInputView funcionObjetivoInputView;

    public FuncionObjetivoInputFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FuncionObjetivoInputFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FuncionObjetivoInputFragment newInstance(String param1, String param2) {
        FuncionObjetivoInputFragment fragment = new FuncionObjetivoInputFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FrameLayout root = new FrameLayout(getActivity());

        if (savedInstanceState != null) {
            funcionObjetivoInputView = new FuncionObjetivoInputView(getActivity(),savedInstanceState.getStringArray("coeficients"));
            root.addView(funcionObjetivoInputView);
        } else {
            funcionObjetivoInputView = new FuncionObjetivoInputView(getActivity());
            root.addView(funcionObjetivoInputView);
        }
        funcionObjetivoInputView.getCaptureButton().setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                try{
                    FuncionObjetivo funcionObjetivoa = new FuncionObjetivo(funcionObjetivoInputView.getCoeficientsValues());
                    mListener.onCaptureModel(funcionObjetivoa);
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
        outState.putStringArray("coeficients", funcionObjetivoInputView.getCoeficients());
    }

    public interface onCaptureModelListener{
        void onCaptureModel(FuncionObjetivo funcionObjetivoa);
    }
}
