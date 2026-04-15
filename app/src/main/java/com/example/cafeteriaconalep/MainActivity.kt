package com.example.cafeteriaconalep

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var rvListaPlatillos: RecyclerView
    lateinit var imprimirBoton: Button
    lateinit var apartadosBoton: Button
    private val BLUETOOTH_PERMISSION_CODE = 100
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        solicitarPermisosBluetooth()

        //VISTAS------------------------------------
        rvListaPlatillos = findViewById(R.id.rvListaPlatillos)
        imprimirBoton = findViewById(R.id.btnImprimir)
        apartadosBoton = findViewById(R.id.btnApartados)

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navigationView)

        //------------------------------------------

        val listaPLatillos = PlatillosProvider.listaPlatillo
        val adapter = PlatillosAdapter(listaPLatillos)
        rvListaPlatillos.layoutManager = LinearLayoutManager(this)
        rvListaPlatillos.adapter = adapter

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Navegación entre pantallas
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> {
                    // Ya estás en MainActivity, solo cierra el menú
                }

                R.id.nav_apartados -> {
                    startActivity(Intent(this, ApartadosActivity::class.java))
                }

                R.id.nav_corte -> {
                    startActivity(Intent(this, CorteVentasActivity::class.java))
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }



        imprimirBoton.setOnClickListener {
            val datos = adapter.obtenerSeleccionados()
            val total = adapter.obtenerTotal() //Obtenemos el costo total del ticket

            if (androidx.core.app.ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.BLUETOOTH_CONNECT
                ) == android.content.pm.PackageManager.PERMISSION_GRANTED
            ) {
                val bluetoothManager =
                    getSystemService(android.bluetooth.BluetoothManager::class.java)
                bluetoothManager?.adapter?.bondedDevices?.forEach {
                    android.util.Log.d("BT_DEVICES", "Nombre: ${it.name} | MAC: ${it.address}")
                }
            } else {
                android.util.Log.d("BT_DEVICES", "Sin permiso Bluetooth")
            }


            println(datos)
            // Convertir los platillos a líneas de texto para el ticket
            val lineas = datos.map { platillo ->
                "x${platillo.cantidad}   ${platillo.nombrePlatillo}   $${platillo.precioPlatillo}"
            }

            // Imprimir en hilo secundario para no bloquear la UI
            Thread {
                val exito = ImpresoraBluetooth.imprimir(
                    context = this,
                    nombreImpresora = "BlueTooth Printer", // parte del nombre del dispositivo BT
                    lineas = lineas,
                    total = total
                )

                runOnUiThread {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Imprimir")
                    builder.setMessage(
                        if (exito) "La lista se está imprimiendo ✅"
                        else "❌ No se pudo conectar a la impresora. ¿Está encendida y emparejada?"
                    )
                    builder.setPositiveButton("Aceptar") { dialog, _ ->
                        if (exito) {
                            adapter.limpiarSeleccionados()
                            adapter.notifyDataSetChanged()
                        }
                        dialog.cancel()
                    }
                    builder.show()
                }
            }.start()


            /*val builder = AlertDialog.Builder(this)
            builder.setTitle("Imprimir")
            builder.setMessage("La lista se esta imprimiendo")
            builder.setPositiveButton("Aceptar") { dialog, which ->
                // Acción al presionar Aceptar (puede estar vacía)
                println(adapter.obtenerTotal()) //Obtenemos el costo total del ticket
                adapter.limpiarSeleccionados()
                dialog.cancel()
            }
            builder.setNegativeButton("Cancelar") { dialog, which ->
                // Acción al presionar Cancelar
            }
            builder.show()*/
            //Esto funciona para agregar un platillo a la lista
            //PlatillosProvider.listaPlatillos.add(Platillos("Agregado extra", 55, false))
            //adapter.notifyDataSetChanged()
        }

        apartadosBoton.setOnClickListener {
            val intent = Intent(this, ApartadosActivity::class.java)
            startActivity(intent)
        }

    }

    private fun solicitarPermisosBluetooth() {
        if (androidx.core.app.ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.BLUETOOTH_CONNECT
            ) != android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            androidx.core.app.ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.BLUETOOTH_CONNECT,
                    android.Manifest.permission.BLUETOOTH_SCAN
                ),
                BLUETOOTH_PERMISSION_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == BLUETOOTH_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                android.util.Log.d("BT_DEVICES", "Permiso concedido")
            } else {
                android.util.Log.d("BT_DEVICES", "Permiso denegado")
            }
        }
    }

    // Para que el botón hamburguesa funcione
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}