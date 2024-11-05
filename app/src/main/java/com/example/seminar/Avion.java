package com.example.seminar;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

public class Avion implements Parcelable {
    private String marca;
    private String model;
    private int nrMaxPasageri;
    private float greutate;
    private boolean areMotorina;

    public Avion() {
        this.marca = "Boeing";
        this.model = "777";
        this.nrMaxPasageri = 150;
        this.greutate = 3.5f;
        this.areMotorina = true;
    }

    public Avion(String marca, String model, int nrMaxPasageri, float greutate, boolean areMotorina) {
        this.marca = marca;
        this.model = model;
        this.nrMaxPasageri = nrMaxPasageri;
        this.greutate = greutate;
        this.areMotorina = areMotorina;
    }


    protected Avion(Parcel in) {
        marca = in.readString();
        model = in.readString();
        nrMaxPasageri = in.readInt();
        greutate = in.readFloat();
        areMotorina = in.readByte() != 0;
    }

    public static final Creator<Avion> CREATOR = new Creator<Avion>() {
        @Override
        public Avion createFromParcel(Parcel in) {
            return new Avion(in);
        }

        @Override
        public Avion[] newArray(int size) {
            return new Avion[size];
        }
    };

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getNrMaxPasageri() {
        return nrMaxPasageri;
    }

    public void setNrMaxPasageri(int nrMaxPasageri) {
        this.nrMaxPasageri = nrMaxPasageri;
    }

    public float getGreutate() {
        return greutate;
    }

    public void setGreutate(float greutate) {
        this.greutate = greutate;
    }

    public boolean isAreMotorina() {
        return areMotorina;
    }

    public void setAreMotorina(boolean areMotorina) {
        this.areMotorina = areMotorina;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(marca);
        dest.writeString(model);
        dest.writeInt(nrMaxPasageri);
        dest.writeFloat(greutate);
        dest.writeByte((byte) (areMotorina ? 1 : 0));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Avion{");
        sb.append("marca='").append(marca).append('\'');
        sb.append(", model='").append(model).append('\'');
        sb.append(", nrMaxPasageri=").append(nrMaxPasageri);
        sb.append(", greutate=").append(greutate);
        sb.append(", areMotorina=").append(areMotorina);
        sb.append('}');
        return sb.toString();
    }
}