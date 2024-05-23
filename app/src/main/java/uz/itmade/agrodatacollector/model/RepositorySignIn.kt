package uz.itmade.agrodatacollector.model

import uz.itmade.agrodatacollector.sharedpref.SharedPref

class RepositorySignIn {
    private val  sharedPref= SharedPref.getInstance()

    companion object {
        private var repsosiory:RepositorySignIn?=null
        fun init(){
            if (repsosiory==null){
                repsosiory= RepositorySignIn()
            }
        }
        fun getInstance()= repsosiory!!
    }

    fun getIsFirst(): Boolean {
        return sharedPref.getIsFirst()
    }

    fun setIsFirst() {
        sharedPref.setIsFirst()
    }

    fun getPhone(): String {
        return sharedPref.getPhone()!!
    }

    fun setPhone(phone:String) {
        sharedPref.setPhone(phone)
    }
}