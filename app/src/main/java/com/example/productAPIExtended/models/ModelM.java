package com.example.productAPIExtended.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ModelM implements Parcelable, Serializable {
    @SerializedName("id")
    @Expose
    int modelId;
    @SerializedName("title")
    @Expose
    String modelTitle;
    @SerializedName("price")
    @Expose
    Double price_d;
    @SerializedName("description")
    @Expose
    String modelDescription;
    @SerializedName("image")
    @Expose
    String modelImage;

    @SerializedName("counterProduct")
    @Expose
    int counterProduct;


    protected ModelM(Parcel in) {
        modelId = in.readInt();
        modelTitle = in.readString();
        if (in.readByte() == 0) {
            price_d = null;
        } else {
            price_d = in.readDouble();
        }
        modelDescription = in.readString();
        modelImage = in.readString();
        counterProduct = in.readInt();
    }

    public static final Creator<ModelM> CREATOR = new Creator<ModelM>() {
        @Override
        public ModelM createFromParcel(Parcel in) {
            return new ModelM(in);
        }

        @Override
        public ModelM[] newArray(int size) {
            return new ModelM[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(modelId);
        dest.writeString(modelTitle);
        if (price_d == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(price_d);
        }
        dest.writeString(modelDescription);
        dest.writeString(modelImage);
        dest.writeInt(counterProduct);
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public String getModelTitle() {
        return modelTitle;
    }

    public void setModelTitle(String modelTitle) {
        this.modelTitle = modelTitle;
    }

    public Double getPrice_d() {
        return price_d;
    }

    public void setPrice_d(Double price_d) {
        this.price_d = price_d;
    }

    public String getModelDescription() {
        return modelDescription;
    }

    public void setModelDescription(String modelDescription) {
        this.modelDescription = modelDescription;
    }

    public String getModelImage() {
        return modelImage;
    }

    public void setModelImage(String modelImage) {
        this.modelImage = modelImage;
    }

    public int getCounterProduct() {
        return counterProduct;
    }

    public void setCounterProduct(int counterProduct) {
        this.counterProduct = counterProduct;
    }

    public ModelM(int modelId, String modelTitle, Double price_d, String modelDescription, String modelImage, int counterProduct) {
        this.modelId = modelId;
        this.modelTitle = modelTitle;
        this.price_d = price_d;
        this.modelDescription = modelDescription;
        this.modelImage = modelImage;
        this.counterProduct = counterProduct;
    }
}
