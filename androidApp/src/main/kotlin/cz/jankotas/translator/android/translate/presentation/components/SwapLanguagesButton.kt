package cz.jankotas.translator.android.translate.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import cz.jankotas.translator.SharedRes
import cz.jankotas.translator.android.R
import cz.jankotas.translator.android.core.presentation.stringResource
import cz.jankotas.translator.android.core.theme.CustomPreview
import cz.jankotas.translator.android.core.theme.PreviewBox

@Composable
fun SwapLanguagesButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colors.primary),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.swap_languages),
            contentDescription = stringResource(id = SharedRes.strings.swap_languages),
            tint = MaterialTheme.colors.onPrimary,
        )
    }
}

@CustomPreview
@Composable
fun SwapLanguagesButtonPreview() {
    PreviewBox {
        SwapLanguagesButton(
            onClick = {},
        )
    }
}
