package ba.etf.rma21.projekat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import ba.etf.rma21.projekat.data.models.Pitanje
import java.util.*


class FragmentPitanje(var pitanje: Pitanje): Fragment() {
    private lateinit var tekstPitanja: TextView
    private lateinit var listaOdgovora: ListView
    private var tacno: Int = 0
    private var odgovoreno: Boolean = false
    private var enabled : Boolean = true
    private var savedState: Bundle = Bundle()
    private var tacanOdgovor: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.pitanje_fragment, container, false)
        var odgovori: ArrayList<String>? = null

        tekstPitanja = view.findViewById(R.id.tekstPitanja)
        listaOdgovora = view.findViewById(R.id.odgovoriLista)

        if(savedState.size() > 0){
            odgovoreno = savedState.getBoolean("odgovoreno")
            enabled = savedState.getBoolean("enabled")
        }

        odgovori = ArrayList(pitanje.opcije)
        tacno = pitanje.tacan
        tekstPitanja.text = pitanje.tekst
        val dataAdapter: ArrayAdapter<String> = ArrayAdapter<String>(view.context, android.R.layout.simple_list_item_1, odgovori)
        listaOdgovora.adapter = dataAdapter


        if(enabled) {
            listaOdgovora.setOnItemClickListener { parent, view, position, id ->
                var textview: TextView = listaOdgovora.getChildAt(position) as TextView
                if (textview.text.toString() != odgovori.get(tacno)) {

                    tacanOdgovor = false
                    for (element in parent.children) {
                        if (element != parent.getChildAt(tacno))
                            (element as TextView).setTextColor(ContextCompat.getColor(view.context, R.color.wrong))
                    }
                    (parent.getChildAt(tacno) as TextView).setTextColor(ContextCompat.getColor(view.context, R.color.correct))

                }
                else{
                    textview.setTextColor(ContextCompat.getColor(view.context, R.color.correct))
                    tacanOdgovor = true
                }
                setFragmentResult("requestKey", bundleOf(Pair("odgovor",tacanOdgovor)))
                listaOdgovora.isEnabled = false
                listaOdgovora.onItemClickListener = null
                enabled = false
            }
        }

        return view
    }



    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putBoolean("odgovoreno", odgovoreno)
        savedInstanceState.putBoolean("enabled", enabled)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        savedState.putBoolean("odgovoreno", odgovoreno)
        savedState.putBoolean("enabled", enabled)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    companion object{
        fun newInstance(pitanje: Pitanje): FragmentPitanje = FragmentPitanje(pitanje)
    }
}