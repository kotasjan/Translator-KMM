package cz.jankotas.translator.android.translate.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cz.jankotas.translator.android.core.theme.CustomPreview
import cz.jankotas.translator.android.core.theme.PreviewBox

@Composable
fun ProgressButton(
    text: String,
    isLoading: Boolean,
    onCLick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(100))
            .background(MaterialTheme.colors.primary)
            .clickable(onClick = onCLick)
            .padding(8.dp),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedContent(
            targetState = isLoading,
            label = "ProgressButtonLoading",
        ) { isLoading ->
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colors.onPrimary,
                    strokeWidth = 2.dp,
                )
            } else {
                Text(
                    text = text,
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.button,
                )
            }
        }
    }
}

@CustomPreview
@Composable
fun ProgressButtonPreview() {
    PreviewBox {
        ProgressButton(
            text = "Button Text",
            isLoading = true,
            onCLick = {},
        )
    }
}
