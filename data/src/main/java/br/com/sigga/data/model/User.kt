package br.com.sigga.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class User(
    @PrimaryKey
    var id: Long = 0L,
    var name: String = "",
    var username: String = "",
    var email: String = "",
    var phone: String = "",
    var website: String = "",
    @Ignore
    var address: UserAddress? = null
) : Serializable {

    companion object {
        const val serialVersionUID = 1L
    }

}