package com.carbajal.danniel.ioapp.models.programacionlineal;
import android.os.Parcel;
import android.os.Parcelable;

import com.carbajal.danniel.ioapp.support.StringManipulation;

import java.util.ArrayList;


public class Restriccion implements Parcelable{
	/*
		restriccion representacion A1X1 + A2X2 + .. + AnXn = expresionNumerica
		donde Ai es el coeficiente de cada variable de restriccion
	*/
	private ArrayList<Double> variablesDecision = new ArrayList<Double>();

	private double expresionNumerica =0;
	private TiposIgualdades _igualdad = TiposIgualdades.MENOR_IGUAL;
	
	public Restriccion(double expresionNumerica,double...valoresDeVariables){
		agregarVariableRestriccion(valoresDeVariables);
		setExpresionNumerica(expresionNumerica);
	}
	public Restriccion(double expresionNumerica){
		setExpresionNumerica(expresionNumerica);
	}

	protected Restriccion(Parcel in) {
		expresionNumerica = in.readDouble();
		variablesDecision = (ArrayList<Double>)in.readSerializable();
		_igualdad = (TiposIgualdades)in.readSerializable();
	}

	public static final Creator<Restriccion> CREATOR = new Creator<Restriccion>() {
		@Override
		public Restriccion createFromParcel(Parcel in) {
			return new Restriccion(in);
		}

		@Override
		public Restriccion[] newArray(int size) {
			return new Restriccion[size];
		}
	};

	public void agregarVariableRestriccion(double...valoresDeVariables){
		for (double valor:valoresDeVariables){
			variablesDecision.add(valor);
		}
	}
	public void setExpresionNumerica(double expresionNumerica){
		this.expresionNumerica =expresionNumerica;
	}
	public double getExpresionNumerica(){
		return expresionNumerica;
	}
	public void setIgualdad(TiposIgualdades igualdad){
		_igualdad = igualdad;
	}
	public void setIgualdad(int igualdad){
		switch (igualdad){
			case 0:
				_igualdad = TiposIgualdades.MENOR;
				break;
			case 1:
				_igualdad = TiposIgualdades.MENOR_IGUAL;
				break;
			case 2:
				_igualdad = TiposIgualdades.IGUAL;
				break;
			case 3:
				_igualdad = TiposIgualdades.MAYOR_IGUAL;
				break;
			case 4:
				_igualdad = TiposIgualdades.MAYOR;
				break;
		}
	}
	public TiposIgualdades getIgualdad(){
		return _igualdad;
	}
	public ArrayList<Double> getVariablesRestriccion(){
		return variablesDecision;
	}

    public String getString(){
        String functionPreviewStr = "";
        int i = 1;
        for (double x : variablesDecision) {
			if (x!=0) {
				if (functionPreviewStr.length()==0)
					functionPreviewStr += StringManipulation.firstCoeficientToVariable(x, i);
				else
					functionPreviewStr += StringManipulation.coeficientToVariable(x, i);
			}
			i++;
        }
        functionPreviewStr += " "+getIgualdadString()+" "+ expresionNumerica;
        return functionPreviewStr;
    }

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDouble(expresionNumerica);
		dest.writeSerializable(variablesDecision);
		dest.writeSerializable(_igualdad);
	}

	public String getIgualdadString(){
		String toReturn = "";
		switch (_igualdad){
			case MENOR:
				toReturn = "<";
				break;
			case MENOR_IGUAL:
				toReturn = "≤";
				break;
			case IGUAL:
				toReturn = "=";
				break;
			case MAYOR_IGUAL:
				toReturn = "≥";
				break;
			case MAYOR:
				toReturn = ">";
				break;

		}
		return toReturn;

	}
}
