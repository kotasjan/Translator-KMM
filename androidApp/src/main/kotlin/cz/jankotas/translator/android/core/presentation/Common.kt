package cz.jankotas.translator.android.core.presentation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import cz.jankotas.translator.core.presentation.Strings
import dev.icerock.moko.resources.StringResource

@Composable
fun stringResource(id: StringResource, vararg args: Any): String {
    return Strings(LocalContext.current).get(id, args.toList())
}

fun Context.getString(id: StringResource, vararg args: Any): String {
    return Strings(this).get(id, args.toList())
}
