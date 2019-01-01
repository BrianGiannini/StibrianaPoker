package fr.poker.stibriana.stibrianapoker

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast


class MainActivity : Activity() {

    private var points: Int = 0
    private var gain: Int = 0
    private var coef = doubleArrayOf(0.0, 5.0, 4.0, 3.0, 2.5, 2.25, 2.0, 1.75, 1.5, 1.25)
    private var coef2 = doubleArrayOf(0.0, 3.5, 2.0, 7 / 6.5)
    internal var joueurs = 0
    internal var place = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val joueursRecup = findViewById<View>(R.id.joueurs) as EditText
        val placeRecup = findViewById<View>(R.id.place) as EditText

        joueursRecup.setOnKeyListener { view, actionId, event ->
            if (KeyEvent.KEYCODE_ENTER == actionId) {
                sending()
            }
            false
        }

        placeRecup.setOnKeyListener { view, actionId, event ->
            if (KeyEvent.KEYCODE_ENTER == actionId) {
                sending()
            }
            false
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    fun sendMessage(view: View) {
        sending()
    }

    fun sending() {
        val joueursRecup = findViewById<View>(R.id.joueurs) as EditText
        val placeRecup = findViewById<View>(R.id.place) as EditText
        val pointsdisplay = findViewById<View>(R.id.textView) as TextView
        val gaindisplay = findViewById<View>(R.id.textView2) as TextView

        if (joueursRecup.text.toString().trim { it <= ' ' }.isEmpty()) {
            Toast.makeText(this, "Veuillez entrez le nombre de joueurs", Toast.LENGTH_SHORT).show()
        } else if (placeRecup.text.toString().trim { it <= ' ' }.isEmpty()) {
            Toast.makeText(this, "Veuillez entrez votre place", Toast.LENGTH_SHORT).show()
        } else {

            joueurs = Integer.valueOf(joueursRecup.text.toString())
            place = Integer.valueOf(placeRecup.text.toString())

            if (place > joueurs) {
                Toast.makeText(this, "Vous avez rentré de mauvaises informations", Toast.LENGTH_SHORT).show()
            } else {

                if (place > 9) {
                    points = Math.round((joueurs + 1 - place).toFloat())
                } else {
                    points = Math.round(coef[place] * (joueurs - place + 1)).toInt()
                }

                if (place < 4) {
                    gain = Math.round(joueurs * coef2[place]).toInt()
                } else if (place == 4) {
                    gain = joueurs * 7 - Math.round(joueurs * 3.5).toInt() - Math.round((joueurs * 2).toFloat()) - Math.round(joueurs * 7 / 6.5).toInt()
                } else {
                    gain = 0
                }

                pointsdisplay.text = points.toString() + "pts"
                gaindisplay.text = gain.toString() + "€"
            }
        }
    }

    fun goScore(view: View) {
        val uri = Uri.parse("https://docs.google.com/spreadsheets/d/1w6YO3aF3N5l0KGuf-y67v2m2EpgKSJn2Z97I4tpZFnQ/edit?usp=sharing")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}

