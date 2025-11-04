package fr.iutvannes.dual.model.Algo

class VMA {

    fun calculer(distance: Double, temps: Double): Double {
        if(temps <= 0.0) {
            throw IllegalArgumentException("Le temps doit être supérieur à zéro")
        }
        return distance / temps * 3.6
    }
}