package project.legto.twojaksiazka3.ui.bookShow

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.ResponseDeserializable


class CommentData : ResponseDeserializable<Comment> {
    override fun deserialize(bytes: ByteArray) =
        jacksonObjectMapper().readValue<Comment>(bytes)
}

data class Comment(val userName: String,val userMark:String,val userOpinion:String,val howManyComments:String)