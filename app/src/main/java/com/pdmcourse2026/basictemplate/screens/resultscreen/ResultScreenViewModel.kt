package com.pdmcourse2026.basictemplate.screens.resultscreen

import android.util.Log.e
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdmcourse2026.basictemplate.data.api.KtorClient
import com.pdmcourse2026.basictemplate.data.repository.ApiRepository
import com.pdmcourse2026.basictemplate.data.repository.RepositoryInterface
import com.pdmcourse2026.basictemplate.screens.home.HomeScreenUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class ResultScreenViewModel() : ViewModel()
{
    private val repository: RepositoryInterface = ApiRepository(KtorClient.client)

    private val _uiState = MutableStateFlow(HomeScreenUIState())
    val uiState: StateFlow<HomeScreenUIState> = _uiState.asStateFlow()

    init {
        fetchOptions()
    }

    fun fetchOptions() {
        viewModelScope.launch{
            try {
                _uiState.update { it.copy(isLoading = true, error = null) }
                repository.getOptions()
                    .onSuccess { options -> 
                        _uiState.update { 
                            it.copy(
                                options = options.sortedByDescending { it.votes }, 
                                isLoading = false
                            ) 
                        }
                    }
                    .onFailure { error -> 
                        _uiState.update { it.copy(error = error.message, isLoading = false) } 
                    }
            } catch (e: Exception) {
                e("ResultScreenViewModel", "Error fetching options: ${e.message}", e)
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun resetAllVotes(){
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, error = null) }
                repository.resetVotes()
                    .onSuccess {
                        _uiState.update { it.copy(
                                hasVoted = false,
                                selectedOptionId = null,
                                isLoading = false
                            )
                        }
                        fetchOptions()
                    }
                    .onFailure { error -> 
                        _uiState.update { it.copy(isLoading = false, error = error.message) }
                    }
            } catch (e: Exception) {
                e("ResultScreenViewModel", "Error resetting votes: ${e.message}", e)
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }

        }
    }
}