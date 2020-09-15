package project.legto.twojaksiazka3

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.github.kittinunf.fuel.Fuel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.legto.twojaksiazka3.R
import org.json.JSONException
import project.legto.twojaksiazka3.findBook.FindPanelBookFragment
import project.legto.twojaksiazka3.ui.bookShow.*
import project.legto.twojaksiazka3.ui.databaseBooks.FilterShowBookInDatabaseBookLayout
import project.legto.twojaksiazka3.ui.databaseBooks.ShowBookWithOneCategory
import project.legto.twojaksiazka3.ui.profil.ProfilFragment
import project.legto.twojaksiazka3.utility.BaseFrragment
import project.legto.twojaksiazka3.utility.SaveUserAuthorization
import project.legto.twojaksiazka3.utility.UserData
import project.legto.twojaksiazka3.utility.UserIdMapper

import java.security.AccessController.getContext


class MainAppAcivity : AppCompatActivity(), BookFragment.OnFragmentInteractionListener, YourMark.OnFragmentInteractionListener,
    VotedBook.OnFragmentInteractionListener, CommentBookFragment.OnFragmentInteractionListener,
    ScheduleMarkBookFragment.OnFragmentInteractionListener, FindPanelBookFragment.OnFragmentInteractionListener,
    ShowBookWithOneCategory.OnFragmentInteractionListener, FilterShowBookInDatabaseBookLayout.OnFragmentInteractionListener {


    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    private  fun hideApplicationTitleBar(){
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar!!.hide()

    }
    override fun onBackPressed() {
        Log.e("gha","x")

        fragmentManager!!.popBackStackImmediate()
        val fragments: List<Fragment> =
            supportFragmentManager.fragments

        for (f in fragments) {
            f.fragmentManager!!.popBackStackImmediate()

            Log.e("gha","")
            Log.e("gha","v"+f.id)
            if(f!=null){
                Log.e("gha","kurwa")

            }
            if (f != null && f is BaseFrragment)
            { var isReturn= (f as BaseFrragment).onBackPressed()
                Log.e("gha","mam")
                if(!isReturn){
                    moveTaskToBack(true);
                }
        }

        }



    //   moveTaskToBack(true);
      //  exitProcess(-1)
    }
    private fun saveUserLoginDetails(login:String,password: String){
        val prefs =
            getSharedPreferences(getString(R.string.NAME_SAVE_FILE_WITH_LOGIN_INFO), Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(getString(R.string.NAME_KEY_WITH_SAVE_LOGIN), login)
        editor.putString(getString(R.string.NAME_KEY_WITH_SAVE_PASSWORD),password)
        editor.commit()
    Log.e("asf","zapis")
    }
    private fun registerUserViaFb(email:String,id:String,login:String){
        Fuel.post(
            getString(R.string.REGISTER_USER_VIA_FB),
            listOf("email" to email ,"fbId" to id ,"login" to login

            )
        )
            .response { _, response, _ ->



                }


    }


    private fun downlaodRestDataUserWayLoginFb(fbId:String){
        Fuel.get(
           getString(R.string.USER_ID_FROM_FB_ID_ADRESS),
            listOf(
                "fbId" to fbId
            )
        ).response { _, _, result ->
            result.fold(
                success = {
                    val userId =
                        UserIdMapper().deserialize(
                            it
                        )
                    UserData.saveUserId(userId.userId)

                },
                failure = { error ->


                }
            )
        }

          Log.e("sad",UserData.toString())
    }
    private fun getFbUserData() {
        val request = GraphRequest.newMeRequest(
            AccessToken.getCurrentAccessToken()
        ) { `object`, response ->
            try {

                Log.e("as", response.toString())
                val id = `object`.getString("id")
                UserData.saveIdfacebook(id)
                val first_name = `object`.getString("first_name")
                val last_name = `object`.getString("last_name")
                UserData.saveLogin(first_name+" "+last_name)

                val image_url =
                    "http://graph.facebook.com/$id/picture?type=large"
                ProfilFragment.adresPhoto=image_url
                var email: String=" "
                if (`object`.has("email")) {
                    email = `object`.getString("email")
                    UserData.saveMail(email)
                }


                registerUserViaFb(email,id,first_name+" "+last_name)
                downlaodRestDataUserWayLoginFb(id)

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        val parameters = Bundle()
        parameters.putString(
            "fields",
            "id,first_name,last_name,email,gender,birthday,picture.type(large)"
        ) // id,first_name,last_name,email,gender,birthday,cover,picture.type(large)
        request.parameters = parameters
        request.executeAsync()
    }

    fun getUserDataLoginByEmail(){
        Fuel.get(
            getString(R.string.USER_ID_FROM_EMAIL),
            listOf(
                "email" to SaveUserAuthorization.getUserLogin(this)
            )
        ).response { _, _, result ->
            result.fold(
                success = {
                    val userId =
                        UserIdMapper().deserialize(
                            it
                        )
                    UserData.saveUserId(userId.userId)

                },
                failure = { error ->


                }
            )
        }
    }

    fun fillUserDataDependOnWayLogin(){
        if(UserData.ifLoginFb){
            getFbUserData()

        }else{
            getUserDataLoginByEmail()
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
      //  getFbInfo()
  /*      try {
            val info = packageManager.getPackageInfo(
                packageName,  //Or replace to your package name directly, instead getPackageName()  "com.your.app"
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e(
                    "KeyHash:",
                    Base64.encodeToString(md.digest(), Base64.DEFAULT)
                )
            }
        } catch (e: PackageManager.NameNotFoundException) {
        } catch (e: NoSuchAlgorithmException) {
        }*/

        hideApplicationTitleBar()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_app_acivity)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,R.id.navigation_profil
            )
        )
        fillUserDataDependOnWayLogin()


        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}
