package project.legto.twojaksiazka3.ui.bookShow

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.ResponseDeserializable


class IsUserVotedBook : ResponseDeserializable<Voted> {
    override fun deserialize(bytes: ByteArray) =
        jacksonObjectMapper().readValue<Voted>(bytes)
}

data class Voted(val ifUserGiveMark: Boolean)