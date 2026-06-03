package com.pdmcourse2026.basictemplate.screens.home

import android.util.Log.e
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.pulltorefresh.PullToRefreshBox


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
  onNavigateToResultScreen: () -> Unit,
  modifier: Modifier = Modifier,
  viewModel: HomeScreenViewModel = viewModel()
) {

  val uiState by viewModel.uiState.collectAsState()

  //val refreshing by viewModel.refreshing.collectAsState()

  Scaffold(
    topBar = {
      TopAppBar(
        colors = topAppBarColors(
          containerColor = MaterialTheme.colorScheme.primaryContainer,
          titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = { Text("RankeUca - Vota") },
      )
    }
  ) { innerPadding ->
    Column(modifier = Modifier.padding(innerPadding)) {
      Text(text = "Home Screen")

      if(uiState.isLoading){
        CircularProgressIndicator()
      }

      else if (uiState.error != null){
        Text(text = "Error: ${uiState.error}")
      }

        else {
        PullToRefreshBox (
          isRefreshing = uiState.isLoading,
          onRefresh = { viewModel.fetchOptions() }
        ) {
              LazyColumn() {
                  items(uiState.options){
                      option ->
                      OptionItem(option = option)
                  }
              }
          }
      }
    }

  }
}

@Composable
fun OptionItem(option: Option) {
  Card(
    modifier = Modifier.fillMaxWidth(),
    elevation = CardDefaults.cardElevation(4.dp)
  ) {
    Column {

      AsyncImage(
        model = option.imageUrl,
        contentDescription = option.name,
        modifier = Modifier
          .fillMaxWidth()
          .height(180.dp),
        contentScale = ContentScale.Crop
      )


      ListItem(
        headlineContent = {
          Text(option.name, style = MaterialTheme.typography.titleLarge)
        },
//        supportingContent = {
//          Text("Votos actuales: ${option.votes}")
//        }
      )
    }
  }
}