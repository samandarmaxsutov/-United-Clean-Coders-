package uz.itmade.agrodatacollector.data

import java.util.UUID

data class ImageData(
    val id:String = UUID.randomUUID().toString(),
    val imageLink: String,
    val description:String,
    val userId:String
)