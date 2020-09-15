package project.legto.twojaksiazka3.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.github.kittinunf.fuel.Fuel
import com.legto.twojaksiazka3.R
import project.legto.twojaksiazka3.MainAppAcivity
import project.legto.twojaksiazka3.utility.ModifyTextViewUtility
import project.legto.twojaksiazka3.utility.UserData

import java.util.*
import kotlin.system.exitProcess
import android.content.Context as Context1


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class LoginActivity : AppCompatActivity() {

    private lateinit var   buttonRegister: Button
    private lateinit var emailLoginButton: Button
   val callbackManager = CallbackManager.Factory.create()


    private lateinit var ModifyTextViewUtility: ModifyTextViewUtility
    private lateinit var facebookButton:FrameLayout

    private fun saveUserLoginDetails(login:String,password: String){
        val prefs =
            getSharedPreferences(getString(R.string.NAME_SAVE_FILE_WITH_LOGIN_INFO), android.content.Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(getString(R.string.NAME_KEY_WITH_SAVE_LOGIN), login)
        editor.putString(getString(R.string.NAME_KEY_WITH_SAVE_PASSWORD),password)
        editor.commit()

    }
    private fun runMainActivityAfterLoginSuccess(){
        val intent = Intent(this, MainAppAcivity::class.java)
        startActivity(intent)
    }


    private  fun hideApplicationTitleBar(){
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar!!.hide()

    }




    private fun addCallbackStartActivityLoginEmailActivity(){


        emailLoginButton.setOnClickListener {

            val intent = Intent(this, LoginEmailActivity::class.java)

            startActivity(intent)
        }


    }


    override fun finish() {
        super.finish()
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_right
        )

    }


    private fun addCallbackToStartFacebookLogin(){
        val  loginButton =  findViewById(R.id.login_button) as LoginButton
        facebookButton.setOnClickListener {
            loginButton.performClick();
            registerLoginFacebookCallback()
        }
    }

    private fun addCallbackStartActivityLoginRegister(){
        buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)

           startActivity(intent)

       }
    }

    private fun getIdReferenceToElemView(){
        emailLoginButton=findViewById(R.id.loginEmailAndPasswordButton)
        buttonRegister=findViewById(R.id.Login_registerButton)
        facebookButton=findViewById(R.id.loginFacebookButton)
    }

    private fun setTransitionBetweenActivityActivityShow(){
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_right
        )
    }



    private fun initializeStrategy(){
        ModifyTextViewUtility= ModifyTextViewUtility()

    }


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {

        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        logUser()
    }
    fun isLoggedIn(): Boolean {
        val accessToken = AccessToken.getCurrentAccessToken()
        return accessToken != null
    }

    fun logUser(){
        if(isLoggedIn()){
        UserData.setIfLoginFacebook(true)
        runMainActivityAfterLoginSuccess()
    }}
    private fun registerLoginFacebookCallback(){
        AccessToken.setCurrentAccessToken(null)
     var  callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(this,
            Arrays.asList("public_profile","email","user_photos","user_friends"))

        LoginManager.getInstance().registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    logUser()
                    UserData.setIfLoginFacebook(true)
                    runMainActivityAfterLoginSuccess()
                }

                override fun onCancel() {
                    // App code
                    runMainActivityAfterLoginSuccess()
                }

                override fun onError(exception: FacebookException) {
                    // App code
                    runMainActivityAfterLoginSuccess()
                }
            })

    }

    override fun onBackPressed() {
        moveTaskToBack(true)
        exitProcess(-1)
    }





    private fun checkIfSaveDataLoginIsCorrect(login:String,password:String){
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

                }

            }

    }

    private fun checkIfUserHaveSaveLogged(){
        val prefs = getSharedPreferences(
            getString(R.string.NAME_SAVE_FILE_WITH_LOGIN_INFO),
            Context1.MODE_PRIVATE)


        if(prefs!=null) {
            val loginSave = prefs.getString(
                getString(R.string.NAME_KEY_WITH_SAVE_LOGIN),
                ""
            )

            val passwordSave = prefs.getString(
                getString(R.string.NAME_KEY_WITH_SAVE_PASSWORD),
                ""
            )


            if (loginSave != "" && passwordSave != "") checkIfSaveDataLoginIsCorrect(loginSave!!,passwordSave!!)


        }

    }




    override fun onCreate(savedInstanceState: Bundle?) {

        hideApplicationTitleBar()
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(getApplicationContext());

        logUser()
        setContentView(R.layout.activity_login)
        initializeStrategy()
        getIdReferenceToElemView()
        setTransitionBetweenActivityActivityShow()
        checkIfUserHaveSaveLogged()
        addCallbackStartActivityLoginEmailActivity()
        addCallbackStartActivityLoginRegister()
       if(isLoggedIn()==false) {
          UserData.setIfLoginFacebook(false)
        }else{
           UserData.setIfLoginFacebook(true)
       }



        addCallbackToStartFacebookLogin()




    }


}
