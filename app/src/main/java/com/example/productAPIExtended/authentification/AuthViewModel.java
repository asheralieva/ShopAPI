package com.example.productAPIExtended.authentification;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AuthViewModel extends ViewModel {
    MutableLiveData<String> token = new MutableLiveData<>();
}