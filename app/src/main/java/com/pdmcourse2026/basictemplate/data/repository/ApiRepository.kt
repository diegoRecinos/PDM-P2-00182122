package com.pdmcourse2026.basictemplate.data.repository

import android.util.Log
import android.util.Log.e
import com.pdmcourse2026.basictemplate.data.api.options.OptionDTO
import com.pdmcourse2026.basictemplate.data.api.options.VoteOptionRequestDTO
import com.pdmcourse2026.basictemplate.data.api.options.VoteResponseDTO
import com.pdmcourse2026.basictemplate.data.api.options.toModel
import io.ktor.client.HttpClient
import com.pdmcourse2026.basictemplate.data.model.Option
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
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


    override suspend fun voteOption(optionId: Int): Result<Unit> {

        return try {

            val response: VoteResponseDTO = client.post("vote"){
                setBody(VoteOptionRequestDTO(optionId))
            }.body()

            if(response.ok){
                Result.success(Unit)
            }else{
                Result.failure(Exception(response.message))
            }

        }catch (e: Exception){
            Log.e("ApiRepository", "Error voting option: ${e.message}", e)
            Result.failure(e)
        }

    }

    override suspend fun resetVotes(): Result<Unit> {
        return try {

            val response: VoteResponseDTO = client.post("reset")
                .body()

            if(response.ok){
                Result.success(Unit)
            }else{
                Result.failure(Exception(response.message ?: "error"))
            }

        }catch (e: Exception){
            Log.e("ApiRepository", "Error voting option: ${e.message}", e)
            Result.failure(e)
        }
    }


}