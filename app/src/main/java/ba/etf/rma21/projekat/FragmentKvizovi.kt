package ba.etf.rma21.projekat

import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.text.SpannableString
import android.text.style.AlignmentSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.obracunajBodoveZaKviz
import ba.etf.rma21.projekat.viewmodel.KvizViewModel
import ba.etf.rma21.projekat.viewmodel.PitanjeKvizViewModel
import ba.etf.rma21.projekat.viewmodel.PredmetViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentKvizovi: Fragment() {
    private lateinit var listaKvizova: RecyclerView
    private lateinit var  spinner: Spinner
    private lateinit var kvizAdapter: KvizAdapter

    private var kvizViewModel =  KvizViewModel()
    private var predmetViewModel = PredmetViewModel()
    private var pitanjeKvizViewModel = PitanjeKvizViewModel()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view  = inflater.inflate(R.layout.kvizovi_fragment, container, false)
        spinner = view.findViewById(R.id.filterKvizova)

        ArrayAdapter.createFromResource(
                view.context,
                R.array.spinner_choices,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

        }

        listaKvizova = view.findViewById(R.id.listaKvizova)
        listaKvizova.setHasFixedSize(true)
        listaKvizova.layoutManager = GridLayoutManager(
                view.context,
                2
        )

        spinner.setSelection(0)
        kvizAdapter = KvizAdapter(listOf(), activity?.supportFragmentManager, spinner.selectedItem.toString())

        if(arguments != null){
            if(arguments?.containsKey("godina")!! && arguments?.containsKey("grupa")!! && arguments?.containsKey("predmet")!!) {
                val godina = arguments?.getString("godina")
                val grupa = arguments?.getString("grupa")
                val predmet = arguments?.getString("predmet")

                if (godina != "Empty" && grupa != "Empty" && predmet != "Empty") {
                    kvizViewModel.addGroup(Grupa(grupa.toString(), predmet.toString()))
                    predmetViewModel.addPredmet(Predmet(predmet.toString(), Integer.parseInt(godina.toString())))
                    kvizAdapter.updateKvizovi(kvizViewModel.getMyKvizes())
                }
            }
            else{
                val kviz = arguments?.getString("nazivKviza")
                val grupa = arguments?.getString("nazivGrupe")
                val multiplier = arguments?.getFloat("tacnost")

                kvizViewModel.changeStatus( pitanjeKvizViewModel.dajBodove(kviz!!)* multiplier!!, kviz!!, grupa!!)

            }
        }


        listaKvizova.adapter = kvizAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                if(spinner.selectedItem.toString() == "Svi kvizovi"){
                    kvizAdapter.updateKvizovi(kvizViewModel.getAll())
                }
                else if(spinner.selectedItem.toString() == "Svi moji kvizovi"){
                    kvizAdapter.updateKvizovi(kvizViewModel.getMyKvizes())
                }
                else if(spinner.selectedItem.toString() == "Urađeni kvizovi"){
                    kvizAdapter.updateKvizovi(kvizViewModel.getDone())
                }
                else if(spinner.selectedItem.toString() == "Budući kvizovi"){
                    kvizAdapter.updateKvizovi(kvizViewModel.getFuture())
                }
                else if(spinner.selectedItem.toString() == "Prošli kvizovi"){
                    kvizAdapter.updateKvizovi(kvizViewModel.getNotTaken())
                }
                kvizAdapter.updateSpinner(spinner.selectedItem.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        return view
    }

    companion object{
        fun newInstance(): FragmentKvizovi = FragmentKvizovi()

    }

}