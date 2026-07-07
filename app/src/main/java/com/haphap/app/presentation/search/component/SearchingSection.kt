package com.haphap.app.presentation.search.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.component.image.UrlImage
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable
import com.haphap.app.data.model.search.RelatedKeywordListModel
import com.haphap.app.data.model.search.SearchAutoCompleteModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun SearchingSection(
    searchAutoCompleteList: ImmutableList<SearchAutoCompleteModel>,
    relatedKeywordList: ImmutableList<RelatedKeywordListModel>,
    onAutoCompleteItemClick: () -> Unit,
    onRelatedItemClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(top = 12.dp)
            .padding(horizontal = 20.dp),
    ) {
        searchAutoCompleteList.forEach {
            SearchResultItem(
                imageUrl = it.imageUrl,
                text = it.text,
                highlightLength = it.length,
                onClick = onAutoCompleteItemClick,
            )

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = HapHapTheme.colors.gray100,
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "관련 검색어",
            color = HapHapTheme.colors.gray400,
            style = HapHapTheme.typography.caption.m12,
        )

        Spacer(modifier = Modifier.height(8.dp))

        relatedKeywordList.forEach {
            RelatedKeywordItem(
                text = it.text,
                highlightLength = it.length,
                onClick = onRelatedItemClick,
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }

}

@Composable
private fun SearchResultItem(
    imageUrl: String,
    text: String,
    highlightLength: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        UrlImage(
            modifier = Modifier
                .size(30.dp)
                .clip(shape = RoundedCornerShape(4.dp))
                .border(
                    width = 1.dp,
                    color = HapHapTheme.colors.gray100,
                ),
            url = imageUrl,
            placeholderDrawable = R.drawable.ic_launcher_background,
            contentScale = ContentScale.Crop,
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = HapHapTheme.colors.primary500)) {
                    append(text.take(highlightLength))
                }
                withStyle(SpanStyle(color = HapHapTheme.colors.gray700)) {
                    append(text.drop(highlightLength))
                }
            },
            style = HapHapTheme.typography.body.sb14,
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "바로가기",
            color = HapHapTheme.colors.gray600,
            style = HapHapTheme.typography.caption.r12,
        )

        Spacer(modifier = Modifier.width(2.dp))

        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_right_20),
            contentDescription = null,
            tint = HapHapTheme.colors.gray500,
            modifier = Modifier
                .size(20.dp)
                .noRippleClickable(onClick = onClick),
        )
    }

}

@Composable
private fun RelatedKeywordItem(
    text: String,
    highlightLength: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = HapHapTheme.colors.primary500)) {
                    append(text.take(highlightLength))
                }
                withStyle(SpanStyle(color = HapHapTheme.colors.gray600)) {
                    append(text.drop(highlightLength))
                }
            },
            style = HapHapTheme.typography.body.sb14,
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_right_20),
            contentDescription = null,
            tint = HapHapTheme.colors.gray500,
            modifier = Modifier.noRippleClickable(onClick = onClick),
        )
    }
}

@Preview (showBackground = true)
@Composable
private fun SearchingSectionPreview() {
    HapHapTheme{
        SearchingSection(
            searchAutoCompleteList = persistentListOf(
                SearchAutoCompleteModel(
                    id = 1,
                    imageUrl = "",
                    text = "카카오 기획 공개 채용",
                    length = 3,
                ),
                SearchAutoCompleteModel(
                    id = 2,
                    imageUrl = "",
                    text = "카카오 기획 공개 채용",
                    length = 3,
                ),
            ),
            relatedKeywordList = persistentListOf(
                RelatedKeywordListModel(
                    id = 1,
                    text = "카카오",
                    length = 3,
                ),
                RelatedKeywordListModel(
                    id = 1,
                    text = "카카오 스타일",
                    length = 3,
                ),
                RelatedKeywordListModel(
                    id = 1,
                    text = "카카오 뱅크",
                    length = 3,
                ),
                RelatedKeywordListModel(
                    id = 1,
                    text = "카카오 맵",
                    length = 3,
                ),
            ),
            onAutoCompleteItemClick = {},
            onRelatedItemClick = {},
        )
    }
}
