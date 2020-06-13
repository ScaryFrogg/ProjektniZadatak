package me.vojinpuric.projektnizadatak.model

data class Proizvod(
    val id:Int,
    val naziv: String,
    val slika: String,
    var stanje: Int,
    val opis: String,
    var isporuka: Int,
    val drzava : String,
    var cena: Double
) {
}