package project.legto.twojaksiazka3.login

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.ResponseDeserializable


class MailInDatabase : ResponseDeserializable<Mail> {
    override fun deserialize(bytes: ByteArray) =
        jacksonObjectMapper().readValue<Mail>(bytes)
}

data class Mail(val emailInDatabase: Boolean)