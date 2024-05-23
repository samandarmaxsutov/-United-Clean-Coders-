package uz.itmade.agrodatacollector.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import uz.itmade.agrodatacollector.MainActivity
import uz.itmade.agrodatacollector.R


@SuppressLint("CustomSplashScreen")
class SplashScreen:Fragment(R.layout.screen_splash) {
    private val viewModel by viewModels<SplashScreenViewModel>()
    private val navController :NavController by lazy { findNavController() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openSignInScreenLiveData.observe(this){
            navController.navigate(SplashScreenDirections.actionSplashScreenToSignInScreen())
        }
        viewModel.openMainScreenLiveData.observe(this){
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}