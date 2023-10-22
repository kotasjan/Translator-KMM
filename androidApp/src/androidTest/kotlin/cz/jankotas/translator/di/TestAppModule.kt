package cz.jankotas.translator.di

import cz.jankotas.translator.translate.data.local.FakeHistoryDataSource
import cz.jankotas.translator.translate.data.remote.FakeTranslateClient
import cz.jankotas.translator.translate.domain.history.HistoryDataSource
import cz.jankotas.translator.translate.domain.translate.TranslateClient
import cz.jankotas.translator.translate.domain.translate.TranslateUseCase
import cz.jankotas.translator.voiceToText.data.FakeVoiceToTextParser
import cz.jankotas.translator.voiceToText.domain.VoiceToTextParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Provides
    @Singleton
    fun provideFakeTranslateClient(): TranslateClient {
        return FakeTranslateClient()
    }

    @Provides
    @Singleton
    fun provideFakeHistoryDataSource(): HistoryDataSource {
        return FakeHistoryDataSource()
    }

    @Provides
    @Singleton
    fun provideTranslateUseCase(
        client: TranslateClient,
        dataSource: HistoryDataSource,
    ): TranslateUseCase {
        return TranslateUseCase(
            client = client,
            historyDataSource = dataSource,
        )
    }

    @Provides
    @Singleton
    fun provideFakeVoiceToTextParser(): VoiceToTextParser {
        return FakeVoiceToTextParser()
    }
}
