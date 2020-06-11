package me.vojinpuric.projektnizadatak.fragmenti

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_proizvod.*
import me.vojinpuric.projektnizadatak.MainActivity

import me.vojinpuric.projektnizadatak.R
import me.vojinpuric.projektnizadatak.helpers.ProizvodListAdapter
import me.vojinpuric.projektnizadatak.model.DatabaseHandler
import me.vojinpuric.projektnizadatak.model.Proizvod


class ProizvodFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_proizvod, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val databaseHandler = DatabaseHandler(requireContext())
        val proizvod = databaseHandler.getProizvod(arguments!!["id"] as Int)
        (proizvod as Proizvod).also {
            cena.text= "Cena: "+it.cena.toString()
            naziv.text = it.naziv
            stanje.text = "Stanje: "+it.stanje.toString()
            dostava.text = "Vreme isporuke: ${it.isporuka} dana"
            opis.text = it.opis
            Picasso.get().load(it.slika).into(slika)
        }

    }
    companion object{
        fun newInstance()= ProizvodFragment()
    }
}
