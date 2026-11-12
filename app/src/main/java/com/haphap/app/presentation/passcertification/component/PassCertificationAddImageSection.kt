package com.haphap.app.presentation.passcertification.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.component.image.UrlImage
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable
import com.haphap.app.presentation.passcertification.model.PassCertificationImageModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

private const val MaxImageCount = 3

@Composable
fun PassCertificationAddImageSection(
    images: ImmutableList<PassCertificationImageModel>,
    onAddImageClick: () -> Unit,
    onDeleteImageClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val validImageCount = images.count { !it.isError }

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "제출한 이미지",
                style = HapHapTheme.typography.body.sb16,
                color = HapHapTheme.colors.gray900,
            )

            Text(
                text = "$validImageCount/$MaxImageCount",
                style = HapHapTheme.typography.body.b14,
                color = HapHapTheme.colors.gray600,
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            images.forEachIndexed { index, image ->
                PassCertificationAddedImage(
                    image = image,
                    onDeleteClick = { onDeleteImageClick(index) },
                )
            }

            if (images.size < MaxImageCount) {
                PassCertificationAddImageButton(onClick = onAddImageClick)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "최대 3장, 이미지당 10MB까지 가능해요.\n반려된 이미지가 한달동안 수정되지 않으면 등록이 취소돼요.",
            style = HapHapTheme.typography.caption.m12,
            color = HapHapTheme.colors.gray300,
        )
    }
}

@Composable
private fun PassCertificationAddedImage(
    image: PassCertificationImageModel,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(100.dp)
            .clip(shape = RoundedCornerShape(6.dp))
            .background(color = HapHapTheme.colors.white),
    ) {
        UrlImage(
            url = image.uri,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )

        if (image.isError) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_alert_red_20),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(48.dp),
            )
        } else {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_delete_circle_17),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 3.dp, end = 2.dp)
                    .noRippleClickable(onClick = onDeleteClick),
            )
        }
    }
}

@Composable
private fun PassCertificationAddImageButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .size(100.dp)
            .clip(shape = RoundedCornerShape(6.dp))
            .background(color = HapHapTheme.colors.gray100)
            .dashedBorder(
                color = HapHapTheme.colors.gray200,
                cornerRadius = 6.dp,
            )
            .noRippleClickable(onClick = onClick),
        verticalArrangement = Arrangement.spacedBy(
            space = 10.dp,
            alignment = Alignment.CenterVertically,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_register_add_image_42),
            contentDescription = null,
            tint = HapHapTheme.colors.gray600,
        )

        Text(
            text = "사진 추가",
            style = HapHapTheme.typography.caption.sb12,
            color = HapHapTheme.colors.gray600,
        )
    }
}

// TODO: #141 머지 후 core/extensions의 공통 dashedBorder로 교체
private fun Modifier.dashedBorder(
    color: Color,
    cornerRadius: Dp,
    strokeWidth: Dp = 1.dp,
    dashLength: Dp = 4.dp,
    gapLength: Dp = 4.dp,
): Modifier = drawBehind {
    val strokeWidthPx = strokeWidth.toPx()
    val inset = strokeWidthPx / 2
    val radius = cornerRadius.toPx() - inset

    drawRoundRect(
        color = color,
        topLeft = Offset(inset, inset),
        size = Size(size.width - strokeWidthPx, size.height - strokeWidthPx),
        cornerRadius = CornerRadius(radius),
        style = Stroke(
            width = strokeWidthPx,
            pathEffect = PathEffect.dashPathEffect(
                intervals = floatArrayOf(dashLength.toPx(), gapLength.toPx()),
            ),
        ),
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PassCertificationAddImageSectionPreview() {
    HapHapTheme {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            PassCertificationAddImageSection(
                images = persistentListOf(),
                onAddImageClick = {},
                onDeleteImageClick = {},
            )

            PassCertificationAddImageSection(
                images = persistentListOf(
                    PassCertificationImageModel(uri = ""),
                    PassCertificationImageModel(uri = ""),
                ),
                onAddImageClick = {},
                onDeleteImageClick = {},
            )

            PassCertificationAddImageSection(
                images = persistentListOf(
                    PassCertificationImageModel(uri = "", isError = true),
                ),
                onAddImageClick = {},
                onDeleteImageClick = {},
            )
        }
    }
}
