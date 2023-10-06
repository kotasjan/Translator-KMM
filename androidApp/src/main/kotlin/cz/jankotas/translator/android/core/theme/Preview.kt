package cz.jankotas.translator.android.core.theme

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cz.jankotas.translator.android.TranslatorTheme

@Preview(name = "Light preview", showBackground = true)
@Preview(name = "Dark preview", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
annotation class CustomPreview

@Composable
fun PreviewBox(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    TranslatorTheme {
        Box(
            modifier = modifier.background(MaterialTheme.colors.background),
        ) {
            content()
        }
    }
}
