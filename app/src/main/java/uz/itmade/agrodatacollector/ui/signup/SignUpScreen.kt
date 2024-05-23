package uz.itmade.agrodatacollector.ui.signup

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
import uz.itmade.agrodatacollector.R
import uz.itmade.agrodatacollector.ui.signin.SignInScreenDirections
import uz.itmade.agrodatacollector.utils.withIcon

class SignUpScreen : Fragment() {

    private lateinit var progressBar: ProgressBar
    private val navController : NavController by lazy { findNavController() }

    private val viewModel: SignUpScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.messageLiveData.observe(this){
            withIcon(it,requireActivity())
        }

        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        viewModel.openLoginScreen.observe(this){
            navController.navigateUp()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_sign_up_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val buttonLogin = view.findViewById<TextView>(R.id.buttonLogin)
        val buttonRegister = view.findViewById<TextView>(R.id.buttonRegister)
        val phoneText = view.findViewById<EditText>(R.id.editTextPhone)
        val nameText = view.findViewById<EditText>(R.id.editTextName)
        val passwordText = view.findViewById<EditText>(R.id.editTextPassword)
        progressBar = view.findViewById(R.id.progressBar)

        buttonRegister.setOnClickListener {
            if (phoneText.text.isNullOrEmpty()){
                withIcon("Iltimos telefon raqamni kiriting!!!",requireActivity())
                return@setOnClickListener
            }

            if (passwordText.text.isNullOrEmpty()){
                withIcon("Iltimos parolni  kiriting!!!",requireActivity())
                return@setOnClickListener
            }
            if (nameText.text.isNullOrEmpty()){
                withIcon("Iltimos ismingizni kiriting!!!",requireActivity())
                return@setOnClickListener
            }

            viewModel.register(name = nameText.text.toString(),phone=phoneText.text.toString(), password = passwordText.text.toString())

        }
        buttonLogin.setOnClickListener {
            viewModel.openLogin()
        }
    }
}