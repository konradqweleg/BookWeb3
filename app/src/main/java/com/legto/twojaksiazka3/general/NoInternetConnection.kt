package com.legto.twojaksiazka3.general

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import com.legto.twojaksiazka3.R
import project.legto.twojaksiazka3.login.LoginActivity
import project.legto.twojaksiazka3.login.LoginEmailActivity
import project.legto.twojaksiazka3.login.PasswordResetActivity
import project.legto.twojaksiazka3.login.RegisterActivity

import kotlin.system.exitProcess

class NoInternetConnection : AppCompatActivity() {

    private lateinit var tryCheckConnectionButton:Button
    private lateinit var activityNext: Class<*>

    private  fun hideApplicationTitleBar(){
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar!!.hide()

    }

    private fun startNextActivity(){
        val intent = Intent(this, activityNext)

        startActivity(intent)

    }


    override fun onBackPressed() {
        moveTaskToBack(true)
        exitProcess(-1)
    }
    private fun checkInternetAccess(){

        Fuel.get(getString(R.string.IF_INTERNET_ACCESS_ADRESS))
            .response { _,_, result ->

                when (result) {
                    is Result.Failure -> {
                     //   startNoInternetActivity()
                    }
                    is Result.Success -> {
                        startNextActivity()
                    }
                }

            }

    }

    private fun addCallbackToButtonCheckInternetConnection(){
        tryCheckConnectionButton.setOnClickListener {
           tryCheckConnectionButton.isEnabled=false
          checkInternetAccess()
        }
    }

    private  fun getIdReferenceToElemView(){
        tryCheckConnectionButton=findViewById(R.id.NoInternetConnection_buttonTryConnection)
    }

    private fun setNextActivity(nextActivityName: String?){
        if(nextActivityName!=null ){
            activityNext = when(nextActivityName){
                "RegisterActivity"->{
                    RegisterActivity::class.java

                }
                "LoginActivity"->{
                   LoginEmailActivity::class.java
                }
                "PasswordResetActivity"->{
                    PasswordResetActivity::class.java
                }
                else->{
                    LoginActivity::class.java
                }


            }


        }else{
            activityNext=LoginActivity::class.java
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        hideApplicationTitleBar()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_internet_connection)

        val nameNextActivity=intent.getStringExtra(getString(R.string.fromActivityStartNoInternetActivity))
        setNextActivity(nameNextActivity)


        getIdReferenceToElemView()
        addCallbackToButtonCheckInternetConnection()
    }
}
