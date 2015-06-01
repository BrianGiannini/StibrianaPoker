package fr.poker.stibriana.stibrianapoker;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    int points;
    int gain;
    double coef[] = {0, 5, 4, 3, 2.5, 2.25, 2, 1.75, 1.5, 1.25};
    double coef2[] = {0, 3.5, 2, 7 / 6.5};
    int joueurs = 0;
    int place = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText joueursRecup = (EditText) findViewById(R.id.joueurs);
        EditText placeRecup = (EditText) findViewById(R.id.place);

        joueursRecup.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int actionId, KeyEvent event) {
                if (KeyEvent.KEYCODE_ENTER == actionId) {
                    sending();
                }
                return false;
            }
        });

        placeRecup.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int actionId, KeyEvent event) {
                if (KeyEvent.KEYCODE_ENTER == actionId) {
                    sending();
                }
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View view) {

        sending();

    }

    public void sending(){
        EditText joueursRecup = (EditText) findViewById(R.id.joueurs);
        EditText placeRecup = (EditText) findViewById(R.id.place);
        TextView Pointsdisplay = (TextView) findViewById(R.id.textView);
        TextView Gaindisplay = (TextView) findViewById(R.id.textView2);

        if (joueursRecup.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Veuillez entrez le nombre de joueurs", Toast.LENGTH_SHORT).show();
        } else if (placeRecup.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Veuillez entrez votre place", Toast.LENGTH_SHORT).show();
        } else {

            joueurs = Integer.valueOf(joueursRecup.getText().toString());
            place = Integer.valueOf(placeRecup.getText().toString());

            if (place > joueurs) {
                Toast.makeText(this, "Vous avez rentré de mauvaises informations", Toast.LENGTH_SHORT).show();
            } else {

                if (place > 9) {
                    points = (int) Math.round((joueurs + 1 - place));
                } else {
                    points = (int) Math.round(coef[place] * (joueurs - place + 1));
                }

                if (place < 4) {
                    gain = (int) Math.round((joueurs * coef2[place]));
                } else if (place == 4) {
                    gain = joueurs * 7 - (int) Math.round(joueurs * 3.5) - (int) Math.round(joueurs * 2) - (int) Math.round(joueurs * 7 / 6.5);
                } else {
                    gain = 0;
                }

                Pointsdisplay.setText(String.valueOf(points) + "pts");
                Gaindisplay.setText(String.valueOf(gain) + "€");
            }
        }
    }

    public void goScore(View view) {
        Uri uri = Uri.parse("https://docs.google.com/spreadsheets/d/1J64YMpKCAftDtn9-YyFMVYranCEjTOKGyP9nzkgpY4I/edit?usp=sharing");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}

