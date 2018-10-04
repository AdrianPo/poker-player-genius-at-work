package org.leanpoker.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.Map;

public class Player {

    static final String VERSION = "1";

    public static int betRequest(JsonElement request) {
        JsonArray communityCards = request.getAsJsonObject().getAsJsonArray("community_cards");
        int currentBuyIn = request.getAsJsonObject().getAsJsonPrimitive("current_buy_in").getAsInt();
        int playerInAction = request.getAsJsonObject().getAsJsonPrimitive("in_action").getAsInt();
        int playerBet = request.getAsJsonObject().getAsJsonArray("players").get(playerInAction)
              .getAsJsonObject().getAsJsonPrimitive("bet").getAsInt();

        int smallBlind = request.getAsJsonObject().getAsJsonPrimitive("small_blind").getAsInt();

        int bigBlind = smallBlind * 2;
        int minimalBet = currentBuyIn - playerBet;
        //Erste Runde
        if (communityCards.size() == 0) {
            if(bigBlind == currentBuyIn){
                return minimalBet;
            }
        }
        //Zweite Runde
        if (communityCards.size() == 3) {
            if(bigBlind == currentBuyIn){
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
