package cz.jankotas.translator.android.translate.presentation

import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import cz.jankotas.translator.SharedRes
import cz.jankotas.translator.android.R
import cz.jankotas.translator.android.core.presentation.getString
import cz.jankotas.translator.android.core.presentation.stringResource
import cz.jankotas.translator.android.core.theme.CustomPreview
import cz.jankotas.translator.android.core.theme.PreviewBox
import cz.jankotas.translator.android.translate.presentation.components.LanguageDropDown
import cz.jankotas.translator.android.translate.presentation.components.SwapLanguagesButton
import cz.jankotas.translator.android.translate.presentation.components.TranslateHistoryItem
import cz.jankotas.translator.android.translate.presentation.components.TranslateTextField
import cz.jankotas.translator.android.translate.presentation.components.rememberTextToSpeech
import cz.jankotas.translator.core.presentation.UiLanguage
import cz.jankotas.translator.translate.domain.translate.TranslateError
import cz.jankotas.translator.translate.presentation.TranslateEvent
import cz.jankotas.translator.translate.presentation.TranslateState
import java.util.Locale

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TranslateScreen(
    state: TranslateState,
    onEvent: (TranslateEvent) -> Unit,
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = state.error) {
        val message = when (state.error) {
            TranslateError.ServiceUnavailable -> context.getString(id = SharedRes.strings.error_service_unavailable)
            TranslateError.ServerError -> context.getString(id = SharedRes.strings.error_server_error)
            TranslateError.ClientError -> context.getString(id = SharedRes.strings.error_client_error)
            TranslateError.UnknownError -> context.getString(id = SharedRes.strings.error_unknown_error)
            else -> null
        }

        message?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            onEvent(TranslateEvent.OnErrorSeen)
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(TranslateEvent.RecordAudio)
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .size(75.dp),
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.mic),
                    contentDescription = stringResource(id = SharedRes.strings.record_audio),
                    modifier = Modifier
                        .size(50.dp),
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { padding ->
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
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    LanguageDropDown(
                        modifier = Modifier.weight(1f),
                        language = state.fromLanguage,
                        isOpen = state.isChoosingFromLanguage,
                        onClick = { onEvent(TranslateEvent.OpenFromLanguageDropDown) },
                        onDismiss = { onEvent(TranslateEvent.StopChoosingLanguage) },
                        onSelectLanguage = { onEvent(TranslateEvent.ChooseFromLanguage(it)) },
                    )
                    SwapLanguagesButton(onClick = { onEvent(TranslateEvent.SwapLanguages) })
                    LanguageDropDown(
                        modifier = Modifier.weight(1f),
                        language = state.toLanguage,
                        isOpen = state.isChoosingToLanguage,
                        onClick = { onEvent(TranslateEvent.OpenToLanguageDropDown) },
                        onDismiss = { onEvent(TranslateEvent.StopChoosingLanguage) },
                        onSelectLanguage = { onEvent(TranslateEvent.ChooseToLanguage(it)) },
                    )
                }
            }
            item {
                val clipboardManager = LocalClipboardManager.current
                val keyboardController = LocalSoftwareKeyboardController.current
                val tts = rememberTextToSpeech()
                TranslateTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    fromText = state.fromText,
                    toText = state.toText,
                    isTranslating = state.isTranslating,
                    fromLanguage = state.fromLanguage,
                    toLanguage = state.toLanguage,
                    onTranslateClick = {
                        keyboardController?.hide()
                        onEvent(TranslateEvent.Translate)
                    },
                    onTextChange = {
                        onEvent(TranslateEvent.ChangeTranslationText(it))
                    },
                    onCopyClick = { text ->
                        clipboardManager.setText(
                            buildAnnotatedString {
                                append(text = text)
                            },
                        )
                        Toast.makeText(
                            context,
                            context.getString(id = SharedRes.strings.copied_to_clipboard),
                            Toast.LENGTH_LONG,
                        ).show()
                    },
                    onCloseClick = {
                        onEvent(TranslateEvent.CloseTranslation)
                    },
                    onSpeakerClick = {
                        tts.language = state.toLanguage.toLocale() ?: Locale.ENGLISH
                        tts.speak(
                            state.toText,
                            TextToSpeech.QUEUE_FLUSH,
                            null,
                            null,
                        )
                    },
                    onTextFieldClick = {
                        onEvent(TranslateEvent.EditTranslation)
                    },
                )
            }
            item {
                if (state.history.isNotEmpty()) {
                    Text(
                        text = stringResource(id = SharedRes.strings.history),
                        style = MaterialTheme.typography.h2,
                    )
                }
            }
            items(state.history) { item ->
                TranslateHistoryItem(
                    modifier = Modifier
                        .fillMaxWidth(),
                    item = item,
                    onClick = { onEvent(TranslateEvent.SelectHistoryItem(item)) },
                )
            }
        }
    }
}

@CustomPreview
@Composable
fun TranslateScreenPreview() {
    PreviewBox {
        TranslateScreen(
            state = TranslateState(
                fromText = "Ahoj",
                fromLanguage = UiLanguage.byCode("cs"),
                toText = "Hello",
                toLanguage = UiLanguage.byCode("en"),
            ),
            onEvent = {},
        )
    }
}
