package com.orhanucar.artbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.orhanucar.artbook.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var artList: ArrayList<Art>
    private lateinit var artAdapter: ArtAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        artList = ArrayList<Art>()
        artAdapter = ArtAdapter(artList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = artAdapter

        try {

            val dataBase = this.openOrCreateDatabase("Arts", MODE_PRIVATE, null)
            val cursor = dataBase.rawQuery("SELECT * FROM arts", null)
            val artNameIx = cursor.getColumnIndex("artname")
            val idIx = cursor.getColumnIndex("id")

            while (cursor.moveToNext()) {
                val name = cursor.getString(artNameIx)
                val id = cursor.getInt(idIx)
                val art = Art(name,id)
                artList.add(art)
            }

            artAdapter.notifyDataSetChanged()
            cursor.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //inflater
        val menuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.art_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //intent
        if (item.itemId == R.id.add_art_item) {
            val intent = Intent(this@MainActivity,ArtActivity :: class.java)
            intent.putExtra("info","new")
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

}