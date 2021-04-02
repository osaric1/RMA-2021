package ba.etf.rma21.projekat

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.data.models.Kviz
import java.util.*

class KvizAdapter(
        private var kvizovi: List<Kviz>
): RecyclerView.Adapter<KvizAdapter.KvizViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KvizAdapter.KvizViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.element_liste, parent, false)
        return KvizViewHolder(view)
    }

    override fun onBindViewHolder(holder: KvizAdapter.KvizViewHolder, position: Int) {
        var datumPocetkaCalendar = toCalendar(kvizovi[position].datumPocetka)
        var datumKrajaCalendar = toCalendar(kvizovi[position].datumKraj)

        if(kvizovi[position].osvojeniBodovi == null) holder.kvizPoints.text = ""
        else holder.kvizPoints.text = kvizovi[position].osvojeniBodovi.toString()

        holder.kvizDuration.text = kvizovi[position].trajanje.toString() + " min"

//        if(datumKrajaCalendar.before(Calendar.getInstance()) && kvizovi[position].datumRada != null){
//            var datumRadaCalendar = toCalendar(kvizovi[position].datumRada)
//            holder.kvizDate.text = datumRadaCalendar?.get(Calendar.DAY_OF_MONTH).toString() + "." + datumRadaCalendar?.get(Calendar.MONTH).toString() + "." + datumRadaCalendar?.get(Calendar.YEAR).toString() + "."
//            holder.imageView.setImageResource(R.drawable.plava)
//        }
//
//        else if(datumPocetkaCalendar.before(Calendar.getInstance().time)
//                && datumKrajaCalendar.after(Calendar.getInstance().time) && kvizovi[position].datumRada == null){
//
//            holder.kvizDate.text = datumKrajaCalendar?.get(Calendar.DAY_OF_MONTH).toString() + "." + datumKrajaCalendar?.get(Calendar.MONTH).toString() + "." + datumKrajaCalendar?.get(Calendar.YEAR).toString() + "."
//            holder.imageView.setImageResource(R.drawable.zelena)
//        }
//
//        else if(datumPocetkaCalendar.after(Calendar.getInstance())){
//            holder.kvizDate.text = datumPocetkaCalendar?.get(Calendar.DAY_OF_MONTH).toString() + "." + datumPocetkaCalendar?.get(Calendar.MONTH).toString() + "." + datumPocetkaCalendar?.get(Calendar.YEAR).toString() + "."
//            holder.imageView.setImageResource(R.drawable.zuta)
//        }
//
//         if(datumKrajaCalendar.before(Calendar.getInstance()) && kvizovi[position].osvojeniBodovi == null) {
//             holder.kvizDate.text = datumKrajaCalendar?.get(Calendar.DAY_OF_MONTH).toString() + "." + datumKrajaCalendar?.get(Calendar.MONTH).toString() + "." + datumKrajaCalendar?.get(Calendar.YEAR).toString() + "."
//             holder.imageView.setImageResource(R.drawable.crvena)
//         }

        Log.d("DATUM POCETKA", datumPocetkaCalendar.time.toString())
        Log.d("DATUM KRAJA", datumKrajaCalendar.time.toString())
        Log.d("DANAS", Calendar.getInstance().time.toString())
        if(
                kvizovi[position].datumRada == null
                && datumPocetkaCalendar < Calendar.getInstance()
                && (datumKrajaCalendar > Calendar.getInstance()
                        || datumKrajaCalendar.compareTo(Calendar.getInstance()) == 0)
                ){
            holder.kvizDate.text = datumKrajaCalendar.get(Calendar.DAY_OF_MONTH).toString() + "." + datumKrajaCalendar.get(Calendar.MONTH).toString() + "." + datumKrajaCalendar.get(Calendar.YEAR).toString() + "."
            holder.imageView.setImageResource(R.drawable.zelena)
        }

        holder.kvizTitle.text = kvizovi[position].naziv
        holder.predmetName.text = kvizovi[position].nazivPredmeta
    }

    fun updateKvizovi(kvizovi: List<Kviz>) {
        this.kvizovi = kvizovi
        Collections.sort(kvizovi, Comparator { kviz2, kviz1 -> kviz1.datumPocetka.compareTo(kviz2.datumPocetka) })
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int  = kvizovi.size

    inner class KvizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.statusPicture)
        val kvizTitle: TextView = itemView.findViewById(R.id.kvizTitle)
        val kvizDuration: TextView = itemView.findViewById(R.id.kvizDuration)
        val kvizDate: TextView = itemView.findViewById(R.id.kvizDate)
        val kvizPoints: TextView = itemView.findViewById(R.id.kvizPoints)
        val predmetName: TextView = itemView.findViewById(R.id.predmetName)
    }

    fun toCalendar(date: Date): Calendar{
        val cal = Calendar.getInstance()
        cal.time = date
        return cal
    }

}
