package com.pdmcourse2026.basictemplate.data.api.options

import com.pdmcourse2026.basictemplate.data.model.Option
import io.ktor.http.HttpMethod.Companion.Post
import kotlinx.serialization.Serializable

@Serializable
data class OptionDTO(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val votes: Int
){

}

//mappers
fun OptionDTO.toModel() = Option(
    id = id,
    name = name,
    imageUrl = imageUrl,
    votes = votes
)

fun Option.toDTO() = OptionDTO(
    id = id,
    name = name,
    imageUrl = imageUrl,
    votes = votes
)
