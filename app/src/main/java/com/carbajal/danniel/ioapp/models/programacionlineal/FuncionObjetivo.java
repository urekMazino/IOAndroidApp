package com.carbajal.danniel.ioapp.models.programacionlineal;

import android.os.Parcel;
import android.os.Parcelable;

import com.carbajal.danniel.ioapp.support.StringManipulation;

import java.util.ArrayList;


public class FuncionObjetivo implements Parcelable{

	private ArrayList<Double> variablesDecision = new ArrayList<Double>();
	private boolean maximizar = true;

	public FuncionObjetivo(double...valoresDeVariables){
		agregarVariableDecision(valoresDeVariables);
	}

	public FuncionObjetivo(Boolean maximizar, double...valoresDeVariables){
		this(valoresDeVariables);
		setMaximizar(maximizar);
	}

	protected FuncionObjetivo(Parcel in) {
		maximizar = in.readByte() != 0;
		variablesDecision = (ArrayList<Double>)in.readSerializable();
	}

	public static final Creator<FuncionObjetivo> CREATOR = new Creator<FuncionObjetivo>() {
		@Override
		public FuncionObjetivo createFromParcel(Parcel in) {
			return new FuncionObjetivo(in);
		}

		@Override
		public FuncionObjetivo[] newArray(int size) {
			return new FuncionObjetivo[size];
		}
	};

	public void agregarVariableDecision(double...valoresDeVariables){
		for (double valor:valoresDeVariables){
			variablesDecision.add(valor);
		}
	}
	public void setMaximizar(boolean maximizar){
		this.maximizar = maximizar;
	}
	public boolean getMaximizar(){
		return maximizar;
	}
	public ArrayList<Double> getVariablesRestriccion(){
		return variablesDecision;
	}

    public String getString(){
		String functionPreviewStr = (maximizar)?"Max":"Min";
        functionPreviewStr += " Z =";
        int i = 1;
        for (double x : variablesDecision) {
            functionPreviewStr += StringManipulation.coeficientToVariable(x,i);
            i++;
        }
        return functionPreviewStr;
    }

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeByte((byte) (maximizar ? 1 : 0));
		dest.writeSerializable(variablesDecision);
	}
}
