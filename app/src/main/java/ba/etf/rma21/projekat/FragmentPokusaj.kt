package ba.etf.rma21.projekat

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.*
import androidx.fragment.app.Fragment
import ba.etf.rma21.projekat.data.models.Pitanje
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class FragmentPokusaj(): Fragment() {
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var navigationView: NavigationView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.pokusaj_fragment, container, false)
        navigationView = view.findViewById(R.id.navigacijaPitanja)
        bottomNavigation = activity?.findViewById(R.id.bottomNav)!!
        val pitanja: List<Int> = listOf(1,2,3,4,5,6,7,8,9,9,9,89,8,76,567,56,7,5,457,45,7)
        var meni: Menu = navigationView.menu

        for(i in 1..pitanja.size){
            var tekst: SpannableString = SpannableString(i.toString())
            tekst.setSpan(RelativeSizeSpan(2f), 0,i.toString().length, 0)
            tekst.setSpan(ForegroundColorSpan(Color.WHITE), 0, i.toString().length, 0)
            meni.add(tekst)
        }
        switchVisibility(true)
        return view
    }


    companion object{
        fun newInstance(): FragmentPokusaj = FragmentPokusaj()
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