package com.example.marvelapp.framework.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.data.repository.CharactersRemoteDataSource
import com.example.core.domain.model.Character
import java.lang.Exception

class CharactersPagingSource(
    private val remoteDataSource: CharactersRemoteDataSource,
    private val query: String
) : PagingSource<Int, Character>() {

    @Suppress("TooGenericExceptionCaught")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {

        return try {

            val offset = params.key ?: 0

            val queries = hashMapOf(
                "offset" to offset.toString()
            )

            if (queries.isEmpty()) {
                queries["nameStartsWith"] = query
            }

            val characterPaging = remoteDataSource.fetchCharacters(queries)

            val responseOffset = characterPaging.offset
            val totalCharacter = characterPaging.total

            LoadResult.Page(
                data = characterPaging.listCharacter,
                prevKey = null,
                nextKey = if (responseOffset < totalCharacter) {
                    responseOffset + LIMIT
                } else null
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(LIMIT) ?: anchorPage?.nextKey?.minus(LIMIT)
        }
    }

    companion object {
        private const val LIMIT = 20
    }
}