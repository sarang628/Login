package com.sarang.toringlogin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.torang_core.data.model.User
import com.example.torang_core.login.*
import com.example.torang_core.util.Logger
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.json.JSONObject
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FacebookLoginProviderImpl @Inject constructor() :
    FacebookLoginProvider {
    private var callbackManager: CallbackManager? = null
    private var onResultLoginListener: OnResultLoginListener? = null


    /**
     * 페이스북 가이드에는 onCreate에 구현하라고 한 기능을 클래스로 따로 만들어서
     * 해당 클래스의 onCreate()를 호출해주는 방식으로 구현
     */
    override fun onCreate() {
        // callbackManager 초기화 가이드 따름
        callbackManager = CallbackManager.Factory.create()
        // callbackManager 등록 가이드 따름
        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    Logger.d("onSuccess")
                    if (onResultLoginListener != null) {
                        val user = User()
                        user.access_token = loginResult.accessToken.token
                        onResultLoginListener!!.onResult(0, user)
                    }
                }

                override fun onCancel() {
                    Logger.e("onCancel")
                }
                override fun onError(error: FacebookException) {
                    Logger.e("onError$error")
                    LoginManager.getInstance().logOut()
                }
            })
    }

    /**
     * onActivityResult를 액티비티또는 프레그먼트에서 호출 시
     * callbackManager에 전달해서 로그인 여부 판단
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        Logger.d("onActivityResult")
        if(callbackManager == null){
            Logger.d("callbackManager is null")
        }
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * 로그인 요청
     */
    override fun login(
        activity: Activity,
        onResultLoginListener: OnResultLoginListener
    ) {
        Logger.d("requestLogin")
        this.onResultLoginListener = onResultLoginListener
        LoginManager.getInstance()
            .logInWithReadPermissions(activity, Arrays.asList("public_profile", "email"))
    }

    override fun requestUser(onReceiveUserListener: OnReceiveUserListener) {
        if (isLoggedIn()) {
            callbackManager = CallbackManager.Factory.create()
            val accessToken = AccessToken.getCurrentAccessToken()
            if (accessToken != null) {
                if (!accessToken.isExpired) {
                    val request =
                        GraphRequest.newMeRequest(accessToken) { `object`: JSONObject?, response: GraphResponse? ->
                            Logger.v(`object`.toString())
                        }
                    val parameters = Bundle()
                    //parameters.putString("fields", "userId,name,email,gender,birthday")
                    request.parameters = parameters
                    request.executeAsync()
                }
            }
        }
    }

    override fun isLoggedIn(): Boolean {
        val accessToken = AccessToken.getCurrentAccessToken()
        return accessToken != null
    }

    override fun logout(onResultLogoutListener: OnResultLogoutListener) {
        object : AccessTokenTracker() {
            override fun onCurrentAccessTokenChanged(
                oldAccessToken: AccessToken?,
                currentAccessToken: AccessToken?
            ) {
                if (currentAccessToken == null) {
                    onResultLogoutListener.onResult(0)
                }
            }
        }
        LoginManager.getInstance().logOut()
    }

    override fun onDestory() {}
}