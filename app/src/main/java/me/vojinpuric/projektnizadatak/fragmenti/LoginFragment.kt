package me.vojinpuric.projektnizadatak.fragmenti

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_login.*
import me.vojinpuric.projektnizadatak.MainActivity
import me.vojinpuric.projektnizadatak.R
import me.vojinpuric.projektnizadatak.model.Korisnik


class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnNastavi.setOnClickListener {
            if (ime.text.isNotBlank() && prezime.text.isNotBlank() && email.text.isNotBlank() && adresa.text.isNotBlank() && drzavap.text.isNotBlank()){
                Korisnik.ime= ime.text.toString()
                Korisnik.prezime= prezime.text.toString()
                Korisnik.email= email.text.toString()
                Korisnik.adresa= adresa.text.toString()
                Korisnik.drzava= drzavap.text.toString()

                (activity as MainActivity).logIn()
            }else{
                Toast.makeText(context, "Morate popuniti sva polja",Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        fun newInstance(): LoginFragment =
            LoginFragment()
    }
}
