package lolstats.eldin.com.lolstats;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Eldin on 12-5-2015.
 */
public class OverviewPage extends ActionBarActivity {

    public void btnCurr(View w){
        startSeq();
    }

    public void btnRanked(View w){
        startRanked();
    }

    private void startRanked(){
        TextView tvS = (TextView) findViewById(R.id.test);
        detailedRank d = new detailedRank(OverviewPage.this);
        d.sName = tvS.getText() + "";
        new detailedRank(OverviewPage.this).execute();
    }

    private void setName(){
        TextView tvS = (TextView) findViewById(R.id.test);
        currentMatch c = new currentMatch(OverviewPage.this);
        c.sName = tvS.getText() + "";
    }

    private void startSeq(){
        setName();
        new currentMatch(OverviewPage.this).execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview_page);


        String message = "";
        String level = "";
        String wins = "";
        String losses = "";
        String kills = "";
        String assists = "";
        String deaths = "";
        String division = "";
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            message = extras.getString("name");
            level = extras.getString("level");
            wins = extras.getString("wins");
            losses = extras.getString("losses");
            kills = extras.getString("kills");
            assists = extras.getString("assists");
            deaths = extras.getString("deaths");
            division = extras.getString("division");
        }

        TextView tv = (TextView) findViewById(R.id.test);
        tv.setText(message);

        TextView tvLevel = (TextView) findViewById(R.id.level);
        tvLevel.setText("Level: " + level);

        TextView tvWins = (TextView) findViewById(R.id.wins);
        tvWins.setText("Wins: " + wins);

        TextView tvLosses = (TextView) findViewById(R.id.losses);
        tvLosses.setText("Losses: " + losses);

        TextView tvKills = (TextView) findViewById(R.id.kills);
        tvKills.setText("Kills: " + kills);

        TextView tvAssists = (TextView) findViewById(R.id.assists);
        tvAssists.setText("Assists: " + assists);

        TextView tvDeaths = (TextView) findViewById(R.id.deaths);
        tvDeaths.setText("Deaths: " + deaths);

        TextView tvDivision = (TextView) findViewById(R.id.division);
        tvDivision.setText(division);
    }
}
