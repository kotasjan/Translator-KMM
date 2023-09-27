package cz.jankotas.translator.translate.data.translate

import cz.jankotas.translator.NetworkConstants
import cz.jankotas.translator.core.domain.language.Language
import cz.jankotas.translator.translate.domain.translate.TranslateClient
import cz.jankotas.translator.translate.domain.translate.TranslateError
import cz.jankotas.translator.translate.domain.translate.TranslateException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.utils.io.errors.IOException

class KtorTranslateClient(
    private val httpClient: HttpClient,
) : TranslateClient {
    override suspend fun translate(
        fromLanguage: Language,
        toLanguage: Language,
        text: String,
    ): String {
        val result = try {
            httpClient.post {
                url(NetworkConstants.BaseURL + "/translate")
                contentType(ContentType.Application.Json)
                setBody(
                    TranslateDto(
                        textToTranslate = text,
                        sourceLanguageCode = fromLanguage.langCode,
                        targetLanguageCode = toLanguage.langCode,
                    ),
                )
            }
        } catch (e: IOException) {
            throw TranslateException(TranslateError.ServiceUnavailable)
        }
        when (result.status.value) {
            in 200..299 -> Unit
            500 -> throw TranslateException(TranslateError.ServerError)
            in 400..499 -> throw TranslateException(TranslateError.ClientError)
            else -> throw TranslateException(TranslateError.UnknownError)
        }

        return try {
            result.body<TranslatedDto>().translatedText
        } catch (e: Exception) {
            throw TranslateException(TranslateError.ServerError)
        }
    }
}
