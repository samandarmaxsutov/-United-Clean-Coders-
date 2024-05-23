package uz.itmade.agrodatacollector.model

object Repository {
    val getUserRepo:GetUsersRepo by lazy {  GetUsersRepoImpl()}
    val getImageRepo:GetImageRepo by lazy {  GetImageRepoImpl()}

}