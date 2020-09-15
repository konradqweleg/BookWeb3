package project.legto.twojaksiazka3.utility

public class UserData() {
companion object {
    var idUser:Int=0
    var mail:String=""
    var password:String=""
    var login:String=""
    var ifLoginFb:Boolean=false
    var idFacebook:String=""

    override fun toString(): String {
        return super.toString()+ idUser.toString()+" , "+mail+" , "+ ifLoginFb.toString()+" , "+ idFacebook
    }
    public fun saveUserId(idUser: Int) {
        this.idUser = idUser
    }

    public fun saveMail(mail: String) {
        this.mail = mail
    }

    public fun savePassword(password: String) {
        this.password = password
    }

    public  fun saveLogin(login: String) {
        this.login = login
    }

    fun saveIdfacebook(id:String){
        idFacebook=id
    }

    fun setIfLoginFacebook(ifFb: Boolean) {
        this.ifLoginFb = ifFb
    }
}


}