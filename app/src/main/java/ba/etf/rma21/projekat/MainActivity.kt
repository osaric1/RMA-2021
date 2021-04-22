package ba.etf.rma21.projekat

import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView

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
                openFragment(porukaFragment)
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

