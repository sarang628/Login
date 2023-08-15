package com.sarang.toringlogin

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @param facebookLoginProvider 페이스북 로그인 프로바이더
 */
@Singleton
class TorangLoginManager
@Inject constructor(
    @ApplicationContext val context: Context, private val externalScope: CoroutineScope
) {
    var API_URL = "https://www.vrscoo.com:8080/"
}