package com.revolut.ratesapp.dagger;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.revolut.ratesapp.Rest.Repository;
import com.revolut.ratesapp.ViewModels.RatesViewModel;

import javax.inject.Inject;

public class ViewModelFactory implements ViewModelProvider.Factory  {

    public Repository repository;

    @Inject
    public ViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(RatesViewModel.class)){
            return (T) new RatesViewModel(repository);
        }

        throw new IllegalArgumentException("Unknown class name");
    }


}
