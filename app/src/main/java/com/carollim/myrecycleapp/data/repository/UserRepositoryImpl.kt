package com.carollim.myrecycleapp.data.repository

import com.carollim.myrecycleapp.domain.repository.UserData
import com.carollim.myrecycleapp.domain.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : UserRepository {

    override fun getUserData(uid: String): Flow<Result<UserData>> = callbackFlow {
        val docRef = firestore.collection("users").document(uid)
        
        val subscription = docRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                trySend(Result.failure(error))
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val userData = snapshot.toObject(UserData::class.java)
                if (userData != null) {
                    trySend(Result.success(userData))
                } else {
                    trySend(Result.failure(Exception("User data is null")))
                }
            } else {
                trySend(Result.failure(Exception("User document does not exist")))
            }
        }

        awaitClose { subscription.remove() }
    }
}
