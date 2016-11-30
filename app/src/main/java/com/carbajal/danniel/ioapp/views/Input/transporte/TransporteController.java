package com.carbajal.danniel.ioapp.views.input.transporte;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carbajal.danniel.ioapp.R;
import com.carbajal.danniel.ioapp.activities.OutputActivities.ResultTransporteActivity;
import com.carbajal.danniel.ioapp.models.programacionlineal.transporte.TablaTransporte;
import com.carbajal.danniel.ioapp.views.customViews.FontButtonBuilder;
import com.carbajal.danniel.ioapp.views.exceptions.ExceptionTransporteInput;
import com.carbajal.danniel.ioapp.views.input.transporte.Destinos.DestinoInputView;
import com.carbajal.danniel.ioapp.views.input.transporte.Destinos.DestinosManager;
import com.carbajal.danniel.ioapp.views.input.transporte.Destinos.Header.DestinoHeaderInputView;
import com.carbajal.danniel.ioapp.views.input.transporte.ofertas.OfertasInputView;
import com.carbajal.danniel.ioapp.views.output.CustomViewPager;
import com.carbajal.danniel.ioapp.views.output.transporte.TransportePreviewView;

/**
 * Created by daniel on 11/20/16.
 */

public class TransporteController implements DestinosManager.DestinosManagerEvents, CustomViewPager.customViewPagerEvents {

    private Context context;
    private Activity activity;
    private CustomViewPager customViewPager;
    private DestinosManager destinosManager;
    private DestinoInputView toRemove;
    private TransportePreviewView transportePreviewView;
    private OfertasInputView ofertasInputView;
    private TextView nextButtonOfertas;
    private TablaTransporte tablaTransporte;
    private TextView nextButtonPrevew;

    public TransporteController(Context context){
        this.context = context;
        this.activity = (Activity)context;
        initViewPager();
        initViews();
    }
    private void initViewPager(){
        customViewPager = new CustomViewPager(getContext());
        customViewPager.setListener(this);
    }

    private void initViews(){
        initManager();
        initOfertas();
        initPreview();
        initButtons();
    }
    private void initButtons(){
        initPreviewButton();
        nextButtonOfertas = initNextButton();
        ofertasInputView.addBottomLL(nextButtonOfertas);
    }
    private void initPreviewButton(){
        nextButtonPrevew = FontButtonBuilder.BuildButtonSolidHeight(getContext(),getContext().getResources().getString(R.string.send_icon),40);
        nextButtonPrevew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solveModel();
            }
        });
        transportePreviewView.setButton(nextButtonPrevew);
    }

    private TextView initNextButton(){
        TextView nextButton = FontButtonBuilder.BuildButtonSolidHeight(getContext(),getContext().getResources().getString(R.string.send_icon),40);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestNextPage();
            }
        });
        return nextButton;
    }
    private void initOfertas(){
        ofertasInputView = new OfertasInputView(getContext());
        destinosManager.setOfertasInputView(ofertasInputView);
        customViewPager.addPage(ofertasInputView,"Ofertas",customViewPager.getChildCount());
    }
    private void initManager(){
        destinosManager = new DestinosManager(this,new DestinoHeaderInputView(getContext(),1));
        addedDestino(destinosManager.getHeaderInputView());
    }
    private void initPreview(){
        transportePreviewView = new TransportePreviewView(getContext());
        customViewPager.addPage(transportePreviewView,"Tabla");
    }
    public Context getContext(){
        return context;
    }
    public CustomViewPager getCustomViewPager(){
        return customViewPager;
    }

    @Override
    public void closedDestino(DestinoInputView destinoInputView) {
        toRemove = destinoInputView;
        requestChangePage(destinosManager.indexOf(destinoInputView)-1);
    }

    @Override
    public void addedDestino(DestinoInputView destinoInputView) {
        int indexToAdd = Math.max(0,destinosManager.getCount()-1);
        customViewPager.addPage(destinoInputView,"Destino "+(destinosManager.getCount()),indexToAdd);
        requestChangePage(customViewPager.getCurrentItem()+1);
    }

    @Override
    public void requestAdd() {
        destinosManager.addDestino(new DestinoInputView(getContext(),destinosManager.getCount()+1,destinosManager.getOrigins()));
    }

    public void requestChangePage(int i) {

        customViewPager.smoothTransition(i);
    }

    private void solveModel(){
        Activity host = (Activity) getContext();
        Intent newIntent = new Intent(host, ResultTransporteActivity.class);
        newIntent.putExtra("tabla_transporte",tablaTransporte);
        host.startActivity(newIntent);
    }
    @Override
    public void requestNextPage() {
        requestChangePage(customViewPager.getCurrentItem()+1);
    }

    @Override
    public void finishChanging() {
        if (toRemove != null) {
            int index = customViewPager.getCurrentItem();
            customViewPager.removePage(toRemove);
            customViewPager.setCurrentItem(index);
            toRemove = null;
        }
        ViewGroup currentViewGroup = customViewPager.get(customViewPager.getCurrentItem());
        if (currentViewGroup instanceof DestinoInputView){
            ((DestinoInputView)currentViewGroup).setFocus();
        }else if (currentViewGroup instanceof TransportePreviewView){
            try {
                tablaTransporte = destinosManager.getTabla();
                transportePreviewView.renderTable(tablaTransporte);
            } catch (ExceptionTransporteInput e){
                e.printStackTrace();
            }
        }
    }
}
