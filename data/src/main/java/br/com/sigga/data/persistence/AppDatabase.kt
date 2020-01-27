package br.com.sigga.data.persistence

import android.location.Address
import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.sigga.data.dao.*
import br.com.sigga.data.model.*

@Database(entities = [
    Address::class,
    Album::class,
    Comment::class,
    Photo::class,
    Post::class,
    User::class
], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun addressDao(): AddressDao
    abstract fun albumDao(): AlbumDao
    abstract fun commentDao(): CommentDao
    abstract fun photoDao(): PhotoDao
    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao

}