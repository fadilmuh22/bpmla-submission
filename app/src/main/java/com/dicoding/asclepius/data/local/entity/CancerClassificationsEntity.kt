package com.dicoding.asclepius.data.local.entity

import android.net.Uri
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "cancer_classifications")
@Parcelize
data class CancerClassificationsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "image_uri")
    var imageUri: String,
    @ColumnInfo(name = "result")
    var result: String,
) : Parcelable