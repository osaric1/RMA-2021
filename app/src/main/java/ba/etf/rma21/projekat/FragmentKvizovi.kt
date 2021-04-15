package ba.etf.rma21.projekat

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
import ba.etf.rma21.projekat.viewmodel.KvizViewModel
import ba.etf.rma21.projekat.viewmodel.PredmetViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentKvizovi: Fragment() {
    private lateinit var listaKvizova: RecyclerView
    private lateinit var  spinner: Spinner
    private lateinit var kvizAdapter: KvizAdapter

    private var kvizViewModel =  KvizViewModel()
    private var predmetViewModel = PredmetViewModel()
    private var godinaDefault : Int = -1
    private val SECOND_ACTIVITY_REQUEST_CODE  = 0
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

        kvizAdapter = KvizAdapter(listOf())


        listaKvizova.adapter = kvizAdapter
        spinner.setSelection(1)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()
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
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        return view
    }
    companion object{
        fun newInstance(): FragmentKvizovi = FragmentKvizovi()
    }
}