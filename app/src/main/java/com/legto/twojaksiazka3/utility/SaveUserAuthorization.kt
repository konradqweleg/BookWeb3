package project.legto.twojaksiazka3.utility

import android.app.Activity
import android.content.Context


class SaveUserAuthorization {


    companion object{
        private val NAME_SAVE_FILE_WITH_LOGIN_INFO="dane"
        private val NAME_KEY_WITH_SACE_LOGIN="login"

       fun getUserLogin(activity:Activity):String{

           val usersSavedLoginData = activity!!.getSharedPreferences(
              NAME_SAVE_FILE_WITH_LOGIN_INFO,
               Context.MODE_PRIVATE)
           val userLogin = usersSavedLoginData.getString(
               NAME_KEY_WITH_SACE_LOGIN ,
               ""
           )


           return userLogin!!
       }
    }
}