package fr.iutvannes.dual

import org.junit.Test
import fr.iutvannes.dual.model.Algo.VMA
import org.junit.Before

class TestVMA {
    /**
     * L’objet VMA à tester
     */
    private lateinit var vma: VMA

    /**
     * Instancie l’objet avant chaque test
     */
    @Before
    fun setUp() {
        vma = VMA()
    }

    /**
     * Test de la méthode calculer()
     */
    @Test
    fun testCalculer() {
        println()
        println("Test calculer(distance, temps)")
        println("Cas normaux")
        testCasCalculer(vma, 1000.0, 300.0, 12.0, false)   // 1000 m en 300 s → 12 km/h
        testCasCalculer(vma, 500.0, 100.0, 18.0, false)    // 500 m en 100 s → 18 km/h
        println("Cas d’erreur")
        testCasCalculer(vma, 1000.0, 0.0, 0.0, true)       // temps nul
        testCasCalculer(vma, 1000.0, -10.0, 0.0, true)     // temps négatif
        testCasCalculer(null, 1000.0, 300.0, 0.0, true)    // objet nul
    }

    /**
     * Test d’un cas particulier de calculer()
     * @param v l’objet VMA à tester
     * @param distance la distance parcourue
     * @param temps le temps mis
     * @param attendu le résultat attendu en km/h
     * @param casErr vrai si une erreur est attendue
     */
    private fun testCasCalculer(v: VMA?, distance: Double, temps: Double, attendu: Double, casErr: Boolean) {
        try {
            val res = v!!.calculer(distance, temps)
            if (casErr) {
                println("Échec du test (aucune exception alors qu’attendue)")
            } else {
                if (kotlin.math.abs(res - attendu) < 0.001) {
                    println("Test réussi : $res km/h")
                } else {
                    println("Échec du test : résultat obtenu = $res, attendu = $attendu")
                }
            }
        } catch (e: Exception) {
            if (casErr) {
                println("Test réussi (exception capturée : ${e::class.simpleName})")
            } else {
                println("Échec du test (exception inattendue : ${e::class.simpleName})")
            }
        }
    }
}