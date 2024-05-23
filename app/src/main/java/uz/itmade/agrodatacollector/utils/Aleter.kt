package uz.itmade.agrodatacollector.utils

import android.app.Activity
import android.content.Context
import com.tapadoo.alerter.Alerter
import uz.itmade.agrodatacollector.R

fun withIcon(text:String, context:Activity) {
    Alerter.create(context)
        .setText(text)
        .setIcon(R.drawable.notification)
        .setBackgroundColorRes(R.color.aleter)

        .show()
}