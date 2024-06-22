package com.vroomvroom.android.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vroomvroom.android.data.local.entity.merchant.SearchEntity

@Dao
interface SearchDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(searchEntity: SearchEntity)

    @Delete
    suspend fun deleteSearch(searchEntity: SearchEntity)

    @Transaction
    @Query("SELECT * FROM search_table ORDER BY createdAt DESC")
    fun getAllSearch(): LiveData<List<SearchEntity>>
}