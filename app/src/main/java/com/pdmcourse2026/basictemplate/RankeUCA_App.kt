package com.pdmcourse2026.basictemplate

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.pdmcourse2026.basictemplate.screens.home.HomeScreen
import com.pdmcourse2026.basictemplate.screens.home.HomeScreenViewModel
import com.pdmcourse2026.basictemplate.screens.resultscreen.ResultScreen
import com.pdmcourse2026.basictemplate.screens.resultscreen.ResultScreenViewModel

@Composable
fun RankeUCA_App() {
  val backStack = rememberNavBackStack(Routes.Home)
  val homeViewModel: HomeScreenViewModel = viewModel()
  val resultViewModel: ResultScreenViewModel = viewModel()

  NavDisplay(
    backStack = backStack,
    onBack = { backStack.removeLastOrNull() },
    entryProvider = entryProvider {
      entry<Routes.Home> {
        HomeScreen(
          viewModel = homeViewModel,
          onNavigateToResultScreen = {
            resultViewModel.fetchOptions()
            backStack.add(Routes.ResultScreen)
          }
        )
      }
      entry<Routes.ResultScreen> {
        ResultScreen(
          viewModel = resultViewModel,
          onBack = { backStack.removeLastOrNull() },
          onNewVote = {
            homeViewModel.resetLocalVote()
            backStack.removeLastOrNull()
          }
        )
      }

    },
  )


}