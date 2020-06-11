package me.vojinpuric.projektnizadatak.fragmenti

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_korpa.*
import me.vojinpuric.projektnizadatak.helpers.KorpaListAdapter
import me.vojinpuric.projektnizadatak.MainActivity

import me.vojinpuric.projektnizadatak.R
import me.vojinpuric.projektnizadatak.model.Korisnik


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

        val updateCenaListener = object : KorpaListAdapter.UpdateCenu{
            override fun updateCenu() {
                izracunajCenu()
            }
        }

        val korpaAdapter =
            KorpaListAdapter(Korisnik.korpa, context!!, updateCenaListener)
        recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = korpaAdapter
        }
        btnKupi.setOnClickListener {
            if(tvUkupnaCena.text.toString().toDouble()>0){
                (activity as MainActivity).openFragment(PoruciFragment.newInstance())
            }
        }
        izracunajCenu()

        val touchHelperCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Korisnik.korpa.remove((Korisnik.korpa.keys.toList())[viewHolder.adapterPosition])
                korpaAdapter.notifyDataSetChanged()
                izracunajCenu()
            }
        }
        ItemTouchHelper(touchHelperCallback).apply { attachToRecyclerView(recycler) }


    }

    fun izracunajCenu() {
        tvUkupnaCena.text = Korisnik.korpa.keys.map {
            it.cena * Korisnik.korpa[it]!!
        }.sum().toString()
    }


    companion object {
        fun newInstance() = KorpaFragment()
    }
}
