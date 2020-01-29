package br.com.sigga.data.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.sigga.data.model.Comment
import br.com.sigga.data.model.Photo

@Dao
interface PhotoDao  : GenericDao<Photo> {

    @Query("SELECT * FROM Photo")
    fun getPhoto(): List<Photo>

}