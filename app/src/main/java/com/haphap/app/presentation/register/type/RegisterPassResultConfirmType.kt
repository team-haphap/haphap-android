package com.haphap.app.presentation.register.type

fun PassResultStatusButton.toPassResultType(): RegisterPassResultType = when (this) {
    PassResultStatusButton.PASS -> RegisterPassResultType.PASS
    PassResultStatusButton.FAILED -> RegisterPassResultType.FAILED
    PassResultStatusButton.DONT_KNOW -> RegisterPassResultType.DONT_KNOW
}