package com.vroomvroom.android.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vroomvroom.android.data.local.entity.user.AddressEntity
import com.vroomvroom.android.data.local.entity.user.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)
    @Update
    suspend fun updateUser(userEntity: UserEntity)
    @Query("UPDATE user_table SET name = :name WHERE id = :id")
    suspend fun updateUserName(id: String, name: String)
    @Query("DELETE FROM user_table")
    suspend fun deleteUser()
    @Transaction
    @Query("SELECT * FROM user_table")
    fun getUser(): LiveData<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddress(addressEntity: AddressEntity)
    @Update
    suspend fun updateAddress(addressEntity: AddressEntity)
    @Query("UPDATE address_table SET currentUse = :currentUse")
    suspend fun updateAllAddress(currentUse: Boolean = false): Int
    @Delete
    suspend fun deleteAddress(addressEntity: AddressEntity)
    @Query("DELETE FROM address_table")
    suspend fun deleteAllAddress()
    @Transaction
    @Query("SELECT * FROM address_table")
    fun getAllAddress(): LiveData<List<AddressEntity>>

}