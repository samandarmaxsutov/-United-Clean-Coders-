package uz.itmade.agrodatacollector.ui.signin

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.tapadoo.alerter.Alerter
import uz.itmade.agrodatacollector.MainActivity
import uz.itmade.agrodatacollector.R
import uz.itmade.agrodatacollector.ui.splash.SplashScreenDirections
import uz.itmade.agrodatacollector.utils.withIcon

class SignInScreen : Fragment() {
    private lateinit var progressBar: ProgressBar
    private val navController : NavController by lazy { findNavController() }
    private val viewModel: SignInScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.messageLiveData.observe(this){
            withIcon(it,requireActivity())
        }

        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        viewModel.openMainScreen.observe(this) {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        viewModel.openRegisterScreen.observe(this){
            navController.navigate(SignInScreenDirections.actionSignInScreenToSignUpScreen())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_sign_in_screen, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val buttonLogin = view.findViewById<TextView>(R.id.buttonLogin)
        val buttonRegister = view.findViewById<TextView>(R.id.buttonRegister)
        val phoneText = view.findViewById<EditText>(R.id.editTextPhone)
        val passwordText = view.findViewById<EditText>(R.id.editTextPassword)
        progressBar = view.findViewById(R.id.progressBar)


        buttonLogin.setOnClickListener {
            if (phoneText.text.isNullOrEmpty()){
                withIcon("Iltimos telefon raqamni kiriting!!!",requireActivity())
                return@setOnClickListener
            }

            if (passwordText.text.isNullOrEmpty()){
                withIcon("Iltimos parolni  kiriting!!!",requireActivity())
                return@setOnClickListener
            }

            viewModel.login(phoneText.text.toString(),passwordText.text.toString())

        }
        buttonRegister.setOnClickListener {
            viewModel.openRegisterScreen()
        }
    }


}