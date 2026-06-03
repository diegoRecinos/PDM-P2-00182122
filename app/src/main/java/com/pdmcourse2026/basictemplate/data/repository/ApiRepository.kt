package com.pdmcourse2026.basictemplate.data.repository

import android.util.Log
import android.util.Log.e
import com.pdmcourse2026.basictemplate.data.api.options.OptionDTO
import com.pdmcourse2026.basictemplate.data.api.options.toModel
import io.ktor.client.HttpClient
import com.pdmcourse2026.basictemplate.data.model.Option
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlin.collections.map


class ApiRepository(private val client: HttpClient) : RepositoryInterface {

    override suspend fun getOptions(): Result<List<Option>> {

        return try {
            val response: List<OptionDTO> = client.get("options").body()
            Result.success(response.map { it.toModel() })

        }catch (e: Exception){
            Log.e("ApiRepository", "Error fetching options: ${e.message}", e)
            Result.failure(e)
        }

    }



    override suspend fun createOption(option: Option): Option {
        TODO()
    }


}