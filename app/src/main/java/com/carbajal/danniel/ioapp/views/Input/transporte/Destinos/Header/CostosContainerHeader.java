package com.carbajal.danniel.ioapp.views.input.transporte.Destinos.Header;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.views.customViews.FontButtonBuilder;
import com.carbajal.danniel.ioapp.views.input.transporte.Destinos.CostoField;
import com.carbajal.danniel.ioapp.views.input.transporte.Destinos.CostosContainer;

/**
 * Created by daniel on 11/20/16.
 */

public class CostosContainerHeader extends CostosContainer implements CostoFieldHeader.Events{

    private CostosContainerHeaderEvents listener;
    private TextView addButton;
    public CostosContainerHeader(Context context,CostosContainerHeaderEvents listener) {
        super(context,1);
        this.listener = listener;
        init();
    }

    public CostosContainerHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CostosContainerHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(){
        final EditText toFocus = costoFields.get(0).getNumField();
        final InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        toFocus.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                toFocus.requestFocus();
                imm.showSoftInput(toFocus, 0);
            }
        }, 100);
    }

    private void createAddButon(){
        TextView button = FontButtonBuilder.BuildCircularButton(getContext(),getResources().getString(R.string.add_icon),26,getResources().getColor(R.color.green));
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.requestAddOrigin();
            }
        });
        addButton = button;
    }
    @Override
    public CostoField addCostoField() {
        if (addButton==null){
            createAddButon();
        }
        CostoFieldHeader costoField = createCostoField(costoFields.size()+1);
//        setNumFieldListener(costoField.getNumField());
        if (costoFields.size()>0){
            ((ViewGroup)addButton.getParent()).removeView(addButton);
        }
        costoFields.add(costoField);
        scrollViewLL.addView(costoField);
        costoField.addAddButton(addButton);
        costoField.getNumField().requestFocus();
        return costoField;
    }

    @Override
    public void removeCostoField(int index) {
        if (costoFields.size()-1 == index){
            ((CostoFieldHeader)costoFields.get(index)).removeButton(addButton);
            ((CostoFieldHeader)costoFields.get(index-1)).addAddButton(addButton);
        }
        super.removeCostoField(index);

    }

    //    private void setNumFieldListener(final CustomNumField numField){
//        numField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if ((actionId == EditorInfo.IME_ACTION_NEXT) || actionId == EditorInfo.IME_ACTION_DONE){
//                    if(numField == costoFields.get(costoFields.size()-1).getNumField()) {
//                        listener.requestAddOrigin();
//                        return true;
//                    }
//                    return false;
//                } else {
//                    return false;
//                }
//            }
//        });
//    }
    @Override
    protected CostoFieldHeader createCostoField(int index) {
        return new CostoFieldHeader(getContext(),this,index);
    }

    @Override
    public void closeButtonClicked(CostoFieldHeader costoFieldHeader) {
        requestCloseOrigin(costoFields.indexOf(costoFieldHeader));
    }

    public void requestCloseOrigin(int index){
        listener.requestCloseOrigin(index);
    }
    public interface CostosContainerHeaderEvents{
        void requestCloseOrigin(int index);
        void requestAddOrigin();
    }
}
