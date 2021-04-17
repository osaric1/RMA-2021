package ba.etf.rma21.projekat

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView

    private val myOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.kvizovi -> {
                val kvizoviFragment = FragmentKvizovi.newInstance()
                setFragmentArguments(kvizoviFragment)
                openFragment(kvizoviFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.predmeti -> {
                val predmetiFragment = FragmentPredmeti.newInstance()
                openFragment(predmetiFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.predajKviz -> {

                return@OnNavigationItemSelectedListener true
            }
            R.id.zaustaviKviz -> {

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        if(fragment !is FragmentKvizovi)
            transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 0){
            supportFragmentManager.popBackStack(supportFragmentManager.getBackStackEntryAt(0).id, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            if(bundle != null){ //posto skidamo sa stacka fragmente sve dok ne dodjemo do main fragmenta, kako ne bi stvorili dvije instance izbrisemo postojecu i proslijedimo argumente novoj
                supportFragmentManager.popBackStack()
                val kvizovi = FragmentKvizovi.newInstance()
                setFragmentArguments(kvizovi)
                openFragment(kvizovi)
            }
        }
        else{
            super.onBackPressed()
        }
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
    }

    fun setFragmentArguments(fragment: Fragment){
        if(fragment is FragmentKvizovi && bundle != null)
            fragment.arguments = bundle
    }



}

