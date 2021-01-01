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
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt

const val LINK_SCORE_SHEET = "https://docs.google.com/spreadsheets/d/1hdUt6IjVTEQW_RgmaxluXW4IRBAMucE0MBxc-ypa9pg/edit?usp=sharing"

class MainActivity : Activity() {

    private var points: Int = 0
    private var gain: Int = 0
    private var coef = doubleArrayOf(0.0, 5.0, 4.0, 3.0, 2.5, 2.25, 2.0, 1.75, 1.5, 1.25)
    private var coef2 = doubleArrayOf(0.0, 3.5, 2.0, 7 / 6.5)
    private var playersNumber = 0
    private var placeNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val players = findViewById<View>(R.id.players) as EditText
        val place = findViewById<View>(R.id.place_editext) as EditText

        players.setOnKeyListener { _, actionId, event ->
            if (KeyEvent.KEYCODE_ENTER == actionId) {
                sending()
            }
            false
        }

        place.setOnKeyListener { _, actionId, _ ->
            if (KeyEvent.KEYCODE_ENTER == actionId) {
                sending()
            }
            false
        }

        send_result.setOnClickListener {
            sending()
        }

        goToScore.setOnClickListener {
            goScore()
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

    private fun sending() {
        if (players.text.toString().trim { it <= ' ' }.isEmpty()) {
            Toast.makeText(this, getString(R.string.enter_players_number_text), Toast.LENGTH_SHORT).show()
        } else if (place_editext.text.toString().trim { it <= ' ' }.isEmpty()) {
            Toast.makeText(this, getString(R.string.enter_place_text), Toast.LENGTH_SHORT).show()
        } else {

            playersNumber = Integer.valueOf(players.text.toString())
            placeNumber = Integer.valueOf(place_editext.text.toString())

            if (placeNumber > playersNumber) {
                Toast.makeText(this, getString(R.string.wrong_informaiton_message_text), Toast.LENGTH_SHORT).show()
            } else {

                points = if (placeNumber > 9) {
                    (playersNumber + 1 - placeNumber).toFloat().roundToInt()
                } else {
                    (coef[placeNumber] * (playersNumber - placeNumber + 1)).roundToInt()
                }

                gain = if (placeNumber < 4) {
                    (playersNumber * coef2[placeNumber]).roundToInt()
                } else if (placeNumber == 4) {
                    playersNumber * 7 - (playersNumber * 3.5).roundToInt() - (playersNumber * 2).toFloat().roundToInt() - (playersNumber * 7 / 6.5).roundToInt()
                } else {
                    0
                }

                points_txt.text = getString(R.string.showed_pts, points)
                gain_txt.text = getString(R.string.showed_gain, gain)
            }
        }
    }

    private fun goScore() {
        val uri = Uri.parse(LINK_SCORE_SHEET)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}

