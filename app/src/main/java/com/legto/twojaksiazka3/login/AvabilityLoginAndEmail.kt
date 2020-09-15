package project.legto.twojaksiazka3.login

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.ResponseDeserializable

class AvaibilityLoginAndEmail : ResponseDeserializable<AccessLoginEmail> {
    override fun deserialize(bytes: ByteArray) =
        jacksonObjectMapper().readValue<AccessLoginEmail>(bytes)
}

data class AccessLoginEmail(val isEmailAvailability: Boolean,val isLoginAvailability:Boolean)