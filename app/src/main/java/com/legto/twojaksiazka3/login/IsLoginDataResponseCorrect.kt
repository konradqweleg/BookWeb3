package project.legto.twojaksiazka3.login

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.ResponseDeserializable

class IsLoginDataResponseCorrect : ResponseDeserializable<LoginIsCorrectData> {
    override fun deserialize(bytes: ByteArray) =
        jacksonObjectMapper().readValue<LoginIsCorrectData>(bytes)
}

data class LoginIsCorrectData(val isAuthenticationSucceeded: Boolean,val messageAuthentication:String)