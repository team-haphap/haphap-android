package com.haphap.app.presentation.main.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
    @StringRes val titleRes: Int,
    val route: MainTabRoute,
) {
    HOME(
        iconRes = R.drawable.ic_bottom_bar_home_24,
        titleRes = R.string.nav_home,
        route = Home,
    ),

    JOB(
        iconRes = R.drawable.ic_bottom_bar_list_24,
        titleRes = R.string.nav_list,
        route = Job,
    ),
    REGISTER(
        iconRes = R.drawable.ic_bottom_bar_register_24,
        titleRes = R.string.nav_register,
        route = Register,
    ),
    CALENDAR(
        iconRes = R.drawable.ic_bottom_bar_calendar_24,
        titleRes = R.string.nav_calendar,
        route = Calendar,
    ),
    MYPAGE(
        iconRes = R.drawable.ic_bottom_bar_my_24,
        titleRes = R.string.nav_mypage,
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
