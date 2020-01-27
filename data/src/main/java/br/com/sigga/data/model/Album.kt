package br.com.sigga.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Album(
    @PrimaryKey
    var userId: Long = 0L,
    @PrimaryKey
    var id: Long = 0L,
    var title: String = ""
) : Serializable {

    companion object {
        const val serialVersionUID = 1L
    }

}
