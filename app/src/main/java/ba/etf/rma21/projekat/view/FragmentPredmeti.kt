package ba.etf.rma21.projekat.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.AppDatabase
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.AccountRepository
import ba.etf.rma21.projekat.viewmodel.*
import kotlinx.coroutines.*

class FragmentPredmeti() : Fragment() {
    private lateinit var  odabirGodine: Spinner
    private lateinit var  odabirPredmeta: Spinner
    private lateinit var  odabirGrupe: Spinner
    private lateinit var  dodajPredmet: Button

    private var predmetIGrupaViewModel  = PredmetIGrupaViewModel()
    private var grupaViewModel  = GrupaViewModel()
    private var kvizViewModel = KvizViewModel()
    private var pitanjeKvizViewModel = PitanjeKvizViewModel()

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    companion object{
        private var presetGodina: Int = -1
        private var presetGrupa: Int = -1
        private var presetPredmet: Int = -1
        private var zavrsenUpis: Boolean = false
        fun newInstance(): FragmentPredmeti = FragmentPredmeti()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.predmeti_fragment, container, false)
        odabirGodine = view.findViewById(R.id.odabirGodina)
        odabirPredmeta = view.findViewById(R.id.odabirPredmet)
        odabirGrupe = view.findViewById(R.id.odabirGrupa)
        dodajPredmet = view.findViewById(R.id.dodajPredmetDugme)



        dodajPredmet.setOnClickListener {

            if(odabirPredmeta.selectedItem != null && odabirGrupe.selectedItem != null && odabirPredmeta.selectedItem.toString() != "-Empty-" && odabirPredmeta.selectedItem.toString() != "" &&
                odabirGrupe.selectedItem.toString() != "-Empty-" && odabirGrupe.selectedItem.toString() != "") {

                zavrsenUpis = true
                val bundle = Bundle()
                bundle.putString("godina", odabirGodine.selectedItem.toString())
                bundle.putString("grupa", odabirGrupe.selectedItem.toString())
                bundle.putString("predmet", odabirPredmeta.selectedItem.toString())

                val fragmentPoruka = FragmentPoruka.newInstance()
                fragmentPoruka.arguments = bundleOf(Pair("poruka", "Uspješno ste upisani u grupu " + odabirGrupe.selectedItem.toString() + " predmeta " + odabirPredmeta.selectedItem.toString() + "!"))

                val godina = odabirGodine.selectedItem.toString()
                val grupa = odabirGrupe.selectedItem.toString()
                val predmet = odabirPredmeta.selectedItem.toString()
                var result: Deferred<Unit>
                scope.launch {

                    result = async {
                        val grupe = predmetIGrupaViewModel.getGrupe()
                        val pronadjenaGrupa = grupe.find { grupa1 -> grupa1.naziv == grupa }
                        predmetIGrupaViewModel.upisiUGrupu(pronadjenaGrupa!!.id)


                        val upisaniKvizovi = kvizViewModel.getUpisani()
                        val pronadjenPredmet = predmetIGrupaViewModel.getPredmetById(pronadjenaGrupa.predmetId)
                        val db = AppDatabase.getInstance(requireContext())

                        if (db.grupaDao().checkDuplicate(pronadjenaGrupa.id) == null)
                            db.grupaDao().insert(pronadjenaGrupa)

                        for (upisaniKviz in upisaniKvizovi) {
                            val novaPitanja = pitanjeKvizViewModel.getPitanja(upisaniKviz.id)
                            novaPitanja.forEach { novoPitanje -> novoPitanje.KvizId = upisaniKviz.id }

                            if (db.kvizDao().checkDuplicate(upisaniKviz.id) == null) {
                                db.kvizDao().insert(upisaniKviz)
                                for(novoPitanje in novaPitanja){
                                    if(db.pitanjeDao().checkDuplicate(novoPitanje.id) == null)
                                        db.pitanjeDao().insert(novoPitanje)
                                }
                            }
                        }


                        if (db.predmetDao().checkDuplicate(pronadjenPredmet!!.id) == null)
                            db.predmetDao().insert(pronadjenPredmet)

                    }
                    result.await()
                    val transaction = activity?.supportFragmentManager?.beginTransaction()
                    transaction?.replace(R.id.container, fragmentPoruka)
                    transaction?.addToBackStack(null)
                    transaction?.commit()
                }
            }

        }

        ArrayAdapter.createFromResource(
            view.context,
            R.array.godine,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            odabirGodine.adapter = adapter

        }
        if(presetGodina >= 0){
            odabirGodine.setSelection(presetGodina)
        }

        odabirGodine.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long)
            {
                //treba izvuci slobodne
                scope.launch{
                    val zauzetiPredmetiId = predmetIGrupaViewModel.getUpisaneGrupe()!!.map { grupa ->  grupa.predmetId }.toList()
                    var slobodniPredmeti: MutableList<Predmet> = predmetIGrupaViewModel.getPredmeti().filter { predmet -> predmet.godina == Integer.parseInt(odabirGodine.selectedItem.toString())  } as MutableList<Predmet>

                    slobodniPredmeti.removeAll { predmet -> zauzetiPredmetiId.contains(predmet.id)  }

                    var stringovi = slobodniPredmeti.map { predmet -> predmet.toString()  }.toMutableList()

                    if(slobodniPredmeti.isEmpty()){
                        stringovi = listOf("-Empty-").toMutableList()
                    }

                    val dataAdapter: ArrayAdapter<String> = ArrayAdapter<String>(view.context, android.R.layout.simple_spinner_item, stringovi)
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    odabirPredmeta.adapter = dataAdapter
                    if(presetPredmet >=  0 && !stringovi.contains("-Empty-")){
                        odabirPredmeta.setSelection(presetPredmet)
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        odabirPredmeta.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long) {

                scope.launch {
                    var grupe: List<String>
                    val predmet = predmetIGrupaViewModel.getPredmeti().find { predmet1 ->  predmet1.naziv == odabirPredmeta.selectedItem.toString() }
                    if(predmet == null) grupe = listOf("-Empty-")
                    else {
                        grupe = predmetIGrupaViewModel.getGrupeZaPredmet(predmet!!.id)!!.map { grupa -> grupa.toString() }
                        if (grupe.isEmpty()) {
                            grupe = listOf("-Empty-")
                        }
                    }
                    val dataAdapter: ArrayAdapter<String> = ArrayAdapter<String>(view.context, android.R.layout.simple_spinner_item, grupe)
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    odabirGrupe.adapter = dataAdapter
                    if(presetGrupa >= 0 && !grupe.contains("-Empty-")){
                        odabirGrupe.setSelection(presetGrupa)
                    }
                }
            }




            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


        return view
    }


    override fun onPause() {
        presetGodina = odabirGodine.selectedItemPosition
        if(!zavrsenUpis) {
            presetPredmet = odabirPredmeta.selectedItemPosition
            presetGrupa = odabirGrupe.selectedItemPosition
        }
        super.onPause()
    }



}