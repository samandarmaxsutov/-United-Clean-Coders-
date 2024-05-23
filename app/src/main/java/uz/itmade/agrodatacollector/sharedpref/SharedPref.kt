package uz.itmade.agrodatacollector.sharedpref

import android.content.Context
import android.content.SharedPreferences

class SharedPref private constructor(){
    companion object{
        private const val isFirst="isFirst"
        private const val phone="phone"
        private var sharedPref:SharedPref?=null
        private var sharedPreferences:SharedPreferences?=null
        fun init(context: Context){
            if (sharedPref==null){
                sharedPref=SharedPref()
                sharedPreferences=context.getSharedPreferences("SharedPref", Context.MODE_PRIVATE)
            }
        }
        fun getInstance()= sharedPref!!

    }

    fun getIsFirst():Boolean{
        return sharedPreferences!!.getBoolean(isFirst,true)
    }
    fun setIsFirst(){
        sharedPreferences!!.edit().putBoolean(isFirst,false).apply()
    }

    fun getPhone(): String? {
        return sharedPreferences!!.getString(phone,"")
    }
    fun setPhone(phone1:String){
        sharedPreferences!!.edit().putString(phone,phone1).apply()
    }

}