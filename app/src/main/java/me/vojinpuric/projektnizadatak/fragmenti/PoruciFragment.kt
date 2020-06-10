package me.vojinpuric.projektnizadatak.fragmenti

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.fragment_poruci.*
import me.vojinpuric.projektnizadatak.R
import me.vojinpuric.projektnizadatak.model.Korisnik

//Постоји страна где се потврђује корпа, на овој страни има могућност да се одабере да ли
//је ово потребно доставити на другу адресу која се разликује од оне почетно унете.
//Након коринсникове сагласноти излази дијалог прозор са информацијама на коју адресу стиже
//испорука, колико дана је потребно да стигне и колико је коначна цена поруџбине.

class PoruciFragment : Fragment() {
    private var ukupnaCena: Double = 0.0
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
        //TODO dohvatanje cene



        // listener za radio buttone
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                usporena.id -> ukupnaCena *= 0.9
                ubrzana.id -> ukupnaCena *= 1.2
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
            Korisnik.adresa=adresa.text.toString()
            //TODO dialog za potvrdu
        }
    }
}
