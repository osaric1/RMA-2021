package ba.etf.rma21.projekat.view

import android.os.Bundle
import android.text.Layout
import android.text.SpannableString
import android.text.style.AlignmentSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import ba.etf.rma21.projekat.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class FragmentPoruka: Fragment() {


    private lateinit var poruka: TextView
    private lateinit var bottomNavigationView: BottomNavigationView
    private var  result: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.poruka_fragment, container, false)
        poruka = view.findViewById(R.id.tvPoruka)
        bottomNavigationView = activity?.findViewById(R.id.bottomNav)!!

        if(arguments != null){
            poruka.text = arguments?.getString("poruka")
            var tekst = SpannableString(poruka.text)
            tekst.setSpan(AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, poruka.text.toString().length, 0)
            poruka.text = tekst
        }
        else {
            setFragmentResultListener("requestKey") { requestKey, bundle ->
                result = bundle.getString("tacnost")
                if (result != null) {
                    poruka.text = result
                    var tekst = SpannableString(poruka.text)
                    tekst.setSpan(AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, poruka.text.toString().length, 0)
                    poruka.text = tekst
                }

            }

            setFragmentResultListener("ispisi") { requestKey, bundle ->
                result = bundle.getString("rezultat")
                if (result != null) {
                    poruka.text = result
                    var tekst = SpannableString(poruka.text)
                    tekst.setSpan(AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, poruka.text.toString().length, 0)
                    poruka.text = tekst
                }

            }
        }
        return view
    }


//
//    override fun onAttach(context: Context) {
//
//        setFragmentResultListener("requestKey") { requestKey, bundle ->
//            result  = bundle.getString("tacnost")
//            uradjenKviz = true
//            setFragmentResult("uradjenKviz", bundleOf(Pair("uradjenKviz", uradjenKviz)))
//        }
//        super.onAttach(context)
//    }

    companion object{
        fun newInstance(): FragmentPoruka = FragmentPoruka()

    }


}