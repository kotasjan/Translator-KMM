package cz.jankotas.translator.di

import cz.jankotas.translator.database.TranslateDatabase
import cz.jankotas.translator.translate.data.history.SqlDelightHistoryDataSource
import cz.jankotas.translator.translate.data.local.DatabaseDriverFactory
import cz.jankotas.translator.translate.data.remote.HttpClientFactory
import cz.jankotas.translator.translate.data.translate.KtorTranslateClient
import cz.jankotas.translator.translate.domain.history.HistoryDataSource
import cz.jankotas.translator.translate.domain.translate.TranslateClient
import cz.jankotas.translator.translate.domain.translate.TranslateUseCase

class AppModule {

    val historyDataSource: HistoryDataSource by lazy {
        SqlDelightHistoryDataSource(
            TranslateDatabase(
                DatabaseDriverFactory().create(),
            ),
        )
    }

    private val translateClient: TranslateClient by lazy {
        KtorTranslateClient(
            HttpClientFactory().create(),
        )
    }

    val translateUseCase: TranslateUseCase by lazy {
        TranslateUseCase(translateClient, historyDataSource)
    }
}
