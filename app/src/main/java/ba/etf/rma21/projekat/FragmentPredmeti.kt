package ba.etf.rma21.projekat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.viewmodel.GrupaViewModel
import ba.etf.rma21.projekat.viewmodel.PredmetViewModel

class FragmentPredmeti : Fragment() {
    private lateinit var  odabirGodine: Spinner
    private lateinit var  odabirPredmeta: Spinner
    private lateinit var  odabirGrupe: Spinner
    private lateinit var  dodajPredmet: Button

    private var predmetViewModel  = PredmetViewModel()
    private var grupaViewModel  = GrupaViewModel()
    private var defaultGodina: Int = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.predmeti_fragment, container, false)
        odabirGodine = view.findViewById(R.id.odabirGodina)
        odabirPredmeta = view.findViewById(R.id.odabirPredmet)
        odabirGrupe = view.findViewById(R.id.odabirGrupa)

        dodajPredmet = view.findViewById(R.id.dodajPredmetDugme)

        dodajPredmet.setOnClickListener {
            val intent = Intent(view.context, MainActivity::class.java)
            intent.putExtra("godina",odabirGodine.selectedItem.toString())
            intent.putExtra("grupa", odabirGrupe.selectedItem.toString())
            intent.putExtra("predmet", odabirPredmeta.selectedItem.toString())
            intent.putExtra("godinaDefault", defaultGodina.toString())
        }

        ArrayAdapter.createFromResource(
                view.context,
                R.array.godine,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            odabirGodine.adapter = adapter
//            defaultGodina = Integer.parseInt(intent.getStringExtra("godinaDefault"))
            if(defaultGodina >= 0)
                odabirGodine.setSelection(defaultGodina)
        }



        return view
    }
    companion object{
        fun newInstance(): FragmentPredmeti = FragmentPredmeti()
    }

}