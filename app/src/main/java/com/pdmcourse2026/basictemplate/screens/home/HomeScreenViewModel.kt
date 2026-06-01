package com.pdmcourse2026.basictemplate.screens.home

import androidx.lifecycle.ViewModel
import com.pdmcourse2026.basictemplate.data.api.KtorClient
import com.pdmcourse2026.basictemplate.data.model.Option
import com.pdmcourse2026.basictemplate.data.repository.ApiRepository
import com.pdmcourse2026.basictemplate.data.repository.RepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class HomeScreenUIState(
    val isLoading: Boolean = false,
    val options: List<Option> = emptyList(),
    val error: String? = null
)

class HomeScreenViewModel(): ViewModel() {

    private val repository: RepositoryInterface = ApiRepository(KtorClient.client)

    private val _uiState = MutableStateFlow(HomeScreenUIState())
    val uiState: StateFlow<HomeScreenUIState> = _uiState.asStateFlow()



}
