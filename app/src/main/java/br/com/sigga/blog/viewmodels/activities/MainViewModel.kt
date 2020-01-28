package br.com.sigga.blog.viewmodels.activities

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.koin.core.KoinComponent

class MainViewModel : ViewModel(), KoinComponent {


    //region ~~~~ Factory ~~~~
    class Factory(
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = MainViewModel() as T
    }
    //endregion
}