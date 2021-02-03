package com.legto.twojaksiazka3.ui.home



import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.ResponseDeserializable


class MarkData : ResponseDeserializable<Mark> {
    override fun deserialize(bytes: ByteArray) =
        jacksonObjectMapper().readValue<Mark>(bytes)
}

data class Mark(val mark: Int)