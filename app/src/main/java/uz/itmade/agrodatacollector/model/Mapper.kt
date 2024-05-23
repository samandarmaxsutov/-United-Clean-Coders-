package uz.itmade.agrodatacollector.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.google.firebase.firestore.DocumentSnapshot
import uz.itmade.agrodatacollector.data.ImageData
import uz.itmade.agrodatacollector.data.ProductData
import uz.itmade.agrodatacollector.data.UserData

object Mapper {
    fun DocumentSnapshot.toUserData()=UserData(
        id = getString("id") ?: "",
        name = getString("name")?:"",
        phone = getString("phone")?:"",
        agroCoin = getLong("agroCoin")?:0,
        telegramUserId = getString("telegramUserId")?:"",
        password = getString("password")?:""
    )
    fun DocumentSnapshot.toImageData()=ImageData(
        id = getString("id") ?: "",
        imageLink = getString("imageLink")?:"",
        description = getString("description")?:"",
        userId = getString("userId")?:""
    )
    fun DocumentSnapshot.toProductData()=ProductData(
        id = getString("id") ?: "",
        name = getString("name")?:"",
        price = getString("price")?:"",
        imageUrl = getString("imageUrl")?:""
    )


}

fun <T, K> MediatorLiveData<T>.addDisposable(source: LiveData<K>, block: Observer<K>) {
    addSource(source) {
        block.onChanged(it)
        removeSource(source)
    }
}