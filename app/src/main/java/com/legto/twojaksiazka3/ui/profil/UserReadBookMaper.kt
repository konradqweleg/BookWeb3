package project.legto.twojaksiazka3.ui.profil

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.ResponseDeserializable

class UserReadBookMaper : ResponseDeserializable<userStatistics> {
    override fun deserialize(bytes: ByteArray) =
        jacksonObjectMapper().readValue<userStatistics>(bytes)
}

data class userStatistics(val login:String,val  readPage:Int,val  readBook:Int,val avgMark:Double)