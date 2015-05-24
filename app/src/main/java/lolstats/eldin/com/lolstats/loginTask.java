package lolstats.eldin.com.lolstats;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.summoner.Summoner;
import com.robrua.orianna.type.exception.APIException;

/**
 * Created by Eldin on 17-5-2015.
 */
public class loginTask extends AsyncTask<Void, Void, Void> {
    static String sName;
    String userName;
    String userLevel;
    String wins;
    String losses;
    String kills;
    String assists;
    String deaths;
    String division;
    ProgressDialog p;

    Activity mActivity;

    public loginTask (Activity activity){
        mActivity = activity;
    }

    public String getName(String input){
        sName = input;
        return sName;
    }

    public boolean sFound;

    @Override
    protected void onPreExecute(){
        p = ProgressDialog.show(mActivity, "Loading", "Retrieving and calculating data..", true);
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            RiotAPI.setMirror(Region.EUW);
            RiotAPI.setRegion(Region.EUW);
            RiotAPI.setAPIKey("ebe43318-cee4-4d2a-bf19-1c195a32aa93");

            Summoner summoner = RiotAPI.getSummonerByName(sName);
            userName = summoner.getName();
            userLevel = summoner.getLevel() + "";

                try {
                    RiotAPI.setMirror(Region.EUW);
                    RiotAPI.setRegion(Region.EUW);
                    RiotAPI.setAPIKey("ebe43318-cee4-4d2a-bf19-1c195a32aa93");
                    wins = RiotAPI.getRankedStats(summoner).get(null).getStats().getTotalWins() + "";
                    losses = RiotAPI.getRankedStats(summoner).get(null).getStats().getTotalLosses() + "";
                    kills = RiotAPI.getRankedStats(summoner).get(null).getStats().getTotalKills() + "";
                    assists = RiotAPI.getRankedStats(summoner).get(null).getStats().getTotalAssists() + "";
                    deaths = RiotAPI.getRankedStats(summoner).get(null).getStats().getTotalDeaths() + "";
                    division = summoner.getLeagueEntries().get(0).getTier() + " " + summoner.getLeagueEntries().get(0).getParticipantEntry().getDivision() + " - " + summoner.getLeagueEntries().get(0).getParticipantEntry().getLeaguePoints() + "LP";
                } catch (APIException e){
                    wins = 0 + "";
                    losses = 0 + "";
                    kills = 0 + "";
                    assists = 0 + "";
                    deaths = 0 + "";
                    division = "No ranked games available..";
                }

            Log.d("","");
            sFound = true;

        } catch (APIException e){
            sFound = false;
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void unused){
        p.dismiss();
        if (sFound){
            super.onPostExecute(unused);
            Intent intent = new Intent(mActivity, OverviewPage.class);
            intent.putExtra("name", userName);
            intent.putExtra("level", userLevel);
            intent.putExtra("wins", wins);
            intent.putExtra("losses", losses);
            intent.putExtra("kills", kills);
            intent.putExtra("assists", assists);
            intent.putExtra("deaths", deaths);
            intent.putExtra("division", division);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mActivity.startActivity(intent);
        } else {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(mActivity);
            builder1.setMessage("Wrong username or region!");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            final AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }
}
