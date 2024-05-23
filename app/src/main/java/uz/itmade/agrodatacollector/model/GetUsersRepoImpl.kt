package uz.itmade.agrodatacollector.model
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import uz.itmade.agrodatacollector.data.UserData
import uz.itmade.agrodatacollector.model.Mapper.toUserData
import java.lang.Exception


class GetUsersRepoImpl : GetUsersRepo {
    private val db = Firebase.firestore
    override val progressBar= MutableLiveData<Boolean>()

    override fun getAll(collectionName:String): LiveData<Result<List<UserData>>> {
        progressBar.value=true
        val liveData = MutableLiveData<Result<List<UserData>>>()
        db.collection(collectionName)
            .get().addOnSuccessListener{
                val ls = it.documents.map { item -> Mapper.run { item.toUserData()
                } }
                Log.d("Users",it.documents.toString())
                liveData.value = Result.success(ls)
            }
            .addOnFailureListener { liveData.value = Result.failure(it) }

        return liveData
    }

    override fun getAll2(collectionName:String): LiveData<Result<List<UserData>>> {
        progressBar.value=true
        val liveData = MediatorLiveData<Result<List<UserData>>>()
        liveData.addDisposable(getAll(collectionName)) { liveData.value = it }

        db.collection(collectionName).addSnapshotListener { snapshot, e ->
            liveData.addDisposable(getAll(collectionName)) { liveData.value = it }
        }

        return liveData
    }

    override fun findUser(collectionName: String, phone: String, password: String): LiveData<Result<UserData>> {
        val userData = MutableLiveData<Result<UserData>>()
        Log.d("findUser", "Finding user with phone: $phone")

        db.collection(collectionName)
            .whereEqualTo("phone", phone)
            .whereEqualTo("password", password)
            .get()
            .addOnSuccessListener { result ->
                val user = result.documents
                    .map { Mapper.run { it.toUserData() } }
                    .firstOrNull()

                if (user != null) {
                    Log.d("findUser", "User found: ${user.phone}")
                    userData.value = Result.success(user)
                } else {
                    Log.d("findUser", "User not found or incorrect password")
                    userData.value = Result.failure(Exception("User not found or incorrect password"))
                }
            }
            .addOnFailureListener { exception ->
                Log.e("findUser", "Error fetching user", exception)
                userData.value = Result.failure(exception)
            }

        return userData
    }




    override fun update(userData: UserData,collectionName: String): LiveData<Result<Unit>> {
        progressBar.value=true
        val liveData = MutableLiveData<Result<Unit>>()

        db.collection(collectionName).document(userData.id).set(userData)
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

    override fun updateAgroCoin(userId: String, coin: Int): LiveData<Result<Unit>> {
        val liveData = MutableLiveData<Result<Unit>>()

        // Fetch the current coin value, add the provided coin value, and then update Firestore
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val currentCoin = document.getLong("agroCoin") ?: 0
                    val newCoinValue = currentCoin + coin
                    db.collection("users").document(userId).update("agroCoin", newCoinValue)
                        .addOnSuccessListener {
                            liveData.value = Result.success(Unit)
                        }
                        .addOnFailureListener { exception ->
                            liveData.value = Result.failure(exception)
                        }
                } else {
                    liveData.value = Result.failure(Exception("User not found"))
                }
            }
            .addOnFailureListener { exception ->
                liveData.value = Result.failure(exception)
            }

        return liveData
    }


    override fun add(userData: UserData): LiveData<Result<Unit>> {
        val liveData = MutableLiveData<Result<Unit>>()
        Log.d("AddUser", "Failed to add user:")
        db.collection("users").document(userData.id).set(userData)
            .addOnSuccessListener {
                liveData.value = Result.success(Unit)
                Log.d("AddUser", "Failed to add user:$it")
            }
            .addOnFailureListener { exception ->
                liveData.value = Result.failure(exception)

            }

        return liveData
    }

    fun _get(id: String): LiveData<Result<UserData>> {

        progressBar.value=true
        val liveData = MutableLiveData<Result<UserData>>()
        db.collection("users")
            .document(id).get().addOnSuccessListener{

                liveData.value = Result.success(it.toUserData())
            }
            .addOnFailureListener { liveData.value = Result.failure(it) }

        return liveData
    }
    override fun get(id: String): LiveData<Result<UserData>> {
        progressBar.value=true
        val liveData = MediatorLiveData<Result<UserData>>()
        liveData.addDisposable(_get(id)) { liveData.value = it }

        db.collection("users").addSnapshotListener { snapshot, e ->
            liveData.addDisposable(_get(id)) { liveData.value = it }
        }

        return liveData

    }
}