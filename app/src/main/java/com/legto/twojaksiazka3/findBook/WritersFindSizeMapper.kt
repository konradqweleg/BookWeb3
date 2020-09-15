package project.legto.twojaksiazka3.findBook

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.ResponseDeserializable

class WritersFindSizeMapper : ResponseDeserializable<WritersDataFindSize> {
    override fun deserialize(bytes: ByteArray) =
        jacksonObjectMapper().readValue<WritersDataFindSize>(bytes)
}

data class WritersDataFindSize(val size:Int)