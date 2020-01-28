package br.com.sigga.data.dao

import androidx.room.Dao
import br.com.sigga.data.model.Photo

@Dao
interface PhotoDao  : GenericDao<Photo> {
}