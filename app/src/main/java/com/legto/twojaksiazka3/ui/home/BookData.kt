package project.legto.twojaksiazka3.ui.home

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.kittinunf.fuel.core.ResponseDeserializable


class BookData : ResponseDeserializable<Book> {
    override fun deserialize(bytes: ByteArray) =
        jacksonObjectMapper().readValue<Book>(bytes)
}

data class Book(val titleBook: String,val idWriters:Int,val nameAuthor:String,val pageBook:String,val yearPublish:String,val descriptionBook:String,val countMark:String,val markBook:String,val gentreBook:String,val idBook:Int,val gentre_1:String,val gentre_2:String,val gentre_3:String,val gentre_4:String,val gentre_5:String,val gentre_6:String,val gentre_7:String,val gentre_8:String)