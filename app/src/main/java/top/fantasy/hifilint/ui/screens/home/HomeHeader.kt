package top.fantasy.hifilint.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import top.fantasy.hifilint.data.model.AudioDeviceDetail
import top.fantasy.hifilint.ui.components.deviceTypeIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeHeader(
    deviceOptions: List<AudioDeviceDetail>,
    selectedDevice: AudioDeviceDetail,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
){
    var devicesExpanded by remember { mutableStateOf(false) } // 是否展开设备列表
    Row(modifier = Modifier.background(Color(0xEEEEEEEE)).height(50.dp), verticalAlignment = Alignment.CenterVertically) {
        Text("Device", fontSize = 26.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 16.dp))
        Box(modifier = Modifier.weight(1f).fillMaxHeight())
        ExposedDropdownMenuBox(
            expanded = devicesExpanded,
            onExpandedChange = { devicesExpanded = it},
            modifier = Modifier.padding(0.dp)
        ) {
            OutlinedTextField(
                value = selectedDevice.name,
                onValueChange = {},
                readOnly = true,
                textStyle = TextStyle(textAlign = TextAlign.End),
                trailingIcon = {
                    Icon(imageVector = deviceTypeIcon(selectedDevice.type), contentDescription = null)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                ),
                modifier = modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = devicesExpanded,
                onDismissRequest = { devicesExpanded = false }
            ) {
                deviceOptions.forEach{ option ->
                    DropdownMenuItem(
                        text = { Text(option.name) },
                        onClick = {
                            viewModel.onDeviceSelected(option)
                            devicesExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        leadingIcon = {
                            Icon(
                                imageVector = deviceTypeIcon(option.type),
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        }
    }
}