package uz.itmade.agrodatacollector.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.google.firebase.firestore.DocumentSnapshot
import uz.itmade.agrodatacollector.data.ImageData
import uz.itmade.agrodatacollector.data.UserData

object Mapper {
    fun DocumentSnapshot.toUserData()=UserData(
        id = getString("id") ?: "",
        name = getString("name")?:"",
        phone = getString("phone")?:"",
        agroCoin = getLong("agro_coin")?:0,
        telegramUserId = getString("telegram_user_id")?:"",
        password = getString("password")?:""
    )
    fun DocumentSnapshot.toImageData()=ImageData(
        id = getString("id") ?: "",
        imageLink = getString("image_link")?:"",
        description = getString("description")?:"",
        userId = getString("user_id")?:""
    )

}

fun <T, K> MediatorLiveData<T>.addDisposable(source: LiveData<K>, block: Observer<K>) {
    addSource(source) {
        block.onChanged(it)
        removeSource(source)
    }
}