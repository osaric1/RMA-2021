package ba.etf.rma21.projekat

import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.fragment.app.*
import androidx.fragment.app.setFragmentResultListener
import ba.etf.rma21.projekat.data.models.Account
import ba.etf.rma21.projekat.data.models.KvizTaken
import ba.etf.rma21.projekat.data.repositories.AccountRepository
import ba.etf.rma21.projekat.view.FragmentKvizovi
import ba.etf.rma21.projekat.view.FragmentPoruka
import ba.etf.rma21.projekat.view.FragmentPredmeti
import ba.etf.rma21.projekat.viewmodel.KvizViewModel
import ba.etf.rma21.projekat.viewmodel.OdgovorViewModel
import ba.etf.rma21.projekat.viewmodel.PitanjeKvizViewModel
import ba.etf.rma21.projekat.viewmodel.TakeKvizViewModel
//import ba.etf.rma21.projekat.view.FragmentPredmeti
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch



class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView
    private var kvizZavrsen = false
    private var job: Job = Job()
    private var scope = CoroutineScope(Dispatchers.Main + job)

    private var odgovorViewModel = OdgovorViewModel()
    private var pitanjeKvizViewModel = PitanjeKvizViewModel()
    private var takeKvizViewModel = TakeKvizViewModel()
    private var kvizViewModel = KvizViewModel()

    private val myOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.kvizovi -> {
                val fragment = supportFragmentManager.findFragmentByTag("kviz")
                if(fragment == null){
                    val kvizoviFragment = FragmentKvizovi.newInstance()
                    setFragmentArguments(kvizoviFragment)
                    openFragment(kvizoviFragment)
                }
                else{
                    setFragmentArguments(fragment)
                    openFragment(fragment)
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.predmeti -> {
                val predmetiFragment = FragmentPredmeti.newInstance()
                openFragment(predmetiFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.predajKviz -> {
                val porukaFragment = FragmentPoruka.newInstance()
                kvizZavrsen = true

                var idKviza: Int? = null
                var pokusajKviza: KvizTaken?
                var pokusajKvizaId: Int? = null

                if(bundle != null){
                    idKviza = bundle!!.getInt("idKviza")
                    pokusajKvizaId = bundle!!.getInt("pokusajKviza")
                }

                scope.launch {
                    val odgovori = odgovorViewModel.getOdgovoriKviz(idKviza!!)
                    val pitanja = pitanjeKvizViewModel.getPitanja(idKviza)
                    val kviz = kvizViewModel.getById(idKviza)

                    pokusajKviza = takeKvizViewModel.getPocetiKvizovi()
                        ?.find { kvizTaken -> kvizTaken.id == pokusajKvizaId }

                    if (odgovori.size != pitanja.size) {
                        for (pitanje in pitanja) {
                            val odgovor = odgovorViewModel.getOdgovoriKviz(idKviza).find{odgovor ->  odgovor.PitanjeId == pitanje.id }

                            if (odgovor == null) {
                                odgovorViewModel.postaviOdgovorKviz(
                                    pokusajKviza!!.id,
                                    pitanje.id,
                                    10
                                )
                            }
                        }
                    }
                    kviz!!.predan = true
                    openFragment(porukaFragment)
                }
                return@OnNavigationItemSelectedListener true
            }
            R.id.zaustaviKviz -> {
                val kvizoviFragment = FragmentKvizovi.newInstance()
                openFragment(kvizoviFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }



    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        if(fragment is FragmentKvizovi){
            transaction.replace(R.id.container, fragment, "kviz")
        }
        else{
            transaction.replace(R.id.container, fragment)
        }

        transaction.addToBackStack(null)

        transaction.commit()
    }

    override fun onBackPressed() {
//            supportFragmentManager.popBackStackImmediate(supportFragmentManager.getBackStackEntryAt(0).name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
//            if(bundle != null){//posto skidamo sa stacka fragmente sve dok ne dodjemo do main fragmenta, kako ne bi stvorili dvije instance izbrisemo postojecu i proslijedimo argumente novoj
//                supportFragmentManager.popBackStack()
//                val kvizovi = FragmentKvizovi.newInstance()
//                setFragmentArguments(kvizovi)
//                openFragment(kvizovi)
//            }

        if(supportFragmentManager.backStackEntryCount > 0){
            val index = supportFragmentManager.backStackEntryCount -1
            val fragment = supportFragmentManager.findFragmentByTag("kviz")

            if(supportFragmentManager.getBackStackEntryAt(index).name == "poruka"){
                supportFragmentManager.popBackStack()
            }
            else if(fragment!!.isVisible){
                moveTaskToBack(true)
            }
            else {
                val fragment = supportFragmentManager.findFragmentByTag("kviz")
                if(kvizZavrsen){
                    setFragmentArguments(fragment!!)
                    kvizZavrsen = false
                }
                openFragment(fragment!!)
            }
        }
        else super.onBackPressed()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation= findViewById(R.id.bottomNav)
        bottomNavigation.menu.findItem(R.id.predajKviz).setVisible(false)
        bottomNavigation.menu.findItem(R.id.zaustaviKviz).setVisible(false)
        bottomNavigation.menu.findItem(R.id.kvizovi).setVisible(true)
        bottomNavigation.menu.findItem(R.id.predmeti).setVisible(true)

        bottomNavigation.setOnNavigationItemSelectedListener(myOnNavigationItemSelectedListener)
        bottomNavigation.selectedItemId= R.id.kvizovi
        val kvizoviFragment = FragmentKvizovi.newInstance()
        openFragment(kvizoviFragment)

    }

    companion object{
        private var bundle : Bundle? = null

        fun passData(bundle: Bundle){
            this.bundle = bundle
        }
        fun setFragmentArguments(fragment: Fragment){
            if(bundle != null) {
                fragment.arguments = bundle
                bundle = null
            }
        }
    }

}

