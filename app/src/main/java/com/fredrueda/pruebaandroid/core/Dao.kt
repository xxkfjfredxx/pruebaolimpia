package com.fred.prueba.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fredrueda.pruebaandroid.models.DataBook


@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllPosts(leagues:List<DataBook>)

    @Query("SELECT * FROM DataBook")
    fun getPosts():LiveData<List<DataBook>>

    @Query("UPDATE DataBook set isVisible=:isRead where id=:id")
    suspend fun readPost(isRead:Boolean, id:Int)

    @Query("DELETE from DataBook where id=:id")
    suspend fun deletePost(id: Int)

    @Query("DELETE from DataBook")
    suspend fun deleteAllPosts()

    @Insert
    fun insert(dataBook: DataBook)

    @Query("select * from DataBook")
    fun getAllPosts():List<DataBook>

}