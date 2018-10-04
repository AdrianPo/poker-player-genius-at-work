package org.leanpoker.player.model;

public class Bet {

   private String tournament_id;
   private String game_id;
   private int round;
   private int bet_index;
   private int small_blind;
   private int orbits;
   private int in_action;
   private int dealer;
   private Card[] communityCards;
   private int current_buy_in;
   private int pot;
   private Player[] players;

   public String getTournament_id() {
      return tournament_id;
   }

   public void setTournament_id(String tournament_id) {
      this.tournament_id = tournament_id;
   }

   public String getGame_id() {
      return game_id;
   }

   public void setGame_id(String game_id) {
      this.game_id = game_id;
   }

   public int getRound() {
      return round;
   }

   public void setRound(int round) {
      this.round = round;
   }

   public int getBet_index() {
      return bet_index;
   }

   public void setBet_index(int bet_index) {
      this.bet_index = bet_index;
   }

   public int getSmall_blind() {
      return small_blind;
   }

   public void setSmall_blind(int small_blind) {
      this.small_blind = small_blind;
   }

   public int getOrbits() {
      return orbits;
   }

   public void setOrbits(int orbits) {
      this.orbits = orbits;
   }

   public int getIn_action() {
      return in_action;
   }

   public void setIn_action(int in_action) {
      this.in_action = in_action;
   }

   public int getDealer() {
      return dealer;
   }

   public void setDealer(int dealer) {
      this.dealer = dealer;
   }

   public Card[] getCommunityCards() {
      return communityCards;
   }

   public void setCommunityCards(Card[] communityCards) {
      this.communityCards = communityCards;
   }

   public int getCurrent_buy_in() {
      return current_buy_in;
   }

   public void setCurrent_buy_in(int current_buy_in) {
      this.current_buy_in = current_buy_in;
   }

   public int getPot() {
      return pot;
   }

   public void setPot(int pot) {
      this.pot = pot;
   }

   public Player[] getPlayers() {
      return players;
   }

   public void setPlayers(Player[] players) {
      this.players = players;
   }
}
