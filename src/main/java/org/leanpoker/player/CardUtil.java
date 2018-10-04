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
}
