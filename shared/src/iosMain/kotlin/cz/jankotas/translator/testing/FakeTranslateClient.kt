package cz.jankotas.translator.testing

import cz.jankotas.translator.core.domain.language.Language
import cz.jankotas.translator.translate.domain.translate.TranslateClient

class FakeTranslateClient : TranslateClient {

    var translatedText = "text translation"
    override suspend fun translate(
        fromLanguage: Language,
        toLanguage: Language,
        text: String,
    ): String {
        return translatedText
    }
}
