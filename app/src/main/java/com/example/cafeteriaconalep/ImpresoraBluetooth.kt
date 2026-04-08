package com.example.cafeteriaconalep

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.pm.PackageManager
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import java.io.OutputStream
import java.util.UUID

object ImpresoraBluetooth {

    // UUID estándar para SPP (Serial Port Profile) - funciona con la mayoría de impresoras BT
    private val UUID_SPP: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    fun imprimir(context: Context, nombreImpresora: String, lineas: List<String>, total: Int): Boolean {

        val bluetoothManager: BluetoothManager = context.getSystemService(BluetoothManager::class.java)
        val bluetooth: BluetoothAdapter? = bluetoothManager.getAdapter()

        // Verificar permiso antes de continuar
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false // sin permiso, no continuar
        }

        // Busca la impresora ya emparejada por nombre
        val dispositivo = bluetooth?.bondedDevices
            ?.firstOrNull { it.name.contains(nombreImpresora, ignoreCase = true) }
            ?: return false

        return try {
            val socket: BluetoothSocket = dispositivo
                .createRfcommSocketToServiceRecord(UUID_SPP)
            socket.connect()

            val output: OutputStream = socket.outputStream
            output.write(buildTicket(lineas, total))
            output.flush()

            Thread.sleep(500) // pequeña pausa para que termine de imprimir
            socket.close()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun buildTicket(lineas: List<String>, total: Int): ByteArray {
        val sb = StringBuilder()

        // Inicializar impresora (ESC @)
        val init = byteArrayOf(0x1B, 0x40)

        // Centrar texto (ESC a 1)
        val centrar = byteArrayOf(0x1B, 0x61, 0x01)
        // Alinear izquierda (ESC a 0)
        val izquierda = byteArrayOf(0x1B, 0x61, 0x00)

        sb.append("========================\n")
        sb.append("       MI RESTAURANTE   \n")
        sb.append("========================\n")
        sb.append("\n")

        lineas.forEach { linea ->
            sb.append("$linea\n")
        }

        sb.append("------------------------\n")
        sb.append("TOTAL: $${"%.2f".format(total)}\n")
        sb.append("========================\n")
        sb.append("\n\n\n") // líneas de corte/avance

        // Corte de papel (si la impresora lo soporta): GS V 66 0
        val corte = byteArrayOf(0x1D, 0x56, 0x42, 0x00)

        return init + centrar + sb.toString().toByteArray(Charsets.UTF_8) + corte
    }
}