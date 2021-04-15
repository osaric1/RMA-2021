package ba.etf.rma21.projekat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.viewmodel.KvizViewModel
import ba.etf.rma21.projekat.viewmodel.PredmetViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView
    private val myOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener {item ->
        when(item.itemId){
            R.id.kvizovi -> {
                val kvizoviFragment = FragmentKvizovi.newInstance()
                openFragment(kvizoviFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.predmeti -> {
                val predmetiFragment = FragmentPredmeti.newInstance()
                openFragment(predmetiFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigation= findViewById(R.id.bottomNav)
        bottomNavigation.setOnNavigationItemSelectedListener(myOnNavigationItemSelectedListener)
        bottomNavigation.selectedItemId= R.id.kvizovi
        val kvizoviFragment = FragmentKvizovi.newInstance()
        openFragment(kvizoviFragment)
//
//
//        ArrayAdapter.createFromResource(
//                this,
//                R.array.spinner_choices,
//                android.R.layout.simple_spinner_item
//        ).also { adapter ->
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            spinner.adapter = adapter
//
//        }
//
//
//
//        listaKvizova = findViewById(R.id.listaKvizova)
//        listaKvizova.setHasFixedSize(true)
//        listaKvizova.layoutManager = GridLayoutManager(
//                this,
//                2
//        )
//
//        kvizAdapter = KvizAdapter(listOf())
//
//
//        listaKvizova.adapter = kvizAdapter
//
//        spinner.setSelection(1)
//        spinner.onItemSelectedListener = object : OnItemSelectedListener {
//            override fun onItemSelected(
//                    parent: AdapterView<*>,
//                    view: View?,
//                    position: Int,
//                    id: Long
//            ) {
//                val selectedItem = parent.getItemAtPosition(position).toString()
//                if(spinner.selectedItem.toString() == "Svi kvizovi"){
//                    kvizAdapter.updateKvizovi(kvizViewModel.getAll())
//                }
//                else if(spinner.selectedItem.toString() == "Svi moji kvizovi"){
//                    kvizAdapter.updateKvizovi(kvizViewModel.getMyKvizes())
//                }
//                else if(spinner.selectedItem.toString() == "Urađeni kvizovi"){
//                    kvizAdapter.updateKvizovi(kvizViewModel.getDone())
//                }
//                else if(spinner.selectedItem.toString() == "Budući kvizovi"){
//                    kvizAdapter.updateKvizovi(kvizViewModel.getFuture())
//                }
//                else if(spinner.selectedItem.toString() == "Prošli kvizovi"){
//                    kvizAdapter.updateKvizovi(kvizViewModel.getNotTaken())
//                }
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {}
//        }
    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if(requestCode == SECOND_ACTIVITY_REQUEST_CODE){
//            if(resultCode == RESULT_OK){
//
//                val odabraniPredmet = data?.getStringExtra("predmet")
//                val odabranaGrupa  = data?.getStringExtra("grupa")
//                val odabranaGodina = data?.getStringExtra("godina")
//                godinaDefault = Integer.parseInt(data?.getStringExtra("godinaDefault"))
//
//                if(odabraniPredmet != "-Empty-" && odabranaGrupa != "-Empty-" && odabranaGodina != "-Empty-") {
//                    kvizViewModel.addGroup(Grupa(odabranaGrupa.toString(), odabraniPredmet.toString()))
//                    predmetViewModel.addPredmet(Predmet(odabraniPredmet.toString(), Integer.parseInt(odabranaGodina.toString())))
//                }
//                kvizAdapter.updateKvizovi(kvizViewModel.getMyKvizes())
//                spinner.setSelection(0)
//            }
//        }
//    }

}

