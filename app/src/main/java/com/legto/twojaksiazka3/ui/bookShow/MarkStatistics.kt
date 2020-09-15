package project.legto.twojaksiazka3.ui.bookShow

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.ResponseDeserializable

class MarkStatistics : ResponseDeserializable<MarkStat> {
    override fun deserialize(bytes: ByteArray) =
        jacksonObjectMapper().readValue<MarkStat>(bytes)
}

data class MarkStat(val mark_1:String,val  mark_2:String,val  mark_3:String,val  mark_4:String,val  mark_5:String,
                    val  mark_6:String,val  mark_7:String,val  mark_8:String,val  mark_9:String,val  mark_10:String)