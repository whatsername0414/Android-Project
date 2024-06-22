package com.vroomvroom.android.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vroomvroom.android.data.local.entity.cart.CartProductOptionEntity
import com.vroomvroom.android.data.local.dao.CartItemDAO
import com.vroomvroom.android.data.local.entity.cart.CartItemEntity
import com.vroomvroom.android.data.local.dao.SearchDao
import com.vroomvroom.android.data.local.entity.merchant.SearchEntity
import com.vroomvroom.android.data.local.dao.UserDao
import com.vroomvroom.android.data.local.entity.user.UserEntity
import com.vroomvroom.android.data.local.entity.user.AddressEntity

@Database(
    entities = [
        CartItemEntity::class,
        CartProductOptionEntity::class,
        UserEntity::class,
        AddressEntity::class,
        SearchEntity::class
               ],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun cartItemDao(): CartItemDAO
    abstract fun userDao(): UserDao
    abstract fun searchDao(): SearchDao

}