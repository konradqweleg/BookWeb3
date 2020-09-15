package project.legto.twojaksiazka3.ui.favorite

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.ResponseDeserializable

class MapperSizeList : ResponseDeserializable<sizeList> {
    override fun deserialize(bytes: ByteArray) =
        jacksonObjectMapper().readValue<sizeList>(bytes)
}

data class sizeList(val size:Int)