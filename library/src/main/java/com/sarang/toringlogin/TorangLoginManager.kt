package com.sarang.toringlogin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.example.torangrepository.*
import com.sryang.torang_core.data.entity.User
import com.sryang.torang_core.login.*
import com.sryang.torang_core.util.Logger
import com.sryang.torang_repository.data.AppDatabase
import com.sryang.torang_repository.repository.preference.TorangPreference
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @param facebookLoginProvider 페이스북 로그인 프로바이더
 */
@Singleton
class TorangLoginManager
@Inject constructor(
    @ApplicationContext val context: Context, private val externalScope: CoroutineScope
) : TorangPreference(), LoginManager {
    @Inject
    lateinit var facebookLoginProvider: FacebookLoginProvider

    val database = AppDatabase.getInstance(context)

    var API_URL = "https://www.vrscoo.com:8080/"

    private val loggedInUserDao = database.LoggedInUserDao()

    private val isLogin: LiveData<Boolean> =
        loggedInUserDao.getLoggedInUserEntity().switchMap {
            if (it != null) {
                Logger.v("login check $it")
                MutableLiveData(it.userId != 0)
            } else {
                MutableLiveData(false)
            }
        }

    override fun loadUser(contxt: Context): User {
        return User.createEmptyValue()
    }

    override fun requestFacebookLogin(
        activity: AppCompatActivity,
        onLoginResultListener: OnLoginResultListener
    ) {
        //페이스북 로그인 요청
        facebookLoginProvider.login(activity, object : OnResultLoginListener {
            override fun onResult(result: Int, user: User) {
                user.accessToken?.let {
                    onLoginResultListener.onResult(result, it)
                    return;
                }
                onLoginResultListener.onResult(result, "error")


                //결과를 받으면 API 호출하여 사용자 등록
                //externalScope.launch {
                    /*val user = getService().facebook_login(user.access_token!!).data

                    if (user == null) {
                        onLoginResultListener.onResult(result, "user dats is null")
                        return@launch
                    }

                    LoggedInUserData.parse(user)?.let {
                        loggedInUserDao.insert(it)
                    }*/
                //}
            }
        })
    }

    override fun requestKakaoLogin(onResultLoginListener: OnResultLoginListener) {
        //KakaoLoginProvider.getInstance(context).requestLogin(onResultLoginListener);
    }

    override fun onActivityResult(
        appCompatActivity: AppCompatActivity, requestCode: Int, resultCode: Int, data: Intent
    ) {
        Logger.v("")
        facebookLoginProvider.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCreate(appCompatActivity: AppCompatActivity) {
        facebookLoginProvider.onCreate()
    }

    override fun onDestroy(appCompatActivity: AppCompatActivity) {
        facebookLoginProvider.onDestory()
//                KakaoLoginProvider.getInstance(context).onDestroy();
    }

    override fun logout(onResultLogoutListener: OnResultLogoutListener) {
//        if (facebookLoginProvider.isLoggedIn()) {
//            facebookLoginProvider.logout(onResultLogoutListener)
//        }
        //        } else if (KakaoLoginProvider.getInstance(context).isLoggedIn()) {
        //            KakaoLoginProvider.getInstance(context).logout(onResultLogoutListener);
        //        } else {
        //            onResultLogoutListener.onResult(0);
        //        }

        externalScope.launch {
            loggedInUserDao.clear()
        }
    }

    override fun isLogin(context: Context): Boolean {
        val userBody = loadUser(context)
        return userBody != null && userBody.userId != -1
    }

    override fun isLogin(): LiveData<Boolean> {
        return isLogin
    }

    override fun logout(context: Context) {

    }

    private fun getService(): LoginService {
        val httpClient = OkHttpClient.Builder()
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.HEADERS
        logger.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logger)
        httpClient.writeTimeout(10, TimeUnit.SECONDS)
        httpClient.connectTimeout(10, TimeUnit.SECONDS)
        httpClient.writeTimeout(10, TimeUnit.SECONDS)
        httpClient.readTimeout(10, TimeUnit.SECONDS)

        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("User-Agent", "android")
                .header(
                    "accessToken",
                    if (context == null) "" else TorangPreference().getAccessToken(context!!)!!
                )
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        }

        val client = httpClient.build()


        //레트로핏 초기화 BASE URL 설정하는 곳
        val retrofit: Retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //통신인터페이스 기반 서비스 생성
        return retrofit.create(LoginService::class.java)
    }
}