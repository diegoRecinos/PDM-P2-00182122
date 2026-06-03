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
    val error: String? = null,
    val hasVoted: Boolean = false,
    val isVoting: Boolean = false,
    val selectedOptionId: Int? = null
)

class HomeScreenViewModel(): ViewModel() {

    private val repository: RepositoryInterface = ApiRepository(KtorClient.client)

    private val _uiState = MutableStateFlow(HomeScreenUIState())
    val uiState: StateFlow<HomeScreenUIState> = _uiState.asStateFlow()

    init {
        fetchOptions()
    }

    fun fetchOptions() {
        viewModelScope.launch {
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
                e("HomeScreenViewModel", "Error fetching options: ${e.message}", e)
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun vote(optionId: Int) {
        if (!_uiState.value.isVoting && !_uiState.value.hasVoted) {
            viewModelScope.launch {
                _uiState.update { it.copy(isVoting = true, selectedOptionId = optionId, error = null) }
                repository.voteOption(optionId)
                    .onSuccess {
                        _uiState.update {
                            it.copy(
                                selectedOptionId = optionId,
                                hasVoted = true,
                                isVoting = false
                            )
                        }
                        fetchOptions()
                    }
                    .onFailure { error ->
                        _uiState.update {
                            it.copy(
                                isVoting = false,
                                selectedOptionId = null,
                                error = error.message
                            )
                        }
                    }
            }
        }
    }

    fun resetLocalVote() {
        _uiState.update {
            it.copy(
                hasVoted = false,
                selectedOptionId = null,
                error = null
            )

        }
        fetchOptions()
    }


    fun resetAllVotesAdmin() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, error = null) }
                repository.resetVotes()
                    .onSuccess {
                        resetLocalVote()
                        fetchOptions()
                    }
                    .onFailure { error ->
                        _uiState.update { it.copy(error = error.message, isLoading = false) }
                    }
            } catch (e: Exception) {
                e("HomeScreenViewModel", "Error resetting votes: ${e.message}", e)
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }
}
