package me.vojinpuric.projektnizadatak.fragmenti

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_korpa.*
import me.vojinpuric.projektnizadatak.KorpaListAdapter

import me.vojinpuric.projektnizadatak.R
import me.vojinpuric.projektnizadatak.model.Korisnik
import me.vojinpuric.projektnizadatak.model.Proizvod


class KorpaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_korpa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val korpaAdapter = KorpaListAdapter(Korisnik.korpa)
        recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = korpaAdapter
        }
        ukupnaCena.text= izracunajCenu().toString()
    }

    private fun izracunajCenu() =
        Korisnik.korpa.keys.map {
            it.cena * Korisnik.korpa[it]!!
        }.sum()

    companion object {
        fun newInstance(): KorpaFragment =
            KorpaFragment()
    }
}
