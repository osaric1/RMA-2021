package ba.etf.rma21.projekat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import ba.etf.rma21.projekat.viewmodel.GrupaViewModel
import ba.etf.rma21.projekat.viewmodel.PredmetViewModel

class UpisPredmet : AppCompatActivity() {
    private lateinit var  odabirGodine: Spinner
    private lateinit var  odabirPredmeta: Spinner
    private lateinit var  odabirGrupe: Spinner
    private lateinit var  dodajPredmet: Button

    private var predmetViewModel  = PredmetViewModel()
    private var grupaViewModel  = GrupaViewModel()
    private var defaultGodina: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        odabirGodine = findViewById(R.id.odabirGodina)
        odabirPredmeta = findViewById(R.id.odabirPredmet)
        odabirGrupe = findViewById(R.id.odabirGrupa)

        dodajPredmet = findViewById(R.id.dodajPredmetDugme)

        dodajPredmet.setOnClickListener {
                intent = Intent(applicationContext, MainActivity::class.java)


                intent.putExtra("godina",odabirGodine.selectedItem.toString())
                intent.putExtra("grupa", odabirGrupe.selectedItem.toString())
                intent.putExtra("predmet", odabirPredmeta.selectedItem.toString())
                intent.putExtra("godinaDefault", defaultGodina.toString())
                setResult(RESULT_OK, intent)
                finish()
        }

        ArrayAdapter.createFromResource(
                this,
                R.array.godine,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            odabirGodine.adapter = adapter
            defaultGodina = Integer.parseInt(intent.getStringExtra("godinaDefault"))
            if(defaultGodina >= 0)
                odabirGodine.setSelection(defaultGodina)
        }

        odabirGodine.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long)
            {
                defaultGodina = odabirGodine.selectedItemPosition
                var predmeti : List<String> = predmetViewModel.getSlobodni(Integer.parseInt(odabirGodine.selectedItem.toString())).map { predmet -> predmet.toString()  }

                if(predmeti.isEmpty()){
                    predmeti = listOf("-Empty-")
                }

                val dataAdapter: ArrayAdapter<String> = ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_item, predmeti)
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

                var grupe : List<String> = grupaViewModel.getGroupsByPredmet(odabirPredmeta.selectedItem.toString()).map { grupa -> grupa.toString() }

                if(grupe.isEmpty()){
                    grupe = listOf("-Empty-")
                }

                val dataAdapter: ArrayAdapter<String> = ArrayAdapter<String>(applicationContext, android.R.layout.simple_spinner_item, grupe)
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                odabirGrupe.adapter = dataAdapter

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


    }



}