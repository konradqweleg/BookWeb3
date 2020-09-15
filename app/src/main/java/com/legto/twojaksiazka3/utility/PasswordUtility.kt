package project.legto.twojaksiazka3.utility

import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageButton
import com.legto.twojaksiazka3.R


class PasswordUtility {
   fun whenClickIconShowPasswordInEditText(button:ImageButton,passwordField: EditText){
       ManagedPasswordShowInEditText(
           button,
           passwordField
       )

   }


    class ManagedPasswordShowInEditText(button:ImageButton,passwordField: EditText){
        var isPasswordVisible=false
       init {
           button.setOnClickListener {


               if (isPasswordVisible) {
                   isPasswordVisible = !isPasswordVisible
                   passwordField.transformationMethod = PasswordTransformationMethod()
                   passwordField.setSelection(passwordField.text.length)
                   if (passwordField.text.isNotEmpty()) {
                       button.setImageResource(R.drawable.hide_password)
                   }
               } else {
                   isPasswordVisible = !isPasswordVisible
                   passwordField.transformationMethod = null
                   passwordField.setSelection(passwordField.text.length)
                   if (passwordField.text.isNotEmpty()) {
                       button.setImageResource(R.drawable.show_password)
                   }
               }


           }
       }


    }


}