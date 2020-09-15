package project.legto.twojaksiazka3.login

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

import project.legto.twojaksiazka3.utility.AnimUtility
import project.legto.twojaksiazka3.utility.EmailUtility
import project.legto.twojaksiazka3.utility.KeyboardUtility
import project.legto.twojaksiazka3.utility.LayoutUtility

import java.lang.Exception

class PasswordResetActivity : AppCompatActivity() {


    private var keyboardHeightProvider: KeyboardHeightProvider? = null
    private val Int.px: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()


    private lateinit var layout:RelativeLayout
    private lateinit var arrowLeft:ImageView
    private lateinit var descriptionPage:TextView
    private lateinit var descriptionEmail:TextView
    private lateinit var resetPasswordButton:Button
    private lateinit var logoText:TextView
    private lateinit var inputLogin:EditText
    private lateinit var redInputLogin:EditText
    private lateinit var frameInputLogin:FrameLayout
    private lateinit var errorMessage:TextView


    private val MEDIUM_SLIDE_HEIGH= -60
    private val BIG_SLIDE_HEIGH= -75
    private val BASE_SLIDE_POSITION=0
    private val ANIMATION_DURATION_MS:Long=330
    private val PADDING_EDIT_TEXT= listOf(15.px,0.px,15.px,0.px)
    private val HOW_HEIGH_MEAN_KEYBOARD_IS_SHOW=400
    private lateinit var  EmailUtility: EmailUtility
    private lateinit var AnimUtility: AnimUtility
    private lateinit var KeyboardUtility: KeyboardUtility
    private lateinit var LayoutUtility: LayoutUtility

    override fun onResume() {
        super.onResume()
        keyboardHeightProvider?.onResume()
    }


    private fun slideTopViewWhenKeyboardShow(){

        AnimUtility.slideTopCollectionView(listOf(logoText,arrowLeft),MEDIUM_SLIDE_HEIGH,ANIMATION_DURATION_MS)
        AnimUtility.slideTopCollectionView(listOf(descriptionEmail,resetPasswordButton,errorMessage
        ,frameInputLogin,descriptionPage),BIG_SLIDE_HEIGH,ANIMATION_DURATION_MS)



    }

    private fun slideDownViewWhenKeyboardDisShow(){
        AnimUtility.slideDownCollectionView(listOf(logoText,arrowLeft,descriptionEmail,
            descriptionPage,resetPasswordButton,errorMessage,frameInputLogin),BASE_SLIDE_POSITION,ANIMATION_DURATION_MS)

    }
    override fun onPause() {
        super.onPause()
        keyboardHeightProvider?.onPause()
    }
    private fun inputFieldSetPadding(){
        inputLogin.setPadding(PADDING_EDIT_TEXT[0],PADDING_EDIT_TEXT[1],
            PADDING_EDIT_TEXT[2],PADDING_EDIT_TEXT[3])

    }

    private fun setVisibleInputRed(visible:Boolean){
        if(visible){
            redInputLogin.visibility=View.VISIBLE


        }else{
            redInputLogin.visibility=View.GONE

        }

    }

    private fun showErrorMessage(textMessage:String){
        errorMessage.visibility = View.VISIBLE
        errorMessage.text=textMessage

        inputLogin.setBackgroundResource(R.drawable.error_input_background)
        setAlphaInputRed(1f)
        scaleEditRed()
        setVisibleInputRed(true)


    }

    private fun scaleEditRed(){
        AnimUtility.animInputError(redInputLogin,ANIMATION_DURATION_MS,this::inputFieldSetPadding,this::setVisibleInputRed)


    }
    private fun setAlphaInputRed(alpha:Float){
        inputLogin.alpha=alpha

    }

    private fun hideErrorMessage(){
        errorMessage.text = ""
        errorMessage.visibility = View.GONE
        inputLogin.setBackgroundResource(R.drawable.input_background)
        inputFieldSetPadding()

        setVisibleInputRed(false)


    }


