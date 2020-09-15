package project.legto.twojaksiazka3.login

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import com.github.kittinunf.fuel.Fuel
import com.hold1.keyboardheightprovider.KeyboardHeightProvider
import com.legto.twojaksiazka3.R
import com.legto.twojaksiazka3.general.NoInternetConnection

import project.legto.twojaksiazka3.utility.*

import java.lang.Exception


class WritePasswordRestore : AppCompatActivity() {
    private lateinit var inputPassword:EditText
    private lateinit var inputRepeatPassword:EditText
    private lateinit var inputPasswordRed:EditText
    private lateinit var inputRepeatPasswordRed:EditText
    private lateinit var inputPasswordShowButton:ImageButton
    private lateinit var inputPasswordRepeatShowButton:ImageButton
    private lateinit var changePasswordButton:Button
    private lateinit var messageView:TextView
    private lateinit var allLayout:RelativeLayout
    private lateinit var logo:TextView
    private lateinit var frameInputnewPassword:RelativeLayout
    private lateinit var frameInputRepeatPassword:FrameLayout
    private lateinit var codeChangePasswordLink:String

    private lateinit var passwordDescription:TextView
    private lateinit var passwordRepeatDescription:TextView
    private lateinit var pageDescription:TextView
    private lateinit var exit:ImageView


    private lateinit var KeyboardUtility: KeyboardUtility
    private lateinit var PasswordUtlity: PasswordUtility
    private lateinit var AnimUtility: AnimUtility
    private lateinit var ModifyTextViewUtility: ModifyTextViewUtility
    private lateinit var LayoutUtility: LayoutUtility
    private val ANIM_ALPHA_IN_EDIT=0.7f
    private  val HOW_HEIGH_MEAN_KEYBOARD_IS_SHOW=400

    private var keyboardHeightProvider: KeyboardHeightProvider? = null

    private val ANIMATION_DURATION_MS:Long=330
    private val MEDIUM_SLIDE_HEIGH= -65
    private val BIG_SLIDE_HEIGH= -90
    private val BASE_SLIDE_POSITION=0
    private val SHOW_ELEM_VISIBILITY=1.0F
    private val HIDE_ELEM_VISIBILITY=0.0F


    private fun initializeStrategy(){
        KeyboardUtility= KeyboardUtility()
        PasswordUtlity= PasswordUtility()
        AnimUtility= AnimUtility()
        ModifyTextViewUtility=
            ModifyTextViewUtility()
        LayoutUtility= LayoutUtility()
    }






    private fun changePassword(){



        Fuel.post(getString(R.string.CHANGE_PASSWORD_ADRESS), listOf("code" to codeChangePasswordLink.substringAfter("="),"newPassword" to inputPassword.text.toString())
            )
            .response { _, response, _ ->


                try{
                    val intent = Intent(this, LoginEmailActivity::class.java)
                    intent.putExtra("sendMail", getString(R.string.changedPassword))
                    changeSavePassword(inputPassword.text.toString())
                    startActivity(intent)

                    }
                catch(E: Exception){

                    startNoInternetActivity()
                }

            }

    }


    private fun changeSavePassword(password: String){
        val prefs =
            getSharedPreferences(getString(R.string.NAME_SAVE_FILE_WITH_LOGIN_INFO), Context.MODE_PRIVATE)
        val editor = prefs.edit()

        editor.putString(getString(R.string.NAME_KEY_WITH_SAVE_PASSWORD),password)
        editor.commit()

    }








    private fun slideTopViewWhenKeyboardShow(){
        AnimUtility.slideTopCollectionView(listOf(logo,pageDescription,exit),MEDIUM_SLIDE_HEIGH,ANIMATION_DURATION_MS)
        AnimUtility.slideTopCollectionView(listOf(passwordDescription,changePasswordButton,frameInputnewPassword,passwordRepeatDescription,frameInputRepeatPassword,messageView),BIG_SLIDE_HEIGH,ANIMATION_DURATION_MS)

        messageView.alpha=HIDE_ELEM_VISIBILITY

    }

