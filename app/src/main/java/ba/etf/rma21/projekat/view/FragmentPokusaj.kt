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
import java.lang.NullPointerException
import java.math.RoundingMode
import java.util.*


class FragmentPokusaj(var pitanja: List<Pitanje>): Fragment() {
    private lateinit var bottomNavigation: BottomNavigationView
    private lateinit var navigationView: NavigationView
    private lateinit var meni: Menu

    private var indeks: Int = 0
    private var tacnost: Double = 0.0

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
                val fragment = fragmentManager?.findFragmentByTag(nazivKviza + indeks.toString())

                val bundle: Bundle = Bundle()
                bundle.putInt("idKviza", idKviza)

                if(uradjenKviz){
                    bundle.putBoolean("disableList", !uradjenKviz)
                }

                if (fragment == null) {
                    val fragmentPitanje = FragmentPitanje.newInstance(pitanja.get(indeks - 1))
                    fragmentPitanje.arguments = bundle
                    transaction?.replace(R.id.framePitanja, fragmentPitanje, nazivKviza + indeks.toString())
                }
                else{
                    fragment.arguments = bundle
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

        navigationView = view.findViewById(R.id.navigacijaPitanja)
        bottomNavigation = activity?.findViewById(R.id.bottomNav)!!

        tacnost = 0.0

        switchVisibility(true)

        meni = navigationView.menu

        for(i in 1..pitanja.size){
            val tekst = SpannableString(i.toString())
            tekst.setSpan(RelativeSizeSpan(2f), 0, i.toString().length, 0)
            tekst.setSpan(ForegroundColorSpan(Color.WHITE), 0, i.toString().length, 0)
            tekst.setSpan(AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, i.toString().length, 0)
            meni.add(0, i - 1, i - 1, tekst)
        }

        val tekst = SpannableString("Rezultat")
        tekst.setSpan(RelativeSizeSpan(2f), 0, 8, 0)
        tekst.setSpan(ForegroundColorSpan(Color.WHITE), 0, 8, 0)
        tekst.setSpan(AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, 8, 0)
        meni.add(0, pitanja.size, pitanja.size, tekst)
        meni.getItem(pitanja.size).isVisible = false

        scope.launch{

            pokusajKviza = takeKvizViewModel.getPocetiKvizovi()?.find { kvizTaken -> kvizTaken.KvizId == idKviza  }
            val listaOdgovora: List<Odgovor>
            if(pokusajKviza != null) {
                listaOdgovora = odgovorViewModel.getOdgovoriKviz(idKviza)
                if (listaOdgovora.isNotEmpty()) {
                    for (odgovor in listaOdgovora) {
                        val pitanje = pitanja.find { pitanje -> pitanje.id == odgovor.PitanjeId }

                        if (pitanje != null) {
                            val opcijeParsed = pitanje.opcije.split(",").map { it.trim() }

                            val tekst = SpannableString((pitanja.indexOf(pitanje) + 1).toString())
                            tekst.setSpan(RelativeSizeSpan(2f), 0, (pitanja.indexOf(pitanje) + 1).toString().length, 0)

                            if (pitanje.tacan == odgovor.odgovoreno){
                                tekst.setSpan(ForegroundColorSpan(ContextCompat.getColor(view.context, R.color.correct)), 0, (pitanja.indexOf(pitanje) + 1).toString().length, 0)
                                tacnost++
                            }

                            else if(odgovor.odgovoreno >= opcijeParsed.size){
                                tekst.setSpan(ForegroundColorSpan(ContextCompat.getColor(view.context, R.color.white)), 0, (pitanja.indexOf(pitanje) + 1).toString().length, 0)
                            }


                            else if(pitanje.tacan != odgovor.odgovoreno){
                                tekst.setSpan(ForegroundColorSpan(ContextCompat.getColor(view.context, R.color.wrong)), 0, (pitanja.indexOf(pitanje) + 1).toString().length, 0)
                            }

                            tekst.setSpan(AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, (pitanja.indexOf(pitanje) + 1).toString().length, 0)
                            meni.getItem((pitanja.indexOf(pitanje))).title = tekst

                        }
                    }
                }

            }
            else{
                try {
                    pokusajKviza = takeKvizViewModel.zapocniKviz(idKviza)
                }
                catch(e: NullPointerException){
                    println("Greska kod metode zapocniKviz\n")
                }
            }

            //odmah cemo ovdje poslati u main bundle za idKviza
            MainActivity.passData(bundleOf(Pair("idKviza", idKviza), Pair("pokusajKviza", pokusajKviza!!.id)))
        }

        if(arguments != null){

            nazivKviza = arguments?.getString("kvizNaziv").toString()
            nazivGrupa = arguments?.getString("grupaNaziv").toString()
            idKviza = arguments?.getInt("idKviza")!!

            if(arguments?.containsKey("uradjenKviz")!!){
                uradjenKviz = arguments?.getBoolean("uradjenKviz")!!

                if(uradjenKviz) {
                    meni.getItem(pitanja.size).isVisible = true
                }

            }

        }


        setFragmentResultListener("odgovoreno") { _, bundle ->
            val result:Boolean = bundle.getBoolean("odgovor")
            scope.launch {

                odgovorViewModel.postaviOdgovorKviz(pokusajKviza!!.id, pitanja[indeks-1].id, bundle.getInt("position"))
            }

            val tekst = SpannableString(meni[indeks - 1].title)

            if(result){
                tekst.setSpan(ForegroundColorSpan(ContextCompat.getColor(view.context, R.color.correct)), 0, meni[indeks - 1].title.length, 0)
                tacnost++
            }
            else{
                tekst.setSpan(ForegroundColorSpan(ContextCompat.getColor(view.context, R.color.wrong)), 0, meni[indeks - 1].title.length, 0)
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
        setFragmentResult("requestKey", bundleOf(Pair("tacnost", "Završili ste kviz"))) //nema naziva kviza niti tacnosti zbog testova
        super.onStop()

    }

    override fun onPause()
    {
        if (!uradjenKviz) {
            Log.d("dadadad", "ovdje")
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
    }



}