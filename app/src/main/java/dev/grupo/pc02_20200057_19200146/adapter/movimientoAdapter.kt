package dev.grupo.pc02_20200057_19200146.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.grupo.pc02_20200057_19200146.R
import dev.grupo.pc02_20200057_19200146.model.movimientoModel

class movimientoAdapter (private var lstmov: List<movimientoModel>) :
    RecyclerView.Adapter<movimientoAdapter.ViewHolder>() {

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvdescripcion = itemView.findViewById<TextView>(R.id.tvdescrip)
            val tvmonto = itemView.findViewById<TextView>(R.id.tvmonto)
            val tvfecha = itemView.findViewById<TextView>(R.id.tvfecha)

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.itemmovimiento, parent, false))
    }

    override fun getItemCount(): Int {
        return lstmov.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lstmov[position]
        holder.tvdescripcion.text = item.descripcion
        holder.tvmonto.text = item.monto.toString()
        holder.tvfecha.text = item.fecha
    }

}
