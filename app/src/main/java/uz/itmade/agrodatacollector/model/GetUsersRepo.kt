package uz.itmade.agrodatacollector.model

import androidx.lifecycle.LiveData
import uz.itmade.agrodatacollector.data.UserData


interface GetUsersRepo {
    val progressBar: LiveData<Boolean>

    fun getAll(collectionName:String): LiveData<Result<List<UserData>>>
    fun getAll2(collectionName:String): LiveData<Result<List<UserData>>>
    fun findUser(collectionName:String,phone:String, password:String): LiveData<Result<UserData>>
    fun update(userData: UserData,collectionName: String): LiveData<Result<Unit>>
    fun updateAgroCoin(userId:String,coin:Int): LiveData<Result<Unit>>
    fun add(userData: UserData):LiveData<Result<Unit>>
    fun get(id:String):LiveData<Result<UserData>>
}