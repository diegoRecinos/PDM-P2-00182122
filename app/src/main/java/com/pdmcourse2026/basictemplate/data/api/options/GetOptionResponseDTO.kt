package com.pdmcourse2026.basictemplate.data.api.options

import com.pdmcourse2026.basictemplate.data.model.Option
import kotlinx.serialization.Serializable

@Serializable
data class GetOptionResponseDTO(
    val page: Int? = null,
    val results: List<OptionDTO>,
){

}