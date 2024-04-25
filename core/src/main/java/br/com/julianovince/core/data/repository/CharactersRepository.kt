package br.com.julianovince.core.data.repository

import androidx.paging.PagingSource
import br.com.julianovince.core.domain.model.Character

interface CharactersRepository {

    fun getCharacters(query: String): PagingSource<Int, Character>

}