package uz.itmade.agrodatacollector.model

import uz.itmade.agrodatacollector.data.GetProductsRepoImpl

object Repository {
    val getUserRepo:GetUsersRepo by lazy {  GetUsersRepoImpl()}
    val getImageRepo:GetImageRepo by lazy {  GetImageRepoImpl()}
    val getProductRepo:GetProductsRepo by lazy { GetProductsRepoImpl()}

}