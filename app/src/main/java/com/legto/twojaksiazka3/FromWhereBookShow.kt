package project.legto.twojaksiazka3

class FromWhereBookShow {

      enum class From{
            FAVORITE_BOOK,FIND_BOOK,LIST_CATEGORY_BOOK,READ_BOOKS,RANK_BOOK,ID_BOOK

        }

    companion object{
        var FromWhere=From.FAVORITE_BOOK
        var Position=0
        var Filter=""
        var idBook=0

        fun setFromWherIMustShowBook(from:From,pos:Int){
            FromWhere=from
            Position=pos

        }

        fun setFilterShowBook(fil:String){
            Filter=fil
        }

    }
}