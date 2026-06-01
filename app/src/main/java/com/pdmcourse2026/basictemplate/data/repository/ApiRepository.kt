package com.pdmcourse2026.basictemplate.data.repository

import io.ktor.client.HttpClient
import com.pdmcourse2026.basictemplate.data.model.Option


class ApiRepository(private val client: HttpClient) : RepositoryInterface {

    override suspend fun getOptions(): List<Option> {
        TODO()
    }

    override suspend fun createOption(option: Option): Option {
        TODO()
    }


}