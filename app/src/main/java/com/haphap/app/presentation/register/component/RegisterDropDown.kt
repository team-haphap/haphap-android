package com.haphap.app.presentation.register.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable

private const val MAX_VISIBLE_ITEMS = 4

@Composable
fun RegisterDropDown(
    items: List<String>,
    selectedItem: String?,
    onItemSelected: (String) -> Unit,
    placeholder: String,
    isEditable: Boolean,
    modifier: Modifier = Modifier,
) {
    var isExpanded by remember { mutableStateOf(false) }

    val triggerBackgroundColor =
        if (selectedItem != null) HapHapTheme.colors.sub100 else HapHapTheme.colors.gray100
    val triggerTextColor =
        if (selectedItem != null) HapHapTheme.colors.primary500 else HapHapTheme.colors.gray600

    val isEditablePadding =
        if (isEditable)  10.dp else 15.dp

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = triggerBackgroundColor,
                shape = RoundedCornerShape(8.dp),
            )
            .noRippleClickable(onClick = { isExpanded = !isExpanded }, isEnabled = isEditable)
            .padding(vertical = isEditablePadding, horizontal = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = selectedItem ?: placeholder,
            style = HapHapTheme.typography.body.sb14,
            color = triggerTextColor,
        )
        if (isEditable) {
            Icon(
                imageVector = ImageVector.vectorResource(
                    id = if (isExpanded) R.drawable.ic_register_up_30 else R.drawable.ic_register_down_30
                ),
                contentDescription = null,
                tint = Color.Unspecified,
            )
        }
    }

    if (isExpanded) {
        Spacer(modifier = Modifier.height(12.dp))

        val listHeight = RegisterDropDownItemHeight * items.size.coerceAtMost(MAX_VISIBLE_ITEMS)

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(listHeight)
                .padding(horizontal = 11.dp)
                .background(
                    color = HapHapTheme.colors.white,
                    shape = RoundedCornerShape(8.dp),
                ),
        ) {
            items(items) { item ->
                RegisterDropDownItem(
                    text = item,
                    isSelected = item == selectedItem,
                    onClick = {
                        onItemSelected(item)
                        isExpanded = false
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun RegisterDropDownEditablePreview() {
    HapHapTheme {
        var selected by remember { mutableStateOf<String?>(null) }

        Column(modifier = Modifier.padding(20.dp)) {
            RegisterDropDown(
                items = listOf(
                    "카카오 2026 신입 개발자 공개 채용",
                    "네이버 2026 신입 개발자 공개 채용",
                    "라인 2026 신입 개발자 공개 채용",
                    "토스 2026 신입 개발자 공개 채용",
                    "당근 2026 신입 개발자 공개 채용",
                ),
                selectedItem = selected,
                onItemSelected = { selected = it },
                placeholder = "원하는 공고를 선택해주세요",
                isEditable = true,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "전형", style = HapHapTheme.typography.body.b18)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun RegisterDropDownNotEditablePreview() {
    HapHapTheme {
        Column(modifier = Modifier.padding(20.dp)) {
            RegisterDropDown(
                items = listOf("카카오 2026 신입 개발자 공개 채용"),
                selectedItem = "카카오 2026 신입 개발자 공개 채용",
                onItemSelected = {},
                placeholder = "원하는 공고를 선택해주세요",
                isEditable = false,
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "전형", style = HapHapTheme.typography.body.b18)
        }
    }
}