package ba.etf.rma21.projekat

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import ba.etf.rma21.projekat.data.models.Grupa
import ba.etf.rma21.projekat.data.models.Predmet

class UpisPredmet : AppCompatActivity() {
    private lateinit var  odabirGodine: Spinner
    private lateinit var  odabirPredmeta: Spinner
    private lateinit var  odabirGrupe: Spinner
    private lateinit var  dodajPredmet: Button

    private var predmetViewModel  = PredmetViewModel()
    private var grupaViewModel  = GrupaViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upis_predmet)

        odabirGodine = findViewById(R.id.odabirGodina)
        odabirPredmeta = findViewById(R.id.odabirPredmet)
        odabirGrupe = findViewById(R.id.odabirGrupa)

        dodajPredmet = findViewById(R.id.dodajPredmetDugme)

        dodajPredmet.setOnClickListener {
                finish()
        }

        ArrayAdapter.createFromResource(
                this,
                R.array.godine,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            odabirGodine.adapter = adapter
        }

        odabirGodine.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long)
            {
                var godina : Int = 0

                if(odabirGodine.selectedItem.toString() == "Prva godina")
                    godina = 1

                else if(odabirGodine.selectedItem.toString() == "Druga godina")
                    godina = 2

                else if(odabirGodine.selectedItem.toString() == "Treća godina")
                    godina = 3

                else if(odabirGodine.selectedItem.toString() == "Četvrta godina")
                    godina = 4

                val predmeti : List<Predmet> = predmetViewModel.getPredmetsByGodinama(godina)

                val dataAdapter: ArrayAdapter<Predmet> = ArrayAdapter<Predmet>(applicationContext, android.R.layout.simple_spinner_item, predmeti)
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                odabirPredmeta.adapter = dataAdapter

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        odabirPredmeta.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long)
            {

                val grupe : List<Grupa> = grupaViewModel.getGroupsByPredmet(odabirPredmeta.selectedItem.toString())

                val dataAdapter: ArrayAdapter<Grupa> = ArrayAdapter<Grupa>(applicationContext, android.R.layout.simple_spinner_item, grupe)
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                odabirGrupe.adapter = dataAdapter

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


    }


}