package com.akhayat.poker.simulator.card;

import java.util.Arrays;
import java.util.List;

public class FiveCardHand extends PokerHand{
    public static final byte HAND_SIZE = 5;
    int handSize = HAND_SIZE;
    
    public FiveCardHand(Card... cards) {
        this(Arrays.asList(cards));
    }
    
    public FiveCardHand(List<Card> hand) {
        super(hand, HAND_SIZE);
    }
}
