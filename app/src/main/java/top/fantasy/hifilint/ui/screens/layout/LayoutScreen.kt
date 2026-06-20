package top.fantasy.hifilint.ui.screens.layout

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import kotlinx.coroutines.launch
import top.fantasy.hifilint.common.Screen
import top.fantasy.hifilint.ui.screens.home.HomeScreen
import top.fantasy.hifilint.ui.screens.log.LogScreen
import top.fantasy.hifilint.ui.screens.settings.SettingScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutScreen(
    modifier: Modifier = Modifier,
    viewModel: LayoutViewModel = hiltViewModel()
){
    val screens = listOf(Screen.Home, Screen.Log, Screen.Settings)
    val pagerState = rememberPagerState(pageCount = {screens.size})
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar {
                screens.forEachIndexed{index, screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) { page ->
            when(page){
                0 -> HomeScreen()
                1 -> LogScreen()
                2 -> SettingScreen()
            }
        }
    }
}