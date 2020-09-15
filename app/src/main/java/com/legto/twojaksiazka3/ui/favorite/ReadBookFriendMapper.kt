package project.legto.twojaksiazka3.ui.favorite

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.ResponseDeserializable

class ReadBookFriendMapper : ResponseDeserializable<friendRead> {
    override fun deserialize(bytes: ByteArray) =
        jacksonObjectMapper().readValue<friendRead>(bytes)
}

data class friendRead(val idBook:Int,val  nameBook:String,val  writersName:String,val writersSurname:String,val markBook:Int)