package com.haphap.app.presentation.mypage.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun MyPageInfoField(
    labelText: String,
    @DrawableRes icon: Int,
    contentText: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = labelText,
            color = HapHapTheme.colors.gray600,
            style = HapHapTheme.typography.body.sb14,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = HapHapTheme.colors.white, shape = RoundedCornerShape(4.dp))
                .padding(horizontal = 8.dp, vertical = 3.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = HapHapTheme.colors.sub200,
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = contentText,
                color = HapHapTheme.colors.gray600,
                style = HapHapTheme.typography.body.sb16,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
private fun MyPageInfoFieldPreview() {
    HapHapTheme {
        MyPageInfoField(
            labelText = "닉네임",
            icon = R.drawable.ic_mypage_profile_24,
            contentText = "익명의 죠르디",
        )
    }
}
