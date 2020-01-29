package br.com.sigga.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(primaryKeys = arrayOf("albumId", "id"))
data class Photo(
    @SerializedName("albumId")
    var albumId: Long = 0L,
    var id: Long = 0L,
    var title: String = "",
    var url: String = "",
    @SerializedName("thumbnailUrl")
    var thumbnailUrl: String = ""
) : Serializable {

    companion object {
        const val serialVersionUID = 1L
    }

}


