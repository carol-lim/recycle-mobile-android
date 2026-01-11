package com.carollim.myrecycleapp.data.repository

import com.carollim.myrecycleapp.domain.repository.CatalogItem
import com.carollim.myrecycleapp.domain.repository.CatalogRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class CatalogRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : CatalogRepository {

    override fun getCatalogItems(): Flow<Result<List<CatalogItem>>> = callbackFlow {
        val subscription = firestore.collection("catalog")
            .whereEqualTo("isActive", true)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(Result.failure(error))
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val items = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(CatalogItem::class.java)?.copy(id = doc.id)
                    }
                    trySend(Result.success(items))
                }
            }

        awaitClose { subscription.remove() }
    }
}
