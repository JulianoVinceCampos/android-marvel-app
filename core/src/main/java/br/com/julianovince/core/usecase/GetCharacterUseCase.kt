package br.com.julianovince.core.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.julianovince.core.data.repository.CharactersRepository
import br.com.julianovince.core.domain.model.Character
import br.com.julianovince.core.usecase.base.PagingUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) : PagingUseCase<GetCharacterUseCase.GetCharacterParamns, Character>() {

    data class GetCharacterParamns(val query: String, val pagingConfig: PagingConfig)

    override fun createFlowObservable(params: GetCharacterParamns): Flow<PagingData<Character>> {
        return Pager(config = params.pagingConfig){
            charactersRepository.getCharacters(params.query)
        }.flow
    }
}