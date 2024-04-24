package br.com.julianovince.core.data.repository

interface CharactersRemoteDataSource<T> {

    suspend fun fetchCharacters(queries: Map<String, String>): T

}