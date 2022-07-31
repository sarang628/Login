//package com.sarang.toringlogin
//
//import android.app.Activity
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import com.example.torang_core.data.model.User
//import com.example.torang_core.login.LoginProvider
//import com.example.torang_core.login.OnReceiveUserListener
//import com.example.torang_core.login.OnResultLoginListener
//import com.example.torang_core.login.OnResultLogoutListener
//import com.kakao.auth.*
//import com.kakao.usermgmt.UserManagement
//import com.kakao.usermgmt.callback.LogoutResponseCallback
//import com.kakao.util.exception.KakaoException
//
//class KakaoLoginProvider(var activity: AppCompatActivity?) : LoginProvider {
//    var sessionCallback: SessionCallback
//    private var onResultLoginListener: OnResultLoginListener? = null
//    override fun login(
//        activity: Activity,
//        onResultLoginListener: OnResultLoginListener
//    ) {
//        this.onResultLoginListener = onResultLoginListener
//        Session.getCurrentSession().open(AuthType.KAKAO_TALK, activity)
//    }
//
//    override fun requestUser(onReceiveUserListener: OnReceiveUserListener) {}
//
//    /**
//     * Session.getCurrentSession().isOpen() 여부로 로그인이 되었는지 확인할 수 있습니다. 물론 100%확실한 상태는 아닙니다.
//     * 왜냐하면 open의 상태는 false이지만 refreshToken으로 다시 자동 세션 연결이 될 수도 있기 때문입니다.
//     *
//     *
//     * 때문에 오픈여부를 알기보다는 세션이 닫혀있는 상황인지를 판별하는것이 좋을듯 합니다.
//     * Session.getCurrentSession().isClosed()로 확인하시길 바랍니다.
//     *
//     * @return
//     */
//    override fun isLoggedIn(): Boolean {
//        return !Session.getCurrentSession().isClosed
//    }
//
//    override fun logout(onResultLogoutListener: OnResultLogoutListener) {
//        UserManagement.getInstance().requestLogout(object : LogoutResponseCallback() {
//            override fun onCompleteLogout() {
//                onResultLogoutListener?.onResult(0)
//            }
//        })
//    }
//
//    inner class SessionCallback : ISessionCallback {
//        override fun onSessionOpened() {
//            //Logger.v("onSessionOpened");
//            if (onResultLoginListener != null) {
//                val accessToken = Session.getCurrentSession().tokenInfo.accessToken
//                //                ApiManager.getInstance().requestKakaoLogin(accessToken, new ApiManager.CallbackListener() {
////                    public void callback(String result) {
////                        Logger.v(result);
////                        User user = new Gson().fromJson(result, User.class);
////                        onResultLoginListener.onResult(0, user);
////                    }
////
////                    public void failed(String msg) {
////
////                    }
////                });
//            }
//        }
//
//        override fun onSessionOpenFailed(exception: KakaoException) {
//            //Logger.e("onSessionOpenFailed");
//            if (onResultLoginListener != null) onResultLoginListener!!.onResult(1, User())
//        }
//    }
//
//    private inner class KakaoSDKAdapter : KakaoAdapter() {
//        override fun getSessionConfig(): ISessionConfig {
//            return object : ISessionConfig {
//                override fun getAuthTypes(): Array<AuthType> {
//                    return arrayOf(AuthType.KAKAO_LOGIN_ALL)
//                }
//
//                override fun isUsingWebviewTimer(): Boolean {
//                    return false
//                }
//
//                override fun isSecureMode(): Boolean {
//                    return false
//                }
//
//                override fun getApprovalType(): ApprovalType {
//                    return ApprovalType.INDIVIDUAL
//                }
//
//                override fun isSaveFormData(): Boolean {
//                    return true
//                }
//            }
//        }
//
//        override fun getApplicationConfig(): IApplicationConfig {
//            return IApplicationConfig { activity!!.applicationContext }
//        }
//    }
//
//    override fun onCreate() {
//        if (KakaoSDK.getAdapter() == null) KakaoSDK.init(KakaoSDKAdapter())
//        Session.getCurrentSession().addCallback(sessionCallback)
//        Session.getCurrentSession().checkAndImplicitOpen()
//    }
//
//    override fun onDestory() {
//        Session.getCurrentSession().removeCallback(sessionCallback)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
//        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
//            return
//        }
//    }
//
//    companion object {
//        private var kakaoLoginProvider: KakaoLoginProvider? = null
//        fun getInstance(activity: AppCompatActivity?): KakaoLoginProvider? {
//            if (kakaoLoginProvider == null) kakaoLoginProvider = KakaoLoginProvider(activity)
//            if (activity != null && kakaoLoginProvider!!.activity != activity) kakaoLoginProvider!!.activity =
//                activity
//            return kakaoLoginProvider
//        }
//    }
//
//    init {
//        sessionCallback = SessionCallback()
//    }
//}