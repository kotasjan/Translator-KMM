package cz.jankotas.translator.translate.presentation

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import cz.jankotas.translator.core.presentation.UiLanguage
import cz.jankotas.translator.translate.data.local.FakeHistoryDataSource
import cz.jankotas.translator.translate.data.remote.FakeTranslateClient
import cz.jankotas.translator.translate.domain.history.HistoryItem
import cz.jankotas.translator.translate.domain.translate.TranslateUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.test.BeforeTest
import kotlin.test.Test

class TranslateViewModelTest {

    private lateinit var viewModel: TranslateViewModel

    private lateinit var client: FakeTranslateClient
    private lateinit var dataSource: FakeHistoryDataSource

    @BeforeTest
    fun setUp() {
        client = FakeTranslateClient()
        dataSource = FakeHistoryDataSource()
        val translateUseCase = TranslateUseCase(
            client = client,
            historyDataSource = dataSource,
        )
        viewModel = TranslateViewModel(
            translateUseCase = translateUseCase,
            historyDataSource = dataSource,
            coroutineScope = CoroutineScope(Dispatchers.Default),
        )
    }

    @Test
    fun `State and history items are properly combined`() = runBlocking {
        viewModel.state.test {
            val initialState = awaitItem()
            assertThat(initialState).isEqualTo(TranslateState())

            val item = HistoryItem(
                id = 0,
                fromLanguageCode = "en",
                fromText = "from",
                toLanguageCode = "cs",
                toText = "to",
            )
            dataSource.insertHistoryItem(item)

            val state = awaitItem()
            val expected = UiHistoryItem(
                id = item.id!!,
                fromText = item.fromText,
                fromLanguage = UiLanguage.byCode(item.fromLanguageCode),
                toText = item.toText,
                toLanguage = UiLanguage.byCode(item.toLanguageCode),
            )
            assertThat(state.history.first()).isEqualTo(expected)
        }
    }

    @Test
    fun `Translate success - state properly updated`() = runBlocking {
        viewModel.state.test {
            awaitItem()

            viewModel.onEvent(TranslateEvent.ChangeTranslationText("test"))
            awaitItem()

            viewModel.onEvent(TranslateEvent.Translate)

            val loadingState = awaitItem()
            assertThat(loadingState.isTranslating).isTrue()

            val resultState = awaitItem()
            assertThat(resultState.isTranslating).isFalse()
            assertThat(resultState.toText).isEqualTo(client.translatedText)
        }
    }

    @Test
    fun `Select history item - state properly updated`() = runBlocking {
        viewModel.state.test {
            awaitItem()

            val historyItem = UiHistoryItem(
                id = 0L,
                fromText = "from",
                fromLanguage = UiLanguage.byCode("en"),
                toLanguage = UiLanguage.byCode("cs"),
                toText = "to",
            )

            viewModel.onEvent(
                TranslateEvent.SelectHistoryItem(
                    item = historyItem,
                ),
            )

            val resultState = awaitItem()
            assertThat(resultState.fromText).isEqualTo(historyItem.fromText)
            assertThat(resultState.fromLanguage).isEqualTo(historyItem.fromLanguage)
            assertThat(resultState.toText).isEqualTo(historyItem.toText)
            assertThat(resultState.toLanguage).isEqualTo(historyItem.toLanguage)
        }
    }
}
