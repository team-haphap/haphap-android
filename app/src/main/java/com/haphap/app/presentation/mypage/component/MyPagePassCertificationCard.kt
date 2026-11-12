package com.haphap.app.presentation.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable
import com.haphap.app.presentation.mypage.style.passCertificationStyle
import com.haphap.app.presentation.mypage.type.PassCertificationType

@Composable
fun MyPagePassCertificationCard(
    type: PassCertificationType,
    count: Int,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val certificationStyle = passCertificationStyle(type)
    val isActive = count > 0

    Column(
        modifier = modifier
            .background(
                color = certificationStyle.backgroundColor,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(horizontal = 12.dp, vertical = 10.dp),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = type.iconRes),
            contentDescription = null,
            tint = Color.Unspecified,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = if (isActive) type.activeTitle(count) else type.emptyTitle,
            style = HapHapTheme.typography.body.sb13,
            color = certificationStyle.pointColor,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = if (isActive) type.activeDescription else type.emptyDescription,
            style = HapHapTheme.typography.caption.r10,
            color = HapHapTheme.colors.gray500,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = type.buttonText,
            style = HapHapTheme.typography.caption.sb10,
            color = HapHapTheme.colors.gray50,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = if (isActive) certificationStyle.activeButtonColor else HapHapTheme.colors.gray400,
                    shape = RoundedCornerShape(6.dp),
                )
                .noRippleClickable(onClick = onButtonClick, isEnabled = isActive)
                .padding(7.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPagePassCertificationCardPreview() {
    HapHapTheme {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            listOf(0, 2).forEach { count ->
                Row(
                    modifier = Modifier
                        .height(160.dp)
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    MyPagePassCertificationCard(
                        type = PassCertificationType.REJECTED,
                        count = count,
                        onButtonClick = {},
                        modifier = Modifier.weight(1f)
                    )

                    MyPagePassCertificationCard(
                        type = PassCertificationType.APPROVED,
                        count = count,
                        onButtonClick = {},
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}
