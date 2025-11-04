package fr.iutvannes.dual.infrastructure

import java.net.NetworkInterface
import java.net.Inet4Address

object Utils {

    /**
     * Renvoie l’adresse IP locale de la tablette
     * Retourne null si aucune connexion réseau active.
     */
    fun getLocalIpAddress(): String? {
        try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            for (iface in interfaces) {
                if (!iface.isUp || iface.isLoopback) continue
                for (addr in iface.inetAddresses) {
                    if (addr is Inet4Address && !addr.isLoopbackAddress) {
                        return addr.hostAddress
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}
