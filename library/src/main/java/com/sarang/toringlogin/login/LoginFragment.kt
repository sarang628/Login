package com.sarang.toringlogin.login

import android.content.Intent
import android.os.Bundle
import android.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.torang_core.login.OnLoginResultListener
import com.sarang.toringlogin.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    /** 로그인 뷰모델 */
    private val loginViewModel: LoginViewModel by viewModels()

    /** 로그인 메니저 */
    @Inject
    lateinit var torangLoginManager: LoginManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = ChangeBounds()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        /** 데이터 바인딩 */
        val binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        binding.vm = loginViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initEvent(binding)
        subScribeUI()

        return binding.root
    }

    private fun initEvent(binding: FragmentLoginBinding) {
        binding.relativeLayout.setOnClickListener {
            // 로그인 요청
            requestFacebookLogin()
        }
    }

    private fun requestFacebookLogin() {
        torangLoginManager.requestFacebookLogin(requireActivity() as AppCompatActivity,
            object : OnLoginResultListener {
                override fun onResult(result: Int, token: String) {
                    //로그인 결과 뷰모델이 전달하여 로그인 요청
                    loginViewModel.login(token)
                }
            })
    }

    private fun subScribeUI() {
        loginViewModel.isLoging.observe(viewLifecycleOwner) {
            it?.also {
                if (it)
                    activity?.finish()
            }
        }
        loginViewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            AlertDialog.Builder(requireContext())
                .setMessage(it)
                .show()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        torangLoginManager.onActivityResult(
            requireActivity() as AppCompatActivity,
            requestCode,
            resultCode,
            data!!
        )
        super.onActivityResult(requestCode, resultCode, data)
    }
}