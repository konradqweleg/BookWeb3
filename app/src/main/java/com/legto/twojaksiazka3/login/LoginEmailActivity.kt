package project.legto.twojaksiazka3.login

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.util.Base64
import android.util.Base64.encodeToString
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.github.kittinunf.fuel.Fuel
import com.hold1.keyboardheightprovider.KeyboardHeightProvider
import com.legto.twojaksiazka3.R
import com.legto.twojaksiazka3.general.NoInternetConnection
import project.legto.twojaksiazka3.MainAppAcivity

import project.legto.twojaksiazka3.utility.*

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


class LoginEmailActivity : AppCompatActivity() {

    private var keyboardHeightProvider: KeyboardHeightProvider? = null
    private val Int.px: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    private lateinit var   layoutEmailActivity:RelativeLayout
    private lateinit var   logo:TextView
    private lateinit var   description:TextView
    private lateinit var   arrowLeft:ImageView
    private lateinit var   emailInputDescription:TextView
    private lateinit var   inputLogin:EditText
    private lateinit var   textRegister:TextView
    private lateinit var   passwordInputDescription:TextView
    private lateinit var   inputPassword:EditText
    private lateinit var   login:Button
    private lateinit var   buttonIfShowPassword:ImageButton
    private lateinit var   restorePassword:LinearLayout
    private lateinit var   register:LinearLayout
    private lateinit var   errorMessage:TextView
    private lateinit var   inputLoginOrEmailRed:EditText
    private lateinit var   inputPasswordRed:EditText
    private lateinit var   inputEmailFrame:FrameLayout
    private lateinit var   inputPasswordFrame:FrameLayout

    private val ANIM_ALPHA_IN_EDIT=0.7f
    private val PADDING_EDIT_TEXT= listOf(15.px,0.px,15.px,0.px)


    private  val HOW_HEIGH_MEAN_KEYBOARD_IS_SHOW=400
    private val MEDIUM_SLIDE_HEIGH= -65
    private val BIG_SLIDE_HEIGH= -90
    private val BASE_SLIDE_POSITION=0
    private val ANIMATION_DURATION_MS:Long=330

    private val SHOW_ELEM_VISIBILITY=1.0F
    private val HIDE_ELEM_VISIBILITY=0.0F

    private lateinit var KeyboardUtility: KeyboardUtility
    private lateinit var PasswordUtlity: PasswordUtility
    private lateinit var AnimUtility: AnimUtility
    private lateinit var ModifyTextViewUtility: ModifyTextViewUtility
    private lateinit var LayoutUtility: LayoutUtility


    private fun managedShowPassword(){
        PasswordUtlity.whenClickIconShowPasswordInEditText(buttonIfShowPassword,inputPassword)

    }



    private fun isPasswordOrEmailEmpty(email:String,password:String):Boolean{
      if(email.isEmpty() || password.isEmpty()) return true
          return false

    }



  private fun saveUserLoginDetails(login:String,password: String){
      val prefs =
          getSharedPreferences(getString(R.string.NAME_SAVE_FILE_WITH_LOGIN_INFO), Context.MODE_PRIVATE)
      val editor = prefs.edit()
      editor.putString(getString(R.string.NAME_KEY_WITH_SAVE_LOGIN), login)
      editor.putString(getString(R.string.NAME_KEY_WITH_SAVE_PASSWORD),password)
      editor.commit()
      UserData.setIfLoginFacebook(false)

  }


  private fun runMainActivityAfterLoginSuccess(){
      val intent = Intent(this, MainAppAcivity::class.java)
      startActivity(intent)
  }


    private fun startNoInternetActivity(){

        val intent = Intent(this, NoInternetConnection::class.java)
        intent.putExtra(getString(R.string.fromActivityStartNoInternetActivity),"LoginActivity")
        startActivity(intent)
    }
  private fun isTryLoginGood(){


      Fuel.post(getString(R.string.AUTHORIZATION_USER_ADRESS),listOf(getString(R.string.AUTH_EMAIL_LOGIN_PARAM) to inputLogin.text.toString(),
          getString(R.string.AUTH_PASSWORD_PARAM) to inputPassword.text.toString()))
          .response { _, response, _ ->


          //   try{

              val answerLoginAttempts =
                  IsLoginDataResponseCorrect().deserialize(
                      response.data
                  )
              if(!answerLoginAttempts.isAuthenticationSucceeded){
                  showErrorMessage(answerLoginAttempts.messageAuthentication)
              }else{

                 saveUserLoginDetails(inputLogin.text.toString(),inputPassword.text.toString())
                  runMainActivityAfterLoginSuccess()

              }//}
             // catch(E:Exception){

             //   startNoInternetActivity()
             // }

          }

  }

