package org.d3if3156.staroom.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.d3if3156.staroom.R
import org.d3if3156.staroom.navigation.Screen
import org.d3if3156.staroom.ui.theme.STAROOMTheme

@Composable
fun SplashScreen(navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    val poppinsblack = FontFamily(Font(R.font.poppinsblack))

    DisposableEffect(Unit) {
        coroutineScope.launch {
            delay(1200)
            navController.navigate(Screen.Home.route)
        }
        onDispose { }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        Box(
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text(text = stringResource(R.string.app_name),
                fontFamily = poppinsblack,
                style = TextStyle(color = Color.White, fontSize = 50.sp)
            )
        }
    }
}


@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun SplashPreview() {
    STAROOMTheme {
        SplashScreen( rememberNavController())
    }
}
