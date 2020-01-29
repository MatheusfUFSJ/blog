package br.com.sigga.data.dao

import androidx.room.Dao
import androidx.room.Query
import br.com.sigga.data.model.Album
import br.com.sigga.data.model.Photo

@Dao
interface AlbumDao : GenericDao<Album> {

    @Query("SELECT * FROM Album")
    fun getAlbum(): List<Album>

}