package com.carbajal.danniel.ioapp.models.programacionlineal.transporte;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by daniel on 11/25/16.
 */

public
class Asignacion implements Parcelable {
    private double elementos;
    private int r,c;

    public Asignacion( double elementos, int r,int c) {
        this.c = c;
        this.elementos = elementos;
        this.r = r;
    }

    protected Asignacion(Parcel in) {
        elementos = in.readDouble();
        r = in.readInt();
        c = in.readInt();
    }

    public static final Creator<Asignacion> CREATOR = new Creator<Asignacion>() {
        @Override
        public Asignacion createFromParcel(Parcel in) {
            return new Asignacion(in);
        }

        @Override
        public Asignacion[] newArray(int size) {
            return new Asignacion[size];
        }
    };

    public int getC() {
        return c;
    }

    public double getElementos() {
        return elementos;
    }

    public int getR() {
        return r;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(elementos);
        dest.writeInt(r);
        dest.writeInt(c);
    }
}