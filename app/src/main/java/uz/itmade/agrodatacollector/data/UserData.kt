package uz.itmade.agrodatacollector.data

import java.util.UUID

data class UserData(
    val id:String = UUID.randomUUID().toString(),
    val name: String,
    val phone: String,
    var agroCoin:Long=0,
    val telegramUserId:String,
    val password :String
    )
