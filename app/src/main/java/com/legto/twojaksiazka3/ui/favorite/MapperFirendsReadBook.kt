package project.legto.twojaksiazka3.ui.favorite

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.ResponseDeserializable

class MapperFirendsReadBook : ResponseDeserializable<FriendReads> {
    override fun deserialize(bytes: ByteArray) =
        jacksonObjectMapper().readValue<FriendReads>(bytes)
}

data class FriendReads(val readBookFriend:Int)