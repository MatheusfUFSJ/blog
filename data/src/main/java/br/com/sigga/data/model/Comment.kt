package br.com.sigga.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(primaryKeys = arrayOf("postId", "id"))
data class Comment(
    @SerializedName("postId")
    var postId: Long = 0L,
    var id: Long = 0L,
    var name: String = "",
    var email: String = "",
    var body: String = ""
) : Serializable {

    companion object {
        const val serialVersionUID = 1L
    }

}