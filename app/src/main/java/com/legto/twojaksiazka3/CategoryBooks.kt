package project.legto.twojaksiazka3

class CategoryBooks {
    companion object{
        var CategoryChoice:CategoryBook=CategoryBook.WSZYSTKIE

        enum class CategoryBook(public var nameBookCategory:String){
            HORROR("Horror"),THRILLER("Thriller"),FANTASTYKA("Fantastyka"),KRYMINAL("Kryminał"),DRAMAT("Dramat"),AKCJA("Akcja"),SCIENCEFICTION("SCI-FI"),ROMANS("ROMANS"),POLSKA("POLSKA"),WSZYSTKIE("WSZYSTKIE")



        }

    }
}