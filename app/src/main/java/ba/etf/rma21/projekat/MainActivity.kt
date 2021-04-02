package ba.etf.rma21.projekat

import android.R.attr.data
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.Comparator


class MainActivity : AppCompatActivity() {
    private lateinit var listaKvizova: RecyclerView
    private lateinit var  spinner: Spinner
    private lateinit var kvizAdapter: KvizAdapter
    private lateinit var upisDugme : FloatingActionButton

    private var kvizListViewModel =  KvizListViewModel()
    private var grupaViewModel = GrupaViewModel()
    private var predmetViewModel = PredmetViewModel()

    private val SECOND_ACTIVITY_REQUEST_CODE  = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        spinner = findViewById(R.id.filterKvizova)



        ArrayAdapter.createFromResource(
                this,
                R.array.spinner_choices,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

        }

        kvizListViewModel.addGroup(grupaViewModel.getAll()[6])
        predmetViewModel.addPredmet(predmetViewModel.getAll()[3])

        listaKvizova = findViewById(R.id.listaKvizova)
        listaKvizova.setHasFixedSize(true)
        listaKvizova.layoutManager = GridLayoutManager(
                this,
                2
        )

        kvizAdapter = KvizAdapter(listOf())


        listaKvizova.adapter = kvizAdapter


        upisDugme = findViewById(R.id.upisDugme)
        upisDugme.setOnClickListener{
            showUpisPredmeta()
        }

        spinner.setSelection(1)
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                if(spinner.selectedItem.toString() == "Svi kvizovi"){
                    kvizAdapter.updateKvizovi(kvizListViewModel.getAll())
                }
                else if(spinner.selectedItem.toString() == "Svi moji kvizovi"){
                    kvizAdapter.updateKvizovi(kvizListViewModel.getMyKvizes())
                }
                else if(spinner.selectedItem.toString() == "Urađeni kvizovi"){
                    kvizAdapter.updateKvizovi(kvizListViewModel.getDone())
                }
                else if(spinner.selectedItem.toString() == "Budući kvizovi"){
                    kvizAdapter.updateKvizovi(kvizListViewModel.getFuture())
                }
                else if(spinner.selectedItem.toString() == "Prošli kvizovi"){
                    kvizAdapter.updateKvizovi(kvizListViewModel.getNotTaken())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    fun showUpisPredmeta(){
        val intent = Intent(applicationContext, UpisPredmet::class.java)
        startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == SECOND_ACTIVITY_REQUEST_CODE){
            if(resultCode == RESULT_OK){

                val odabraniPredmet = data?.getStringExtra("predmet")
                val odabranaGrupa  = data?.getStringExtra("grupa")
                val odabranaGodina = data?.getStringExtra("godina")

                kvizListViewModel.addGroup(Grupa(odabranaGrupa.toString(), odabraniPredmet.toString()))
                predmetViewModel.addPredmet(Predmet(odabraniPredmet.toString(), Integer.parseInt(odabranaGodina.toString())))

                kvizAdapter.updateKvizovi(kvizListViewModel.getMyKvizes())
                spinner.setSelection(0)
            }
        }
    }

}

