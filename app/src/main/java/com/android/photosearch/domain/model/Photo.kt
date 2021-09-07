package com.android.photosearch.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "Photo")
data class Photo(
    @PrimaryKey var id: Long,
    @SerializedName("owner")
    var owner: String,
    @SerializedName("secret")
    var secret: String,
    @SerializedName("server")
    var server: String,
    @SerializedName("farm")
    var farm: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("ispublic")
    var ispublic: String,
    @SerializedName("isfriend")
    var isfriend: String,
    @SerializedName("isfamily")
    var isfamily: String
) : Serializable



