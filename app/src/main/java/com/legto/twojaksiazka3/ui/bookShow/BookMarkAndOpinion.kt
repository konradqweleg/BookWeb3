package project.legto.twojaksiazka3.ui.bookShow

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.ResponseDeserializable

class BookMarkAndOpinion : ResponseDeserializable<Mark> {
    override fun deserialize(bytes: ByteArray) =
        jacksonObjectMapper().readValue<Mark>(bytes)
}

data class Mark(val mark: Float,val opinion:String)