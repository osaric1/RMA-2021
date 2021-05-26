package ba.etf.rma21.projekat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.viewmodel.KvizViewModel
import ba.etf.rma21.projekat.viewmodel.PitanjeKvizViewModel
import ba.etf.rma21.projekat.viewmodel.PredmetViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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

            val kviz = arguments?.getString("nazivKviza")
            val grupa = arguments?.getString("nazivGrupe")
            val multiplier = arguments?.getFloat("tacnost")

            //kvizViewModel.changeStatus( pitanjeKvizViewModel.dajBodove(kviz!!)* multiplier!!, kviz!!, grupa!!)


        }


        listaKvizova.adapter = kvizAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                GlobalScope.launch(Dispatchers.IO) {

                    if(spinner.selectedItem.toString() == "Svi kvizovi"){
                        kvizAdapter.updateKvizovi(kvizViewModel.getAll())
                    }
                    else if(spinner.selectedItem.toString() == "Svi moji kvizovi"){
                        kvizAdapter.updateKvizovi(kvizViewModel.getUpisani())
                    }
                    kvizAdapter.updateSpinner(spinner.selectedItem.toString())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        return view
    }

    companion object{
        fun newInstance(): FragmentKvizovi = FragmentKvizovi()

    }

}