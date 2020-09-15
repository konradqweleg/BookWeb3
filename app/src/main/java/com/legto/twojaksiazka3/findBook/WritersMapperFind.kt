package project.legto.twojaksiazka3.findBook

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.ResponseDeserializable

class WritersMapperFind : ResponseDeserializable<WritersDataFind> {
    override fun deserialize(bytes: ByteArray) =
        jacksonObjectMapper().readValue<WritersDataFind>(bytes)
}

data class WritersDataFind(val name:String,val surname:String,val idWriters:Int)