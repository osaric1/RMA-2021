package ba.etf.rma21.projekat

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    private lateinit var listaKvizova: RecyclerView
    private lateinit var  spinner: Spinner
    private lateinit var kvizAdapter: KvizAdapter
    private var kvizListViewModel =  KvizListViewModel()
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
        listaKvizova = findViewById(R.id.listaKvizova)
        listaKvizova.setHasFixedSize(true)
        listaKvizova.layoutManager = GridLayoutManager(
                this,
                3
        )
        kvizAdapter = KvizAdapter(listOf())
        listaKvizova.adapter = kvizAdapter
        kvizAdapter.updateKvizovi(kvizListViewModel.getAll())
        listaKvizova.addItemDecoration(DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL))




    }
}

