package br.com.sigga.service

abstract class BaseService {

    companion object {
        const val ERROR_INTERNET: String =
            "Verifique a sua conexão com a internet e tente novamente."
        const val ERROR_DB: String = "Erro ao buscar do banco de dados."
    }
}