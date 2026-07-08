package com.haphap.app.presentation.register.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.presentation.register.type.RegisterPassResultType

@Composable
fun RegisterPassResultConfirm(
    status: RegisterPassResultType,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = HapHapTheme.colors.sub100,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
        Text(
            text = status.text,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            textAlign = TextAlign.Center,
            color = HapHapTheme.colors.primary500,
            style = HapHapTheme.typography.body.sb14,
        )

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_check_18),
            contentDescription = null,
            tint = HapHapTheme.colors.primary100,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterEnd),
        )
    }
}

@Preview
@Composable
private fun RegisterPassResultConfirmPreview() {
    HapHapTheme {
        Column(
            modifier = Modifier
                .background(HapHapTheme.colors.white)
                .padding(all = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            RegisterPassResultConfirm(status = RegisterPassResultType.PASS)
            RegisterPassResultConfirm(status = RegisterPassResultType.FAILED)
            RegisterPassResultConfirm(status = RegisterPassResultType.DONT_KNOW)
        }
    }
}