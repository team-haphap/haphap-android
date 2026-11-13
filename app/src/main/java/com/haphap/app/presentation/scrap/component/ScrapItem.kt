package com.haphap.app.presentation.scrap.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.component.chip.HapHapStatusChip
import com.haphap.app.core.designsystem.component.image.UrlImage
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.StatusChipType
import com.haphap.app.core.extensions.noRippleClickable

@Composable
fun ScrapItem(
    imageUrl: String,
    companyName: String,
    category: String,
    title: String,
    isScrapped: Boolean,
    onItemClick: () -> Unit,
    onScrapClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = HapHapTheme.colors.gray50)
            .noRippleClickable(onClick = onItemClick)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        UrlImage(
            url = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(54.dp)
                .clip(shape = RoundedCornerShape(6.dp))
                .background(color = HapHapTheme.colors.white)
                .border(
                    width = 1.dp,
                    color = HapHapTheme.colors.gray100,
                    shape = RoundedCornerShape(6.dp),
                ),
        )

        Spacer(modifier = Modifier.width(6.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Text(
                    text = companyName,
                    style = HapHapTheme.typography.caption.m12,
                    color = HapHapTheme.colors.gray700,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill = false),
                )

                HapHapStatusChip(
                    text = category,
                    type = StatusChipType.CATEGORY,
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = title,
                style = HapHapTheme.typography.body.sb14,
                color = HapHapTheme.colors.gray700,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_scrap_24),
            contentDescription = null,
            tint = if (isScrapped) HapHapTheme.colors.subred else HapHapTheme.colors.gray200,
            modifier = Modifier.noRippleClickable(onClick = onScrapClick),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ScrapItemPreview() {
    HapHapTheme {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            ScrapItem(
                imageUrl = "",
                companyName = "카카오",
                category = "개발/데이터",
                title = "2026 신입 공개채용",
                isScrapped = true,
                onItemClick = {},
                onScrapClick = {},
            )

            ScrapItem(
                imageUrl = "",
                companyName = "카카오",
                category = "개발/데이터",
                title = "2026 신입 공개채용",
                isScrapped = false,
                onItemClick = {},
                onScrapClick = {},
            )
        }
    }
}
