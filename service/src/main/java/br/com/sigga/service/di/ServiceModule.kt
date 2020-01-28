package br.com.sigga.service.di

import br.com.sigga.service.AlbumService
import br.com.sigga.service.CommentService
import br.com.sigga.service.PostService
import br.com.sigga.service.UserService
import org.koin.dsl.module

val serviceModule = module {
    single { PostService() }
    single { AlbumService() }
    single { UserService() }
    single { CommentService() }
}







