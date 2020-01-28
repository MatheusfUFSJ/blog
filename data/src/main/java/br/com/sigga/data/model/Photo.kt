package br.com.sigga.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(primaryKeys = arrayOf("albumId", "id"))
data class Photo(
    var albumId: Long = 0L,
    var id: Long = 0L,
    var title: String = "",
    var url: String = "",
    var thumbnailUrl: String = ""
) : Serializable {

    companion object {
        const val serialVersionUID = 1L
    }

}


