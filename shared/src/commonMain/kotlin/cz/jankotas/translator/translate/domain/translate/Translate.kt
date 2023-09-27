package cz.jankotas.translator.translate.domain.translate

import cz.jankotas.translator.core.domain.language.Language
import cz.jankotas.translator.core.domain.util.Resource
import cz.jankotas.translator.translate.domain.history.HistoryDataSource
import cz.jankotas.translator.translate.domain.history.HistoryItem

class Translate(
    private val client: TranslateClient,
    private val historyDataSource: HistoryDataSource,
) {
    suspend fun execute(
        fromLanguage: Language,
        toLanguage: Language,
        text: String,
    ): Resource<String> {
        return try {
            val translatedText = client.translate(
                fromLanguage,
                toLanguage,
                text,
            )
            historyDataSource.insertHistoryItem(
                HistoryItem(
                    id = null,
                    fromLanguageCode = fromLanguage.langCode,
                    fromText = text,
                    toLanguageCode = toLanguage.langCode,
                    toText = translatedText,
                ),
            )
            Resource.Success(translatedText)
        } catch (e: TranslateException) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }
}
