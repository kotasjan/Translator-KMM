package cz.jankotas.translator.android.di

import android.app.Application
import app.cash.sqldelight.db.SqlDriver
import cz.jankotas.translator.database.TranslateDatabase
import cz.jankotas.translator.translate.data.history.SqlDelightHistoryDataSource
import cz.jankotas.translator.translate.data.local.DatabaseDriverFactory
import cz.jankotas.translator.translate.data.remote.HttpClientFactory
import cz.jankotas.translator.translate.data.translate.KtorTranslateClient
import cz.jankotas.translator.translate.domain.history.HistoryDataSource
import cz.jankotas.translator.translate.domain.translate.TranslateClient
import cz.jankotas.translator.translate.domain.translate.TranslateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClientFactory().create()
    }

    @Provides
    @Singleton
    fun provideTranslateClient(httpClient: HttpClient): TranslateClient {
        return KtorTranslateClient(httpClient)
    }

    @Provides
    @Singleton
    fun provideDatabaseDriverFactory(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).create()
    }

    @Provides
    @Singleton
    fun provideHistoryDataSource(driver: SqlDriver): HistoryDataSource {
        return SqlDelightHistoryDataSource(TranslateDatabase(driver))
    }

    @Provides
    @Singleton
    fun provideTranslateUseCase(
        client: TranslateClient,
        dataSource: HistoryDataSource,
    ): TranslateUseCase {
        return TranslateUseCase(client, dataSource)
    }
}
