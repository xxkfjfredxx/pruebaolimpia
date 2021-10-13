package com.fredrueda.pruebaandroid.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "databook")
data class DataBook(
    @PrimaryKey
    var id:Int?=null,
    var nombre:String?=null,
    var cedula:String?=null,
    var direccion: String?=null,
    var ciudad: String?=null,
    var pais: String?=null,
    var celular: String?=null,
    var lat: String?=null,
    var long: String?=null,
    var foto: String?=null,
    var statewifi: String?=null,
    var stateBt: String?=null,
    @ColumnInfo
    var isVisible:Boolean?=false
):Serializable{
}
