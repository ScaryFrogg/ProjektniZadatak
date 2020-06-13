package me.vojinpuric.projektnizadatak.fragmenti

import android.content.ContentValues
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_izmeni.*

import me.vojinpuric.projektnizadatak.R
import me.vojinpuric.projektnizadatak.helpers.*
import me.vojinpuric.projektnizadatak.model.DatabaseHandler
import me.vojinpuric.projektnizadatak.model.Proizvod


class IzmeniFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_izmeni, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val databaseHandler = DatabaseHandler(requireContext())
        val proizvod = databaseHandler.getProizvod(arguments!!["id"] as Int)
        (proizvod as Proizvod).also {
            cena.setText(it.cena.toString())
            naziv.setText(it.naziv)
            stanje.setText(it.stanje.toString())
            dostava.setText(it.isporuka.toString())
            opis.setText(it.opis)
            drzavap.setText(it.drzava)
            slika.setText(it.slika)
        }
        button.setOnClickListener {
            val values = ContentValues().apply {
                put(KEY_NAZIV, naziv.text.toString())
                put(KEY_CENA, cena.text.toString().toDouble())
                put(KEY_STANJE, stanje.text.toString().toInt())
                put(KEY_OPIS, opis.text.toString())
                put(KEY_SLIKA, slika.text.toString())
                put(KEY_DRZAVA, drzavap.text.toString())
                put(KEY_ISPORUKA, dostava.text.toString().toInt())
            }
            databaseHandler.updateProizvod(proizvod.id,values)
        }
    }
    companion object {
        fun newInstance() = IzmeniFragment()
    }
}
