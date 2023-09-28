package cz.jankotas.translator.android.translate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.jankotas.translator.translate.domain.history.HistoryDataSource
import cz.jankotas.translator.translate.domain.translate.TranslateUseCase
import cz.jankotas.translator.translate.presentation.TranslateEvent
import cz.jankotas.translator.translate.presentation.TranslateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidTranslateViewModel @Inject constructor(
    private val translateUseCase: TranslateUseCase,
    private val historyDataSource: HistoryDataSource,
) : ViewModel() {

    private val viewModel by lazy {
        TranslateViewModel(
            translateUseCase = translateUseCase,
            historyDataSource = historyDataSource,
            coroutineScope = viewModelScope,
        )
    }

    val state = viewModel.state

    fun onEvent(event: TranslateEvent) {
        viewModel.onEvent(event)
    }
}
