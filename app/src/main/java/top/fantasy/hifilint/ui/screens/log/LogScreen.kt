package top.fantasy.hifilint.ui.screens.log

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun LogScreen(
    modifier: Modifier = Modifier,
    viewModel: LogViewModel = hiltViewModel()
){
    val logList by viewModel.logs.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(logList.size) {
        if (logList.isNotEmpty()) {
            listState.animateScrollToItem(logList.size - 1)
        }
    }
    Column(modifier = modifier
        .fillMaxSize()
        .padding(5.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(5.dp)) {
            Text("Log", fontSize = 24.sp)
        }
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(logList){ line ->
                Text(
                    text = line,
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}