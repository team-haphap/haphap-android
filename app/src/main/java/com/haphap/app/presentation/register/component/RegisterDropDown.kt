package com.haphap.app.presentation.register.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable
import com.haphap.app.data.model.register.RegisterDropDownItemModel
import com.haphap.app.presentation.register.type.RegisterDropDownItemType
import com.haphap.app.presentation.register.type.toStyle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

private const val MAX_VISIBLE_ITEMS = 4

@Composable
fun RegisterDropDown(
    items: ImmutableList<RegisterDropDownItemModel>,
    selectedItem: RegisterDropDownItemModel?,
    onItemSelected: (RegisterDropDownItemModel) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
) {
    var isExpanded by remember { mutableStateOf(false) }

    val triggerBackgroundColor =
        if (selectedItem != null) HapHapTheme.colors.sub100 else HapHapTheme.colors.gray100
    val triggerTextColor =
        if (selectedItem != null) HapHapTheme.colors.primary500 else HapHapTheme.colors.gray600

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = triggerBackgroundColor,
                shape = RoundedCornerShape(8.dp),
            )
            .noRippleClickable(onClick = { isExpanded = !isExpanded })
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = selectedItem?.text ?: placeholder,
            style = HapHapTheme.typography.body.sb14,
            color = triggerTextColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = ImageVector.vectorResource(
                id = if (isExpanded) R.drawable.ic_register_up_30 else R.drawable.ic_register_down_30
            ),
            contentDescription = null,
            tint = HapHapTheme.colors.gray600,
        )
    }

    if (isExpanded) {
        Spacer(modifier = Modifier.height(12.dp))

        val listHeight = RegisterDropDownItemHeight * items.size.coerceAtMost(MAX_VISIBLE_ITEMS) + 12.dp

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(listHeight)
                .background(
                    color = HapHapTheme.colors.gray50,
                    shape = RoundedCornerShape(10.dp),
                )
                .padding(horizontal = 11.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(
                items = items,
                key = { it.id },
            ) { item ->
                RegisterDropDownItem(
                    text = item.text,
                    isSelected = item.id == selectedItem?.id,
                    onClick = {
                        onItemSelected(item)
                        isExpanded = false
                    },
                )
            }
        }
    }
}

val RegisterDropDownItemHeight = 50.dp

@Composable
fun RegisterDropDownItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val style = (if (isSelected) RegisterDropDownItemType.SELECTED else RegisterDropDownItemType.UNSELECTED)
        .toStyle(HapHapTheme.colors)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = style.backgroundColor,
                shape = RoundedCornerShape(8.dp),
            )
            .noRippleClickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 15.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            text = text,
            style = HapHapTheme.typography.body.sb14,
            color = style.textColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun RegisterDropDownPreview() {
    HapHapTheme {
        var selected by remember { mutableStateOf<RegisterDropDownItemModel?>(null) }

        Column(modifier = Modifier.padding(20.dp)) {
            RegisterDropDown(
                items = persistentListOf(
                    RegisterDropDownItemModel(id = 1, text = "카카오 2026 신입 개발자 공개 채용"),
                    RegisterDropDownItemModel(id = 2, text = "네이버 2026 신입 개발자 공개 채용"),
                    RegisterDropDownItemModel(
                        id = 3,
                        text = "라인 2026 신입 ~~~~~~~~~~~~~~~~~~~~~~~~~~개발자 공개 채용"
                    ),
                    RegisterDropDownItemModel(id = 4, text = "토스 2026 신입 개발자 공개 채용"),
                    RegisterDropDownItemModel(id = 5, text = "당근 2026 신입 개발자 공개 채용"),
                ),
                selectedItem = selected,
                onItemSelected = { selected = it },
                placeholder = "원하는 공고를 선택해주세요",
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "전형", style = HapHapTheme.typography.body.b18)
        }
    }
}