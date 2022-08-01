package com.sarang.toringlogin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.example.torang_core.data.model.User
import com.example.torang_core.login.OnLoginResultListener
import com.example.torang_core.login.OnResultLoginListener
import com.example.torang_core.login.OnResultLogoutListener

interface LoginManager {
    /** 페이스북 로그인 요청 */
    fun requestFacebookLogin(activity: AppCompatActivity, onLoginResultListener: OnLoginResultListener)
    /** 카카오톡 로그인 요청 */
    fun requestKakaoLogin(onResultLoginListener: OnResultLoginListener)
    /** 로그인 상태 */
    fun isLogin(context: Context): Boolean
    fun isLogin() : LiveData<Boolean>
    /** 로그아웃 상태 */
    fun logout(onResultLogoutListener: OnResultLogoutListener)
    fun logout(context: Context)
    /** 사용자 정보 요청 */
    fun loadUser(context: Context): User

    /** lifecycle start */
    fun onCreate(appCompatActivity: AppCompatActivity)
    fun onDestroy(appCompatActivity: AppCompatActivity)
    fun onActivityResult(appCompatActivity: AppCompatActivity, requestCode: Int, resultCode: Int, data: Intent)
    /** lifecycle end */
}