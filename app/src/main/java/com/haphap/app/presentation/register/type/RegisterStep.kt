package com.haphap.app.presentation.register.type

import com.haphap.app.presentation.register.navigation.Register

enum class RegisterStep(
    val btnText: String,
    val isTopBarVariable: Boolean = true,
    val isProgressBarVisible: Boolean = true,
) {
    SELECT_PROCESS(btnText = "다음"),
    RESULT(btnText = "다음"),
    CONTACT(btnText = "다음"),
    PASS_PROOF(btnText = "다음", isProgressBarVisible = false),
    CONFIRM(btnText = "등록하기", isProgressBarVisible = false),
    COMPLETE(btnText = "완료", isTopBarVariable = false, isProgressBarVisible = false);

    fun isSkip(result: PassResultStatusButton?): Boolean = when (this) {
        CONTACT -> result == PassResultStatusButton.DONT_KNOW
        PASS_PROOF -> result != PassResultStatusButton.PASS
        SELECT_PROCESS, RESULT, CONFIRM, COMPLETE -> false
    }

    fun next(result: PassResultStatusButton?): RegisterStep? =
        entries.drop(ordinal + 1).firstOrNull { !it.isSkip(result) }

    fun previous(result: PassResultStatusButton?): RegisterStep? =
        entries.take(ordinal).lastOrNull { !it.isSkip(result) }

    val progress: Int
        get() = entries.take(ordinal + 1).count { it.isProgressBarVisible }

    companion object {
        val progressStepCount: Int = entries.count { it.isProgressBarVisible }
    }
}
