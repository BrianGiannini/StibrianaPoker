package fr.poker.stibriana.stibrianapoker;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    int points;
    int gain;
    double coef[]={0,5,4,3,2.5,2.25,2,1.75,1.5,1.25};
    double coef2[]={0,7/2,2,7/6.5};
    int joueurs=0;
    int place=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        EditText joueursRecup = (EditText) findViewById(R.id.joueurs);
        EditText placeRecup = (EditText) findViewById(R.id.place);
        TextView Pointsdisplay = (TextView) findViewById(R.id.textView);
        TextView Gaindisplay = (TextView) findViewById(R.id.textView2);

        joueurs = Integer.valueOf(joueursRecup.getText().toString());
        place = Integer.valueOf(placeRecup.getText().toString());

        if (place > 9)
        {
            points = (int)Math.round((joueurs+1-place));
        }
        else
        {
            points = (int)Math.round(coef[place]*(joueurs-place+1));
        }

        if (place < 4)
        {
            gain = (int)Math.round((joueurs * coef2[place]));
        }
        else if (place == 4)
        {
            gain = joueurs*7-(int)Math.round(joueurs*3.5)-(int)Math.round(joueurs*2)-(int)Math.round(joueurs*7/6.5);
        }
        else
        {
            gain = 0;
        }

        Pointsdisplay.setText(String.valueOf(points));
        Gaindisplay.setText(String.valueOf(gain));
    }
}