package lolstats.eldin.com.lolstats;

import android.util.Log;

import com.robrua.orianna.api.core.AsyncRiotAPI;
import com.robrua.orianna.type.api.Action;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.summoner.Summoner;
import com.robrua.orianna.type.exception.APIException;

/**
 * Created by Eldin on 12-5-2015.
 */
public class Connect {
    String sLevel;

    public void connectRiot(){
        AsyncRiotAPI.setRegion(Region.EUW);
        AsyncRiotAPI.setMirror(Region.EUW);
        AsyncRiotAPI.setAPIKey("445757e6-c061-4cd3-8216-28d92481d8ed");
    }

    public void getSummoner(final String name){
        final OverviewPage o = new OverviewPage();

    }
}
