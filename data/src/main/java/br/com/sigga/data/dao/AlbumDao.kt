package br.com.sigga.data.dao

import androidx.room.Dao
import br.com.sigga.data.model.Album

@Dao
interface AlbumDao  : GenericDao<Album> {
}