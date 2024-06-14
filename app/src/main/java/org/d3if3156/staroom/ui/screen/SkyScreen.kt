package org.d3if3156.staroom.ui.screen

import android.content.ContentResolver
import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.exceptions.ClearCredentialException
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3156.staroom.R
import org.d3if3156.staroom.database.StarDb
import org.d3if3156.staroom.model.Sky
import org.d3if3156.staroom.model.Star
import org.d3if3156.staroom.model.User
import org.d3if3156.staroom.navigation.Screen
import org.d3if3156.staroom.network.ApiStatus
import org.d3if3156.staroom.network.StarApi
import org.d3if3156.staroom.network.UserDataStore
import org.d3if3156.staroom.ui.theme.STAROOMTheme
import org.d3if3156.staroom.util.SettingsDataStore
import org.d3if3156.staroom.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkyScreen(navController: NavHostController) {
    val poppinsblack = FontFamily(Font(R.font.poppinsblack))

    val dataStore = SettingsDataStore(LocalContext.current)
    val showList by dataStore.layoutFlow.collectAsState(true)

    var showDialog by remember { mutableStateOf(false) }
    var showStarDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val dataStoreUser = UserDataStore(context)
    val user by dataStoreUser.userFlow.collectAsState(User())

    var bitmap: Bitmap? by remember { mutableStateOf(null) }
    val launcher = rememberLauncherForActivityResult(CropImageContract()) {
        bitmap = getCroppedImage(context.contentResolver, it)
        if (bitmap != null) showStarDialog = true
    }

    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        showDialog = true
                    }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = stringResource(R.string.profil),
                            tint = Color.White
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
                            tint = Color.White
                        )
                    }
                }
            )
        },
        floatingActionButton = {
                FloatingActionButton(onClick = {
                    val options = CropImageContractOptions(
                        null, CropImageOptions(
                            imageSourceIncludeGallery = false,
                            imageSourceIncludeCamera = true,
                            fixAspectRatio = true
                        )
                    )
                    launcher.launch(options)
                }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.tambah_rasi)
                    )
                }
        }
    ) { padding ->
        SkyScreenContent(showList, Modifier.padding(padding), navController)

        if (showDialog) {
            ProfilDialog(
                user = user,
                onDismissRequest = { showDialog = false },
                onConfirmation = {
                    CoroutineScope(Dispatchers.IO).launch { signOut(context, dataStoreUser) }
                    showDialog = false
                },
                navController = navController
            )
        }
        if (showStarDialog) {
            StarDialog(
                bitmap = bitmap,
                onDismissRequest = { showStarDialog = false })
            { namaRasiBintang, daerahLangitDifoto ->
            Log.d("TAMBAH", "$namaRasiBintang $daerahLangitDifoto ditambahkan.")
                showStarDialog = false
            }
        }
    }
}
@Composable
fun SkyScreenContent(showList: Boolean, modifier: Modifier, navController: NavHostController) {
    val poppinslight = FontFamily(Font(R.font.poppinslight))

    val viewModel: MainViewModel = viewModel()
    val data by viewModel.datasky
    val status by viewModel.status.collectAsState()

    val context = LocalContext.current
    val db = StarDb.getInstance(context)
    val factory = ViewModelFactory(db.dao)

    when (status) {
        ApiStatus.LOADING -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        ApiStatus.SUCCESS -> {
            LazyVerticalGrid(
                modifier = modifier
                    .fillMaxSize()
                    .padding(4.dp),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(data) { SkyListItem(sky = it) {

                }
                }
            }
        }
        ApiStatus.FAILED -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(id = R.string.error))
                Button(
                    onClick = { viewModel.retrieveData() },
                    modifier = Modifier.padding(top = 16.dp),
                    contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
                ) {
                    Text(text = stringResource(id = R.string.try_again))
                }
            }
        }
    }
}
@Composable
fun SkyListItem(sky: Sky, onClick: () -> Unit) {
    val poppinsbold = FontFamily(Font(R.font.poppinsbold))
    val poppinsregular = FontFamily(Font(R.font.poppinsregular))
    val poppinslight = FontFamily(Font(R.font.poppinslight))

    Box(
        modifier = Modifier
            .padding(4.dp)
            .border(1.dp, Color.Gray),
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(StarApi.getStarUrl(sky.imageId))
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.picture, sky.nama_rasibintang),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.loading_img),
            error = painterResource(id = R.drawable.baseline_broken_image_24),
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .background(Color(red = 0f, green = 0f, blue = 0f, alpha = 0.5f))
                .padding(4.dp)
        ) {
            Text(
                text = sky.nama_rasibintang,
                fontWeight = FontWeight.Bold,
                color = Color.White)
            Text(
                text = sky.daerah_langitdifoto,
                fontStyle = FontStyle.Italic,
                fontSize = 14.sp,
                color = Color.White)
        }
    }
}
@Composable
fun SkyGridItem(star: Star, onClick: () -> Unit) {
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
private suspend fun signOut(context: Context, dataStore: UserDataStore) {
    try {
        val credentialManager = CredentialManager.create(context)
        credentialManager.clearCredentialState(
            ClearCredentialStateRequest()
        )
        dataStore.saveData(User(), isLoggedIn = false)
    } catch (e: ClearCredentialException) {
        Log.e("SIGN-IN", "Error: ${e.errorMessage}")
    }
}
private fun getCroppedImage(
    resolver: ContentResolver,
    result: CropImageView.CropResult
): Bitmap? {
    if (!result.isSuccessful) {
        Log.e("IMAGE", "Error: ${result.error}")
        return null
    }
    val uri = result.uriContent ?: return null

    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
        MediaStore.Images.Media.getBitmap(resolver, uri)
    } else {
        val source = ImageDecoder.createSource(resolver, uri)
        ImageDecoder.decodeBitmap(source)
    }
}
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun SkyPreview() {
    STAROOMTheme {
        SkyScreen(rememberNavController())
    }
}