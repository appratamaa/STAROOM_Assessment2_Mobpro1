package org.d3if3156.staroom.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.d3if3156.staroom.R
import org.d3if3156.staroom.navigation.Screen
import org.d3if3156.staroom.ui.theme.STAROOMTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(navController: NavHostController) {
    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowLeft,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.inverseOnSurface
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.notif),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Black
                    )
                },
                colors =  TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.scrim,
                    titleContentColor = MaterialTheme.colorScheme.inverseOnSurface
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.Home.route)
                    }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Home,
                            contentDescription = stringResource(R.string.home),
                            tint = MaterialTheme.colorScheme.inverseOnSurface,
                        )
                    }
                }
            )
        }
    ) { padding ->
        Text(text = stringResource(R.string.pemberitahuan),
            modifier = Modifier
                .padding(padding)
                .padding(140.dp)
                .padding(top = 80.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Light
        )
        Text(text = stringResource(R.string.copyright),
            modifier = Modifier
                .padding(padding)
                .padding(50.dp),
            fontWeight = FontWeight.ExtraLight,
            textAlign = TextAlign.Center,
        )
    }
}
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun NotificationScreenPreview() {
    STAROOMTheme {
        NotificationScreen(rememberNavController())
    }
}