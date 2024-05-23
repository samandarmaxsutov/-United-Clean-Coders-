package uz.itmade.agrodatacollector.ui.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.itmade.agrodatacollector.data.UserData
import uz.itmade.agrodatacollector.model.Repository

class SignUpScreenViewModel : ViewModel() {
    private val _openMainScreen = MediatorLiveData<Unit>()
    val openMainScreen: LiveData<Unit> get() = _openMainScreen

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> get() = _messageLiveData
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    val openLoginScreen= MediatorLiveData<Unit>()

    private val users = Repository.getUserRepo
    fun register(name:String,phone: String, password: String) {
        _isLoading.value = true
        val user = UserData(phone=phone, name = name, telegramUserId = "", password = password)
        Log.d("AddUser", "Failed to add user:")
        openLoginScreen.addSource(users.add(user)){
            _isLoading.value = false
            if (it.isSuccess){
                Log.d("AddUser", "Failed to add user:$it")
                openLoginScreen.value=Unit
                _messageLiveData.value="Siz Ro'yhatdan o'tdingiz"
            }else{
                Log.d("AddUser", "Failed to add user:$it")
                _messageLiveData.value="Siz Ro'yhatdan o'ta olmadizngiz"
            }
        }
    }

    fun openLogin(){
        openLoginScreen.value=Unit
    }
}