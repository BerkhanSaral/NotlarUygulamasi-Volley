package com.info.notlaruygulamasi

import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.info.notlaruygulamasi.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var notlarListe: ArrayList<Notlar>
    private lateinit var adapter: NotlarAdapter
    private lateinit var vt: VeritabaniYardimcisi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()


        binding.toolbar.title = "Notlar Uygulaması"
        setSupportActionBar(binding.toolbar)

        binding.rv.setHasFixedSize(true)
        binding.rv.layoutManager = LinearLayoutManager(this)

        vt = VeritabaniYardimcisi(this)

        notlarListe = Notlardao().tumNotlar(vt)

        tumNotlar()

        binding.fab.setOnClickListener {
            startActivity(Intent(this@MainActivity, NotKayitActivity::class.java))
        }

        onBackPressedDispatcher.addCallback(this) {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    fun tumNotlar() {
        var url = "http://kasimadalan.pe.hu/notlar/tum_notlar.php"

        val istek = StringRequest(Request.Method.GET, url, { cevap ->

            try {
                var toplam = 0
                notlarListe = ArrayList()

                val jsonObject = JSONObject(cevap)
                val notlar = jsonObject.getJSONArray("notlar")

                for (i in 0 until notlar.length()) {
                    val n = notlar.getJSONObject(i)

                    val not1 = n.getInt("not1")
                    val not2 = n.getInt("not2")


                    val not = Notlar(
                        n.getInt("not_id"),
                        n.getString("ders_adi"),
                        not1,
                        not2
                    )
                    notlarListe.add(not)
                    toplam += (not1 + not2) / 2
                }

                adapter = NotlarAdapter(this, notlarListe)
                binding.rv.adapter = adapter

                if (toplam != 0) {
                    binding.toolbar.subtitle = "Ortalama : ${toplam / notlarListe.size}"
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, { })

        Volley.newRequestQueue(this@MainActivity).add(istek)
    }
}
