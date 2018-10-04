package org.leanpoker.player;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.leanpoker.player.model.Bet;
import org.leanpoker.player.model.Card;

import java.util.Arrays;
import java.util.List;

public class Player {

    static final String VERSION = "5";

    public static int betRequest(JsonElement request) {
        Gson gson = new Gson();
        Bet bet = gson.fromJson(request, Bet.class);

        // ermittle unseren Spieler
        org.leanpoker.player.model.Player myPlayer = getMyPlayer(bet);
        int cardIndex = checkCardsOnHand(myPlayer);
        int bigBlind = bet.getSmall_blind() * 2;
        int minimalBet = bet.getCurrent_buy_in() - bet.getPlayers()[bet.getIn_action()].getBet();

        //Erste Runde
        List<Card> communityCards = Arrays.asList(bet.getCommunityCards());

        if (communityCards.size() == 0) {
            if(bigBlind == bet.getCurrent_buy_in()){
                return minimalBet;
            }
            //Gehe in der ersten Runde all in, wenn Paar auf der Hand
            if(cardIndex > 91){
                return myPlayer.getStack();
            }
            if(cardIndex >= 80){
                if((minimalBet * 4) < myPlayer.getStack()){
                    return minimalBet * 4;
                }
            }
            if(cardIndex >= 70){
                if((minimalBet * 3) < myPlayer.getStack()){
                    return minimalBet * 3;
                }
            }
            if(cardIndex >= 60){
                if((minimalBet * 2) < myPlayer.getStack()){
                    return minimalBet * 2;
                }
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

    public static int checkCardsOnHand(org.leanpoker.player.model.Player player){
        Card firstCard = player.getHole_cards()[0];
        Card secondCard = player.getHole_cards()[1];

        //Überprüfen ob paar auf der Hand
        if(firstCard.getRank().equalsIgnoreCase(secondCard.getRank())){
            if(getCardNumber(firstCard) > 0 && getCardNumber(secondCard) > 0){
                return 95;
            }
            return 100;
        }

        //Überprüfe ob wir hoche Karten auf der Hand haben
        if(cardIsHigh(firstCard) && cardIsHigh(secondCard)){
            if(firstCard.getSuit().equalsIgnoreCase(secondCard.getSuit())){
                return 90;
            }
            return 85;
        }

        //Überprüfe ob wir mind. eine hoche Karten auf der Hand haben
        if(cardIsHigh(firstCard) || cardIsHigh(secondCard)){
            if(firstCard.getSuit().equalsIgnoreCase(secondCard.getSuit())){
                return 80;
            }
            return 75;
        }

        //Überprüfe ob wir dieselbe farbe der Karten auf der Hand haben
        if(firstCard.getSuit().equalsIgnoreCase(secondCard.getSuit())){
            return 70;
        }

        return 60;
    }

    public static org.leanpoker.player.model.Player getMyPlayer(Bet bet){
        return bet.getPlayers()[bet.getIn_action()];
    }

    public static boolean cardIsHigh(Card card){
        try{
            Integer.parseInt(card.getRank());
            return false;
        } catch (Exception e){
            return true;
        }
    }

    private static int getCardNumber(Card card){
        try{
            return Integer.parseInt(card.getRank());
        } catch (Exception e){
            return 0;
        }
    }
}
