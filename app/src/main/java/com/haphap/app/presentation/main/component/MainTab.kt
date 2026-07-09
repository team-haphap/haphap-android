package com.haphap.app.presentation.main.component

import androidx.annotation.DrawableRes
import com.haphap.app.R
import com.haphap.app.core.navigation.MainTabRoute
import com.haphap.app.core.navigation.Route
import com.haphap.app.presentation.calendar.navigation.Calendar
import com.haphap.app.presentation.home.navigation.Home
import com.haphap.app.presentation.job.navigation.Job
import com.haphap.app.presentation.mypage.navigation.MyPage
import com.haphap.app.presentation.register.navigation.Register

enum class MainTab(
    @DrawableRes val iconRes: Int,
    val titleRes: String,
    val route: MainTabRoute,
) {
    HOME(
        iconRes = R.drawable.ic_bottom_bar_home_24,
        titleRes = "홈",
        route = Home,
    ),

    JOB(
        iconRes = R.drawable.ic_bottom_bar_list_24,
        titleRes = "리스트",
        route = Job,
    ),
    REGISTER(
        iconRes = R.drawable.ic_bottom_bar_register_24,
        titleRes = "등록",
        route = Register(),
    ),
    CALENDAR(
        iconRes = R.drawable.ic_bottom_bar_calendar_24,
        titleRes = "캘린더",
        route = Calendar,
    ),
    MYPAGE(
        iconRes = R.drawable.ic_bottom_bar_my_24,
        titleRes = "마이",
        route = MyPage,
    );

    companion object {
        fun find(predicate: (MainTabRoute) -> Boolean): MainTab? {
            return entries.find { predicate(it.route) }
        }

        fun contains(predicate: (Route) -> Boolean): Boolean {
            return entries.any { predicate(it.route) }
        }
    }
}