    private fun slideDownViewWhenKeyboardDisShow(){

        AnimUtility.slideDownCollectionView(listOf(logo,exit,pageDescription,changePasswordButton,passwordDescription,frameInputnewPassword,frameInputRepeatPassword,passwordRepeatDescription,inputRepeatPassword,messageView),BASE_SLIDE_POSITION,ANIMATION_DURATION_MS)
        messageView.alpha=SHOW_ELEM_VISIBILITY

    }







    private fun managedLayoutWhenKeyboardShowHide(heightKeyboard:Int){

        if(heightKeyboard>HOW_HEIGH_MEAN_KEYBOARD_IS_SHOW) {

            LayoutUtility.increaseLayoutHeight(allLayout,heightKeyboard-30.px)
            slideTopViewWhenKeyboardShow()
            messageView.visibility=View.GONE


        }else{
            LayoutUtility.setDefaultLayoutHeight(allLayout)
            slideDownViewWhenKeyboardDisShow()

            if(messageView.text.toString()!=""){
                messageView.visibility=View.VISIBLE
            }

        }


    }
    private fun getKeyboardListener() = object : KeyboardHeightProvider.KeyboardListener {
        override fun onHeightChanged(height: Int) {
            managedLayoutWhenKeyboardShowHide(height)

        }
    }
    private fun initializeKeyboardProvider(){
        keyboardHeightProvider = KeyboardHeightProvider(this)
        keyboardHeightProvider?.addKeyboardListener(getKeyboardListener())
    }


    private fun showErrorMessage(textMessage:String){
        messageView.visibility = View.VISIBLE
        messageView.text=textMessage
        inputPassword.setBackgroundResource(R.drawable.error_input_background)
        inputRepeatPassword.setBackgroundResource(R.drawable.error_input_background)
        setAlphaInputRed(ANIM_ALPHA_IN_EDIT)
        scaleEditRed()
        setVisibleInputRed(true)

    }

    private fun hideErrorMessage(){
        messageView.text = ""
        messageView.visibility = View.GONE
        inputPassword.setBackgroundResource(R.drawable.input_background)
        inputRepeatPassword.setBackgroundResource(R.drawable.input_background)
        inputFieldSetPadding()
        setVisibleInputRed(false)


    }

    override fun onResume() {
        super.onResume()
        keyboardHeightProvider?.onResume()
    }

    private val Int.px: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    private val PADDING_EDIT_TEXT= listOf(15.px,0.px,15.px,0.px)

