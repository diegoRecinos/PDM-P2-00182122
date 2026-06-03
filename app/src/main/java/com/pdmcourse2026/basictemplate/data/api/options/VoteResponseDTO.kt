package com.pdmcourse2026.basictemplate.data.api.options

import kotlinx.serialization.Serializable

@Serializable
data class VoteResponseDTO(
    val ok: Boolean,
    val message: String? = null
)
