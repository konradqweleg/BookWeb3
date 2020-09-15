package project.legto.twojaksiazka3.ui.WritersProfile

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.ResponseDeserializable

class WriteBookByAuthorsMapper : ResponseDeserializable<WritersWriteData> {
    override fun deserialize(bytes: ByteArray) =
        jacksonObjectMapper().readValue<WritersWriteData>(bytes)
}

data class WritersWriteData(val nameBook:String,val markBook:Float,val idBook:Int)