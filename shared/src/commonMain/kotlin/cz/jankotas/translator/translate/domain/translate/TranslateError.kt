package cz.jankotas.translator.translate.domain.translate

enum class TranslateError {
    ServiceUnavailable,
    ClientError,
    ServerError,
    UnknownError,
}

class TranslateException(val error: TranslateError) :
    Exception("An error occurred when translating: $error")
