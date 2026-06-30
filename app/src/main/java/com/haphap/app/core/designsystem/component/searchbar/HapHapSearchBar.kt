package com.haphap.app.core.designsystem.component.searchbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
import com.haphap.app.R.drawable.ic_search_32
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable

/**
 * 검색바 공통 컴포넌트입니다.
 * 검색 기능 없이 화면 이동을 위한 버튼 역할을 합니다.
 *
 * @param placeholder 기본으로 보여줄 문구
 * @param onSearchBarClick 검색바 클릭 시 동작
 *
 */

@Composable
fun HapHapSearchBar(
    placeholder: String,
    onSearchBarClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    Row(
        modifier = modifier
            .background(
                color = HapHapTheme.colors.gray100,
                shape = RoundedCornerShape(8.dp),
            )
            .noRippleClickable(onClick = onSearchBarClick)
            .padding(vertical = 5.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ){
        Text(
            text = placeholder,
            modifier = Modifier.weight(1f),
            color = HapHapTheme.colors.gray400,
            style = HapHapTheme.typography.caption.m12,
        )

        Icon(
            imageVector = ImageVector.vectorResource(ic_search_32),
            contentDescription = null,
            tint = HapHapTheme.colors.gray500,
        )
    }
}

@Preview
@Composable
private fun HapHapSearchBarPreview() {
    HapHapTheme{
        HapHapSearchBar(
            placeholder = "공고명을 검색해보세요!",
            onSearchBarClick = {}
        )
    }
}
