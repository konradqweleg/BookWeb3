package project.legto.twojaksiazka3.ui.home

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.ResponseDeserializable


class FriendLastActivityListMapper : ResponseDeserializable<ActivityConfig> {
    override fun deserialize(bytes: ByteArray) =
        jacksonObjectMapper().readValue<ActivityConfig>(bytes)





}

data class ActivityConfig(
 public   @JsonProperty("res")val res: List<FriendActivity> = emptyList()
)



data class FriendActivity(val textActivity: String,val Login:String,val nameBook:String)