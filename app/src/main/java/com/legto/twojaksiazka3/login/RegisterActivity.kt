package project.legto.twojaksiazka3.login

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.text.Html

import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

import com.github.kittinunf.fuel.Fuel

import com.hold1.keyboardheightprovider.KeyboardHeightProvider
import com.legto.twojaksiazka3.R
import com.legto.twojaksiazka3.general.NoInternetConnection

import project.legto.twojaksiazka3.utility.*

import java.lang.Exception


class RegisterActivity : AppCompatActivity() {



    private lateinit var layout:RelativeLayout
    private lateinit var acceptRulesDescription:TextView
    private lateinit var registerButton:Button
    private lateinit var inputPassword:EditText
    private lateinit var inputPasswordRepeat:EditText
    private lateinit var imageShowPassword:ImageButton
    private lateinit var imageShowPasswordRepeat:ImageButton
    private lateinit var arrowLeft:ImageView
    private lateinit var loginFrame:FrameLayout
    private lateinit var inputLogin:EditText
    private lateinit var inputLoginRed:EditText
    private lateinit var inputPasswordRed:EditText
    private lateinit var inputPasswordRepeatRed:EditText
    private lateinit var inputEmail:EditText
    private lateinit var inputEmailRed:EditText

    private lateinit var loginError:TextView
    private lateinit var passwordError:TextView
    private lateinit var emailError:TextView
    private val PADDING_EDIT_TEXT= listOf(15.px,0.px,15.px,0.px)
    private val ANIMATION_DURATION_MS:Long=330
    private val ANIMATION_DURATION_OFFSET_MS:Long=30
    private val ALL_INPUT_FIELD=3



    private val HOW_HEIGH_MEAN_KEYBOARD_IS_SHOW=400
    private val  layoutDefaultMargin=arrayOf(0.px, 15.px, 20.px, 30.px)
    private val  layoutWithKeyboardShowMargin= arrayOf(0.px,15.px,20.px,300.px)

    private var keyboardHeightProvider: KeyboardHeightProvider? = null
    private lateinit var PasswordUtlity: PasswordUtility
    private lateinit var EmailUtility: EmailUtility
    private lateinit var KeyboardUtility: KeyboardUtility
    private lateinit var AnimUtility: AnimUtility
    private lateinit var LayoutUtility: LayoutUtility


    private  fun hideApplicationTitleBar(){
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar!!.hide()

    }


    private fun showErrorMessage(message:String,inputField:EditText,animField:EditText,messageField:TextView){
        messageField.text=message
        messageField.visibility=View.VISIBLE
        animField.visibility=View.VISIBLE
        inputField.setBackgroundResource(R.drawable.error_input_background)
        AnimUtility.animInputError(animField,ANIMATION_DURATION_MS,this::setPaddingAllViewInput,this::hideAllInputRed,ANIMATION_DURATION_OFFSET_MS)
    }








    private fun showErrorWhenRegisterDataIsBusy(loginAccess:Boolean, emailAccess:Boolean){
        if(!loginAccess){
            showErrorMessage(getString(R.string.RegisterErrorLoginIsBusy),inputLogin,inputLoginRed,loginError)
        }

        if(!emailAccess){
            showErrorMessage(getString(R.string.RegisterErrorEmailIsBusy),inputEmail,inputEmailRed,emailError)

    }}



    private fun sendEmailShowMessage(){
        val intent = Intent(this, LoginEmailActivity::class.java)
        intent.putExtra("sendMail", getString(R.string.activeLinkAccountSend))
        startActivity(intent)
    }


    private fun stopWorkRegisterButton(){
        registerButton.isEnabled=false
    }

