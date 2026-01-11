package com.carollim.myrecycleapp.domain.repository

import kotlinx.coroutines.flow.Flow

data class CatalogItem(
    val id: String = "",
    val name: String = "",
    val desc: String = "",
    val pricePerKg: Double = 0.0,
    val imageUrl: String = "",
    val isActive: Boolean = true
)

interface CatalogRepository {
    fun getCatalogItems(): Flow<Result<List<CatalogItem>>>
}
