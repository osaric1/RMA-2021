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
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.viewmodel.GrupaViewModel
import ba.etf.rma21.projekat.viewmodel.KvizViewModel
import ba.etf.rma21.projekat.viewmodel.PredmetViewModel

class FragmentPredmeti() : Fragment() {
    private lateinit var  odabirGodine: Spinner
    private lateinit var  odabirPredmeta: Spinner
    private lateinit var  odabirGrupe: Spinner
    private lateinit var  dodajPredmet: Button

    private var predmetViewModel  = PredmetViewModel()
    private var grupaViewModel  = GrupaViewModel()
    private var kvizViewModel = KvizViewModel()

//    private var savedState = Bundle()
    
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
        Log.d("whatup", zavrsenUpis.toString())
        dodajPredmet = view.findViewById(R.id.dodajPredmetDugme)

//        if(savedState.size() > 0){
//            presetGodina = savedState.getInt("presetGodina")
//            presetPredmet = savedState.getInt("presetPredmet")
//            presetGrupa = savedState.getInt("presetGrupa")
//        }

        dodajPredmet.setOnClickListener {
            zavrsenUpis = true
            val bundle = Bundle()
            bundle.putString("godina", odabirGodine.selectedItem.toString())
            bundle.putString("grupa", odabirGrupe.selectedItem.toString())
            bundle.putString("predmet", odabirPredmeta.selectedItem.toString())

            if(odabirPredmeta.selectedItem.toString() != "-Empty-") {

                val fragmentPoruka = FragmentPoruka.newInstance()
                fragmentPoruka.arguments = bundleOf(Pair("poruka", "Uspješno ste upisani u grupu " + odabirGrupe.selectedItem.toString() + " predmeta " + odabirPredmeta.selectedItem.toString() + "!"))

                val godina = odabirGodine.selectedItem.toString()
                val grupa = odabirGrupe.selectedItem.toString()
                val predmet = odabirPredmeta.selectedItem.toString()

                kvizViewModel.addGroup(Grupa(grupa, predmet))
                predmetViewModel.addPredmet(Predmet(predmet, Integer.parseInt(godina)))


                val transaction = activity?.supportFragmentManager?.beginTransaction()
                transaction?.replace(R.id.container, fragmentPoruka)
                transaction?.addToBackStack(null)
                transaction?.commit()
            }
            else activity?.supportFragmentManager?.popBackStack()

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
                var predmeti: List<String> = predmetViewModel.getSlobodni(Integer.parseInt(odabirGodine.selectedItem.toString())).map { predmet -> predmet.toString() }
                if(predmeti.isEmpty()){
                    predmeti = listOf("-Empty-")
                }

                val dataAdapter: ArrayAdapter<String> = ArrayAdapter<String>(view.context, android.R.layout.simple_spinner_item, predmeti)
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                odabirPredmeta.adapter = dataAdapter
                if(presetPredmet >= 0){
                    odabirPredmeta.setSelection(presetPredmet)
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

                var grupe: List<String> = grupaViewModel.getGroupsByPredmet(odabirPredmeta.selectedItem.toString()).map { grupa -> grupa.toString() }

                if (grupe.isEmpty()) {
                    grupe = listOf("-Empty-")
                }

                val dataAdapter: ArrayAdapter<String> = ArrayAdapter<String>(view.context, android.R.layout.simple_spinner_item, grupe)
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                odabirGrupe.adapter = dataAdapter
                if(presetGrupa >= 0){
                    odabirGrupe.setSelection(presetGrupa)
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

//    override fun onDestroyView() {
//        super.onDestroyView()
//        Log.d("ovo se desi", "zes")
//        savedState.putInt("presetGodina", odabirGodine.selectedItemPosition)
//        if(!zavrsenUpis){
//            savedState.putInt("presetGrupa", odabirGodine.selectedItemPosition)
//            savedState.putInt("presetPredmet", odabirGodine.selectedItemPosition)
//        }
//    }



}