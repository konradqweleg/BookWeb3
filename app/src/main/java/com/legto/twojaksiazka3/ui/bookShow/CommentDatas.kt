package com.legto.twojaksiazka3.ui.bookShow

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.ResponseDeserializable

class CommentDatas : ResponseDeserializable<CommentUser> {
    override fun deserialize(bytes: ByteArray) =
        jacksonObjectMapper().readValue<CommentUser>(bytes)
}

data class CommentUser(val isCommentExist:Boolean,val comment:String)