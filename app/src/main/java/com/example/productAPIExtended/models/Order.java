package com.example.productAPIExtended.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Order implements Parcelable, Serializable {
    @SerializedName("username")
    @Expose
    String username;

    @SerializedName("address")
    @Expose
    String addressUser;

    @SerializedName("image")
    @Expose
    String modelImage;

    @SerializedName("title")
    @Expose
    String nameProduct;

    @SerializedName("price")
    @Expose
    String priceProduct;

    @SerializedName("counterProduct")
    @Expose
    int counterProduct;

    public Order(String username, String addressUser, String modelImage, String nameProduct, String priceProduct, int counterProduct) {
        this.username = username;
        this.addressUser = addressUser;
        this.modelImage = modelImage;
        this.nameProduct = nameProduct;
        this.priceProduct = priceProduct;
        this.counterProduct = counterProduct;
    }

    protected Order(Parcel in) {
        username = in.readString();
        addressUser = in.readString();
        modelImage = in.readString();
        nameProduct = in.readString();
        priceProduct = in.readString();
        counterProduct = in.readInt();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddressUser() {
        return addressUser;
    }

    public void setAddressUser(String addressUser) {
        this.addressUser = addressUser;
    }

    public String getModelImage() {
        return modelImage;
    }

    public void setModelImage(String modelImage) {
        this.modelImage = modelImage;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(String priceProduct) {
        this.priceProduct = priceProduct;
    }

    public int getCounterProduct() {
        return counterProduct;
    }

    public void setCounterProduct(int counterProduct) {
        this.counterProduct = counterProduct;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(addressUser);
        dest.writeString(modelImage);
        dest.writeString(nameProduct);
        dest.writeString(priceProduct);
        dest.writeInt(counterProduct);
    }
}
