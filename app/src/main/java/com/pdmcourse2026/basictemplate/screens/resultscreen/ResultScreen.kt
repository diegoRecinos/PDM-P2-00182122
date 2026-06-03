package com.pdmcourse2026.basictemplate.screens.resultscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdmcourse2026.basictemplate.data.model.Option
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.pulltorefresh.PullToRefreshBox


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen (
    viewModel: ResultScreenViewModel = viewModel(),
    onBack: () -> Unit,
    onNewVote: () -> Unit
){

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text("RankeUca - Resultados") },
            )
        },
        bottomBar = {
            Button( 
                onClick = onNewVote,
                modifier = Modifier.padding(16.dp)
            ){
                Text("Nuevo (Volver a votar)")
            }
        }
    ) {
        innerPadding ->

        if (uiState.isLoading) {
            CircularProgressIndicator()
        }else{
            Column(modifier = Modifier.padding(innerPadding)) {

                PullToRefreshBox (
                    isRefreshing = uiState.isLoading,
                    onRefresh = { viewModel.fetchOptions() }
                ) {
                    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)){
                        items(uiState.options){
                            option ->
                            ListItem(
                                headlineContent = {
                                    Text(option.name, style = MaterialTheme.typography.titleLarge)
                                },
                                supportingContent = {
                                    Text("Votos: ${option.votes}")
                                }
                            )

                        }
                    }
                }

            }
        }


    }

}