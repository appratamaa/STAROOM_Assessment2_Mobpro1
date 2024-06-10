package org.d3if3156.staroom.ui.screen

import android.content.res.Configuration
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import org.d3if3156.staroom.R
import org.d3if3156.staroom.ui.theme.STAROOMTheme

@Composable
fun DisplayAlertDialog(
    openDialog: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    if (openDialog) {
        val poppinsbold = FontFamily(Font(R.font.poppinsbold))
        val poppinsregular = FontFamily(Font(R.font.poppinsregular))

        AlertDialog(
            text = { Text(text = stringResource(R.string.pesanhapus),
                fontFamily = poppinsregular)
           },
            confirmButton = {
                TextButton(onClick = { onConfirmation() }) {
                    Text(text = stringResource(R.string.tombolhapus),
                        fontFamily = poppinsbold)
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismissRequest() }) {
                    Text(text = stringResource(R.string.tombolbatal),
                        fontFamily = poppinsbold)
                }
            },
            onDismissRequest = { onDismissRequest() }
        )
    }
}
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DialogPreview() {
    STAROOMTheme {
        DisplayAlertDialog(
            openDialog = true,
            onDismissRequest = {},
            onConfirmation = {}
        )
    }
}