    private fun stopWorkLoginButton(){
       login.isEnabled=false
    }
    private fun addCallbackLoginButton(){
        login.setOnClickListener {
            if(isPasswordOrEmailEmpty(inputLogin.text.toString(),inputPassword.text.toString())){
                showErrorMessage(resources.getString(R.string.Error_Login_EmptyPasswordOrLogin))
            }
            else{
               hideErrorMessage()
                isTryLoginGood()

            }

            stopWorkLoginButton()
            KeyboardUtility.hideKeyboard(this@LoginEmailActivity)

        }
    }


    private fun managedLayoutWhenKeyboardShowHide(heightKeyboard:Int){

        if(heightKeyboard>HOW_HEIGH_MEAN_KEYBOARD_IS_SHOW) {

            LayoutUtility.increaseLayoutHeight(layoutEmailActivity,heightKeyboard-30.px)
            slideTopViewWhenKeyboardShow()
            errorMessage.visibility=View.GONE


        }else{
            LayoutUtility.setDefaultLayoutHeight(layoutEmailActivity)
            slideDownViewWhenKeyboardDisShow()

            if(errorMessage.text.toString()!=""){
                errorMessage.visibility=View.VISIBLE
            }

        }


    }

    private fun getKeyboardListener() = object : KeyboardHeightProvider.KeyboardListener {
        override fun onHeightChanged(height: Int) {
            managedLayoutWhenKeyboardShowHide(height)

        }
    }


    private  fun hideApplicationTitleBar(){
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar!!.hide()

    }
    override fun onResume() {
        super.onResume()
        keyboardHeightProvider?.onResume()
    }

    private fun slideTopViewWhenKeyboardShow(){
        AnimUtility.slideTopCollectionView(listOf(logo,arrowLeft),MEDIUM_SLIDE_HEIGH,ANIMATION_DURATION_MS)
        AnimUtility.slideTopCollectionView(listOf(emailInputDescription,inputEmailFrame,inputPasswordFrame,passwordInputDescription,login,errorMessage),BIG_SLIDE_HEIGH,ANIMATION_DURATION_MS)

        description.alpha=HIDE_ELEM_VISIBILITY

    }

    private fun slideDownViewWhenKeyboardDisShow(){

      AnimUtility.slideDownCollectionView(listOf(logo,emailInputDescription,arrowLeft,
          inputEmailFrame,inputPasswordFrame,passwordInputDescription,
          login,errorMessage),BASE_SLIDE_POSITION,ANIMATION_DURATION_MS)


    }


    private fun setUnderlineOnRegisterTextView(){
        ModifyTextViewUtility.underlineTextView(textRegister)

    }

    private fun addCallbackStartActivityLoginRegister(){
        register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)

