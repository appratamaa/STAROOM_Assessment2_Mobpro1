package org.d3if3156.staroom.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3156.staroom.R
import org.d3if3156.staroom.database.StarDb
import org.d3if3156.staroom.model.Star
import org.d3if3156.staroom.navigation.Screen
import org.d3if3156.staroom.ui.theme.STAROOMTheme
import org.d3if3156.staroom.util.SettingsDataStore
import org.d3if3156.staroom.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val poppinsblack = FontFamily(Font(R.font.poppinsblack))

    val dataStore = SettingsDataStore(LocalContext.current)
    val showList by dataStore.layoutFlow.collectAsState(true)

    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.inverseOnSurface
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = TextStyle(color = Color.White),
                        fontSize = 20.sp,
                        fontFamily = poppinsblack,
                        fontWeight = FontWeight.Black
                    )
                },
                colors =  TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Black,
                ),
                actions = {
                    IconButton(
                        onClick = {
                            CoroutineScope(Dispatchers.IO).launch {
                                dataStore.saveLayout(!showList)
                            }
                        }) {
                        Icon(
                            painter = painterResource(
                                if (showList) R.drawable.baseline_grid_view_24
                                else R.drawable.baseline_view_list_24
                            ),
                            contentDescription = stringResource(
                                if (showList) R.string.grid
                                else R.string.list
                            ),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                }
            )
        },
        floatingActionButton = {
            Column {
                FloatingActionButton(onClick = {
                    navController.navigate(Screen.Nakostar.route)
                }) {
                    Icon(imageVector = Icons.Filled.Star,
                        contentDescription = stringResource(R.string.tambahstar),
                        tint = MaterialTheme.colorScheme.primary)
                }
                Spacer(modifier = Modifier.height(16.dp))
                FloatingActionButton(onClick = {
                    navController.navigate(Screen.FormStar.route)
                }) {
                    Icon(imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(R.string.tambahstar),
                        tint = MaterialTheme.colorScheme.primary)
                }
            }
        }
    ) { padding ->
        ScreenContent(showList, Modifier.padding(padding), navController)
    }
}
@Composable
fun ScreenContent(showList: Boolean, modifier: Modifier, navController: NavHostController) {
    val poppinslight = FontFamily(Font(R.font.poppinslight))

    val context = LocalContext.current
    val db = StarDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)
    val viewModel: MainViewModel = viewModel(factory = factory)
    val data by viewModel.data.collectAsState()

    if (data.isEmpty()) {
        Column (
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = stringResource(id = R.string.listkosong),
                fontFamily = poppinslight )

        }
    }
    else {
        if (showList) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(bottom = 84.dp)
            ) {
                items(data) {
                    ListItem(star = it) {
                        navController.navigate(Screen.FormUbah.withId(it.id))
                    }
                    Divider()
                }

            }

        }
        else {
            LazyVerticalStaggeredGrid(
                modifier = modifier.fillMaxSize(),
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp, 8.dp, 8.dp, 84.dp)
            ) {
                items(data) {
                    GridItem(star = it) {
                        navController.navigate(Screen.FormUbah.withId(it.id))

                    }
                }
            }
        }
    }
}
@Composable
fun ListItem(star: Star, onClick: () -> Unit) {
    val poppinsbold = FontFamily(Font(R.font.poppinsbold))
    val poppinsregular = FontFamily(Font(R.font.poppinsregular))
    val poppinslight = FontFamily(Font(R.font.poppinslight))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = star.nama,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontFamily = poppinsbold
        )
        Text(
            text = star.tanggal,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontFamily = poppinsregular)
        Text(text = star.star,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            fontFamily = poppinslight
        )
    }
}
@Composable
fun GridItem(star: Star, onClick: () -> Unit) {
    val poppinsbold = FontFamily(Font(R.font.poppinsbold))
    val poppinsregular = FontFamily(Font(R.font.poppinsregular))
    val poppinslight = FontFamily(Font(R.font.poppinslight))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = star.nama,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = poppinsbold
            )
            Text(
                text = star.tanggal,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontFamily = poppinsregular)
            Text(text = star.star,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                fontFamily = poppinslight
            )
        }
    }
}
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ScreenPreview() {
    STAROOMTheme {
        MainScreen(rememberNavController())
    }
}