package br.com.sigga.data.dao

import androidx.room.Dao
import br.com.sigga.data.model.UserAddress

@Dao
interface AddressDao : GenericDao<UserAddress> {
}