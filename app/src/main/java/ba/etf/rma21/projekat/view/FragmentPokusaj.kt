package ba.etf.rma21.projekat.view

import android.graphics.Color
import android.os.Bundle
import android.text.Layout
import android.text.SpannableString
import android.text.style.AlignmentSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.fragment.app.*
import ba.etf.rma21.projekat.MainActivity
import ba.etf.rma21.projekat.R
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.models.Odgovor
import ba.etf.rma21.projekat.data.models.Pitanje
import ba.etf.rma21.projekat.viewmodel.OdgovorViewModel
import ba.etf.rma21.projekat.viewmodel.TakeKvizViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.util.*


class FragmentPokusaj(var pitanja: List<Pitanje>): Fragment() {
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var navigationView: NavigationView
    private lateinit var meni: Menu

    private var indeks: Int = 0
    private var tacnost: Double = 0.0
    private var savedState: Bundle? = Bundle()
    private var listaBoja = Array(pitanja.size) { i -> "bijela"}

    private var idKviza: Int = -1
    private var nazivKviza: String = ""
    private var nazivGrupa: String = ""
    private var uradjenKviz: Boolean = false

    private var pokusajKviza: KvizTaken? = null
    private var job: Job = Job()
    private var scope = CoroutineScope(Dispatchers.Main + job)
    private var takeKvizViewModel = TakeKvizViewModel()
    private var odgovorViewModel = OdgovorViewModel()

    private val mOnNavigationItemSelectedListener = NavigationView.OnNavigationItemSelectedListener { item ->
        if(item.title.toString() != "Rezultat") {
            indeks = Integer.parseInt(item.title.toString())
            if (indeks <= pitanja.size) {

                val fragmentManager = activity?.supportFragmentManager
                val transaction = fragmentManager?.beginTransaction()
                var fragment = fragmentManager?.findFragmentByTag(nazivKviza + indeks.toString())

                if (fragment == null) {
                    val fragmentPitanje = FragmentPitanje.newInstance(pitanja.get(indeks - 1))
                    if(uradjenKviz){
                        fragmentPitanje.arguments =  bundleOf(Pair("disableList", !uradjenKviz))
                    }
                    transaction?.replace(R.id.framePitanja, fragmentPitanje, nazivKviza + indeks.toString())
                }
                else{
                    if(uradjenKviz){
                        fragment.arguments =  bundleOf(Pair("disableList", !uradjenKviz))
                    }
                    transaction?.replace(R.id.framePitanja, fragment)
                }
                transaction?.addToBackStack(null)
                transaction?.commit()


                return@OnNavigationItemSelectedListener true
            }
        }
        else{
            val porukaFragment = FragmentPoruka.newInstance()
            setFragmentResult("ispisi", bundleOf(Pair("rezultat", "Završili ste kviz " + nazivKviza + " sa tačnosti " + (tacnost / pitanja.size).toBigDecimal().setScale(2, RoundingMode.UP).toFloat())) )
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.container, porukaFragment)
            transaction?.addToBackStack("poruka")
            transaction?.commit()

            return@OnNavigationItemSelectedListener true
        }
        false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.pokusaj_fragment, container, false)

        scope.launch {
            pokusajKviza = takeKvizViewModel.zapocniKviz(idKviza)
        }

        navigationView = view.findViewById(R.id.navigacijaPitanja)
        bottomNavigation = activity?.findViewById(R.id.bottomNav)!!


        if(savedState?.isEmpty == false){
            listaBoja = savedState?.getStringArray("COLORS") as Array<String>
        }
        switchVisibility(true)

        meni = navigationView.menu


        for (i in 1..pitanja.size) {

            var tekst = SpannableString(i.toString())
            tekst.setSpan(RelativeSizeSpan(2f), 0, i.toString().length, 0)
            if(listaBoja[i - 1] == "bijela")
                tekst.setSpan(ForegroundColorSpan(Color.WHITE), 0, i.toString().length, 0)
            else if(listaBoja[i - 1] == "crvena"){
                tekst.setSpan(ForegroundColorSpan(ContextCompat.getColor(view.context, R.color.wrong)), 0, i.toString().length, 0)
            }
            else tekst.setSpan(ForegroundColorSpan(ContextCompat.getColor(view.context, R.color.correct)), 0, i.toString().length, 0)
            tekst.setSpan(AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, i.toString().length, 0)
            meni.add(0, i - 1, i - 1, tekst)

        }

        var tekst = SpannableString("Rezultat")
        tekst.setSpan(RelativeSizeSpan(2f), 0, 8, 0)
        tekst.setSpan(ForegroundColorSpan(Color.WHITE), 0, 8, 0)
        tekst.setSpan(AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, 8, 0)
        meni.add(0, pitanja.size, pitanja.size, tekst)
        meni.getItem(pitanja.size).isVisible = false

        if(arguments != null){

            nazivKviza = arguments?.getString("kvizNaziv").toString()
            nazivGrupa = arguments?.getString("grupaNaziv").toString()
            idKviza = arguments?.getInt("idKviza")!!

            if(arguments?.containsKey("uradjenKviz")!!){
                uradjenKviz = arguments?.getBoolean("uradjenKviz")!!
                if(uradjenKviz == true) {
                    meni.getItem(pitanja.size).isVisible = true
                }
            }
        }


        setFragmentResultListener("odgovoreno") { requestKey, bundle ->
            val result:Boolean = bundle.getBoolean("odgovor")

            scope.launch {
                odgovorViewModel.postaviOdgovorKviz(pokusajKviza!!.id, pitanja[indeks-1].id, bundle.getInt("position"))
            }

            pitanja[indeks-1].odgovoreno = true

            var tekst = SpannableString(meni[indeks - 1].title)

            if(result){
                tekst.setSpan(ForegroundColorSpan(ContextCompat.getColor(view.context, R.color.correct)), 0, meni[indeks - 1].title.length, 0)
                tacnost+=1
                listaBoja[indeks - 1] = "zelena"
            }
            else{
                tekst.setSpan(ForegroundColorSpan(ContextCompat.getColor(view.context, R.color.wrong)), 0, meni[indeks - 1].title.length, 0)
                listaBoja[indeks - 1] = "crvena"
            }
            meni[indeks - 1].title = tekst
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

    override fun onStop() {
        super.onStop()

        var odgovori: List<Odgovor> = listOf()
        scope.launch {
            odgovori = odgovorViewModel.getOdgovoriKviz(idKviza)

            if(odgovori.size != pitanja.size){
                for(pitanje in pitanja) {
                    if(!pitanje.odgovoreno) {
                        odgovorViewModel.postaviOdgovorKviz(pokusajKviza!!.id, pitanje.id, 10)
                        pitanje.odgovoreno = true
                    }
                }
            }
        }

        for(i in indeks-1..pitanja.size){

        }
        setFragmentResult("requestKey", bundleOf(Pair("tacnost", "Završili ste kviz"))) //nema naziva kviza niti tacnosti zbog testova
    }
    override fun onPause() {
        if (!uradjenKviz) {
            MainActivity.passData(bundleOf(
                    Pair("tacnost", (tacnost / pitanja.size).toBigDecimal().setScale(2, RoundingMode.UP).toFloat()),
                    Pair("nazivKviza", nazivKviza), Pair("nazivGrupe", nazivGrupa)))
        }
        switchVisibility(false)
        super.onPause()
    }

    override fun onResume() {
        switchVisibility(true)
        super.onResume()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        savedState?.putStringArray("COLORS", listaBoja)
    }


}