    private fun registerUser(){
        stopWorkRegisterButton()

        Fuel.post(
            getString(R.string.REGISTER_USER_ADRESS),
            listOf(getString(R.string.LOGIN_REGISTER_PARAM) to inputLogin.text.toString(),
                getString(R.string.EMAIL_REGISTER_PARAM) to inputEmail.text.toString(),
                getString(R.string.PASSWORD_REGISTER_PARAM) to inputPassword.text.toString()

            )
        )
            .response { _, response, _ ->


                val successRegistration =
                    IsEmailSend().deserialize(
                        response.data
                    )


                if(successRegistration.emailSend){
                   sendEmailShowMessage()

                }

            }

    }

    private fun checkIsEmailAndLoginAccess(login:String,email:String){

           Fuel.post(
               getString(R.string.CHECK_AVAILABILITY_LOGIN_EMAIL_ADRESS),
               listOf(
                   getString(R.string.LOGIN_IF_ACCESS_PARAM) to login,
                   getString(R.string.EMAIL_IF_ACCESS_PARAM) to email
               )
           )
               .response { _, response, _ ->
                   try {

                   val responseAccessEmailLogin =
                       AvaibilityLoginAndEmail().deserialize(
                           response.data
                       )

                   showErrorWhenRegisterDataIsBusy(
                       responseAccessEmailLogin.isLoginAvailability,
                       responseAccessEmailLogin.isEmailAvailability
                   )


                   if (responseAccessEmailLogin.isLoginAvailability && responseAccessEmailLogin.isEmailAvailability) {
                       registerUser()
                   }
                   }
                   catch (e:Exception){
                       print(e)
                       startNoInternetActivity()

                   }


               }




    }

    private fun startNoInternetActivity(){
        saveDataInFileDueInternetLost()
        val intent = Intent(this, NoInternetConnection::class.java)
        intent.putExtra(getString(R.string.fromActivityStartNoInternetActivity),"RegisterActivity")
        startActivity(intent)
    }

    private fun register(){
        checkIsEmailAndLoginAccess(inputLogin.text.toString(),inputEmail.text.toString())
    }

   private fun isLoginCorrect(login:String): Boolean {

       when {
           login=="" -> {
               showErrorMessage(getString(R.string.Error_LoginIsEmpty),inputLogin,inputLoginRed,loginError)
                return false
           }
           login.length<4 -> {
               showErrorMessage(getString(R.string.Error_LogiIsTooShort),inputLogin,inputLoginRed,loginError)
               return false
           }
           else -> {
               hideErrorMessage(loginError,inputLoginRed,inputLogin)
               inputFieldSetPadding(inputLogin)

               return true
           }
       }

   }


    private fun isEmailCorrect(email:String):Boolean {
         when {
            email == "" -> {
                showErrorMessage(getString(R.string.Error_EmptyMail), inputEmail, inputEmailRed, emailError)
                return false

            }
            !EmailUtility.checkEmail(email) -> {
                showErrorMessage(getString(R.string.Error_BadFormatMail), inputEmail, inputEmailRed, emailError)
                return false
            }
            else -> {
                hideErrorMessage(emailError, inputEmailRed, inputEmail)
                inputFieldSetPadding(inputEmail)
                return true
            }


        }

    }



    private fun isPasswordCorrect(password:String,passwordValid:String):Boolean{
        var filedCheckCorrect=false


        if (password.length in 1..6){
            showErrorMessage(getString(R.string.Error_PasswordIsToShort),inputPassword,inputPasswordRed,passwordError)
            filedCheckCorrect=true
        }


        if(password==""){
            showErrorMessage(getString(R.string.Error_PasswordIsEmpty),inputPassword,inputPasswordRed,passwordError)
            filedCheckCorrect=true
        }

        if(passwordValid==""){
            showErrorMessage(getString(R.string.Error_PasswordIsEmpty),inputPasswordRepeat,inputPasswordRepeatRed,passwordError)
            filedCheckCorrect=true
        }


        when {


            password!=passwordValid->{
                showErrorMessage(getString(R.string.Error_PasswordAreDiffrent),inputPasswordRepeat,inputPasswordRepeatRed,passwordError)
                showErrorMessage(getString(R.string.Error_PasswordAreDiffrent),inputPassword,inputPasswordRed,passwordError)

            }

            !filedCheckCorrect -> {
                hideErrorMessage(passwordError,inputPasswordRed,inputPassword)
                hideErrorMessage(passwordError,inputPasswordRepeatRed,inputPasswordRepeat)
                inputFieldSetPadding(inputPassword)
                inputFieldSetPadding(inputPasswordRepeat)
                return true
            }
        }

        return false
    }


