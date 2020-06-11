package me.vojinpuric.projektnizadatak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import me.vojinpuric.projektnizadatak.fragmenti.HomeFragment
import me.vojinpuric.projektnizadatak.fragmenti.KorpaFragment
import me.vojinpuric.projektnizadatak.fragmenti.LoginFragment

class MainActivity : AppCompatActivity() {
    private val bar by lazy { supportActionBar!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> {
                    bar.title = "Elektronika"
                    openFragment(HomeFragment.newInstance())
                    true
                }
                R.id.navigation_cart -> {
                    bar.title = "Korpa"
                    openFragment(KorpaFragment.newInstance())
                    true
                }
                else -> false
            }
        }
        navigacijaVidljiva(false)
        openFragment(LoginFragment.newInstance())
    }

    fun openFragment(fragment: Fragment ,data:Bundle? = null) {
        val transaction = supportFragmentManager.beginTransaction()
        data?.let {
            fragment.arguments=it
        }
        transaction.replace(R.id.frame, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun navigacijaVidljiva(enabled: Boolean) {
        if (enabled) {
            navigation.visibility = View.VISIBLE
        } else {
            navigation.visibility = View.GONE
        }
    }

    fun logIn() {
        openFragment(HomeFragment.newInstance())
        navigacijaVidljiva(true)
    }
}
