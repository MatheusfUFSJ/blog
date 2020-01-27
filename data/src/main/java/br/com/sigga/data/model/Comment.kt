package br.com.sigga.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Comment(
    @PrimaryKey
    var postId: Long = 0L,
    @PrimaryKey
    var id: Long = 0L,
    var name: String = "",
    var email: String = "",
    var body: String = ""
) : Serializable {

    companion object {
        const val serialVersionUID = 1L
    }

}