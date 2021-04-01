package ba.etf.rma21.projekat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.data.models.Kviz
import org.w3c.dom.Text

class KvizAdapter(private var kvizovi: List<Kviz>): RecyclerView.Adapter<KvizAdapter.KvizViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KvizAdapter.KvizViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.element_liste, parent, false)
        return KvizViewHolder(view)
    }

    override fun onBindViewHolder(holder: KvizAdapter.KvizViewHolder, position: Int) {
        holder.kvizTitle.text = kvizovi[position].naziv
        holder.kvizDate.text = kvizovi[position].datumPocetka.toString()
        holder.kvizPoints.text = kvizovi[position].osvojeniBodovi.toString()
        holder.kvizDuration.text = kvizovi[position].trajanje.toString()
        holder.imageView.setImageResource(R.drawable.crvena)
    }

    fun updateKvizovi(kvizovi: List<Kviz>) {
        this.kvizovi = kvizovi
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int  = kvizovi.size

    inner class KvizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.statusPicture)
        val kvizTitle: TextView = itemView.findViewById(R.id.kvizTitle)
        val kvizDuration: TextView = itemView.findViewById(R.id.kvizDuration)
        val kvizDate: TextView = itemView.findViewById(R.id.kvizDate)
        val kvizPoints: TextView = itemView.findViewById(R.id.kvizPoints)
    }

}
