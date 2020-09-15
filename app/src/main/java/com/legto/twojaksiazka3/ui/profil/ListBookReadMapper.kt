package project.legto.twojaksiazka3.ui.profil

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.ResponseDeserializable


class ListBookReadMapper : ResponseDeserializable<userread> {
    override fun deserialize(bytes: ByteArray) =
        jacksonObjectMapper().readValue<userread>(bytes)
}

data class userread(val nameBook:String,val  nameWriters:String,val  surnameWriters:String,val userMark:Int,val IdBook:Int)