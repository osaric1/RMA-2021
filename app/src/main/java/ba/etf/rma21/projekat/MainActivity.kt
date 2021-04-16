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
    private var godinaDefault: Int = -1
    private val myOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.kvizovi -> {
                val kvizoviFragment = FragmentKvizovi.newInstance()
                openFragment(kvizoviFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.predmeti -> {
                val predmetiFragment = FragmentPredmeti.newInstance()
                openFragment(predmetiFragment)
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
        }
        else{
            super.onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigation= findViewById(R.id.bottomNav)
        bottomNavigation.setOnNavigationItemSelectedListener(myOnNavigationItemSelectedListener)
        bottomNavigation.selectedItemId= R.id.kvizovi
        val kvizoviFragment = FragmentKvizovi.newInstance()
        openFragment(kvizoviFragment)

    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if(requestCode == SECOND_ACTIVITY_REQUEST_CODE){
//            if(resultCode == RESULT_OK){
//
//                val odabraniPredmet = data?.getStringExtra("predmet")
//                val odabranaGrupa  = data?.getStringExtra("grupa")
//                val odabranaGodina = data?.getStringExtra("godina")
//                godinaDefault = Integer.parseInt(data?.getStringExtra("godinaDefault"))
//
//                if(odabraniPredmet != "-Empty-" && odabranaGrupa != "-Empty-" && odabranaGodina != "-Empty-") {
//                    kvizViewModel.addGroup(Grupa(odabranaGrupa.toString(), odabraniPredmet.toString()))
//                    predmetViewModel.addPredmet(Predmet(odabraniPredmet.toString(), Integer.parseInt(odabranaGodina.toString())))
//                }
//                kvizAdapter.updateKvizovi(kvizViewModel.getMyKvizes())
//                spinner.setSelection(0)
//            }
//        }
//    }

}

