package dev.grupo.pc02_20200057_19200146.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import dev.grupo.pc02_20200057_19200146.R
import dev.grupo.pc02_20200057_19200146.adapter.movimientoAdapter
import dev.grupo.pc02_20200057_19200146.databinding.FragmentHomeBinding
import dev.grupo.pc02_20200057_19200146.model.movimientoModel

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: movimientoAdapter
    private lateinit var totalMontoTextView: TextView
    private val movimientosList = mutableListOf<movimientoModel>()
    private val db = FirebaseFirestore.getInstance()
    private var balanceTotal = 0.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.rvtotal)
        totalMontoTextView = view.findViewById(R.id.monto)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = movimientoAdapter(movimientosList)
        recyclerView.adapter = adapter

        cargarmovimiento()

        return view
    }

    private fun cargarmovimiento() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val userID = user.uid
            db.collection("UsuarioTransac").document(userID).collection("transacciones")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val descripcion = document.getString("descripcion") ?: ""
                        val monto = document.getDouble("monto") ?: 0.0
                        val fecha = document.getString("fecha") ?: ""
                        val movimiento = movimientoModel(descripcion, monto, fecha)
                        movimientosList.add(movimiento)
                        balanceTotal += monto
                    }
                    adapter.notifyDataSetChanged()
                }
        }
    }
}