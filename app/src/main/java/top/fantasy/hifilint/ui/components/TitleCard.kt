package top.fantasy.hifilint.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitleCard(
    modifier: Modifier = Modifier,
    title: String = "Title",
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    useDivider: Boolean = false,
    content: @Composable() () -> Unit, ){
    Column(modifier = modifier
        .background(
            color = backgroundColor,
            shape = RoundedCornerShape(16.dp)
        )
        .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(title, fontSize = 18.sp, fontWeight = FontWeight.W600)
        if (useDivider){
            HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(1.dp))
        }
        Box(modifier.fillMaxWidth().weight(1f)) {
            content()
        }
    }
}

@Preview
@Composable
fun TitleCardPreview(){
    TitleCard(
        title = "Title",
        modifier = Modifier.width(200.dp).height(100.dp),
        backgroundColor = Color.White
    ){

    }
}
