package org.d3if3156.staroom.ui.screen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
fun NakostarScreen(navController: NavHostController) {
    Scaffold (
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Screen.Developer.route)
                    }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.AccountCircle,
                            contentDescription = stringResource(R.string.developer),
                            tint = MaterialTheme.colorScheme.inverseOnSurface
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
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
                        navController.navigate(Screen.About.route)
                    }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowRight,
                            contentDescription = stringResource(R.string.next),
                            tint = MaterialTheme.colorScheme.inverseOnSurface
                        )
                    }
                }
            )
        }
    ) { padding ->
        ScreenContent(Modifier.padding(padding))

    }
}
@Composable
fun ScreenContent(modifier: Modifier) {
    var name by rememberSaveable { mutableStateOf("") }
    var nameError by rememberSaveable { mutableStateOf(false) }
    var zodiac by rememberSaveable { mutableStateOf("") }
    var zodiacError by rememberSaveable { mutableStateOf(false) }

    var isChecked by rememberSaveable { mutableStateOf(false) }
    var kategori by rememberSaveable { mutableStateOf(0) }
    val context = LocalContext.current
    val radioOptions = listOf(
        stringResource(id = R.string.pria),
        stringResource(id = R.string.wanita)
    )

    var gender by rememberSaveable { mutableStateOf(radioOptions[0]) }
    var gambarId by rememberSaveable { mutableStateOf(0) }
    var kondisi by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.intro),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.fillMaxWidth(),
        )
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = stringResource(R.string.name)) },
            isError = nameError,
            trailingIcon = { IconPicker(nameError)},
            supportingText = { ErrorHint(nameError)},
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(value = zodiac,
            onValueChange = { zodiac = it},
            label = { Text(text = stringResource(R.string.zodiak)) },
            isError = zodiacError,
            trailingIcon = { IconPicker(zodiacError)},
            supportingText = { ErrorHint(zodiacError)},
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier.fillMaxWidth()
        )
        Row (
            modifier = Modifier
                .padding(top = 6.dp)
                .border(1.dp, Color.Transparent, RoundedCornerShape(4.dp))
        ) {
            radioOptions.forEach { text ->
                GenderOption(label = text,
                    isSelected = gender == text,
                    modifier = Modifier
                        .selectable(
                            selected = gender == text,
                            onClick = { gender = text },
                            role = Role.RadioButton
                        )
                        .weight(1f)
                        .padding(16.dp)
                )
            }
        }
        Switch(
            checked = isChecked,
            onCheckedChange = { isChecked = it },
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.primary,
                checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                uncheckedThumbColor = MaterialTheme.colorScheme.secondary,
            )
        )
        Text(
            text = if (isChecked) stringResource(R.string.percaya) else stringResource(R.string.tidak),
            style = MaterialTheme.typography.labelSmall,
            color = Color.Red
        )
        Button(
            onClick = {
                nameError = (name.isBlank() || name.any { it.isDigit() })
                zodiacError = (zodiac.isBlank() || zodiac.any { it.isDigit() })

                if (nameError || zodiacError) return@Button
                kondisi = true
                kategori = getZodiak(zodiac, gender == radioOptions[0])
            },
            modifier = Modifier.padding(top = 6.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 8.dp)
        ) {
            Text(
                text = stringResource(R.string.ramal),
                fontWeight = FontWeight.ExtraBold
            )
        }
        if (kondisi == true) {

            gambarId = when (kategori) {
                R.string.Taurus -> R.drawable.taurus
                R.string.Leo -> R.drawable.leo
                R.string.Capricorn -> R.drawable.capricorn
                R.string.Sagittarius -> R.drawable.sagittarius
                R.string.Aquarius -> R.drawable.aquarius
                R.string.Cancer -> R.drawable.cancer
                R.string.Scorpio -> R.drawable.scorpio
                R.string.Gemini -> R.drawable.gemini
                R.string.Libra -> R.drawable.libra
                R.string.Pisces -> R.drawable.pisces
                R.string.Aries -> R.drawable.aries
                R.string.Virgo -> R.drawable.virgo
                else -> 0
            }
            if (gambarId != 0) {
                Image(
                    painter = painterResource(gambarId),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .padding(12.dp)
                        .padding(top = 6.dp)
                        .size(140.dp)
                        .fillMaxSize(),
                )
            }
            Text(
                text = stringResource(R.string.hasil, name, stringResource(id = getGender(gender == radioOptions[0]))),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(id = kategori).capitalize(),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
        Button(onClick = {
            shareData(
                context = context,
                message = context.getString(R.string.template,
                    name, zodiac)
            )
        },
            modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 8.dp)
        ) {
            Text(text = stringResource(R.string.bagikan),
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}
@Composable
fun GenderOption(label: String, isSelected: Boolean, modifier: Modifier) {
    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = null)
        Text(text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
private fun getZodiak(zodiacs: String, isMale: Boolean): Int {
    var kategori = 0

    if (isMale) {
        kategori = when {
            zodiacs.equals("Taurus", true) -> R.string.Taurus
            zodiacs.equals("Leo", true) -> R.string.Leo
            zodiacs.equals("Capricorn", true) -> R.string.Capricorn
            zodiacs.equals("Sagittarius", true) -> R.string.Sagittarius
            zodiacs.equals("Aquarius", true) -> R.string.Aquarius
            zodiacs.equals("Cancer", true) -> R.string.Cancer
            zodiacs.equals("Scorpio", true) -> R.string.Scorpio
            zodiacs.equals("Gemini", true) -> R.string.Gemini
            zodiacs.equals("Libra", true) -> R.string.Libra
            zodiacs.equals("Pisces", true) -> R.string.Pisces
            zodiacs.equals("Aries", true) -> R.string.Aries
            zodiacs.equals("Virgo", true) -> R.string.Virgo
            else -> R.string.invalid
        }
    } else {
        kategori = when {
            zodiacs.equals("Taurus", true) -> R.string.Taurus
            zodiacs.equals("Leo", true) -> R.string.Leo
            zodiacs.equals("Capricorn", true) -> R.string.Capricorn
            zodiacs.equals("Sagittarius", true) -> R.string.Sagittarius
            zodiacs.equals("Aquarius", true) -> R.string.Aquarius
            zodiacs.equals("Cancer", true) -> R.string.Cancer
            zodiacs.equals("Scorpio", true) -> R.string.Scorpio
            zodiacs.equals("Gemini", true) -> R.string.Gemini
            zodiacs.equals("Libra", true) -> R.string.Libra
            zodiacs.equals("Pisces", true) -> R.string.Pisces
            zodiacs.equals("Aries", true) -> R.string.Aries
            zodiacs.equals("Virgo", true) -> R.string.Virgo
            else -> R.string.invalid
        }
    }

    return kategori
}

private fun getGender(isMale: Boolean): Int {
    var gender = 0
    if (isMale) gender = R.string.pria else gender = R.string.wanita
    return gender

}
private fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}
@Composable
fun IconPicker(isError: Boolean) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    }
}
@Composable
fun ErrorHint(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(R.string.invalid),
            color = Color.Red)
    }
}
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun NakostarPreview() {
    STAROOMTheme{
        NakostarScreen(rememberNavController())
    }
}