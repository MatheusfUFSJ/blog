package br.com.sigga.data.dao

import androidx.room.*

interface GenericDao<E> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: E): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(entity: E)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(entities: List<E>)

}