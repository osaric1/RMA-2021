package ba.etf.rma21.projekat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class FragmentPoruka: Fragment() {


    private lateinit var poruka: TextView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.poruka_fragment, container, false)
        poruka = view.findViewById(R.id.tvPoruka)
        if(arguments != null){
            poruka.text = arguments?.getString("poruka")
        }
        return view
    }


    companion object{
        fun newInstance(): FragmentPoruka = FragmentPoruka()
    }

}