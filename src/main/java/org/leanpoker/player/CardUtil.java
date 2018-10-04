package org.leanpoker.player;

import org.leanpoker.player.model.Card;

import java.util.Arrays;
import java.util.List;


public class CardUtil {

   public static int countMatchesWithCommunityCards(Card[] communityCards, Card[] playerCards) {
      int matchCount = 0;

      List<Card> communityCardList = Arrays.asList(communityCards);

      for (Card playerCard : playerCards) {
         if (isCardInList(communityCardList, playerCard)) {
            matchCount++;
         }
      }

      return matchCount;
   }

   public static boolean hasDoubleRanks(Card[] playerCards) {
      List<Card> playerCardList = Arrays.asList(playerCards);

      for (Card card : playerCards) {
         if (isCardInList(playerCardList, card)) {
            return true;
         }
      }

      return false;
   }

   private static boolean isCardInList(List<Card> cardList, Card card) {
      for (Card cardx : cardList) {
         if (cardx.getRank().equals(card.getRank())) {
            return true;
         }
      }

      return false;
   }

   public static boolean hasTripleOrFour(List<Card> cardList, final Card firstCard, Card secondCard){
      if(firstCard.getRank().equalsIgnoreCase(secondCard.getRank())){
         for(Card card : cardList){
            if(card.getRank().equalsIgnoreCase(firstCard.getRank())){
               return true;
            }
         }
      } else {
         int firstCardCount = 0;
         int secoundCardCount = 0;

         for(Card card : cardList){
            if(card.getRank().equalsIgnoreCase(firstCard.getRank())){
               firstCardCount++;
               if(firstCardCount == 2){
                  return true;
               }
            }
         }

         for(Card card : cardList){
            if(card.getRank().equalsIgnoreCase(secondCard.getRank())){
               secoundCardCount++;
               if(secoundCardCount == 2){
                  return true;
               }
            }
         }
      }
      return false;
   }

   public static int suitMatch(List<Card> cardList, final Card firstCard, Card secondCard){
      int suitMatch = 1;

      // gleiche Farbe auf der Hand
      if(firstCard.getSuit().equalsIgnoreCase(secondCard.getSuit())){
         suitMatch += 1;
         for(Card card : cardList){
            if(card.getSuit().equalsIgnoreCase(firstCard.getSuit())){
               suitMatch++;
            }
         }
      } else {
         int firstCardCount = 0;
         int secoundCardCount = 0;

         for(Card card : cardList){
            if(card.getSuit().equalsIgnoreCase(firstCard.getSuit())){
               firstCardCount++;
            }
         }

         for(Card card : cardList){
            if(card.getSuit().equalsIgnoreCase(secondCard.getSuit())){
               secoundCardCount++;
            }
         }

         if(firstCardCount > secoundCardCount){
            return suitMatch + firstCardCount;
         } else {
            return suitMatch + secoundCardCount;
         }
      }

      return suitMatch;
   }
}
