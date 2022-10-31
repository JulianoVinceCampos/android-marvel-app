package com.example.core.usecase

import com.example.core.data.repository.CharactersRepository
import com.example.core.domain.model.Comic
import com.example.core.usecase.base.ResultStatus
import com.example.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetComicsUseCase {

    operator fun invoke(params: GetCommicsParams): Flow<ResultStatus<List<Comic>>>

    data class GetCommicsParams(val characterId: Int)

}

class GetComicsUseCaseImpl @Inject constructor(
    private val repository: CharactersRepository
) : GetComicsUseCase,
    UseCase<GetComicsUseCase.GetCommicsParams, List<Comic>>() {

    override suspend fun doWork(params: GetComicsUseCase.GetCommicsParams): ResultStatus<List<Comic>> {
        val comics = repository.getComics(params.characterId)
        return ResultStatus.Sucess(comics)
    }

}

