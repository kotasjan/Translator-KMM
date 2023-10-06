package cz.jankotas.translator.android.translate.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cz.jankotas.translator.android.core.theme.LightBlue
import cz.jankotas.translator.core.presentation.UiLanguage

@Composable
fun LanguageDisplay(
    language: UiLanguage,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SmallLanguageIcon(language = language)
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = language.language.langName,
            color = LightBlue,
        )
    }
}
