package ba.etf.rma21.projekat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.util.*


class FragmentPitanje: Fragment() {
    private lateinit var tekstPitanja: TextView
    private lateinit var listaOdgovora: ListView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.pitanje_fragment, container, false)
        tekstPitanja = view.findViewById(R.id.tekstPitanja)
        listaOdgovora = view.findViewById(R.id.odgovoriLista)
        if(arguments != null){
            val tekst = arguments?.getString("pitanje")
            val odgovori: ArrayList<String>? = arguments?.getStringArrayList("odgovori")
            tekstPitanja.text = tekst

            if (odgovori != null) {
                val dataAdapter: ArrayAdapter<String> = ArrayAdapter<String>(view.context, android.R.layout.simple_list_item_1, odgovori)
                listaOdgovora.adapter = dataAdapter
            }
        }
        return view
    }

    companion object{
        fun newInstance(): FragmentPitanje = FragmentPitanje()
    }
}