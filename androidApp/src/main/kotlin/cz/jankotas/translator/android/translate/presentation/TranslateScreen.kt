package cz.jankotas.translator.android.translate.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cz.jankotas.translator.android.translate.presentation.components.LanguageDropDown
import cz.jankotas.translator.android.translate.presentation.components.SwapLanguagesButton
import cz.jankotas.translator.translate.presentation.TranslateEvent
import cz.jankotas.translator.translate.presentation.TranslateState

@Composable
fun TranslateScreen(
    state: TranslateState,
    onEvent: (TranslateEvent) -> Unit,
) {
    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    LanguageDropDown(
                        language = state.fromLanguage,
                        isOpen = state.isChoosingFromLanguage,
                        onClick = { onEvent(TranslateEvent.OpenFromLanguageDropDown) },
                        onDismiss = { onEvent(TranslateEvent.StopChoosingLanguage) },
                        onSelectLanguage = { onEvent(TranslateEvent.ChooseFromLanguage(it)) },
                    )
                    SwapLanguagesButton(onClick = { onEvent(TranslateEvent.SwapLanguages) })
                    LanguageDropDown(
                        language = state.toLanguage,
                        isOpen = state.isChoosingToLanguage,
                        onClick = { onEvent(TranslateEvent.OpenToLanguageDropDown) },
                        onDismiss = { onEvent(TranslateEvent.StopChoosingLanguage) },
                        onSelectLanguage = { onEvent(TranslateEvent.ChooseToLanguage(it)) },
                    )
                }
            }
        }
    }
}
