package uz.itmade.agrodatacollector.model

import androidx.lifecycle.LiveData
import uz.itmade.agrodatacollector.data.ImageData
import uz.itmade.agrodatacollector.data.UserData


interface GetImageRepo {
    val progressBar: LiveData<Boolean>

    fun getAll(collectionName:String): LiveData<Result<List<ImageData>>>
    fun getAll2(collectionName:String): LiveData<Result<List<ImageData>>>

    fun update(imageData: ImageData,collectionName: String): LiveData<Result<Unit>>
    fun add(imageData: ImageData):LiveData<Result<Unit>>
}