package com.pdmcourse2026.basictemplate.data.repository

import io.ktor.client.HttpClient
import com.pdmcourse2026.basictemplate.data.model.Option
import io.ktor.client.call.body
import io.ktor.client.request.get


class ApiRepository(private val client: HttpClient) : RepositoryInterface {

    override suspend fun getOptions(): List<Option> {

        val response: List<Option> = client.get("https://qjcxdvfzyseuvezacxsd.supabase.co/functions/v1/rankeuca/options").body()

        return response.map { it.toModel()

    }

    override suspend fun createOption(option: Option): Option {
        TODO()
    }


}