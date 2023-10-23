package cz.jankotas.translator.di

import cz.jankotas.translator.testing.FakeHistoryDataSource
import cz.jankotas.translator.testing.FakeTranslateClient
import cz.jankotas.translator.testing.FakeVoiceToTextParser
import cz.jankotas.translator.translate.domain.history.HistoryDataSource
import cz.jankotas.translator.translate.domain.translate.TranslateClient
import cz.jankotas.translator.translate.domain.translate.TranslateUseCase
import cz.jankotas.translator.voiceToText.domain.VoiceToTextParser

class TestAppModule : AppModule {
    override val historyDataSource: HistoryDataSource = FakeHistoryDataSource()
    override val translateClient: TranslateClient = FakeTranslateClient()
    override val translateUseCase: TranslateUseCase =
        TranslateUseCase(translateClient, historyDataSource)
    override val voiceParser: VoiceToTextParser = FakeVoiceToTextParser()
}
