package uz.itmade.agrodatacollector.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.itmade.agrodatacollector.model.RepositorySignIn

class SplashScreenViewModel: ViewModel() {
        private val repository = RepositorySignIn.getInstance()
        val openMainScreenLiveData=MutableLiveData<Unit>()
        val openSignInScreenLiveData=MutableLiveData<Unit>()

        init {
                viewModelScope.launch {
                        delay(2000)
                        if (repository.getPhone()=="") {

                                openSignInScreenLiveData.value=Unit
                        }
                        else{
                                openMainScreenLiveData.value = Unit
                        }
                }

        }
}