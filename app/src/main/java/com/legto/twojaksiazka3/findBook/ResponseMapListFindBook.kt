package project.legto.twojaksiazka3.findBook

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.ResponseDeserializable

class ResponseMapListFindBook : ResponseDeserializable<FindBook> {
    override fun deserialize(bytes: ByteArray) =
        jacksonObjectMapper().readValue<FindBook>(bytes)
}

data class FindBook(val idBook:Int,val author:String,val nameBook:String,val sur:String)