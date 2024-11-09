package dev.grupo.pc02_20200057_19200146.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dev.grupo.pc02_20200057_19200146.R

class RegistroFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_registro, container, false)

        val etFecha: EditText = view.findViewById(R.id.etFecha)
        val etDescripcion: EditText = view.findViewById(R.id.etDescripcion)
        val etMonto: EditText = view.findViewById(R.id.etMonto)
        val btnGuardar: Button = view.findViewById(R.id.btnGuardarRegistro)
        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.uid
        val db = FirebaseFirestore.getInstance()

        btnGuardar.setOnClickListener {
            val fecha = etFecha.text.toString()
            val descripcion = etDescripcion.text.toString()
            val monto = etMonto.text.toString().toIntOrNull()

            // Validar que todos los campos estén llenos y que monto sea un número válido
            if (fecha.isEmpty() || descripcion.isEmpty() || monto == null) {
                Toast.makeText(context, "Por favor completa todos los campos correctamente", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            userId?.let { id ->
                val transaccionesRef = db.collection("UsuariosTransac").document(id).collection("transacciones")

                val transaccionData = hashMapOf(
                    "fecha" to fecha,
                    "descripcion" to descripcion,
                    "monto" to monto
                )

                transaccionesRef.add(transaccionData)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Transacción guardada con éxito", Toast.LENGTH_SHORT).show()
                        // Limpiar campos después de guardar
                        etFecha.text.clear()
                        etDescripcion.text.clear()
                        etMonto.text.clear()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, "Error al guardar: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } ?: run {
                Toast.makeText(context, "Error: Usuario no autenticado", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
