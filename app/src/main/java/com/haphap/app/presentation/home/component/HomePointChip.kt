package com.haphap.app.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable

@Composable
fun HomePointChip(
    point: Int,
    onPointClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(shape = CircleShape)
            .background(color = HapHapTheme.colors.gray100)
            .noRippleClickable(onClick = onPointClick)
            .padding(horizontal = 6.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_home_point_14),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(14.dp),
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = "%,dP".format(point),
            style = HapHapTheme.typography.body.m14,
            color = HapHapTheme.colors.gray400,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePointChipPreview() {
    HapHapTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            HomePointChip(onPointClick = {}, point = 100)
            HomePointChip(onPointClick = {}, point = 300)
            HomePointChip(onPointClick = {}, point = 12345)
        }
    }
}