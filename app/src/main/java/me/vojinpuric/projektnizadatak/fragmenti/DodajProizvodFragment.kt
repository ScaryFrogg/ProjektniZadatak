package me.vojinpuric.projektnizadatak.fragmenti

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_dodaj_proizvod.*
import kotlinx.android.synthetic.main.fragment_izmeni.button
import kotlinx.android.synthetic.main.fragment_izmeni.cena
import kotlinx.android.synthetic.main.fragment_izmeni.dostava
import kotlinx.android.synthetic.main.fragment_izmeni.naziv
import kotlinx.android.synthetic.main.fragment_izmeni.opis
import kotlinx.android.synthetic.main.fragment_izmeni.slika
import kotlinx.android.synthetic.main.fragment_izmeni.stanje

import me.vojinpuric.projektnizadatak.R
import me.vojinpuric.projektnizadatak.model.DatabaseHandler
import me.vojinpuric.projektnizadatak.model.Proizvod


class DodajProizvodFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dodaj_proizvod, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val databaseHandler = DatabaseHandler(requireContext())
        button.setOnClickListener{
            if(cena.text.isNotBlank() and  naziv.text.isNotBlank() and stanje.text.isNotBlank() and dostava.text.isNotBlank() and opis.text.isNotBlank() and slika.text.isNotBlank()){
                val id = databaseHandler.noviIdProizvoda()
                val proizvod = Proizvod(
                        id,
                    naziv.text.toString(),
                    slika.text.toString(),
                    stanje.text.toString().toInt(),
                    isporuka = dostava.text.toString().toInt(),
                    opis = opis.text.toString(),
                    drzava = drzavap.text.toString(),
                    cena = cena.text.toString().toDouble()
                )
                databaseHandler.dodajUBazu(proizvod)
            }
        }
    }

    companion object{
        fun newInstance() = DodajProizvodFragment()
    }
}
