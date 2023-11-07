package cz.jankotas.translator.android.translate.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import cz.jankotas.translator.SharedRes
import cz.jankotas.translator.android.core.presentation.stringResource
import cz.jankotas.translator.android.core.theme.CustomPreview
import cz.jankotas.translator.android.core.theme.PreviewBox
import cz.jankotas.translator.core.presentation.UiLanguage

@Composable
fun LanguageDropDown(
    language: UiLanguage,
    isOpen: Boolean,
    onClick: () -> Unit,
    onDismiss: () -> Unit,
    onSelectLanguage: (UiLanguage) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        DropdownMenu(expanded = isOpen, onDismissRequest = onDismiss) {
            UiLanguage.allLanguages.forEach { language ->
                LanguageDropDownItem(
                    language = language,
                    onClick = {
                        onSelectLanguage(language)
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(16.dp),
        ) {
            AsyncImage(
                model = language.drawableRes,
                contentDescription = language.language.langName,
                modifier = Modifier.size(30.dp),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                modifier = Modifier.weight(1f),
                text = language.language.langName,
                color = colorResource(id = SharedRes.colors.lightBlue.resourceId),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
            Icon(
                imageVector = if (isOpen) {
                    Icons.Default.ArrowDropUp
                } else {
                    Icons.Default.ArrowDropDown
                },
                contentDescription = if (isOpen) {
                    stringResource(id = SharedRes.strings.close)
                } else {
                    stringResource(id = SharedRes.strings.open)
                },
                tint = colorResource(id = SharedRes.colors.lightBlue.resourceId),
                modifier = Modifier
                    .size(30.dp),
            )
        }
    }
}

@CustomPreview
@Composable
fun LanguageDropDownPreview() {
    PreviewBox {
        LanguageDropDown(
            language = UiLanguage.allLanguages.first(),
            isOpen = false,
            onClick = {},
            onDismiss = {},
            onSelectLanguage = {},
        )
    }
}
