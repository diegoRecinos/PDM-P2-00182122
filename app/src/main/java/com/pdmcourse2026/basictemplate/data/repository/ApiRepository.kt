package com.pdmcourse2026.basictemplate.data.repository

import android.util.Log
import com.pdmcourse2026.basictemplate.data.api.options.OptionDTO
import com.pdmcourse2026.basictemplate.data.api.options.toModel
import io.ktor.client.HttpClient
import com.pdmcourse2026.basictemplate.data.model.Option
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlin.collections.map


class ApiRepository(private val client: HttpClient) : RepositoryInterface {

    override suspend fun getPosts(): Result<List<Option>> {

        return try {
            //intentar peticion
            val response: List<OptionDTO> = client.get("posts").body()

            //success devolvemos lista de posts transformados
            Result.success(response.map { it.toModel() })

        } catch (e: Exception) {
            //devolver err
            Log.e("ApiRepository", "Error fetching posts: ${e.message}", e)
            Result.failure(e)
        }

    }


//    override suspend fun getOptions(): List<Option> {
//
//        val response: List<Option> = client.get("https://qjcxdvfzyseuvezacxsd.supabase.co/functions/v1/rankeuca/options").body()
//
//        return response.map { it.toModel()
//
//    }

    override suspend fun createOption(option: Option): Option {
        TODO()
    }


}