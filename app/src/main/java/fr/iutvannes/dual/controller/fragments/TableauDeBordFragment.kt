package fr.iutvannes.dual.controller.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import fr.iutvannes.dual.MainActivity
import fr.iutvannes.dual.R
import fr.iutvannes.dual.controller.viewmodel.SessionViewModel

/**
 * Cette classe représente le fragment du tableau de bord.
 */
class TableauDeBordFragment : Fragment(R.layout.fragment_tableau_de_bord) {

    // On récupère le ViewModel partagé avec l'Activity (MainActivity)
    private val sessionViewModel: SessionViewModel by activityViewModels()

    /**
     * Cette fonction est appelée lorsque la vue du fragment est créée.
     * Elle initialise les interactions avec les vues.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Création du listener pour le bouton de séance
        val boutonSeance = view.findViewById<Button>(R.id.boutonSeance)
        boutonSeance.setOnClickListener {
            // Lancement de la session via le ViewModel
            // Le contexte de l'Activity est passé au ViewModel pour démarrer le serveur
            sessionViewModel.startSession(requireContext())
        }
    }

    private fun genererQRCode(text: String): Bitmap {
        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 512, 512)
        val bmp = Bitmap.createBitmap(512, 512, Bitmap.Config.RGB_565)
        for (x in 0 until 512) {
            for (y in 0 until 512) {
                bmp.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
            }
        }
        return bmp
    }
}
