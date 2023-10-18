package cz.jankotas.voiceToText.di

import android.app.Application
import androidx.lifecycle.ViewModel
import cz.jankotas.translator.android.voiceToText.data.AndroidVoiceToTextParser
import cz.jankotas.translator.voiceToText.domain.VoiceToTextParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModel::class)
object VoiceToTextModule {
    @Provides
    @ViewModelScoped
    fun provideVoiceToTextParser(app: Application): VoiceToTextParser {
        return AndroidVoiceToTextParser(app)
    }
}