    private fun addCallbackToRegister(){
        var quantityCorrectInputField=0
        registerButton.setOnClickListener {
         if(  isLoginCorrect(inputLogin.text.toString())){
             quantityCorrectInputField+=1

         }

          if(isEmailCorrect(inputEmail.text.toString()))  {
              quantityCorrectInputField+=1
          }

            if( isPasswordCorrect(inputPassword.text.toString(),inputPasswordRepeat.text.toString())){
               quantityCorrectInputField+=1
           }



    if(quantityCorrectInputField==ALL_INPUT_FIELD) {

        register()
    }
            quantityCorrectInputField=0
            KeyboardUtility.hideKeyboard(this@RegisterActivity)

        }
    }



    private val Int.px: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    override fun onResume() {
        super.onResume()
        keyboardHeightProvider?.onResume()
    }




    private fun hideInputRed(inputRed:EditText){
        inputRed.visibility=View.GONE

    }
    private fun hideErrorMessage(errorMessage: TextView,inputRed: EditText,input:EditText){
        errorMessage.text = ""
        errorMessage.visibility = View.GONE
        input.setBackgroundResource(R.drawable.input_background)
        hideInputRed(inputRed)


    }

    private fun inputFieldSetPadding(input:EditText){
        input.setPadding(PADDING_EDIT_TEXT[0],PADDING_EDIT_TEXT[1],
            PADDING_EDIT_TEXT[2],PADDING_EDIT_TEXT[3])


    }

    private fun setPaddingAllViewInput(){
        inputFieldSetPadding(inputLogin)
        inputFieldSetPadding(inputEmail)
        inputFieldSetPadding(inputPassword)
        inputFieldSetPadding(inputPasswordRepeat)
    }

    private fun hideAllInputRed(f:Boolean){
        hideInputRed(inputLoginRed)
        hideInputRed(inputEmailRed)
        hideInputRed(inputPasswordRed)
        hideInputRed(inputPasswordRepeatRed)

    }



    private fun clearSaveData(){
        val shared=getSharedPreferences("register_data", Context.MODE_PRIVATE)
        if(shared!=null) {
            val editor = shared.edit()
            editor.putString("loginText", "")
            editor.putString("emailText", "")
            editor.putString("passwordText", "")
            editor.commit()
        }
    }

    private fun restoreDataFromFileAfterErrorInternetConnection(){
        val shared=getSharedPreferences("register_data", Context.MODE_PRIVATE)

       val login= shared.getString("loginText", "")
       inputLogin.setText(login)

        val password= shared.getString("passwordText", "")
        inputPassword.setText(password)
        inputPasswordRepeat.setText(password)

        val email= shared.getString("emailText", "")
        inputEmail.setText(email)

        clearSaveData()

    }

    private fun saveDataInFileDueInternetLost(){
        val shared=getSharedPreferences("register_data", Context.MODE_PRIVATE)
        if(shared!=null) {
            val editor = shared.edit()
            editor.putString("loginText", inputLogin.text.toString())
            editor.putString("emailText", inputEmail.text.toString())
            editor.putString("passwordText", inputPassword.text.toString())
            editor.commit()
        }
    }





