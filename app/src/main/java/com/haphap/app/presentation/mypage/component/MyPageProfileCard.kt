package com.haphap.app.presentation.mypage.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun MyPageProfileCard(
    nickNameText: String,
    emailText: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.padding(horizontal = 36.dp),
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_mypage),
            contentDescription = null,
            modifier = Modifier.aspectRatio(288f / 418f),
        )

        Column(
            modifier = Modifier
                .matchParentSize()
                .padding(horizontal = 20.dp, vertical = 33.dp),
            verticalArrangement = Arrangement.Bottom,
        ) {
            MyPageInfoField(
                labelText = "닉네임",
                icon = R.drawable.ic_mypage_profile_24,
                contentText = nickNameText,
            )

            Spacer(modifier = Modifier.height(12.dp))

            MyPageInfoField(
                labelText = "이메일",
                icon = R.drawable.ic_mypage_mail_24,
                contentText = emailText,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPageProfileCardPreview() {
    HapHapTheme {
        MyPageProfileCard(
            nickNameText = "익명의죠르디",
            emailText = "yeonsoo1234@naver.com",
        )
    }
}
