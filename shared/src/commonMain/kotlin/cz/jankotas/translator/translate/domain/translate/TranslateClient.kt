package cz.jankotas.translator.translate.domain.translate

import cz.jankotas.translator.core.domain.language.Language

interface TranslateClient {
    suspend fun translate(
        fromLanguage: Language,
        toLanguage: Language,
        text: String,
    ): String
}
