package uz.itmade.agrodatacollector.app

import android.app.Application
import uz.itmade.agrodatacollector.model.RepositorySignIn
import uz.itmade.agrodatacollector.sharedpref.SharedPref


class App:Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPref.init(this)
        RepositorySignIn.init()
    }
}