package br.com.sigga.data

import android.content.Context
import androidx.room.Room
import br.com.sigga.data.persistence.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single { provideAppDatabase(androidContext()) }

    //DAO
    single { get<AppDatabase>().addressDao() }
    single { get<AppDatabase>().albumDao() }
    single { get<AppDatabase>().commentDao() }
    single { get<AppDatabase>().photoDao() }
    single { get<AppDatabase>().postDao() }
    single { get<AppDatabase>().userDao() }

}

fun provideAppDatabase(context: Context): AppDatabase {

    return Room.databaseBuilder(context, AppDatabase::class.java, "siggaDB")
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()
}

