package me.vojinpuric.projektnizadatak.fragmenti

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_poruci.*
import me.vojinpuric.projektnizadatak.R
import me.vojinpuric.projektnizadatak.model.Korisnik
import java.util.*

class PoruciFragment : Fragment() {
    private var ukupnaCena = 0.0
    private var danaDoDostave = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_poruci, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adresa.setText(Korisnik.adresa)

        //ukupna cena
        ukupnaCena = Korisnik.korpa.keys.map { it.cena * Korisnik.korpa[it]!! }.sum()
        tvUkupnaCena.text = ukupnaCena.toString()

        val prozvodZaIsporuku = Korisnik.korpa.keys.maxBy { if (it.drzava == Korisnik.drzava) {it.isporuka}else {it.isporuka+1}  }
        danaDoDostave = if(prozvodZaIsporuku!!.drzava == Korisnik.drzava){
            prozvodZaIsporuku.isporuka
        }else {
            prozvodZaIsporuku.isporuka+1
        }
        isporuka.text = danaDoDostave.toString()

        // listener za radio buttone
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                usporena.id -> {
                    isporuka.text = (danaDoDostave+2).toString()
                    tvUkupnaCena.text = (ukupnaCena * 0.9).toString()
                }
                ubrzana.id -> {
                    isporuka.text= if(danaDoDostave-1<2) "1" else (danaDoDostave-1).toString()
                    tvUkupnaCena.text = (ukupnaCena * 1.2).toString()
                }
                else -> {
                    isporuka.text=danaDoDostave.toString()
                    tvUkupnaCena.text = ukupnaCena.toString()
                }
            }
        }

        //opcije dostave definisane u values folderu
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.naciniDostave,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        btnPotvrdi.setOnClickListener {
            Korisnik.adresa = adresa.text.toString()
            val builder = AlertDialog.Builder(context)
            with(builder)
            {
                setTitle("Uspesna kupovina")
                setMessage("""
                    Vaš račun je ${tvUkupnaCena.text}
                    Predmeti će biti dostavljeni za ${isporuka.text} dana na adresu ${adresa.text}
                """.trimIndent())
                setNeutralButton("Ok") { _, _-> }
                show()
            }
        }
    }

    companion object {
        fun newInstance() = PoruciFragment()
    }
}
