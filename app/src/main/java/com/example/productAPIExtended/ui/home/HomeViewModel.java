package com.example.productAPIExtended.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.productAPIExtended.Repository.JemRepository;
import com.example.productAPIExtended.models.ModelM;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private JemRepository jemRepository;
    private LiveData<List<ModelM>> modelMResponseLiveData;

    public HomeViewModel() {
        jemRepository = new JemRepository();
        modelMResponseLiveData = jemRepository.getDashJeminyList();
    }

    public LiveData<List<ModelM>> getModelMResponseLiveData() {
        return modelMResponseLiveData;
    }

}