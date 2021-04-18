package ba.etf.rma21.projekat

import android.graphics.Color
import android.os.Bundle
import android.text.Layout
import android.text.SpannableString
import android.text.style.AlignmentSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.data.models.Pitanje
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import java.util.*
import kotlin.collections.ArrayList


class FragmentPokusaj(var pitanja: List<Pitanje>): Fragment() {
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var navigationView: NavigationView

    private val mOnNavigationItemSelectedListener = NavigationView.OnNavigationItemSelectedListener { item ->
        var indeks = Integer.parseInt(item.title.toString())
        if(indeks <= pitanja.size){
            val fragmentPitanje = FragmentPitanje()
            val bundle: Bundle = Bundle()

            bundle.putString("pitanje", pitanja.get(indeks-1).tekst)

            val novaList: ArrayList<String> = ArrayList(pitanja.get(indeks-1).opcije)
            bundle.putStringArrayList("odgovori", novaList)

            fragmentPitanje.arguments = bundle
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.framePitanja, fragmentPitanje)
            transaction?.addToBackStack(null)
            transaction?.commit()
            return@OnNavigationItemSelectedListener true
        }
        false
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.pokusaj_fragment, container, false)
        navigationView = view.findViewById(R.id.navigacijaPitanja)
        bottomNavigation = activity?.findViewById(R.id.bottomNav)!!
        var meni: Menu = navigationView.menu

        for(i in 1..pitanja.size){
            var tekst = SpannableString(i.toString())
            tekst.setSpan(RelativeSizeSpan(2f), 0, i.toString().length, 0)
            tekst.setSpan(ForegroundColorSpan(Color.WHITE), 0, i.toString().length, 0)
            tekst.setSpan(AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, i.toString().length, 0)
            meni.add(tekst)
        }
        navigationView.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        switchVisibility(true)
        return view
    }


    companion object{
        fun newInstance(pitanja: List<Pitanje>): FragmentPokusaj = FragmentPokusaj(pitanja)
    }

    fun switchVisibility(visibility: Boolean){
        bottomNavigation.menu.findItem(R.id.predajKviz).setVisible(visibility)
        bottomNavigation.menu.findItem(R.id.zaustaviKviz).setVisible(visibility)
        bottomNavigation.menu.findItem(R.id.kvizovi).setVisible(!visibility)
        bottomNavigation.menu.findItem(R.id.predmeti).setVisible(!visibility)
    }

    override fun onPause() {
        switchVisibility(false)
        super.onPause()
    }

    override fun onResume() {
        switchVisibility(true)
        super.onResume()
    }

}