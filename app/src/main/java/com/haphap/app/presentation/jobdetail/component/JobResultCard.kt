package com.haphap.app.presentation.jobdetail.component

import com.haphap.app.presentation.jobdetail.type.JobResultType

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun JobResultCard(
    type: JobResultType,
    count: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(shape = RoundedCornerShape(8.dp)),
    ) {
        Image(
            painter = painterResource(id = type.imageRes),
            contentDescription = type.label,
            modifier = Modifier.fillMaxWidth(),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = HapHapTheme.colors.white)
                .border(
                    width = 1.dp,
                    color = HapHapTheme.colors.gray100,
                )
                .padding(top = 4.dp, bottom = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = count.toString(),
                style = HapHapTheme.typography.body.b18,
                color = HapHapTheme.colors.gray700,
            )
            Text(
                text = type.label,
                style = HapHapTheme.typography.caption.b12,
                color = HapHapTheme.colors.gray400,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JobResultCardPreview() {
    HapHapTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp),
        ) {
            JobResultCard(
                type = JobResultType.PASS,
                count = 12,
                modifier = Modifier.width(100.dp),
            )
            JobResultCard(
                type = JobResultType.FAIL,
                count = 12,
                modifier = Modifier.width(100.dp),
            )
            JobResultCard(
                type = JobResultType.PENDING,
                count = 12,
                modifier = Modifier.width(100.dp),
            )
        }
    }
}