            startActivity(intent)

        }
    }



    private fun addCallbackStartActivityLoginPasswordReset(){
        restorePassword.setOnClickListener {

            val intent = Intent(this, PasswordResetActivity::class.java)
            startActivity(intent)
        }
    }


    private fun inputFieldSetPadding(){
        inputLogin.setPadding(PADDING_EDIT_TEXT[0],PADDING_EDIT_TEXT[1],
            PADDING_EDIT_TEXT[2],PADDING_EDIT_TEXT[3])

        inputPassword.setPadding(PADDING_EDIT_TEXT[0],PADDING_EDIT_TEXT[1],
            PADDING_EDIT_TEXT[2],PADDING_EDIT_TEXT[3])
    }

    private fun setVisibleInputRed(visible:Boolean) = if(visible){
        inputLoginOrEmailRed.visibility=View.VISIBLE
        inputPasswordRed.visibility=View.VISIBLE

    }else{
        inputLoginOrEmailRed.visibility=View.GONE
        inputPasswordRed.visibility=View.GONE
    }




    private fun setAlphaInputRed(alpha:Float){
        inputLoginOrEmailRed.alpha=alpha
        inputPasswordRed.alpha=alpha
    }

    private fun scaleEditRed(){
        AnimUtility.animInputError(
            inputLoginOrEmailRed,
            ANIMATION_DURATION_MS,
            this::inputFieldSetPadding,this::setVisibleInputRed
            )

        AnimUtility.animInputError(
            inputPasswordRed,
            ANIMATION_DURATION_MS,
            this::inputFieldSetPadding,this::setVisibleInputRed
        )

    }

    private fun showErrorMessage(textMessage:String){
        errorMessage.visibility = View.VISIBLE
        errorMessage.text=textMessage
        inputPassword.setBackgroundResource(R.drawable.error_input_background)
        inputLogin.setBackgroundResource(R.drawable.error_input_background)
        setAlphaInputRed(ANIM_ALPHA_IN_EDIT)
        scaleEditRed()
        setVisibleInputRed(true)

    }

    private fun hideErrorMessage(){
        errorMessage.text = ""
        errorMessage.visibility = View.GONE
        inputPassword.setBackgroundResource(R.drawable.input_background)
        inputLogin.setBackgroundResource(R.drawable.input_background)
        inputFieldSetPadding()
        setVisibleInputRed(false)


    }


    private fun getIdReferenceToElemView(){

          layoutEmailActivity=findViewById(R.id.topLayoutLoginEmail)
          logo=findViewById(R.id.loginEmailLogo)
          description=findViewById(R.id.LoginEmail_Description)
          arrowLeft=findViewById(R.id.backToChoiceLoginMethod)
          emailInputDescription=findViewById(R.id.WritePassword_NewPasswordDescription)
          inputLogin=findViewById(R.id.inputLoginOrEmail)
          passwordInputDescription=findViewById(R.id.WritePassword_RepeatNewPasswordDescription)
          inputPassword=findViewById(R.id.WritePassword_InputNewRepeatPassword)
          login=findViewById(R.id.WritePassword_SetNewPassword)
          textRegister=findViewById<EditText>(R.id.register_text)
          buttonIfShowPassword=findViewById(R.id.WritePassword_InputRepeatNewPasswordImage)
          register=findViewById(R.id.LoginEmail_Register)
          restorePassword=findViewById(R.id.LoginEmail_restorePassword)
          errorMessage=findViewById(R.id.WritePassword_infoMessage)
          inputLoginOrEmailRed=findViewById(R.id.inputLoginOrEmailRed)
          inputEmailFrame=findViewById(R.id.inputEmailFrame)
          inputPasswordFrame=findViewById(R.id.WritePassword_InputRepeatNewPasswordLayout)
          inputPasswordRed=findViewById(R.id.WritePassword_InputRepeatNewPasswordRed)


    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_right
        )
    }

    override fun onPause() {
        super.onPause()
        keyboardHeightProvider?.onPause()
    }

    private fun setTransitionBetweenActivityActivityShow(){
        overridePendingTransition(
            R.anim.slide_in,
            R.anim.slide_out
        )
    }


    private fun initializeKeyboardProvider(){
        keyboardHeightProvider = KeyboardHeightProvider(this)
        keyboardHeightProvider?.addKeyboardListener(getKeyboardListener())
    }



    private fun addCallbackStartActivityWhenArrowClickLoginActivity(){
        arrowLeft.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


    }

    private fun initializeStrategy(){
        KeyboardUtility= KeyboardUtility()
        PasswordUtlity= PasswordUtility()
        AnimUtility= AnimUtility()
        ModifyTextViewUtility=
            ModifyTextViewUtility()
        LayoutUtility= LayoutUtility()
    }

    private fun checkIfShowMessageSendPassword(){
        val intent = intent
        val addMessage=intent.getStringExtra(getString(R.string.nameShowMessageSendMail))
        if(addMessage!=null){

            errorMessage.text = addMessage

            errorMessage.setTextColor(resources.getColor(R.color.infoPositive))
            errorMessage.visibility=View.VISIBLE
        }
    }



    private fun makeSureTheAccountIsActivated(code:String){

        Fuel.get(
            getString(R.string.VERIFY_ACCOUNT_ADRESS), listOf("code" to code.substringAfter("="))
        )
            .response { _, response, _ ->

            }

        errorMessage.text = getString(R.string.accountCreated)
        errorMessage.setTextColor(resources.getColor(R.color.infoPositive))
        errorMessage.visibility=View.VISIBLE

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appLinkIntent = intent
        val appLinkAction = appLinkIntent.action
        val appLinkData = appLinkIntent.data



        initializeStrategy()

        hideApplicationTitleBar()
        setContentView(R.layout.activity_login_email)
        getIdReferenceToElemView()
        setTransitionBetweenActivityActivityShow()
        initializeKeyboardProvider()
        setUnderlineOnRegisterTextView()
        addCallbackStartActivityWhenArrowClickLoginActivity()
        managedShowPassword()
        addCallbackStartActivityLoginPasswordReset()
        addCallbackStartActivityLoginRegister()
        hideErrorMessage()
        addCallbackLoginButton()
        checkIfShowMessageSendPassword()
        if(appLinkData!=null) {
            makeSureTheAccountIsActivated(appLinkData.toString())
        }


    }
}
