package cz.jankotas.translator.di

import cz.jankotas.translator.testing.FakeHistoryDataSource
import cz.jankotas.translator.testing.FakeTranslateClient
import cz.jankotas.translator.translate.domain.history.HistoryDataSource
import cz.jankotas.translator.translate.domain.translate.TranslateClient
import cz.jankotas.translator.translate.domain.translate.TranslateUseCase

class TestAppModule : AppModule {
    override val historyDataSource: HistoryDataSource = FakeHistoryDataSource()
    override val translateClient: TranslateClient = FakeTranslateClient()
    override val translateUseCase: TranslateUseCase =
        TranslateUseCase(translateClient, historyDataSource)
}
