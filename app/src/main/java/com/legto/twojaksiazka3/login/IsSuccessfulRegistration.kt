package project.legto.twojaksiazka3.login

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.ResponseDeserializable

class IsEmailSend : ResponseDeserializable<EmailSend> {
    override fun deserialize(bytes: ByteArray) =
        jacksonObjectMapper().readValue<EmailSend>(bytes)
}

data class EmailSend(val emailSend: Boolean)