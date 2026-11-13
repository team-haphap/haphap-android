package com.haphap.app.core.extensions

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import timber.log.Timber

private const val InquiryEmailAddress = "haphapkr@gmail.com"

/**
 * 받는 사람이 문의 메일 주소로 채워진 상태로 메일 앱을 실행한다.
 */

fun Context.sendEmail() {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = "mailto:".toUri()
        putExtra(Intent.EXTRA_EMAIL, arrayOf(InquiryEmailAddress))
    }

    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Timber.e(e, "메일 앱을 찾을 수 없습니다.")
    }
}
