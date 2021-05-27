package ba.etf.rma21.projekat.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.Pitanje
import java.util.*


class FragmentPitanje(var pitanje: Pitanje): Fragment() {
    private lateinit var tekstPitanja: TextView
    private lateinit var listaOdgovora: ListView
    private var tacno: Int = 0
    private var enabled : Boolean = true
    private var tacanOdgovor: Boolean = false
    private var savedState: Bundle = Bundle()

    private var savedOdgovor: Int = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.pitanje_fragment, container, false)
        val odgovori: ArrayList<String>?

        tekstPitanja = view.findViewById(R.id.tekstPitanja)
        listaOdgovora = view.findViewById(R.id.odgovoriLista)

        if(arguments != null){
            enabled = arguments?.getBoolean("disableList")!!
        }

        if(savedState.size() > 0){
            savedOdgovor = savedState.getInt("savedOdgovor")
        }


        odgovori = ArrayList(pitanje.opcije)
        tacno = pitanje.tacan
        tekstPitanja.text = pitanje.tekstPitanja

        val dataAdapter: ArrayAdapter<String> = ArrayAdapter<String>(view.context, android.R.layout.simple_list_item_1, odgovori)
        listaOdgovora.adapter = dataAdapter


        if(savedOdgovor >= 0) {
            listaOdgovora.post {
                val textview = listaOdgovora.getChildAt(savedOdgovor!!) as TextView
                if(textview != listaOdgovora.getChildAt(tacno) as TextView){
                    textview.setTextColor(ContextCompat.getColor(view.context, R.color.wrong))
                    (listaOdgovora.getChildAt(tacno) as TextView).setTextColor(ContextCompat.getColor(view.context, R.color.correct))
                }
                else textview.setTextColor(ContextCompat.getColor(view.context, R.color.correct))
            }
        }



        if(!enabled){
            listaOdgovora.isEnabled = false
        }


        if(enabled) {
            listaOdgovora.setOnItemClickListener { parent, view, position, id ->
                savedOdgovor = position
                var textview: TextView = listaOdgovora.getChildAt(position) as TextView

                if (textview.text.toString() != odgovori.get(tacno)) {
                    textview.setTextColor(ContextCompat.getColor(view.context, R.color.wrong))
                    (parent.getChildAt(tacno) as TextView).setTextColor(ContextCompat.getColor(view.context, R.color.correct))
                }
                else{
                    textview.setTextColor(ContextCompat.getColor(view.context, R.color.correct))
                    tacanOdgovor = true
                }
                listaOdgovora.isEnabled = false
                listaOdgovora.onItemClickListener = null
                enabled = false
                setFragmentResult("odgovoreno", bundleOf(Pair("odgovor",tacanOdgovor), Pair("position", position)))
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        savedState.putInt("savedOdgovor", savedOdgovor)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        setFragmentResultListener("disable") { requestKey, bundle ->
            enabled = bundle.getBoolean("disableList")
        }
    }



    companion object{
        fun newInstance(pitanje: Pitanje): FragmentPitanje = FragmentPitanje(pitanje)
    }
}