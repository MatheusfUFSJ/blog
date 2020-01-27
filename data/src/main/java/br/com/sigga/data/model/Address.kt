package br.com.sigga.data.model

import androidx.room.Entity
import java.io.Serializable

@Entity
data class Address (
    var id: Long = 0L,
    var userId: Long = 0L,
    var street: String = "",
    var suite: String = "",
    var city: String = "",
    var zipcode: String = ""
) : Serializable {

    companion object {
        const val serialVersionUID = 1L
    }

}