package com.example.core.usecase

import androidx.paging.PagingConfig
import com.example.core.data.repository.CharactersRepository
import com.example.testing.MainCoroutineRule
import com.example.testing.model.CharacterFactory
import com.example.testing.pagingsource.PagingSourceFactory
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class GetCharactersUseCaseImplTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutinesApi = MainCoroutineRule()

    private lateinit var getCharacterUseCase: GetCharactersUseCase

    @Mock
    lateinit var charactersRepository: CharactersRepository

    private val hero = CharacterFactory().create(CharacterFactory.Hero.ThreeDMan)
    private val aBomb = CharacterFactory().create(CharacterFactory.Hero.ABomb)

    private val fakePagingSource = PagingSourceFactory().create(
        listOf(
            hero,
            aBomb
        )
    )

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getCharacterUseCase = GetCharactersUseCaseImpl(charactersRepository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should validate flow paging data creation when invoke ferom use case is called`() =
        runBlockingTest {

            whenever(charactersRepository.getCharacters("")).thenReturn(fakePagingSource)

            val result = getCharacterUseCase.invoke(
                GetCharactersUseCase.GetCharactersParms(
                    "",
                    PagingConfig(20)
                )
            )

            verify(charactersRepository).getCharacters("")

            assertNotNull(result.first())
        }

}