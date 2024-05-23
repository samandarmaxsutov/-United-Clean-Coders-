package uz.itmade.agrodatacollector.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.itmade.agrodatacollector.data.ImageData
import uz.itmade.agrodatacollector.model.Repository

class DashboardViewModel : ViewModel() {

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> get() = _messageLiveData
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    val imagesLiveData= MediatorLiveData<List<ImageData>>()

    private val images = Repository.getImageRepo
    init {
        _isLoading.value = true

        imagesLiveData.addSource(images.getAll2("images")){
            _isLoading.value= false
            if (it.isSuccess){
                Log.d("|Tag",it.getOrNull()!!.toString())
                imagesLiveData.value=it.getOrNull()!!
            }
        }
    }
}