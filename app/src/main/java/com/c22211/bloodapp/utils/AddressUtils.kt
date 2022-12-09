package com.c22211.bloodapp.utils

object AddressUtils {

    private val divisions = listOf(
        "Pilih Kabupaten",
        "Kotamadya Yogyakarta",
        "Kab. Sleman",
        "Kab. Gunung Kidul",
        "Kab. Kulon Progo",
        "Kab. Bantul"
    )
    private val districts = hashMapOf(
        divisions[0] to listOf( "Pilih Kecamatan" ),
        divisions[1] to listOf(
            "Pilih Kecamatan",
            "Danurejan", "Gedongtengen", "Gondokusuman", "Gondomanan", "Jetis", "Kotagede",
            "Kraton", "Mantrijeron", "Mergangsan", "Ngampilan", "Pakualaman", "Tegalrejo",
            "Umbulharjo", "Wirobrajan"
        ),
        divisions[2] to listOf(
            "Pilih Kecamatan",
            "Bebah", "Cangkringan", "Depok", "Gamping", "Godean", "Kalasan", "Minggir", "Mlati",
            "Moyudan", "Ngaglik", "Ngemplak", "Pakem", "Prambanan", "Seyegan", "Sleman", "Tempel",
            "Turi"
        ),
        divisions[3] to listOf(
            "Pilih Kecamatan",
            "Gedangsari", "Girisubo", "Karangmojo", "Ngawen", "Nglipar", "Paliyan", "Panggang",
            "Patuk", "Playen", "Ponjong", "Purwosari", "Rongkop", "Saptosari", "Semanu", "Semin",
            "Tanjungsari", "Tepus", "Wonosari"
        ),
        divisions[4] to listOf(
            "Pilih Kecamatan",
            "Galur", "Girimulyo", "Kalibawang", "Kokap", "Lendah", "Nanggulan", "Panjatan",
            "Pengasih", "Samigaluh", "Sentolo", "Temon", "Wates"
        ),
        divisions[5] to listOf(
            "Pilih Kecamatan",
            "Bambangplipuro", "Banguntapan", "Bantul", "Dlingo", "Imogiri", "Jetis", "Kasihan",
            "Kretek", "Pajangan", "Pandak", "Piyungan", "Pleret", "Pundong", "Sanden", "Sedayu",
            "Sewon", "Srandakan"
        ),
    )

    private val thanas = hashMapOf(
        "Abul"  to listOf( "THana", "kana" ),
        "Kabul" to listOf( "KabulTHana", "Kabulkana" )
    )

    fun getDivisions():                  List<String> = divisions

    fun getDistrict( division: String ): List<String> = districts.get( division ) ?: listOf()

    fun getThan( district: String ):     List<String> = thanas[district] ?: listOf()
}