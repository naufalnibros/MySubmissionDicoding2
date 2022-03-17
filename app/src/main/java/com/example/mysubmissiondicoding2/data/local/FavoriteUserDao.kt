package com.example.mysubmissiondicoding2.data.local

import androidx.lifecycle.LiveData
import androidx.room.*


//interface FavoriteUserDao {
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    fun insert(favoriteUser: FavoriteUser)
//    @Update
//    fun update(favoriteUser: FavoriteUser)
//    @Delete
//    fun delete(favoriteUser: FavoriteUser)
//
//    @Query("SELECT * from favoriteuser ORDER BY id ASC")
//    fun getAllNotes(): LiveData<List<FavoriteUser>>
//}
//interface FavoriteUserDao {
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun addToFavorite(favoriteUser: FavoriteUser)
//
//    @Query("SELECT * from favorite_user")
//    fun getFavoriteUser():LiveData<List<FavoriteUser>>
//
//    @Query("SELECT * from favorite_user WHERE favorite_user.login = :username")
//    suspend fun checkUser(username:Int):Int
//
//    @Query("DELETE from favorite_user WHERE favorite_user.id = :id")
//    suspend fun removeFromFavorite(id:Int):Int
//
////    @Query("SELECT * from favoriteuser ORDER BY id ASC")
////    fun getAllNotes(): LiveData<List<FavoriteUser>>
//}
@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addToFavorite(favoriteUser: FavoriteUser)

    @Query("SELECT * from favoriteuser ORDER BY id ASC")
    fun getAllUser(): LiveData<List<FavoriteUser>>

    @Delete
    fun delete(favoriteUser: FavoriteUser)

    @Query("SELECT count(id) from favoriteuser WHERE id = :id")
    fun checkUser(id:Int): LiveData<Int>
}