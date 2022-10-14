package com.example.core.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.data.repository.CharactersRepository
import com.example.core.domain.model.Character
import com.example.core.usecase.GetCharactersUseCase.GetCharactersParms
import com.example.core.usecase.base.PagingUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetCharactersUseCase {

    operator fun invoke(params: GetCharactersParms): Flow<PagingData<Character>>

    data class GetCharactersParms(val query: String, val pagingConfig: PagingConfig)

}

class GetCharactersUseCaseImpl @Inject constructor(
    private val charactersRepository: CharactersRepository
) : PagingUseCase<GetCharactersParms, Character>(),
    GetCharactersUseCase {

    override fun createFlowObservable(params: GetCharactersParms): Flow<PagingData<Character>> {
        val pagingSource = charactersRepository.getCharacters(params.query)
        return Pager(config = params.pagingConfig) {
            pagingSource
        }.flow
    }

}