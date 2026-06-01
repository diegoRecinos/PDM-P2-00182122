package com.pdmcourse2026.basictemplate.screens.home

import android.util.Log.e
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdmcourse2026.basictemplate.data.api.KtorClient
import com.pdmcourse2026.basictemplate.data.model.Option
import com.pdmcourse2026.basictemplate.data.repository.ApiRepository
import com.pdmcourse2026.basictemplate.data.repository.RepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeScreenUIState(
    val isLoading: Boolean = false,
    val options: List<Option> = emptyList(),
    val error: String? = null
)

class HomeScreenViewModel(): ViewModel() {

    private val repository: RepositoryInterface = ApiRepository(KtorClient.client)

    private val _uiState = MutableStateFlow(HomeScreenUIState())
    val uiState: StateFlow<HomeScreenUIState> = _uiState.asStateFlow()

    init {
        fetchOptions()
    }

    fun fetchOptions() {

        viewModelScope.launch{

        try {

                _uiState.value = HomeScreenUIState(isLoading = true)
                val result = repository.getOptions()

                _uiState.update { it.copy(options = result) }

                _uiState.update { it.copy(isLoading = false) }
        } catch (e: Exception) {
            e("HomeScreenViewModel", "Error fetching options: ${e.message}", e)
            _uiState.update { it.copy(error = e.message) }
        }
    }
    }

}
