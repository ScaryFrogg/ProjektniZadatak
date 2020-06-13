package me.vojinpuric.projektnizadatak.helpers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import me.vojinpuric.projektnizadatak.R
import me.vojinpuric.projektnizadatak.model.Korisnik
import me.vojinpuric.projektnizadatak.model.Proizvod

class ProizvodListAdapter(val lista: List<Proizvod>,val context: Context ,val otvoriProizvodListener : OtvoriProizvodListener) :
    RecyclerView.Adapter<ProizvodListAdapter.ProizvodViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProizvodViewHolder {
        return ProizvodViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ProizvodViewHolder, position: Int) {
        val proizvod = lista[position]
        Picasso.get().load(proizvod.slika).into(holder.slika)
        holder.cena.text = proizvod.cena.toString()
        holder.naslov.text = proizvod.naziv
        holder.seekBar.visibility = View.GONE
        holder.btnDodajUkorpu.setOnClickListener {

            val builder = AlertDialog.Builder(context)
            val inflater = LayoutInflater.from(context)
            builder.setTitle("Koliko ovakvih arikla zelite")
            val dialogLayout = inflater.inflate(R.layout.dialog_kolicina, null)
            val editText = dialogLayout.findViewById<TextView>(R.id.editText)
            builder.setView(dialogLayout as View)

            builder.setPositiveButton("Dodaj") { _, _ ->
                Korisnik.korpa[proizvod] = editText.text.toString().toInt()
            }
            builder.show()
        }
        holder.itemView.setOnLongClickListener {
            otvoriProizvodListener.izmeniProizvod(proizvod.id)
        }
        holder.itemView.setOnClickListener {
            otvoriProizvodListener.otvoriProizvod(proizvod.id)
        }
    }

    inner class ProizvodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val slika: ImageView = view.findViewById(R.id.slika)
        val naslov: TextView = view.findViewById(R.id.naslov)
        val cena: TextView = view.findViewById(R.id.cena)
        val btnDodajUkorpu: Button = view.findViewById(R.id.btnDodajUKorpu)
        val seekBar: TextView = view.findViewById(R.id.seekBar)
    }
    interface OtvoriProizvodListener{
        fun otvoriProizvod(id :Int) :Boolean
        fun izmeniProizvod(id : Int):Boolean
    }
}

//uklonjeno dugme dodaj u korpu
class KorpaListAdapter(val lista: HashMap<Proizvod, Int>,val context: Context,val updateCenaListener:UpdateCenu) :
    RecyclerView.Adapter<KorpaListAdapter.ProizvodViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProizvodViewHolder {
        return ProizvodViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ProizvodViewHolder, position: Int) {
        val proizvod = (lista.keys.toList())[position]
        Picasso.get().load(proizvod.slika).into(holder.slika)
        holder.naslov.text = proizvod.naziv
        holder.btnDodajUkorpu.visibility = View.GONE
        holder.cenaTw.text="Ukupna Cena:"
        holder.seekBar.text = "Kolicina : ${lista[proizvod]}"
        holder.cena.text = proizvod.cena.toString()
        holder.itemView.setOnLongClickListener{

            val builder = AlertDialog.Builder(context)
            val inflater = LayoutInflater.from(context)
            builder.setTitle("Koliko ovakvih arikla zelite")
            val dialogLayout = inflater.inflate(R.layout.dialog_kolicina, null)
            val editText = dialogLayout.findViewById<TextView>(R.id.editText)
            builder.setView(dialogLayout as View)

            builder.setPositiveButton("Dodaj") { _, i ->
               lista[proizvod] = editText.text.toString().toInt()
                notifyItemChanged(position)
                updateCenaListener.updateCenu()
            }
            builder.show()
            return@setOnLongClickListener true

        }

    }

    inner class ProizvodViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val slika: ImageView = view.findViewById(R.id.slika)
        val naslov: TextView = view.findViewById(R.id.naslov)
        val cena: TextView = view.findViewById(R.id.cena)
        val cenaTw: TextView = view.findViewById(R.id.cenaTw)
        val btnDodajUkorpu: Button = view.findViewById(R.id.btnDodajUKorpu)
        val seekBar: TextView = view.findViewById(R.id.seekBar)
    }
    interface UpdateCenu {
        fun updateCenu()
    }
}