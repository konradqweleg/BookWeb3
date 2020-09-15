package project.legto.twojaksiazka3.findBook

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.ResponseDeserializable

class ResponseHowMany : ResponseDeserializable<Find> {
    override fun deserialize(bytes: ByteArray) =
        jacksonObjectMapper().readValue<Find>(bytes)
}

data class Find(val howMany:Int)