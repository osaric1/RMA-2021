package ba.etf.rma21.projekat.view

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Kviz
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.viewmodel.OdgovorViewModel
import ba.etf.rma21.projekat.viewmodel.PitanjeKvizViewModel
import ba.etf.rma21.projekat.viewmodel.PredmetIGrupaViewModel
import ba.etf.rma21.projekat.viewmodel.TakeKvizViewModel
import kotlinx.coroutines.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class KvizAdapter(
    private var kvizovi: List<Kviz>,
    private var manager: FragmentManager?,
    private var spinnerTekst: String
): RecyclerView.Adapter<KvizAdapter.KvizViewHolder>(){

    private var predmetIGrupaViewModel = PredmetIGrupaViewModel()
    private var takeKvizViewModel = TakeKvizViewModel()
    private var pitanjeKvizViewModel = PitanjeKvizViewModel()
    private var odgovorViewModel = OdgovorViewModel()

    private var job: Job = Job()
    private var scope = CoroutineScope(Dispatchers.Main + job)

    private var pokusaji: List<KvizTaken>? = listOf()
    private var grupe: List<Grupa> = listOf()
    private var predmet = Predmet(1, "", -1)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KvizViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.element_liste, parent, false)
        return KvizViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: KvizViewHolder, position: Int) {
        holder.predmetName.text = ""
        var ldt =  LocalDate.parse(kvizovi[position].datumPocetka).atStartOfDay()
        var datumPocetkaCalendar = GregorianCalendar.from(
            ZonedDateTime.of(
                ldt,
                ZoneId.systemDefault()
            )
        )
        println(datumPocetkaCalendar)
        var datumKrajaCalendar: Calendar

        if (kvizovi[position].datumKraj != null) {
            ldt = LocalDate.parse(kvizovi[position].datumPocetka).atStartOfDay()
            datumKrajaCalendar = GregorianCalendar.from(
                ZonedDateTime.of(
                    ldt,
                    ZoneId.systemDefault()
                )
            )
        } else datumKrajaCalendar = GregorianCalendar(
            2022,
            datumPocetkaCalendar.get(Calendar.MONTH),
            datumPocetkaCalendar.get(Calendar.DAY_OF_MONTH)
        )


        scope.launch {

            var predmetiStringovi: String =""
            val result = async {
                pokusaji = takeKvizViewModel.getPocetiKvizovi()
                grupe = predmetIGrupaViewModel.getGrupeZaKviz(kvizovi[position].id)!!

                for(grupa in grupe){

                    val tekst = predmetIGrupaViewModel.getPredmetById(grupa.predmetId).toString()

                    if(!predmetiStringovi.contains(tekst)) {
                        predmetiStringovi += tekst +","
                    }
                }
            }

            holder.kvizDuration.text = kvizovi[position].trajanje.toString() + " min"

            holder.kvizTitle.text = kvizovi[position].naziv

            result.await()

            var datumRadaCalendar = GregorianCalendar(1970, 1, 1)


            if (pokusaji != null && !pokusaji!!.isEmpty()) { //nalazimo da li smo vec odgovarali na neki kviz
                val pokusaj = pokusaji!!.find { pokusaj -> pokusaj.KvizId == kvizovi[position].id }

                if(pokusaj != null) {
                    val odgovori = odgovorViewModel.getOdgovoriKviz(kvizovi[position].id)
                    if(odgovori.size == pitanjeKvizViewModel.getPitanja(kvizovi[position].id).size){ //ako su odgovorena sva pitanja i ako je predan kviz
                        holder.kvizPoints.text = pokusaj.osvojeniBodovi.toString()
                        val ldt = LocalDate.parse(pokusaj.datumRada!!).atStartOfDay()
                        datumRadaCalendar = GregorianCalendar.from(
                            ZonedDateTime.of(
                                ldt,
                                ZoneId.systemDefault()
                            )
                        )
                    }
                    else holder.kvizPoints.text = ""
                }
            } else {
                holder.kvizPoints.text = ""
            }

            if (datumRadaCalendar.get(Calendar.YEAR) == 1970
                && datumPocetkaCalendar < Calendar.getInstance()
                && (datumKrajaCalendar > Calendar.getInstance()
                        || datumKrajaCalendar.compareTo(Calendar.getInstance()) == 0)
            ) {

                holder.kvizDate.text =
                    (if (datumKrajaCalendar.get(Calendar.DAY_OF_MONTH) < 10) "0" else "") +
                            datumKrajaCalendar.get(Calendar.DAY_OF_MONTH).toString() + "." +
                            (if (datumKrajaCalendar.get(Calendar.MONTH) + 1 < 10) "0" else "") +
                            (datumKrajaCalendar.get(Calendar.MONTH) + 1).toString() +
                            "." + datumKrajaCalendar.get(Calendar.YEAR).toString() + "."

                holder.imageView.setImageResource(R.drawable.zelena)
                holder.imageView.tag = R.drawable.zelena
            } else if (datumRadaCalendar.get(Calendar.YEAR) == 1970 && datumKrajaCalendar < Calendar.getInstance()) {
                holder.kvizDate.text =
                    (if (datumKrajaCalendar.get(Calendar.DAY_OF_MONTH) < 10) "0" else "") +
                            datumKrajaCalendar.get(Calendar.DAY_OF_MONTH).toString() + "." +
                            (if (datumKrajaCalendar.get(Calendar.MONTH) + 1 < 10) "0" else "") +
                            (datumKrajaCalendar.get(Calendar.MONTH) + 1).toString() +
                            "." + datumKrajaCalendar.get(Calendar.YEAR).toString() + "."

                holder.imageView.setImageResource(R.drawable.crvena)
                holder.imageView.tag = R.drawable.crvena
            } else if (datumRadaCalendar.get(Calendar.YEAR) == 1970 && datumPocetkaCalendar > Calendar.getInstance()) {
                holder.kvizDate.text =
                    if (datumPocetkaCalendar.get(Calendar.DAY_OF_MONTH) < 10) "0" else "" +
                            datumPocetkaCalendar.get(Calendar.DAY_OF_MONTH).toString() + "." +
                            (if (datumPocetkaCalendar.get(Calendar.MONTH) + 1 < 10) "0" else "") +
                            (datumPocetkaCalendar.get(Calendar.MONTH) + 1).toString() +
                            "." + datumPocetkaCalendar.get(Calendar.YEAR).toString() + "."

                holder.imageView.setImageResource(R.drawable.zuta)
                holder.imageView.tag = R.drawable.zuta
            } else {
                holder.kvizDate.text =
                    (if (datumRadaCalendar.get(Calendar.DAY_OF_MONTH) < 10) "0" else "") +
                            datumRadaCalendar.get(Calendar.DAY_OF_MONTH).toString() + "." +
                            (if (datumRadaCalendar.get(Calendar.MONTH) + 1 < 10) "0" else "") +
                            (datumRadaCalendar.get(Calendar.MONTH) + 1).toString() + "." +
                            datumRadaCalendar.get(Calendar.YEAR).toString() + "."

                holder.imageView.setImageResource(R.drawable.plava)
                holder.imageView.tag = R.drawable.plava
            }

            holder.predmetName.text = predmetiStringovi.dropLast(1)

            holder.itemView.setOnClickListener {
                if (spinnerTekst != "Svi kvizovi") {
                    scope.launch {
                        val lista = pitanjeKvizViewModel.getPitanja(kvizovi[position].id)

                        if (lista.isNotEmpty() && holder.imageView.tag != R.drawable.crvena) {
                            val transaction = manager?.beginTransaction()
                            var fragment =
                                manager?.findFragmentByTag("Kviz" + kvizovi[position].naziv)
                            var bundle = Bundle()
                            val fragmentPokusaj = FragmentPokusaj.newInstance(lista)

                            bundle.putString("kvizNaziv", kvizovi[position].naziv)

                            if (holder.imageView.tag == R.drawable.plava) {
                                bundle.putBoolean("uradjenKviz", true)
                            } else if (holder.imageView.tag == R.drawable.zelena) {
                                bundle.putBoolean("uradjenKviz", false)
                            }

                            val grupe = predmetIGrupaViewModel.getGrupeZaKviz(kvizovi[position].id)
                            val predmet = predmetIGrupaViewModel.getPredmetById(grupe!![0].predmetId)
                            val upisanaGrupa = predmetIGrupaViewModel.getUpisaneGrupe()!!
                                .find { grupa -> grupa.predmetId == predmet!!.id }

                            bundle.putString("grupaNaziv", upisanaGrupa!!.naziv)
                            bundle.putInt("idKviza", kvizovi[position].id)

                            if (fragment == null) {
                                fragmentPokusaj.arguments = bundle
                                transaction?.replace(
                                    R.id.container,
                                    fragmentPokusaj,
                                    "Kviz" + kvizovi[position].naziv
                                )
                            } else {
                                fragment.arguments = bundle
                                transaction?.replace(R.id.container, fragment)
                            }
                            transaction?.addToBackStack(null)
                            transaction?.commit()
                        }
                    }
                }

            }

        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateKvizovi(kvizovi: List<Kviz>) {
        this.kvizovi = kvizovi
        Collections.sort(this.kvizovi, Comparator { kviz1, kviz2 ->
            println(kviz1.datumPocetka)
            val df: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

            val datumPocetkaFirst = LocalDate.parse(kviz1.datumPocetka, df).atStartOfDay()
            val datumPocetkaSecond = LocalDate.parse(kviz2.datumPocetka, df).atStartOfDay()
            if (GregorianCalendar.from(
                    ZonedDateTime.of(
                        datumPocetkaFirst,
                        ZoneId.systemDefault()
                    )
                ).timeInMillis > GregorianCalendar.from(
                    ZonedDateTime.of(
                        datumPocetkaSecond,
                        ZoneId.systemDefault()
                    )
                ).timeInMillis
            ) -1
            else if (GregorianCalendar.from(
                    ZonedDateTime.of(
                        datumPocetkaFirst,
                        ZoneId.systemDefault()
                    )
                ).timeInMillis < GregorianCalendar.from(
                    ZonedDateTime.of(
                        datumPocetkaSecond,
                        ZoneId.systemDefault()
                    )
                ).timeInMillis
            ) 1
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

    fun updateSpinner(spinnerTekst: String) {
        this.spinnerTekst = spinnerTekst
    }


}
