package com.carollim.myrecycleapp.domain.repository

import kotlinx.coroutines.flow.Flow

data class UserProfile(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val userType: String = "resident"
)

data class UserStats(
    val totalWeightKg: Double = 0.0,
    val totalEarnings: Double = 0.0,
    val monthlyEarnings: Map<String, Double> = emptyMap(),
    val categoryTotalsKg: Map<String, Double> = emptyMap()
)

data class UserData(
    val profile: UserProfile = UserProfile(),
    val stats: UserStats = UserStats()
)

interface UserRepository {
    fun getUserData(uid: String): Flow<Result<UserData>>
}
