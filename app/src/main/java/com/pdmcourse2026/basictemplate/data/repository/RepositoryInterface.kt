package com.pdmcourse2026.basictemplate.data.repository

import com.pdmcourse2026.basictemplate.data.model.Option

interface RepositoryInterface {

    suspend fun getOptions(): Result<List<Option>>

    suspend fun voteOption(optionId: Int): Result<Option>


}