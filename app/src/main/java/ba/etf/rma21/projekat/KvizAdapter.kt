package ba.etf.rma21.projekat

import android.annotation.SuppressLint
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KvizViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.element_liste, parent, false)
        return KvizViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: KvizViewHolder, position: Int) {
        var datumPocetkaCalendar = toCalendar(kvizovi[position].datumPocetka)
        var datumKrajaCalendar = toCalendar(kvizovi[position].datumKraj)
        var datumRadaCalendar = toCalendar(kvizovi[position].datumRada)

        if(kvizovi[position].osvojeniBodovi == null) holder.kvizPoints.text = ""
        else holder.kvizPoints.text = kvizovi[position].osvojeniBodovi.toString()

        holder.kvizDuration.text = kvizovi[position].trajanje.toString() + " min"

        if(     datumRadaCalendar.get(Calendar.YEAR)  == 1970
                && datumPocetkaCalendar < Calendar.getInstance()
                && (datumKrajaCalendar > Calendar.getInstance()
                || datumKrajaCalendar.compareTo(Calendar.getInstance()) == 0)) {

            holder.kvizDate.text =  (if(datumKrajaCalendar.get(Calendar.DAY_OF_MONTH) < 10) "0"  else "") +
                                    datumKrajaCalendar.get(Calendar.DAY_OF_MONTH).toString() + "." +
                                    (if(datumKrajaCalendar.get(Calendar.MONTH) + 1 < 10) "0"  else "") +
                                    (datumKrajaCalendar.get(Calendar.MONTH)+1).toString() +
                                    "." + datumKrajaCalendar.get(Calendar.YEAR).toString() + "."

            holder.imageView.setImageResource(R.drawable.zelena)
        }

        else if(datumRadaCalendar.get(Calendar.YEAR) == 1970 && datumKrajaCalendar < Calendar.getInstance()){
            holder.kvizDate.text =  (if(datumKrajaCalendar.get(Calendar.DAY_OF_MONTH) < 10) "0"  else "") +
                                    datumKrajaCalendar.get(Calendar.DAY_OF_MONTH).toString() + "." +
                                    (if(datumKrajaCalendar.get(Calendar.MONTH) + 1 < 10) "0"  else "") +
                                    (datumKrajaCalendar.get(Calendar.MONTH)+1).toString() +
                                    "." + datumKrajaCalendar.get(Calendar.YEAR).toString() + "."

            holder.imageView.setImageResource(R.drawable.crvena)
        }

        else if(datumRadaCalendar.get(Calendar.YEAR) == 1970 && datumPocetkaCalendar > Calendar.getInstance()){
            holder.kvizDate.text =  if(datumPocetkaCalendar.get(Calendar.DAY_OF_MONTH) < 10) "0" else "" +
                                    datumPocetkaCalendar.get(Calendar.DAY_OF_MONTH).toString() + "." +
                                    (if(datumPocetkaCalendar.get(Calendar.MONTH) + 1 < 10) "0"  else "") +
                                    (datumPocetkaCalendar.get(Calendar.MONTH)+1).toString()  +
                                    "." + datumPocetkaCalendar.get(Calendar.YEAR).toString() + "."

            holder.imageView.setImageResource(R.drawable.zuta)
        }

        else{
            holder.kvizDate.text =  (if(datumRadaCalendar.get(Calendar.DAY_OF_MONTH) < 10) "0" else "") +
                                    datumRadaCalendar.get(Calendar.DAY_OF_MONTH).toString() + "." +
                                    (if(datumRadaCalendar.get(Calendar.MONTH) + 1 < 10) "0"  else "") +
                                    (datumRadaCalendar.get(Calendar.MONTH)+1).toString() + "." +
                                    datumRadaCalendar.get(Calendar.YEAR).toString() + "."

            holder.imageView.setImageResource(R.drawable.plava)
        }

        holder.kvizTitle.text = kvizovi[position].naziv
        holder.predmetName.text = kvizovi[position].nazivPredmeta
    }

    fun updateKvizovi(kvizovi: List<Kviz>) {
        this.kvizovi = kvizovi
        Collections.sort(this.kvizovi, Comparator { kviz1, kviz2 ->
            if(toCalendar(kviz1.datumPocetka).timeInMillis  > toCalendar(kviz2.datumPocetka).timeInMillis) -1
            else if(toCalendar(kviz1.datumPocetka).timeInMillis  < toCalendar(kviz2.datumPocetka).timeInMillis ) 1
            else 0
        })

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
