package com.legto.twojaksiazka3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.result.Result
import com.legto.twojaksiazka3.R
import com.legto.twojaksiazka3.general.NoInternetConnection
import project.legto.twojaksiazka3.MainAppAcivity

import project.legto.twojaksiazka3.login.IsLoginDataResponseCorrect

import project.legto.twojaksiazka3.login.LoginActivity
import project.legto.twojaksiazka3.utility.BaseFrragment
import java.lang.Exception


class MainActivity : AppCompatActivity() {



    private fun startLoginActivity(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

    }
    private fun startNoInternetActivity(){
        val intent = Intent(this, NoInternetConnection::class.java)
        startActivity(intent)

    }

    private  fun hideApplicationTitleBar(){
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar!!.hide()

    }


    private fun initializeStrategy(){


    }





    private fun checkInternetAccess(){

        Fuel.get(getString(R.string.IF_INTERNET_ACCESS_ADRESS))
            .response { _, _, result ->
                when (result) {
                    is Result.Failure -> {

                        startNoInternetActivity()

                    }
                    is Result.Success -> {

                    }
                }

            }


    }


    override fun onBackPressed() {




            val fragments: List<Fragment> =
                supportFragmentManager.fragments

            for (f in fragments) {
                if (f != null && f is BaseFrragment)
                { var isReturn= (f as BaseFrragment).onBackPressed()

                    if(!isReturn){
                        moveTaskToBack(true);
                    }
                }

            }

        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)

            //   moveTaskToBack(true);
            //  exitProcess(-1)

    }

    //Popraw
    private fun showMessageWithInfoAboutSendActiveAccountonMail(){
        val ss:String? = intent.getStringExtra("sendMail")

        if(ss!=null ){
            Toast.makeText(this,getString(R.string.sendActivationMail), Toast.LENGTH_SHORT)
                .show()
        }

    }



    private fun checkIfSaveDataLoginIsCorrect(login:String,password:String){
     Log.e("polaczenie","próba")




        Fuel.post(
            getString(R.string.AUTHORIZATION_USER_ADRESS),
            listOf(getString(R.string.AUTH_EMAIL_LOGIN_PARAM) to login,
                getString(R.string.AUTH_PASSWORD_PARAM) to password)
        )
            .response { _, response, _ ->

                val authorizationResponseData =
                    IsLoginDataResponseCorrect().deserialize(
                       response.data
                  )

                if (authorizationResponseData.isAuthenticationSucceeded) {
                   val intent = Intent(this, MainAppAcivity::class.java)

                    startActivity(intent)
                    Log.e("fff","mam2")

                }else{
                    startLoginActivity()

               }

            }

    }


    private fun checkIfUserHaveSaveLogged(){
      //  Log.e("fff","mam")
     //  checkIfSaveDataLoginIsCorrect("konradnrog@gmail.com","Windows10!47")
      try{
        val prefs = getSharedPreferences(
            getString(R.string.NAME_SAVE_FILE_WITH_LOGIN_INFO),
            Context.MODE_PRIVATE)

      //  Log.e("fff","mam")
        if(prefs!=null) {
            val loginSave = prefs.getString(
                getString(R.string.NAME_KEY_WITH_SAVE_LOGIN),
                ""
            )

            val passwordSave = prefs.getString(
                getString(R.string.NAME_KEY_WITH_SAVE_PASSWORD),
                ""
            )

            Log.e("fff",loginSave+passwordSave+"aaa")
            if (loginSave != "" && passwordSave != "") checkIfSaveDataLoginIsCorrect(loginSave!!,passwordSave!!)
            else{
                checkIfSaveDataLoginIsCorrect("","")
            }

            Log.e("fff","mam1")
        }else{

            checkIfSaveDataLoginIsCorrect("konradnrog@gmail.com","Windows10!47")
        }}
      catch(Exp:Exception){
        //  checkIfSaveDataLoginIsCorrect("konradnrog@gmail.com","Windows10!47")
        //  Log.e("fff","mam1")
      }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        hideApplicationTitleBar()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//Tutaj twój kod
       // val intent = Intent(this, LoginActivity::class.java)

      //  startActivity(intent)

        initializeStrategy()
        checkInternetAccess()
        showMessageWithInfoAboutSendActiveAccountonMail()
        checkIfUserHaveSaveLogged()


    }
}
