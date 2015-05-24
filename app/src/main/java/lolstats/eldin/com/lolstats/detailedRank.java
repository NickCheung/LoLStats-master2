package lolstats.eldin.com.lolstats;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.core.common.QueueType;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.summoner.Summoner;
import com.robrua.orianna.type.exception.APIException;

/**
 * Created by Eldin on 18-5-2015.
 */
public class detailedRank extends AsyncTask<Void, Void, Void>{
    Activity o;
    static String sName;
    ProgressDialog p;
    Boolean found;

    String division;
    String total;
    String totalKDA;
    int iKills;
    int iAssists;
    int iDeaths;


    public detailedRank(Activity a){
        o = a;
    }

    @Override
    protected void onPreExecute(){
        p = ProgressDialog.show(o, "Loading", "Retrieving and calculating data..", true);
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            RiotAPI.setMirror(Region.EUW);
            RiotAPI.setRegion(Region.EUW);
            RiotAPI.setAPIKey("ebe43318-cee4-4d2a-bf19-1c195a32aa93");

            Summoner summoner = RiotAPI.getSummonerByName(sName);
            division =  summoner.getLeagueEntries().get(0).getTier() + " " +
                        summoner.getLeagueEntries().get(0).getParticipantEntry().getDivision() + " - " + summoner.getLeagueEntries().get(0).getParticipantEntry().getLeaguePoints() + " LP";
            int iWins = summoner.getLeagueEntries().get(0).getParticipantEntry().getWins();
            int iLosses = summoner.getLeagueEntries().get(0).getParticipantEntry().getLosses();
            String wins = iWins + "";
            String losses = iLosses + "";
            int iTotal = iWins + iLosses;

            total =  wins + " / " + losses + " / " + iTotal;

            iKills = summoner.getRankedStats().get(null).getStats().getTotalKills();
            iAssists = summoner.getRankedStats().get(null).getStats().getTotalAssists();
            iDeaths = summoner.getRankedStats().get(null).getStats().getTotalDeaths();

            totalKDA = iKills + " / " + iAssists + " / " + iDeaths;


            found = true;
        } catch (APIException e){
            found = false;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused){
        p.dismiss();
        if (found) {
            super.onPostExecute(unused);
            Intent intent = new Intent(o, detailedRankedPage.class);
            intent.putExtra("division", division);
            intent.putExtra("games", total);
            intent.putExtra("KDA", totalKDA);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            o.startActivity(intent);
        } else {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(o);
            builder1.setMessage("Oops! Something went wrong! Please try again later.");
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
