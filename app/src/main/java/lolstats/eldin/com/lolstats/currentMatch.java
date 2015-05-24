package lolstats.eldin.com.lolstats;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.stats.PlayerStatsSummaryType;
import com.robrua.orianna.type.core.summoner.Summoner;
import com.robrua.orianna.type.exception.APIException;

/**
 * Created by Eldin on 18-5-2015.
 */
public class currentMatch extends AsyncTask<Void, Void, Void>{
    //static String sName;
    static String sName;
    String userName;
    String currentGame;
//    String Summoner1Name;
//    String Summoner2Name;
    public boolean sFound;

    ProgressDialog p;

    Activity mActivity;


    Activity o;
    public currentMatch(Activity activity){
        mActivity = activity;
    }

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
            String Player = summoner.getCurrentGame().getParticipants().toString();
            userName = summoner.getName();

            Player = Player.replaceAll("Participant", "").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("CurrentGameInfo", "");

            String[] parts = Player.split(", ");
            String Summoner1 = parts[0].replaceAll("^\\s+", "").replaceAll(" .*", "");
            String Summoner2 = parts[1].replaceAll("^\\s+", "").replaceAll(" .*", "");
            String Summoner3 = parts[2].replaceAll("^\\s+", "").replaceAll(" .*", "");
            String Summoner4 = parts[3].replaceAll("^\\s+", "").replaceAll(" .*", "");
            String Summoner5 = parts[4].replaceAll("^\\s+", "").replaceAll(" .*", "");
            String Summoner6 = parts[5].replaceAll("^\\s+", "").replaceAll(" .*", "");
            String Summoner7 = parts[6].replaceAll("^\\s+", "").replaceAll(" .*", "");
            String Summoner8 = parts[7].replaceAll("^\\s+", "").replaceAll(" .*", "");
            String Summoner9 = parts[8].replaceAll("^\\s+", "").replaceAll(" .*", "");
            String Summoner10 = parts[9].replaceAll("^\\s+", "").replaceAll(" .*", "");

            currentGame = ("\n"+ Summoner1 + "\n" + Summoner2 + "\n" + Summoner3 + "\n" + Summoner4 + "\n" + Summoner5 + "\n" + Summoner6 + "\n" + Summoner7 + "\n" + Summoner8 + "\n" + Summoner9 + "\n" + Summoner10);

            sFound = true;
        } catch (NullPointerException e)
        {
            sFound = false;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused){
        p.dismiss();
        if (sFound){
            super.onPostExecute(unused);
            Intent intent = new Intent(mActivity, currentMatchPage.class);
            intent.putExtra("name", userName);
            intent.putExtra("currentGame", currentGame);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mActivity.startActivity(intent);
        } else {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(mActivity);
            builder1.setMessage("Summoner not in game.");
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
