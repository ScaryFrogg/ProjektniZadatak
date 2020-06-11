package me.vojinpuric.projektnizadatak.fragmenti

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import me.vojinpuric.projektnizadatak.MainActivity
import me.vojinpuric.projektnizadatak.helpers.ProizvodListAdapter
import me.vojinpuric.projektnizadatak.R
import me.vojinpuric.projektnizadatak.model.DatabaseHandler
import me.vojinpuric.projektnizadatak.model.Proizvod


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val databaseHandler = DatabaseHandler(requireContext())

        val proizvodi: List<Proizvod> = databaseHandler.ocitajProizvode()
        val otvoriProizvodListener = object : ProizvodListAdapter.OtvoriProizvodListener {
            override fun otvoriProizvod(id: Int): Boolean {
                val data = bundleOf("id" to id)
                (activity as MainActivity).openFragment(ProizvodFragment.newInstance(), data)
                return true
            }
        }
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter =
            ProizvodListAdapter(
                proizvodi,
                requireContext(),
                otvoriProizvodListener
            )

    }

    companion object {
        fun newInstance(): HomeFragment =
            HomeFragment()
    }
}
