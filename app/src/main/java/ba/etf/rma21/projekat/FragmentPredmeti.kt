package ba.etf.rma21.projekat

import android.content.Intent
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
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.viewmodel.GrupaViewModel
import ba.etf.rma21.projekat.viewmodel.PredmetViewModel

class FragmentPredmeti : Fragment() {
    private lateinit var  odabirGodine: Spinner
    private lateinit var  odabirPredmeta: Spinner
    private lateinit var  odabirGrupe: Spinner
    private lateinit var  dodajPredmet: Button

    private var predmetViewModel  = PredmetViewModel()
    private var grupaViewModel  = GrupaViewModel()

    companion object{
        private var presetGodina: Int? = null
        private var presetGrupa: Int? = null
        private var presetPredmet: Int? = null
        fun newInstance(): FragmentPredmeti = FragmentPredmeti()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.predmeti_fragment, container, false)
        odabirGodine = view.findViewById(R.id.odabirGodina)
        odabirPredmeta = view.findViewById(R.id.odabirPredmet)
        odabirGrupe = view.findViewById(R.id.odabirGrupa)

        dodajPredmet = view.findViewById(R.id.dodajPredmetDugme)

        dodajPredmet.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("godina", odabirGodine.selectedItem.toString())
            bundle.putString("grupa", odabirGrupe.selectedItem.toString())
            bundle.putString("predmet", odabirPredmeta.selectedItem.toString())

            val fragmentPoruka = FragmentPoruka.newInstance()
            fragmentPoruka.arguments = bundleOf(Pair("poruka", "Uspješno ste upisani u grupu " + odabirGrupe.selectedItem.toString() + " predmeta " + odabirPredmeta.selectedItem.toString() + "!"))
            MainActivity.passData(bundle)

            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.container, fragmentPoruka)
            transaction?.addToBackStack(null)
            transaction?.commit()

        }

        ArrayAdapter.createFromResource(
                view.context,
                R.array.godine,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            odabirGodine.adapter = adapter

//            defaultGodina = Integer.parseInt(intent.getStringExtra("godinaDefault"))
        }

        odabirGodine.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long)
            {
                var predmeti : List<String> = predmetViewModel.getSlobodni(Integer.parseInt(odabirGodine.selectedItem.toString())).map { predmet -> predmet.toString()  }

                if(predmeti.isEmpty()){
                    predmeti = listOf("-Empty-")
                }

                val dataAdapter: ArrayAdapter<String> = ArrayAdapter<String>(view.context, android.R.layout.simple_spinner_item, predmeti)
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                odabirPredmeta.adapter = dataAdapter


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

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        return view
    }


    override fun onPause() {
        presetGodina = odabirGodine.selectedItemPosition
        presetGrupa = odabirGrupe.selectedItemPosition
        presetPredmet = odabirPredmeta.selectedItemPosition
        Log.d("DENINA", presetGodina.toString() + "$" + presetGrupa.toString() + "$" + presetPredmet.toString())

        super.onPause()
    }

    override fun onResume() {
        if(presetGodina != null){
            odabirGodine.setSelection(presetGodina!!)
        }
        if(presetGrupa != null){
            odabirGrupe.setSelection(presetGrupa!!)
        }
        if(presetPredmet != null){
            odabirPredmeta.setSelection(presetPredmet!!)
        }
        Log.d("DENINA RESUME", presetGodina.toString() + "$" + presetGrupa.toString() + "$" + presetPredmet.toString())
        super.onResume()
    }

}