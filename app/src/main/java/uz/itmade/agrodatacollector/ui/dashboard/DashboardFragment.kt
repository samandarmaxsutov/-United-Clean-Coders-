package uz.itmade.agrodatacollector.ui.dashboard


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import uz.itmade.agrodatacollector.R
import uz.itmade.agrodatacollector.data.ImageData
import uz.itmade.agrodatacollector.data.UserData
import uz.itmade.agrodatacollector.databinding.FragmentDashboardBinding
import uz.itmade.agrodatacollector.model.Repository
import uz.itmade.agrodatacollector.model.RepositorySignIn
import uz.itmade.agrodatacollector.utils.withIcon
import java.util.UUID

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {


    private lateinit var description: EditText
    private lateinit var addImage: TextView
    private lateinit var submit: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var selectedImageView: ImageView
    private val repository = RepositorySignIn.getInstance()
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var imageUri: Uri
    private lateinit var storageReference: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

       description = view.findViewById<EditText>(R.id.editTextDescription)
         addImage = view.findViewById(R.id.buttonSelectImage)
         submit = view.findViewById(R.id.buttonSubmit)
         selectedImageView = view.findViewById(R.id.imageView)

        // Initialize Firebase Storage reference
        storageReference = FirebaseStorage.getInstance().reference
        progressBar = view.findViewById(R.id.progressBar)

        addImage.setOnClickListener {
            openFileChooser()
        }

        submit.setOnClickListener {



            if (description.text.isEmpty()) {
                withIcon("Iltimos rasm haqida ma'lumot bering",requireActivity())
                return@setOnClickListener
            }




            progressBar.visibility=View.VISIBLE
            submit.isClickable=false
            addImage.isClickable=false
            uploadImageToFirebase(imageUri) { uploadedImageUrl ->

                val image = ImageData(imageLink = uploadedImageUrl, description=description.text.toString(), userId = repository.getPhone().toString())
                Repository.getImageRepo.add(image).observe(viewLifecycleOwner){
                    submit.isClickable=true
                    addImage.isClickable=true
                    progressBar.visibility=View.GONE
                    if (it.isSuccess) {

                        withIcon(description.text.toString()+" qo'shildi ",requireActivity())
                        description.setText("")
                        selectedImageView.setImageResource(R.drawable.baseline_add_a_photo_24)
                    } else {
                        withIcon(description.text.toString()+" qo'shilmadi ",requireActivity())

                    }
                }

            }


            Repository.getUserRepo.updateAgroCoin(RepositorySignIn.getInstance().getPhone(),2)

        }
    }

    private fun openFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data!!
            selectedImageView.setImageURI(imageUri)
        }
    }

    private fun uploadImageToFirebase(fileUri: Uri, onSuccess: (String) -> Unit) {
        val fileReference = storageReference.child("products/${UUID.randomUUID()}.jpg")
        fileReference.putFile(fileUri)
            .addOnSuccessListener {
                fileReference.downloadUrl.addOnSuccessListener { uri ->
                    onSuccess(uri.toString())
                }
            }
            .addOnFailureListener {

            }
    }



}