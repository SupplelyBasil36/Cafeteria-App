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
    fun imprimir(
        context: Context,
        nombreImpresora: String,
        lineas: List<String>,
        total: Double
    ): Boolean {

        val bluetoothManager: BluetoothManager =
            context.getSystemService(BluetoothManager::class.java)
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

    private fun buildTicket(lineas: List<String>, total: Double): ByteArray {
        val init = byteArrayOf(0x1B, 0x40) // inicializar
        val centrar = byteArrayOf(0x1B, 0x61, 0x01) // centrar
        val izquierda = byteArrayOf(0x1B, 0x61, 0x00) // izquierda

        val header = StringBuilder()
        header.append("========================\n")
        header.append("       MI CAFETERIA     \n")
        header.append("========================\n\n")

        val cuerpo = StringBuilder()
        lineas.forEach { linea ->
            cuerpo.append("$linea\n")
        }
        cuerpo.append("------------------------\n")
        cuerpo.append("TOTAL: $${"%.2f".format(total.toDouble())}\n")
        cuerpo.append("========================\n")
        cuerpo.append("\n\n\n")

        val corte = byteArrayOf(0x1D, 0x56, 0x42, 0x00)

        // Header centrado, cuerpo alineado a la izquierda
        return init +
                centrar + header.toString().toByteArray(Charsets.UTF_8) +
                izquierda + cuerpo.toString().toByteArray(Charsets.UTF_8) +
                corte
    }
}