    private  fun hideApplicationTitleBar(){
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar!!.hide()

    }



    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_right
        )
    }

    private fun startNoInternetActivity(){

        val intent = Intent(this, NoInternetConnection::class.java)
        intent.putExtra(getString(R.string.fromActivityStartNoInternetActivity),"PasswordResetActivity")
        startActivity(intent)
    }

    private fun stopWorkChangePasswordButton(){
        changePasswordButton.isEnabled=false
    }


    private fun addCallbackChangePasswordButton(){
        changePasswordButton.setOnClickListener {
            if(isPasswordsEmpty(inputRepeatPassword.text.toString(),inputPassword.text.toString())){
                showErrorMessage(resources.getString(R.string.emptyFieldPassword))
            }else if(inputPassword.text.toString().length<6){
                showErrorMessage(resources.getString(R.string.tooShortPassword))

            }
            else{
                hideErrorMessage()
                stopWorkChangePasswordButton()
               changePassword()

            }


            KeyboardUtility.hideKeyboard(this@WritePasswordRestore)

        }
    }


    private fun isPasswordsEmpty(passwordTwo:String,password:String):Boolean{
        if(passwordTwo.isEmpty() || password.isEmpty()) return true
        return false

    }

    private fun managedShowPassword(){
        PasswordUtlity.whenClickIconShowPasswordInEditText(inputPasswordShowButton,inputPassword)
        PasswordUtlity.whenClickIconShowPasswordInEditText(inputPasswordRepeatShowButton,inputRepeatPassword)

    }

    private fun inputFieldSetPadding(){
        inputRepeatPassword.setPadding(PADDING_EDIT_TEXT[0],PADDING_EDIT_TEXT[1],
            PADDING_EDIT_TEXT[2],PADDING_EDIT_TEXT[3])

        inputPassword.setPadding(PADDING_EDIT_TEXT[0],PADDING_EDIT_TEXT[1],
            PADDING_EDIT_TEXT[2],PADDING_EDIT_TEXT[3])
    }

    private fun setVisibleInputRed(visible:Boolean) = if(visible){
        inputRepeatPasswordRed.visibility= View.VISIBLE
        inputPasswordRed.visibility= View.VISIBLE

    }else{
        inputRepeatPasswordRed.visibility= View.GONE
        inputPasswordRed.visibility= View.GONE
    }




    private fun setAlphaInputRed(alpha:Float){
       inputRepeatPasswordRed.alpha=alpha
        inputPasswordRed.alpha=alpha
    }

    private fun scaleEditRed(){
        AnimUtility.animInputError(
            inputRepeatPasswordRed,
            ANIMATION_DURATION_MS,
            this::inputFieldSetPadding,this::setVisibleInputRed
        )

        AnimUtility.animInputError(
            inputPasswordRed,
            ANIMATION_DURATION_MS,
            this::inputFieldSetPadding,this::setVisibleInputRed
        )

    }










    private fun getIdReferenceToElemView(){

        inputPassword=findViewById(R.id.WritePassword_InputNewPassword)
        inputRepeatPassword=findViewById(R.id.WritePassword_InputNewRepeatPassword)
        inputPasswordRed=findViewById(R.id.WritePassword_InputNewPasswordRed)
        inputRepeatPasswordRed=findViewById(R.id.WritePassword_InputRepeatNewPasswordRed)
         inputPasswordShowButton=findViewById(R.id.WritePassword_inputPasswordImageShowPassword)
         inputPasswordRepeatShowButton=findViewById(R.id.WritePassword_InputRepeatNewPasswordImage)
        changePasswordButton=findViewById(R.id.WritePassword_SetNewPassword)
        messageView=findViewById(R.id.WritePassword_infoMessage)
        allLayout=findViewById(R.id.WritePassword_AllLayout)
        logo=findViewById(R.id.WritePassword_Logo)
        passwordDescription=findViewById(R.id.WritePassword_NewPasswordDescription)
        passwordRepeatDescription=findViewById(R.id.WritePassword_RepeatNewPasswordDescription)
        frameInputnewPassword=findViewById(R.id.WritePassword_InputNewPasswordLayout)
        frameInputRepeatPassword=findViewById(R.id.WritePassword_InputRepeatNewPasswordLayout)
        pageDescription=findViewById(R.id.WritePassword_PageDescription)
        exit=findViewById(R.id.WritePassword_ExitButton)

    }

    private fun setTransitionBetweenActivityActivityShow(){
        overridePendingTransition(
            R.anim.slide_in,
            R.anim.slide_out
        )
    }

    private fun addCallbackToExitToMenu(){
        exit.setOnClickListener {

            val intent = Intent(this, LoginActivity::class.java)

            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        hideApplicationTitleBar()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_password_restore)
        initializeStrategy()
        getIdReferenceToElemView()
        setTransitionBetweenActivityActivityShow()
        managedShowPassword()
        addCallbackChangePasswordButton()
        hideErrorMessage()
        managedShowPassword()
        initializeKeyboardProvider()

        val appLinkIntent = intent
        val appLinkAction = appLinkIntent.action
        val appLinkData = appLinkIntent.data

        if(appLinkData!=null) {
            codeChangePasswordLink=appLinkData.toString()
        }

        addCallbackToExitToMenu()
    }
}
