package uz.itmade.agrodatacollector.ui.signin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.itmade.agrodatacollector.model.Repository
import uz.itmade.agrodatacollector.model.RepositorySignIn

class SignInScreenViewModel : ViewModel() {
    private val repository = RepositorySignIn.getInstance()
    private val _openMainScreen = MediatorLiveData<Unit>()
    val openMainScreen: LiveData<Unit> get() = _openMainScreen

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> get() = _messageLiveData
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    val openRegisterScreen=MutableLiveData<Unit>()

    private val users =Repository.getUserRepo
    fun login(phone: String, password: String) {
        _isLoading.value = true

        _openMainScreen.addSource(users.findUser("users", phone, password)) { result ->
            Log.d("loginObserver", "Result received")

            _isLoading.value = false

            if (result.isSuccess) {
                repository.setPhone(result.getOrNull()!!.id)
                _messageLiveData.value = "Xush kelibsiz"
                _openMainScreen.value = Unit
                Log.d("loginObserver", "Login successful: ${result.getOrNull()}")
            } else {
                _messageLiveData.value = "Foydalanuvchi topilmadi"
                Log.d("loginObserver", "Login failed: ${result.exceptionOrNull()?.message}")
            }
        }
    }

    fun openRegisterScreen(){
        openRegisterScreen.value=Unit
    }
}