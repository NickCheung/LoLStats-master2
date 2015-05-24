package lolstats.eldin.com.lolstats;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

/**
 * Created by Eldin on 18-5-2015.
 */
public class currentMatchPage extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currentmatch);

        String userName = "";
        String currentGame = "";

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            userName = extras.getString("name");
            currentGame = extras.getString("currentGame");

        }
        TextView tvUserName = (TextView) findViewById(R.id.Username);
        tvUserName.setText(userName);

        TextView tvCurrentGame = (TextView) findViewById(R.id.CurrentGame);
        tvCurrentGame.setText(currentGame);
    }
}
