package com.info.notlaruygulamasi

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.info.notlaruygulamasi.databinding.ActivityNotKayitBinding
import org.json.JSONObject

class NotKayitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotKayitBinding
    private lateinit var vt: VeritabaniYardimcisi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotKayitBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        vt = VeritabaniYardimcisi(this)

        binding.toolbarNotKayit.title = "Not Kayıt"
        setSupportActionBar(binding.toolbarNotKayit)

        binding.buttonKaydet.setOnClickListener {

            /*val ders_adi = binding.editTextDers.text.toString().trim()
            val not1 = binding.editTextNot1.text.toString().trim().toString()
            val not2 = binding.editTextNot2.text.toString().trim().toString()

            if (TextUtils.isEmpty(ders_adi)) {
                Snackbar.make(binding.toolbarNotKayit, "Ders Adi Giriniz", Snackbar.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(not1)) {
                Snackbar.make(binding.toolbarNotKayit, "1.Notu Giriniz", Snackbar.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(not2)) {
                Snackbar.make(binding.toolbarNotKayit, "2.Notu Giriniz", Snackbar.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            Notlardao().notEkle(vt, ders_adi, not1.toInt(), not2.toInt())
*/

            val ders_adi = binding.editTextDers.text.toString().trim()
            val not1 = binding.editTextNot1.text.toString().trim()
            val not2 = binding.editTextNot2.text.toString().trim()

            if (TextUtils.isEmpty(ders_adi)) {
                Snackbar.make(binding.toolbarNotKayit, "Ders Adini Giriniz", Snackbar.LENGTH_SHORT)
                    .show()

                return@setOnClickListener
            }

            if (TextUtils.isEmpty(not1)) {
                Snackbar.make(binding.toolbarNotKayit, "1.Notu Giriniz", Snackbar.LENGTH_SHORT)
                    .show()

                return@setOnClickListener
            }

            if (TextUtils.isEmpty(not2)) {
                Snackbar.make(binding.toolbarNotKayit, "2.Notu  Giriniz", Snackbar.LENGTH_SHORT)
                    .show()

                return@setOnClickListener
            }

            notKayit(ders_adi, not1.toInt(), not2.toInt())

            startActivity(Intent(this@NotKayitActivity, MainActivity::class.java))
            finish()
        }

    }

    fun notKayit(ders_ad: String, not1: Int, not2: Int) {
        var url = "http://kasimadalan.pe.hu/notlar/insert_not.php"

        val istek = object : StringRequest(Request.Method.POST, url, { cevap ->


        }, { }) {
            override fun getParams(): Map<String?, String?>? {
                val params = HashMap<String?, String?>()
                params["ders_adi"] = ders_ad
                params["not1"] = not1.toString()
                params["not2"] = not2.toString()

                return params
            }
        }

        Volley.newRequestQueue(this@NotKayitActivity).add(istek)
    }


}