package com.info.notlaruygulamasi

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.info.notlaruygulamasi.databinding.ActivityDetayBinding

class DetayActivity : AppCompatActivity() {
    private lateinit var not: Notlar
    private lateinit var vt: VeritabaniYardimcisi
    private lateinit var binding: ActivityDetayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        vt = VeritabaniYardimcisi(this)

        binding.toolbarNotDetay.title = "Not Detay"
        setSupportActionBar(binding.toolbarNotDetay)
        not = intent.getSerializableExtra("nesne") as Notlar
        binding.editTextDetayDers.setText(not.ders_adi)
        binding.editTextDetayNot1.setText((not.not1).toString())
        binding.editTextDetayNot2.setText((not.not2).toString())


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater?.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_sil -> {
                Snackbar.make(
                    binding.toolbarNotDetay,
                    "Not Silinsin mi?",
                    Snackbar.LENGTH_SHORT
                )
                    .setAction("Evet") {

                        notSil(not.not_id)

                        startActivity(Intent(this@DetayActivity, MainActivity::class.java))
                        finish()
                    }.show()
                return true
            }

            R.id.action_duzenle -> {
                Snackbar.make(
                    binding.toolbarNotDetay,
                    "Not Duzenlendi",
                    Snackbar.LENGTH_SHORT
                )
                    .setAction("Evet")
                    {
                        val ders_adi = binding.editTextDetayDers.text.toString().trim()
                        val not1 = binding.editTextDetayNot1.text.toString().trim().toString()
                        val not2 = binding.editTextDetayNot2.text.toString().trim().toString()

                        if (TextUtils.isEmpty(ders_adi)) {
                            Snackbar.make(
                                binding.toolbarNotDetay,
                                "Ders Adi Giriniz",
                                Snackbar.LENGTH_SHORT
                            )
                                .show()
                            return@setAction
                        }

                        if (TextUtils.isEmpty(not1)) {
                            Snackbar.make(
                                binding.toolbarNotDetay,
                                "1.Notu Giriniz",
                                Snackbar.LENGTH_SHORT
                            )
                                .show()
                            return@setAction
                        }

                        if (TextUtils.isEmpty(not2)) {
                            Snackbar.make(
                                binding.toolbarNotDetay,
                                "2.Notu Giriniz",
                                Snackbar.LENGTH_SHORT
                            )
                                .show()
                            return@setAction
                        }

                        notGuncelle(not.not_id, ders_adi, not1.toInt(), not2.toInt())

                        startActivity(Intent(this@DetayActivity, MainActivity::class.java))
                        finish()
                    }.show()
                return true
            }

            else -> return false
        }
        return super.onOptionsItemSelected(item)
    }

    fun notGuncelle(not_id: Int, ders_ad: String, not1: Int, not2: Int) {
        var url = "http://kasimadalan.pe.hu/notlar/update_not.php"

        val istek = object : StringRequest(Method.POST, url, Response.Listener { cevap ->


        }, Response.ErrorListener {}) {
            override fun getParams(): Map<String?, String?>? {
                val params = HashMap<String?, String?>()
                params["not_id"] = not_id.toString()
                params["ders_adi"] = ders_ad
                params["not1"] = not1.toString()
                params["not2"] = not2.toString()
                return params
            }
        }
        Volley.newRequestQueue(this@DetayActivity).add(istek)
    }

    fun notSil(not_id: Int) {
        var url = "http://kasimadalan.pe.hu/notlar/delete_not.php"

        val istek = object : StringRequest(Method.POST, url, Response.Listener { cevap ->


        }, Response.ErrorListener {}) {
            override fun getParams(): Map<String?, String?>? {
                val params = HashMap<String?, String?>()
                params["not_id"] = not_id.toString()

                return params
            }
        }
        Volley.newRequestQueue(this@DetayActivity).add(istek)
    }
}