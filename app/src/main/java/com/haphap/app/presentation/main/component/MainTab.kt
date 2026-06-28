package com.haphap.app.presentation.main.component

import androidx.annotation.DrawableRes
import com.haphap.app.R.drawable.ic_launcher_background
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
        iconRes = ic_launcher_background,
        titleRes = "home",
        route = Home,
    ),

    JOB(
        iconRes = ic_launcher_background,
        titleRes = "job",
        route = Job,
    ),
    REGISTER(
        iconRes = ic_launcher_background,
        titleRes = "register",
        route = Register,
    ),
    CALENDAR(
        iconRes = ic_launcher_background,
        titleRes = "calender",
        route = Calendar,
    ),
    MYPAGE(
        iconRes = ic_launcher_background,
        titleRes = "mypage",
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
