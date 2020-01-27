package br.com.sigga.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Photo(
    @PrimaryKey
    var albumId: Long = 0L,
    @PrimaryKey
    var id: Long = 0L,
    var title: String = "",
    var url: String = "",
    var thumbnailUrl: String = ""
) : Serializable {

    companion object {
        const val serialVersionUID = 1L
    }

}


