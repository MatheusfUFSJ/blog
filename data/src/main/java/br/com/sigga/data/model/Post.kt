package br.com.sigga.data.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(primaryKeys = arrayOf("userId", "id"))
data class Post(
    @SerializedName("userId")
    var userId: Long = 0L,
    var id: Long = 0L,
    var title: String = "",
    var body: String = ""
) : Serializable {

    companion object {
        const val serialVersionUID = 1L
    }

}