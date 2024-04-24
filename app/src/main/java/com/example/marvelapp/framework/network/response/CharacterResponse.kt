package com.example.marvelapp.framework.network.response

import br.com.julianovince.core.domain.model.Character

data class CharacterResponse(
    val id:String,
    val name:String,
    val thubmbnail: ThubmbnailResponse
)

fun CharacterResponse.toCharacterModel(): Character {
    return Character(
        name = this.name,
        imageUrl = "${this.thubmbnail.path}.${this.thubmbnail.extension}"
    )
}
