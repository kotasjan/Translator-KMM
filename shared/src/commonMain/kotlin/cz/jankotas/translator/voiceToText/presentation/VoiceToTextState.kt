package cz.jankotas.translator.voiceToText.presentation

data class VoiceToTextState(
    val powerRatios: List<Float> = emptyList(),
    val spokenText: String = "",
    val canRecord: Boolean = false,
    val recordError: String? = null,
    val displayState: DisplayState? = null,
)

enum class DisplayState {
    WaitingToTalk,
    Speaking,
    DisplayingResults,
    Error,
}
