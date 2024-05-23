package uz.itmade.agrodatacollector.ui.notifications

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.itmade.agrodatacollector.data.ImageData
import uz.itmade.agrodatacollector.data.ProductData
import uz.itmade.agrodatacollector.model.Repository

class NotificationsViewModel : ViewModel() {

    private val _messageLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> get() = _messageLiveData
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    val productsLiveData= MediatorLiveData<List<ProductData>>()

    private val products = Repository.getProductRepo
    init {
        _isLoading.value = true

        productsLiveData.addSource(products.getAll2("products")){
            _isLoading.value= false
            if (it.isSuccess){
                Log.d("|Tag",it.getOrNull()!!.toString())
                productsLiveData.value=it.getOrNull()!!
            }
        }
    }
}