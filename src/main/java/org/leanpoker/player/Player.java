package org.leanpoker.player;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.leanpoker.player.model.Bet;
import org.leanpoker.player.model.Card;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Player {

    static final String VERSION = "2";

    public static int betRequest(JsonElement request) {
        Gson gson = new Gson();
        Bet bet = gson.fromJson(request, Bet.class);

        /*
        JsonArray communityCards = request.getAsJsonObject().getAsJsonArray("community_cards");
        int currentBuyIn = request.getAsJsonObject().getAsJsonPrimitive("current_buy_in").getAsInt();
        int playerInAction = request.getAsJsonObject().getAsJsonPrimitive("in_action").getAsInt();
        int playerBet = request.getAsJsonObject().getAsJsonArray("players").get(playerInAction)
              .getAsJsonObject().getAsJsonPrimitive("bet").getAsInt();

        int smallBlind = request.getAsJsonObject().getAsJsonPrimitive("small_blind").getAsInt();
*/

        int bigBlind = bet.getSmall_blind() * 2;
        int minimalBet = bet.getCurrent_buy_in() - bet.getPlayers()[bet.getIn_action()].getBet();

        //Erste Runde
        List<Card> communityCards = Arrays.asList(bet.getCommunityCards());

        if (communityCards.size() == 0) {
            if(bigBlind == bet.getCurrent_buy_in()){
                return minimalBet;
            }
        }
        //Zweite Runde
        if (communityCards.size() == 3) {
            if(bigBlind == bet.getCurrent_buy_in()){
                return minimalBet;
            }
        }
        //Dritte Runde
        if (communityCards.size() == 4) {

        }
        //Vierte Runde
        if (communityCards.size() == 5) {

        }

        return 0;
    }

    public static void showdown(JsonElement game) {
    }
}
