package com.vroomvroom.android.data.local.entity.merchant

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vroomvroom.android.utils.Constants.SEARCH_TABLE

@Entity(tableName = SEARCH_TABLE)
data class SearchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val searchTerm: String,
    val fromLocal: Boolean = false,
    val createdAt: Long = 0
)
