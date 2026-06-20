package top.fantasy.hifilint.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun HiFiLintLabel(
    modifier: Modifier = Modifier,
    label: String,
    value: String){
    Row(modifier = modifier) {
        Text("$label: ", fontWeight = FontWeight.W500)
        Text(value, fontWeight = FontWeight.W300)
    }
}

@Preview
@Composable
private fun HiFiLintLabelPreview(){
    HiFiLintLabel(label = "Label: ", value = "Value")
}
