package project.legto.twojaksiazka3.ui.favorite

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.ResponseDeserializable

class PositionRankFriendsMapper : ResponseDeserializable<FriendPosition> {
    override fun deserialize(bytes: ByteArray) =
        jacksonObjectMapper().readValue<FriendPosition>(bytes)
}

data class FriendPosition(val positionRank:Int)