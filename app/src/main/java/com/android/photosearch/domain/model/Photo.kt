package com.android.photosearch.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "Photo")
data class Photo(
    @PrimaryKey var id: Long,
    var text: String,
    var meanings: ArrayList<Meaning>
) : Serializable {

    data class Meaning(
        var id: Long,
        var translation: Translation,
        var previewUrl: String,
        var imageUrl: String,
        var transcription: String
    ) : Serializable {

        data class Translation(
            @SerializedName("text")
            var translationText: String,
            @SerializedName("note")
            var translationNote: String
        ) : Serializable

    }

}