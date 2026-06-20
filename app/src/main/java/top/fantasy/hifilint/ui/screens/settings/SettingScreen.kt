package top.fantasy.hifilint.ui.screens.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel()
){
    val errorMsg by viewModel.errorMsg.collectAsState()
    val adbMode by viewModel.adbMode.collectAsState(initial = 0)
    Column(modifier = modifier
        .fillMaxSize()
        .padding(5.dp)) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(5.dp)) {
            Text("Settings", fontSize = 24.sp)
        }
        Column(modifier) {
            Row(modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(60.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("ADB Mode")
                Box(modifier = Modifier.weight(1f))
                Switch(checked = adbMode == 1, onCheckedChange = {
                    viewModel.toggleAdbMode(adbMode == 0)
                })
            }
        }
    }
    if (errorMsg != null) {
        AlertDialog(
            onDismissRequest = { viewModel.dismissDialog() },
            title = { Text("提示") },
            text = { Text(errorMsg!!) },
            confirmButton = {
                TextButton(onClick = { viewModel.dismissDialog() }) {
                    Text("确定")
                }
            }
        )
    }
}