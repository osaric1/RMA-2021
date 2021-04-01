package ba.etf.rma21.projekat

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import ba.etf.rma21.projekat.data.models.Predmet
import ba.etf.rma21.projekat.data.repositories.PredmetRepository

class UpisPredmet : AppCompatActivity() {
    private lateinit var  odabirGodine: Spinner
    private lateinit var  odabirPredmeta: Spinner
    private lateinit var  odabirGrupe: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upis_predmet)

        odabirGodine = findViewById(R.id.odabirGodina)
        odabirPredmeta = findViewById(R.id.odabirPredmet)
        odabirGrupe = findViewById(R.id.odabirGrupa)


        ArrayAdapter.createFromResource(
            this,
            R.array.godine,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            odabirGodine.adapter = adapter
        }

        populateDetails()
    }

    private fun populateDetails() {
        Log.d("2123123", "3525")
        val odabranaGodina = Integer.parseInt(odabirGodine.selectedItem.toString())
        val predmeti: List<Predmet> = PredmetRepository.getPredmetsByGodinama(odabranaGodina)

        val dataAdapter: ArrayAdapter<Predmet> = ArrayAdapter<Predmet>(
            this,
            android.R.layout.simple_spinner_item, predmeti)

        odabirPredmeta.adapter = dataAdapter

    }
}