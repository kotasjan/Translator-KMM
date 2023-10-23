package cz.jankotas.translator.di

import cz.jankotas.translator.database.TranslateDatabase
import cz.jankotas.translator.translate.data.history.SqlDelightHistoryDataSource
import cz.jankotas.translator.translate.data.local.DatabaseDriverFactory
import cz.jankotas.translator.translate.data.remote.HttpClientFactory
import cz.jankotas.translator.translate.data.translate.KtorTranslateClient
import cz.jankotas.translator.translate.domain.history.HistoryDataSource
import cz.jankotas.translator.translate.domain.translate.TranslateClient
import cz.jankotas.translator.translate.domain.translate.TranslateUseCase

interface AppModule {
    val historyDataSource: HistoryDataSource
    val translateClient: TranslateClient
    val translateUseCase: TranslateUseCase
}

class AppModuleImpl : AppModule {

    override val historyDataSource: HistoryDataSource by lazy {
        SqlDelightHistoryDataSource(
            TranslateDatabase(
                DatabaseDriverFactory().create(),
            ),
        )
    }

    override val translateClient: TranslateClient by lazy {
        KtorTranslateClient(
            HttpClientFactory().create(),
        )
    }

    override val translateUseCase: TranslateUseCase by lazy {
        TranslateUseCase(translateClient, historyDataSource)
    }
}
