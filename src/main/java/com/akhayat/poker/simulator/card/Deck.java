package com.akhayat.poker.simulator.card;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import com.akhayat.poker.simulator.card.Card.Rank;
import com.akhayat.poker.simulator.card.Card.Suit;

/**
 * Represents a standard 52-card deck. Mostly just a wrapper around
 * an array of Cards.
 */
public class Deck {

    /**
     * Right now, this Deck only handles 52 cards, but in the future,
     * maybe functionality for jokers could be added.
     */
    private final int DECK_SIZE = 52;
    private List<Card> cardList = new ArrayList<>(DECK_SIZE);
    private int topCard = 0;

    /**
     * Constructs a deck with all the cards in order of rank.
     */
    public Deck() {
        this(0);
    }

    /**
     * Constructs a deck and shuffles it the given amount of times.
     */
    public Deck(int numberOfShuffles) {
        this(numberOfShuffles, false);
    }

    /**
     * Constructs a deck with the given number of shuffles and
     * cuts it afterwards if cut is true.
     */
    public Deck(int numberOfShuffles, boolean cut) {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cardList.add(new Card(rank, suit));
            }
        }
        multiShuffle(numberOfShuffles);
        if (cut) {
            this.cut();
        }
    }

    /**
     * Returns a COPY of the card at the given index.
     */
    public Card copyCardAt(int index) {
        return new Card(cardList.get(index).getRank(), cardList.get(index).getSuit());
    }

    /**
     * Returns the index of the given card or -1 if it doesn't exist.
     */
    public int indexOf(Card card) {
        return cardList.indexOf(card);
    }

    /**
    * Swaps the cards at the given indices. Returns false if it fails
    * (index is negative or greater than/equal to cardArray.length)
    */
    private boolean swapCards(int firstIndex, int secondIndex) {
        boolean success = false;
        if (firstIndex < cardList.size() && secondIndex < cardList.size()
                && firstIndex >= 0 && secondIndex >= 0) {
            Card hold = cardList.get(firstIndex);
            cardList.set(firstIndex, cardList.get(secondIndex));
            cardList.set(secondIndex, hold);
            success = true;
        }
        return success;
    }
    
    public void multiShuffle(int numberOfShuffles) {
        if (numberOfShuffles > 0) {
            IntStream.range(0, numberOfShuffles).forEach(i -> shuffle());
        }
    }

    /**
    * Rearranges the cards randomly.
    * I recommend the in-place (Durstenfield) version of the Fisher-Yates shuffle.
    * Check here to see what that gibberish means:
    * http://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle#The_.22inside-out.22_algorithm
    * Also a random number of "riffle" shuffles works, too.
    */
    public void shuffle() {
        IntStream.iterate(cardList.size() - 1, i -> i >= topCard, i -> i - 1)
                 .forEach(i -> swapCards(i, (int)(Math.random() * (i - topCard)) + topCard));
    }
    
    /**
    * Takes the top half of the deck and puts it on the bottom.
    */
    public void cut() {
        List<Card> newList = new ArrayList<>(cardList.size());
        newList.addAll(cardList.subList(cardList.size() / 2, cardList.size()));
        newList.addAll(cardList.subList(0, cardList.size() / 2));
        cardList = newList;
    }
    
    public int getTopCardIndex() {
        return topCard;
    }
    
    public Card dealCard() {
        return topCard < cardList.size() ? cardList.get(topCard++) : null;
    }
    
    public List<Card> dealCards(int numberOfCards) {
        if (numberOfCards <= 0) {
            return new ArrayList<>();
        }
        int oldTopCard = topCard;
        topCard = Math.min(topCard + numberOfCards, cardList.size());
        return cardList.subList(oldTopCard, topCard);
    }
    
    public Card peekTopCard() {
        return cardList.get(topCard);
    }
    
    public void resetTopCard() {
        topCard = 0;
    }
    
    @Override
    public String toString() {
        return cardList.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Deck otherDeck = (Deck) obj;
        return cardList.equals(otherDeck.cardList) && topCard == otherDeck.topCard;
    }
    
    @Override
    public int hashCode() {
        int result = cardList.hashCode();
        result = 31 * result + topCard;
        return result;
    }
}
