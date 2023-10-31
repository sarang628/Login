package com.sarang.toringlogin.login.compose

import android.graphics.Paint.Join
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.theme.R
import com.sarang.toringlogin.login.viewmodels.LoginViewModel
import com.sarang.toringlogin.login.compose.email.EmailLoginScreen
import com.sarang.toringlogin.login.compose.join.JoinName

@Composable
internal fun LoginScreen(
    onClickFacebookLogin: (Int) -> Unit,
    onClickEmail: (Int) -> Unit,
    onSignUp: (() -> Unit)
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.colorSecondaryLight))
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "T O R A N G",
                color = colorResource(id = R.color.colorSecondary),
                fontSize = 45.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hit the spot",
                color = colorResource(id = R.color.colorSecondary),
                fontSize = 20.sp
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 150.dp), horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                onClickFacebookLogin.invoke(0)
            }) {
                Text(text = "LOG IN WITH FACEBOOK")
            }
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 10.dp), horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                onClickEmail.invoke(0)
            }) {
                Text(text = "LOG IN WITH EMAIL")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = "Don't have an account?")
            Spacer(modifier = Modifier.width(3.dp))
            Text(text = "Sign up.", color = Color.Blue,
                modifier = Modifier.clickable(interactionSource = remember {
                    MutableInteractionSource()
                }, indication = null) {
                    onSignUp.invoke()
                })
        }

    }
}

@Composable
fun LoginNavHost(
) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(onClickFacebookLogin = {
                Toast.makeText(context, "facebook login", Toast.LENGTH_SHORT).show()
            }, onClickEmail = {
                navController.navigate("emailLogin")
            }, onSignUp = {
                navController.navigate("signUp")
            })
        }
        composable("emailLogin") {
            EmailLoginScreen()
        }
        composable("signUp") {
            JoinName()
        }
    }
}

@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreen(onClickEmail = {}, onClickFacebookLogin = {}, onSignUp = {})
}