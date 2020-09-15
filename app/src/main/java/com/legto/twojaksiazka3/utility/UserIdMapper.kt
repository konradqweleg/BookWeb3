package project.legto.twojaksiazka3.utility

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.ResponseDeserializable


    class UserIdMapper : ResponseDeserializable<userId> {
        override fun deserialize(bytes: ByteArray) =
            jacksonObjectMapper().readValue<userId>(bytes)
    }

    data class userId(val userId:Int)
