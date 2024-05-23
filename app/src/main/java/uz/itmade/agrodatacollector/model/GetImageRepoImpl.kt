package uz.itmade.agrodatacollector.model
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import uz.itmade.agrodatacollector.data.ImageData



class GetImageRepoImpl : GetImageRepo {
    private val db = Firebase.firestore
    override val progressBar= MutableLiveData<Boolean>()

    override fun getAll(collectionName:String): LiveData<Result<List<ImageData>>> {
        progressBar.value=true
        val liveData = MutableLiveData<Result<List<ImageData>>>()
        db.collection(collectionName)
            .get().addOnSuccessListener{
                val ls = it.documents.map { item -> Mapper.run { item.toImageData()
                } }

                liveData.value = Result.success(ls)
            }
            .addOnFailureListener { liveData.value = Result.failure(it) }

        return liveData
    }

    override fun getAll2(collectionName:String): LiveData<Result<List<ImageData>>> {
        progressBar.value=true
        val liveData = MediatorLiveData<Result<List<ImageData>>>()
        liveData.addDisposable(getAll(collectionName)) { liveData.value = it }

        db.collection(collectionName).addSnapshotListener { snapshot, e ->
            liveData.addDisposable(getAll(collectionName)) { liveData.value = it }
        }

        return liveData
    }

    override fun update(imageData: ImageData,collectionName: String): LiveData<Result<Unit>> {
        progressBar.value=true
        val liveData = MutableLiveData<Result<Unit>>()

        db.collection(collectionName).document(imageData.id).set(imageData)
            .addOnCompleteListener {
            }
            .addOnSuccessListener {
                liveData.value = Result.success(Unit)
            }
            .addOnFailureListener {
                liveData.value = Result.failure(it)
            }
        return liveData
    }

    override fun add(imageData: ImageData): LiveData<Result<Unit>> {
        progressBar.value=true
        val liveData = MutableLiveData<Result<Unit>>()

        db.collection("images").document(imageData.id).set(imageData)
            .addOnCompleteListener {
            }
            .addOnSuccessListener {
                liveData.value = Result.success(Unit)
            }
            .addOnFailureListener {
                liveData.value = Result.failure(it)
            }
        return liveData
    }
}