package br.com.sigga.data.dao

import androidx.room.Dao
import br.com.sigga.data.model.User

@Dao
interface UserDao : GenericDao<User>  {
}