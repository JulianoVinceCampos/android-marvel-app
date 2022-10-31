package com.example.marvelapp.framework.di

import com.example.core.usecase.GetCharactersUseCase
import com.example.core.usecase.GetCharactersUseCaseImpl
import com.example.core.usecase.GetComicsUseCase
import com.example.core.usecase.GetComicsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModel {

    @Binds
    fun bindGetCharactersUseCase(
        useCaseImpl: GetCharactersUseCaseImpl
    ): GetCharactersUseCase

    @Binds
    fun bindGetComicsUseCase(
        useCaseComicsImpl: GetComicsUseCaseImpl
    ): GetComicsUseCase
}