    override fun onPause() {
        super.onPause()
        keyboardHeightProvider?.onPause()
    }
    private fun getKeyboardListener() = object : KeyboardHeightProvider.KeyboardListener {
        override fun onHeightChanged(height: Int) {

            managedLayoutWhenKeyboardShowHide(height)

        }
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

    private fun managedLayoutWhenKeyboardShowHide(heightKeyboard:Int){
        if(heightKeyboard>HOW_HEIGH_MEAN_KEYBOARD_IS_SHOW) {

            val marginRegisterButton = registerButton.layoutParams as RelativeLayout.LayoutParams
            marginRegisterButton.setMargins(layoutWithKeyboardShowMargin[0], layoutWithKeyboardShowMargin[1], layoutWithKeyboardShowMargin[2], layoutWithKeyboardShowMargin[3])
            registerButton.layoutParams = marginRegisterButton

        }else{

            val marginRegisterButton = registerButton.layoutParams as RelativeLayout.LayoutParams
            marginRegisterButton.setMargins(layoutDefaultMargin[0], layoutDefaultMargin[1], layoutDefaultMargin[2], layoutDefaultMargin[3])
            registerButton.layoutParams = marginRegisterButton

        }


    }


    private fun addCallbackStartActivityWhenArrowClickLoginActivity(){

        arrowLeft.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }



    private fun managedShowPasswordAfterClickButton(){
        PasswordUtlity.whenClickIconShowPasswordInEditText(imageShowPassword,inputPassword)
        PasswordUtlity.whenClickIconShowPasswordInEditText(imageShowPasswordRepeat,inputPasswordRepeat)

    }



    private fun getIdReferenceToElemView(){

    layout=findViewById(R.id.LoginRegister_layout)
    acceptRulesDescription=findViewById(R.id.LoginRegister_acceptRules)
    registerButton=findViewById(R.id.LoginRegister_registerButton)
    inputPassword=findViewById(R.id.LoginRegister_inputPassword)
    inputPasswordRepeat=findViewById(R.id.LoginRegister_inputRepeatPassword)
    imageShowPassword=findViewById(R.id.LoginRegister_showPassword)
    imageShowPasswordRepeat=findViewById(R.id.LoginRegister_showPasswordRepeat)
    arrowLeft=findViewById(R.id.LoginRegister_arrowLeft)
    loginFrame=findViewById(R.id.LoginRegister_inputLoginFrame)
    inputLogin=findViewById(R.id.LoginRegister_inputLogin)
    inputLoginRed=findViewById(R.id.LoginRegister_inputLoginRed)
    loginError=findViewById(R.id.LoginRegister_loginErrorMessage)
    emailError=findViewById(R.id.LoginRegister_emailErrorMessage)
    passwordError=findViewById(R.id.LoginRegister_passwordErrorMessage)
    inputEmail=findViewById(R.id.LoginRegister_inputEmail)
        inputEmailRed=findViewById(R.id.LoginRegister_inputEmailRed)
     inputPasswordRed=findViewById(R.id.LoginRegister_inputPasswordRed)
        inputPasswordRepeatRed=findViewById(R.id.LoginRegister_inputRepeatPasswordRed)

    }
    private fun setColorRulesDescription(){
        acceptRulesDescription.text = Html.fromHtml(getString(R.string.rulescreateAccountPartOne) + "<font color=#F89D13> "+getString(R.string.rulescreateAccountPartTwo)
                + "</font> "+getString(R.string.rulescreateAccountPartThree) +" <font color=#F89D13 >"+ getString(R.string.rulescreateAccountPartFour)+" </font>")

    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_right
        )
    }

    private fun initializeStrategy(){
        PasswordUtlity= PasswordUtility()
        EmailUtility= EmailUtility()
        KeyboardUtility= KeyboardUtility()
        AnimUtility= AnimUtility()
        LayoutUtility= LayoutUtility()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        hideApplicationTitleBar()
        super.onCreate(savedInstanceState)
        initializeStrategy()
        setContentView(R.layout.activity_login_register)
        initializeKeyboardProvider()
        getIdReferenceToElemView()
        setColorRulesDescription()
        managedShowPasswordAfterClickButton()
        setTransitionBetweenActivityActivityShow()
        addCallbackStartActivityWhenArrowClickLoginActivity()
        addCallbackToRegister()
        restoreDataFromFileAfterErrorInternetConnection()




    }
}
