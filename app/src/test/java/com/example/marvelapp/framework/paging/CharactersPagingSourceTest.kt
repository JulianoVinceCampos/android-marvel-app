package com.example.marvelapp.framework.paging

import androidx.paging.PagingSource
import com.example.core.data.repository.CharactersRemoteDataSource
import com.example.marvelapp.factory.response.DataWrapperResponseFactory
import com.example.marvelapp.framework.network.response.DataWrapperResponse
import com.example.testing.MainCoroutineRule
import com.example.testing.model.CharacterFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.lang.RuntimeException

@RunWith(MockitoJUnitRunner::class)
class CharactersPagingSourceTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCorotinesRule = MainCoroutineRule()

    @Mock
    lateinit var remoteDataSource: CharactersRemoteDataSource<DataWrapperResponse>

    private lateinit var charactersPagingSource: CharactersPagingSource

    private val dataWrapperResponseFactory = DataWrapperResponseFactory()

    private val characterFactory = CharacterFactory()

    @Before
    fun setUp() {
        charactersPagingSource = CharactersPagingSource(remoteDataSource, "")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should return a sucess load result load is called`() = runBlockingTest {
        //Arrange
        whenever(remoteDataSource.fetchCharacters(any())).thenReturn(dataWrapperResponseFactory.create())

        //Act
        val result = charactersPagingSource.load(
            PagingSource.LoadParams.Refresh(
                0,
                loadSize = 2,
                false
            )
        )

        val expected = listOf(
            characterFactory.create(hero = CharacterFactory.Hero.ThreeDMan),
            characterFactory.create(hero = CharacterFactory.Hero.ABomb)
        )

        //Assert
        assertEquals(
            PagingSource.LoadResult.Page(data = expected, prevKey = null, nextKey = 20),
            result
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `shoud return a error load result when is called`() = runBlockingTest {

        //Arrange
        val exception = RuntimeException()
        whenever(remoteDataSource.fetchCharacters(any())).thenThrow(exception)

        //ACT
        val result = charactersPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                false
            )
        )

        //Assert
        assertEquals(PagingSource.LoadResult.Error<Int, Exception>(exception), result)

    }

}