    private fun checkEmailIsCorrect(email:String){
        when {
            email=="" -> {
                showErrorMessage("Pusty e-mail")
            }
            !EmailUtility.checkEmail(email) -> {
                showErrorMessage("Niepoprawny e-mail")
            }
            else -> {
                hideErrorMessage()
                checkEmailExistInDatabase(inputLogin.text.toString())
                KeyboardUtility.hideKeyboard(this@PasswordResetActivity)
            }
        }

    }


    private fun managedLayoutWhenKeyboardShowHide(heightKeyboard:Int){

        if(heightKeyboard>HOW_HEIGH_MEAN_KEYBOARD_IS_SHOW) {
            LayoutUtility.increaseLayoutHeight(layout,heightKeyboard)
            slideTopViewWhenKeyboardShow()

        }else{
            LayoutUtility.setDefaultLayoutHeight(layout)
            slideDownViewWhenKeyboardDisShow()
        }


    }

    private fun getKeyboardListener() = object : KeyboardHeightProvider.KeyboardListener {
        override fun onHeightChanged(height: Int) {

           managedLayoutWhenKeyboardShowHide(height)

        }
    }




    private fun getIdReferenceToElemView(){
        layout=findViewById(R.id.LoginPasswordReset_layout)
        arrowLeft=findViewById(R.id.LoginPasswordReset_arrowLeft)
        descriptionPage=findViewById(R.id.LoginPasswordReset_descriptionPage)
        descriptionEmail=findViewById(R.id.LoginPasswordReset_descriptionEmail)
        resetPasswordButton=findViewById(R.id.LoginPasswordReset_resetPasswordButton)
        logoText=findViewById(R.id.LoginPasswordReset_logo)
        inputLogin=findViewById(R.id.LoginPasswordReset_inputLogin)
        redInputLogin=findViewById(R.id.LoginPasswordReset_inputLoginRed)
        errorMessage=findViewById(R.id.LoginPasswordReset_errorMessage)
        frameInputLogin=findViewById(R.id.LoginPasswordReset_inputEmailFrame)




    }


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



    private fun startNoInternetActivity(){

        val intent = Intent(this, NoInternetConnection::class.java)
        intent.putExtra(getString(R.string.fromActivityStartNoInternetActivity),"PasswordResetActivity")
        startActivity(intent)
    }


    private fun checkEmailExistInDatabase(email:String){
        resetPasswordButton.isEnabled = false
        Fuel.get(
            getString(R.string.CHECK_EMAIL_EXIST_IN_DATABASE_ADRESS),
            listOf(
                "mail" to email
            )
        )
            .response { _, response, _ ->
                try {

                    val responseMailInDatabase =
                        MailInDatabase().deserialize(
                            response.data
                        )

                    if(!responseMailInDatabase.emailInDatabase){
                        showErrorMessage(getString(R.string.Error_AccoundNotExist))
                    }else{
                        errorMessage.text=getString(R.string.sendLinkChangePassword)
                        errorMessage.setTextColor(resources.getColor(R.color.infoPositive))
                        errorMessage.visibility=View.VISIBLE
                    }


                }
                catch (e: Exception){

                    startNoInternetActivity()

                }


            }

    }

    private fun addCallbackToChangePasswordButton(){
        resetPasswordButton.setOnClickListener {
            checkEmailIsCorrect(inputLogin.text.toString())


        }
    }


    private fun initialiseStrategy(){
        EmailUtility= EmailUtility()
        AnimUtility= AnimUtility()
        KeyboardUtility= KeyboardUtility()
        LayoutUtility= LayoutUtility()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        hideApplicationTitleBar()
        super.onCreate(savedInstanceState)
        initialiseStrategy()
        setContentView(R.layout.activity_login_password_reset)
        initializeKeyboardProvider()
        setTransitionBetweenActivityActivityShow()
        getIdReferenceToElemView()
        addCallbackStartActivityWhenArrowClickLoginActivity()
        addCallbackToChangePasswordButton()



    }
}
