package project.legto.twojaksiazka3.ui.rank

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.ResponseDeserializable

class RankAuthorsMapper : ResponseDeserializable<authors> {
    override fun deserialize(bytes: ByteArray) =
        jacksonObjectMapper().readValue<authors>(bytes)
}

data class authors(val name:String,val  surname:String,val  markAuthors:Float,val idWriters:Int)