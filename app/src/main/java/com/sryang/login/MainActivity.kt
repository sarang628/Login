package com.sryang.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.torang_core.login.FacebookLoginProvider
import com.example.torang_core.login.OnLoginResultListener
import com.example.torang_core.login.OnResultLogoutListener
import com.sarang.toringlogin.login.LoginActivity
import com.sryang.login.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var facebookLoginProvider: FacebookLoginProvider

    /*@Inject
    lateinit var faceBookLoginProviderForView: FaceBookLoginProviderForView

    @Inject
    lateinit var facebookLoginProviderForRepository: FaceBookLoginProviderForRepository*/

    @Inject
    lateinit var loginManager: LoginManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lifecycleOwner = this
        binding.loginManager = loginManager

        findViewById<Button>(R.id.btn_login).setOnClickListener {
            loginManager.requestFacebookLogin(
                this,
                object : OnLoginResultListener {
                    override fun onResult(result: Int, resultMsg: String) {
                        findViewById<TextView>(R.id.tv_login).text = resultMsg
                    }
                })
        }

        findViewById<Button>(R.id.btn_isLogin).setOnClickListener {
            //findViewById<TextView>(R.id.tv_isLogin).text = facebookLoginProviderForRepository.isLoggedIn().toString()
        }

        findViewById<Button>(R.id.btn_logout).setOnClickListener {
            loginManager.logout(object :
                OnResultLogoutListener {
                override fun onResult(result: Int) {

                }
            })
        }

        findViewById<Button>(R.id.btn_go_login).setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("__sarang", "onActivityResult:" + data)
        data?.let {
            facebookLoginProvider.onActivityResult(requestCode, resultCode, it)
        }
    }
}