package project.legto.twojaksiazka3.ui.WritersProfile

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.ResponseDeserializable

class WritersMapper : ResponseDeserializable<WritersData> {
    override fun deserialize(bytes: ByteArray) =
        jacksonObjectMapper().readValue<WritersData>(bytes)
}

data class WritersData(val nameAuthors:String,val surnameAuthors:String,val markAuthors:Float,val howManyBookWriteAuthors:Int
,val birthDateAuthors:String,val ifTopAuthors:Boolean,val ifTopBookAuthors:Boolean,val writersInfo:String,val writersId:Int)