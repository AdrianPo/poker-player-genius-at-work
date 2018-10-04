package org.leanpoker.player;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.leanpoker.player.model.Bet;
import org.leanpoker.player.model.Card;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Player {

   static final String VERSION = "11";

   public final static Logger LOGGER = Logger.getAnonymousLogger();

   public static int betRequest(JsonElement request) {
      Gson gson = new Gson();
      Bet bet = gson.fromJson(request, Bet.class);

      // ermittle unseren Spieler
      org.leanpoker.player.model.Player myPlayer = getMyPlayer(bet);
      int cardIndex = checkCardsOnHand(myPlayer);
      int bigBlind = bet.getSmall_blind() * 2;
      int minimalBet = bet.getCurrent_buy_in() - bet.getPlayers()[bet.getIn_action()].getBet();

      LOGGER.info("Load Community Cards");

      //Erste Runde
      List<Card> communityCards = Arrays.asList(bet.getCommunity_cards());

      LOGGER.info("Card Index: " + cardIndex);

      if (communityCards.size() == 0) {
         if (bigBlind == bet.getCurrent_buy_in()) {
            LOGGER.info("Set Big Blind Round 0 " + cardIndex);
            return minimalBet;
         }
         //Gehe in der ersten Runde all in, wenn Paar auf der Hand
         if (cardIndex > 91) {
            LOGGER.info("Go All-In Round 0 " + cardIndex);
            return myPlayer.getStack();
         }
         if (cardIndex >= 80) {
            LOGGER.info("Go 80 Round 0 " + cardIndex);
            if (minimalBet < myPlayer.getStack()) {
               long currentValue = minimalBet / myPlayer.getStack();
               if (currentValue > 0) {
                  return minimalBet;
               }
            }
         }
         if (cardIndex >= 70) {
            LOGGER.info("Go 70 Round 0 " + cardIndex);
            if (minimalBet < myPlayer.getStack()) {
               int currentValue = myPlayer.getStack() - minimalBet;
               if (currentValue > 0) {
                  return minimalBet;
               }
            }
         }
         if (cardIndex >= 60) {
            LOGGER.info("Go 60 Round 0 " + cardIndex);
            if (minimalBet < myPlayer.getStack()) {
               int currentValue = myPlayer.getStack() - minimalBet;
               if (currentValue > 0) {
                  return minimalBet;
               }
            }
         }
      }
      //Zweite Runde
      if (communityCards.size() == 3) {

         if (bigBlind == bet.getCurrent_buy_in()) {
            return minimalBet;
         }

         int countMatches = CardUtil.countMatchesWithCommunityCards(bet.getCommunity_cards(), myPlayer.getHole_cards());
         int suitCounter = CardUtil.suitMatch(communityCards, myPlayer.getHole_cards()[0], myPlayer.getHole_cards()[1]);
         boolean hasTripleOrFour = CardUtil.hasTripleOrFour(communityCards, myPlayer.getHole_cards()[0], myPlayer.getHole_cards()[1]);

         if (hasTripleOrFour) {
            return myPlayer.getStack();
         }

         if (suitCounter == 5) {
            return myPlayer.getStack();
         }
         if (countMatches == 0) {
            if (cardIndex < 90 && cardIndex >= 70) {
               return bet.getCurrent_buy_in();
            }
         } else if (countMatches == 1) {
            return raise(myPlayer, bet, minimalBet, 20);
         } else if (countMatches == 2) {
            return raise(myPlayer, bet, minimalBet, 40);
         }
      }
      //Dritte Runde
      if (communityCards.size() == 4) {
         int countMatches = CardUtil.countMatchesWithCommunityCards(bet.getCommunity_cards(), myPlayer.getHole_cards());
         int suitCounter = CardUtil.suitMatch(communityCards, myPlayer.getHole_cards()[0], myPlayer.getHole_cards()[1]);
         boolean hasTripleOrFour = CardUtil.hasTripleOrFour(communityCards, myPlayer.getHole_cards()[0], myPlayer.getHole_cards()[1]);

         if (hasTripleOrFour) {
            return myPlayer.getStack();
         }

         if (suitCounter == 5) {
            return myPlayer.getStack();
         }

         if (countMatches == 0) {
            if (cardIndex < 90 && cardIndex >= 70) {
               return bet.getCurrent_buy_in();
            }
         } else if (countMatches == 1) {
            return raise(myPlayer, bet, minimalBet, 20);
         } else if (countMatches == 2) {
            return raise(myPlayer, bet, minimalBet, 40);
         }
      }
      //Vierte Runde
      if (communityCards.size() == 5) {
         int countMatches = CardUtil.countMatchesWithCommunityCards(bet.getCommunity_cards(), myPlayer.getHole_cards());
         int suitCounter = CardUtil.suitMatch(communityCards, myPlayer.getHole_cards()[0], myPlayer.getHole_cards()[1]);
         boolean hasTripleOrFour = CardUtil.hasTripleOrFour(communityCards, myPlayer.getHole_cards()[0], myPlayer.getHole_cards()[1]);

         if (hasTripleOrFour) {
            return myPlayer.getStack();
         }

         if (suitCounter == 5) {
            return myPlayer.getStack();
         }

         if (countMatches == 1) {
            return raise(myPlayer, bet, minimalBet, 20);
         } else if (countMatches == 2) {
            return raise(myPlayer, bet, minimalBet, 80);
         }
      }

      return 0;
   }

   public static void showdown(JsonElement game) {
   }

   public static int checkCardsOnHand(org.leanpoker.player.model.Player player) {
      if (player.getHole_cards().length == 2) {
         Card firstCard = player.getHole_cards()[0];
         Card secondCard = player.getHole_cards()[1];

         //Überprüfen ob paar auf der Hand
         if (firstCard.getRank().equalsIgnoreCase(secondCard.getRank())) {
            if (getCardNumber(firstCard) > 0 && getCardNumber(secondCard) > 0) {
               return 95;
            }
            return 100;
         }

         //Überprüfe ob wir hoche Karten auf der Hand haben
         if (cardIsHigh(firstCard) && cardIsHigh(secondCard)) {
            if (firstCard.getSuit().equalsIgnoreCase(secondCard.getSuit())) {
               return 90;
            }
            return 85;
         }

         //Überprüfe ob wir mind. eine hoche Karten auf der Hand haben
         if (cardIsHigh(firstCard) || cardIsHigh(secondCard)) {
            if (firstCard.getSuit().equalsIgnoreCase(secondCard.getSuit())) {
               return 80;
            }
            return 75;
         }

         //Überprüfe ob wir dieselbe farbe der Karten auf der Hand haben
         if (firstCard.getSuit().equalsIgnoreCase(secondCard.getSuit())) {
            return 70;
         }

         return 60;
      }
      return 0;
   }

   public static org.leanpoker.player.model.Player getMyPlayer(Bet bet) {
      return bet.getPlayers()[bet.getIn_action()];
   }

   public static boolean cardIsHigh(Card card) {
      try {
         Integer.parseInt(card.getRank());
         return false;
      } catch (Exception e) {
         return true;
      }
   }

   private static int getCardNumber(Card card) {
      try {
         return Integer.parseInt(card.getRank());
      } catch (Exception e) {
         return 0;
      }
   }

   private static int raise(org.leanpoker.player.model.Player myPlayer, Bet bet, int minimalBet, int amount) {
      if (myPlayer.getStack() >= bet.getCurrent_buy_in()) {

         if (myPlayer.getStack() >= (minimalBet + amount)) {
            return minimalBet + amount;
         }
         else {
            return bet.getCurrent_buy_in();
         }
      } else if(myPlayer.getBet() > 0){
         return myPlayer.getStack();
      }

      return 0;
   }
}
