package br.com.sigga.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(primaryKeys = arrayOf("userId", "id"))
data class Album(
    var userId: Long = 0L,
    var id: Long = 0L,
    var title: String = ""
) : Serializable {

    companion object {
        const val serialVersionUID = 1L
    }

}
