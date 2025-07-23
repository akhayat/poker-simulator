package com.akhayat.poker.simulator.card;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.akhayat.poker.simulator.evaluator.PokerHandEvaluation;

class PokerHandTest {
    
    @Test
    public void testPokerHandConstructors() {
        PokerHand hand = new FiveCardHand(List.of(
                new Card("10", "h"),
                new Card("k", "d"),
                new Card("A", "s"),
                new Card("j", "c"),
                new Card("Q", "h")
        ));
        assertThat(hand.getHand()).isEqualTo(List.of(
                new Card("10", "h"),
                new Card("k", "d"),
                new Card("A", "s"),
                new Card("j", "c"),
                new Card("Q", "h")
        ));
        assertThat(hand.getOrdered()).isEqualTo(List.of(
                new Card("A", "s"),
                new Card("k", "d"),
                new Card("Q", "h"),
                new Card("j", "c"),
                new Card("10", "h")
        ));
        assertThat(hand.getEvaluation()).isNotNull();
        assertThat(hand.getEvaluation().getHandType()).isEqualTo(PokerHandEvaluation.PokerHandType.STRAIGHT);
        
        hand = PokerHand.fromStrings("4", "h", "5", "c", "4", "d", "4", "s", "4", "c");
        
        assertThat(hand.getHand()).isEqualTo(List.of(
                new Card("4", "h"),
                new Card("5", "c"),
                new Card("4", "d"),
                new Card("4", "s"),
                new Card("4", "c")
        ));
    }
    
    @Test
    public void testPokerHandConstructorErrors() {
        assertThatThrownBy(() -> {
            new FiveCardHand(List.of(
                    new Card("10", "h"),
                    new Card("k", "d"),
                    new Card("A", "s"),
                    new Card("j", "c")
            ));
        }).isInstanceOf(IllegalArgumentException.class);
        
        assertThatThrownBy(() -> {
            new FiveCardHand(List.of(
                    new Card("10", "h"),
                    new Card("k", "d"),
                    new Card("A", "s"),
                    new Card("j", "c"),
                    new Card("Q", "h"),
                    new Card("2", "d")
            ));
        }).isInstanceOf(IllegalArgumentException.class);
        
        assertThatThrownBy(() -> {
            new FiveCardHand(List.of());
        }).isInstanceOf(IllegalArgumentException.class);
        
        List<Card> cards = null;
        assertThatThrownBy(() -> {
            new FiveCardHand(cards);
        }).isInstanceOf(IllegalArgumentException.class);
        
        assertThatThrownBy(() -> {
            new FiveCardHand();
        }).isInstanceOf(IllegalArgumentException.class);
        
        assertThatThrownBy(() -> {
            PokerHand.fromStrings("10", "h", "k", "d", "A", "s", "j", "c");
        }).isInstanceOf(IllegalArgumentException.class);
        
        assertThatThrownBy(() -> {
            PokerHand.fromStrings("10", "h", "k", "d", "A", "s", "j", "c", "Q", "h", "2", "d");
        }).isInstanceOf(IllegalArgumentException.class);
        
        assertThatThrownBy(() -> {
            PokerHand.fromStrings("F", "f", "c", "fdsa");
        }).isInstanceOf(IllegalArgumentException.class);
        
        assertThatThrownBy(() -> {
            PokerHand.fromStrings("10", "h", "k", "d", "A", "s", "j", "c", "Q");
        }).isInstanceOf(IllegalArgumentException.class);
        
        assertThatThrownBy(() -> {
            PokerHand.fromStrings("h", "10", "d", "k", "s", "a", "c", "j", "h", "q");
        }).isInstanceOf(IllegalArgumentException.class);
        
    }
    
    private PokerHand createHand(String... cards) {
        return PokerHand.fromStrings(cards);
    }
    // straight flushes
    private PokerHand sf_a2345_h = createHand("5", "h", "A", "h", "4", "h", "3", "h", "2", "h");
    private PokerHand sf_9tjqk_d = createHand("K", "d", "Q", "d", "J", "d", "t", "d", "9", "d");
    private PokerHand sf_2345a_s = createHand("A", "s", "2", "s", "3", "s", "4", "s", "5", "s");
    private PokerHand sf_89tjq_h = createHand("J", "h", "Q", "h", "t", "h", "8", "h", "9", "h");
    
    //quads
    private PokerHand quads_jt = createHand("J", "d", "J", "h", "J", "s", "J", "c", "t", "h");
    private PokerHand quads_jq = createHand("j", "h", "j", "c", "Q", "s", "j", "d", "j", "s");
    private PokerHand quads_jq_alt = createHand("Q", "d", "j", "c", "j", "s", "j", "h", "j", "d");
    private PokerHand quads_2j = createHand("2", "d", "2", "c", "j", "s", "2", "h", "2", "s");
    private PokerHand quads_qj = createHand("Q", "d", "Q", "c", "j", "s", "Q", "h", "Q", "s");
    
    //full houses
    private PokerHand fh_7q = createHand("7", "d", "7", "h", "7", "s", "Q", "c", "Q", "d");
    private PokerHand fh_78 = createHand("7", "d", "7", "h", "8", "s", "7", "c", "8", "d");
    private PokerHand fh_6k = createHand("6", "d", "6", "h", "6", "s", "K", "c", "K", "d");
    private PokerHand fh_a2 = createHand("A", "d", "A", "h", "A", "s", "2", "c", "2", "d");
    private PokerHand fh_a2_alt = createHand("2", "d", "2", "c", "A", "s", "A", "h", "A", "s");
    
    // flushes
    private PokerHand flush_ajt54_s = createHand("A", "s", "J", "s", "t", "s", "5", "s", "4", "s");
    private PokerHand flush_2k39j_c = createHand("2", "c", "K", "c", "3", "c", "9", "c", "J", "c");
    private PokerHand flush_aqkj9_h = createHand("A", "h", "Q", "h", "K", "h", "J", "h", "9", "h");
    private PokerHand flush_3k49j_d = createHand("3", "d", "K", "d", "4", "d", "9", "d", "J", "d");
    private PokerHand flush_3k49j_s = createHand("3", "s", "K", "s", "4", "s", "9", "s", "J", "s");
    
    // straights
    private PokerHand straight_a2345 = createHand("A", "d", "2", "d", "3", "d", "4", "s", "5", "d");
    private PokerHand straight_jqkta = createHand("J", "h", "Q", "d", "K", "c", "A", "h", "t", "c");
    private PokerHand straight_74356 = createHand("7", "h", "4", "d", "3", "c", "5", "d", "6", "d");
    private PokerHand straight_23456 = createHand("2", "d", "3", "h", "4", "s", "5", "c", "6", "d");
    private PokerHand straight_jqkta_alt = createHand("t", "s", "Q", "d", "K", "s", "A", "c", "j", "s");
    
    //trips
    private PokerHand trips_7q2 = createHand("7", "d", "7", "h", "7", "s", "Q", "c", "2", "d");
    private PokerHand trips_6k3 = createHand("3", "d", "6", "h", "6", "s", "K", "c", "6", "d");
    private PokerHand trips_7q3 = createHand("7", "d", "7", "h", "7", "s", "Q", "c", "3", "d");
    private PokerHand trips_73q = createHand("Q", "d", "7", "h", "7", "s", "3", "c", "7", "d");
    private PokerHand trips_ja7 = createHand("J", "d", "A", "h", "7", "s", "J", "c", "J", "s");
    
    // two pairs
    private PokerHand tp_76q = createHand("7", "d", "7", "h", "6", "s", "Q", "c", "6", "d");
    private PokerHand tp_6k3 = createHand("6", "d", "6", "h", "K", "s", "3", "c", "K", "d");
    private PokerHand tp_76a = createHand("7", "d", "7", "h", "6", "s", "A", "c", "6", "d");
    private PokerHand tp_a67 = createHand("6", "d", "6", "h", "A", "s", "7", "c", "A", "d");
    private PokerHand tp_a76 = createHand("A", "d", "A", "h", "6", "s", "7", "c", "7", "d");
    private PokerHand tp_7a6 = createHand("7", "d", "7", "h", "A", "s", "6", "c", "A", "d");
    
    // pair
    private PokerHand p_taj2 = createHand("t", "d", "J", "h", "A", "s", "t", "c", "2", "d");
    private PokerHand p_tkj2 = createHand("t", "d", "K", "h", "J", "s", "t", "c", "2", "d");
    private PokerHand p_2k39 = createHand("2", "c", "K", "c", "3", "c", "9", "c", "2", "d");
    private PokerHand p_239k = createHand("2", "c", "3", "c", "9", "c", "K", "c", "2", "d");
    private PokerHand p_aqk9 = createHand("A", "h", "Q", "h", "K", "h", "A", "c", "9", "h");
    private PokerHand p_aqj9 = createHand("A", "h", "Q", "h", "J", "h", "A", "c", "9", "h");
    
    // high card
    private PokerHand hc_aj542 = createHand("A", "d", "J", "s", "5", "s", "4", "s", "2", "s");
    private PokerHand hc_245ja = createHand("2", "d", "4", "h", "5", "s", "J", "c", "A", "d");
    private PokerHand hc_75432 = createHand("7", "d", "5", "h", "4", "s", "3", "c", "2", "d");
    private PokerHand hc_j9543 = createHand("3", "d", "9", "h", "5", "s", "4", "c", "j", "d");
    private PokerHand hc_j9432 = createHand("3", "d", "9", "h", "2", "s", "4", "c", "j", "d");
    

    @Test
    public void straightFlushTest() {
        // Test straight flush with higher strength wins
        assertThat(sf_9tjqk_d.beats(sf_a2345_h)).isTrue();
        assertThat(sf_9tjqk_d.beats(sf_89tjq_h)).isTrue();
        
        // Test straight flush with same strength different suits ties
        assertThat(sf_a2345_h.tiesWith(sf_2345a_s)).isTrue();
        
        //Test straight flush with lower strength loses
        assertThat(sf_a2345_h.losesTo(sf_9tjqk_d)).isTrue();
        assertThat(sf_89tjq_h.losesTo(sf_9tjqk_d)).isTrue();
        
        //test straight flush beats all other hands
        assertThat(sf_a2345_h.beats(quads_jt)).isTrue();
        assertThat(sf_89tjq_h.beats(fh_7q)).isTrue();
        assertThat(sf_9tjqk_d.beats(flush_ajt54_s)).isTrue();
        assertThat(sf_a2345_h.beats(straight_jqkta)).isTrue();
        assertThat(sf_89tjq_h.beats(trips_7q2)).isTrue();
        assertThat(sf_9tjqk_d.beats(tp_76q)).isTrue();
        assertThat(sf_a2345_h.beats(p_taj2)).isTrue();
        assertThat(sf_89tjq_h.beats(hc_aj542)).isTrue();

    }
    
    @Test
    public void quadsTest() {
        // Test quads with higher strength wins
        assertThat(quads_qj.beats(quads_jq)).isTrue();
        
        // Test quads with lower strength loses
        assertThat(quads_2j.losesTo(quads_jq)).isTrue();
        
        // Test quads with same strength and higher kicker wins
        assertThat(quads_jq.beats(quads_jt)).isTrue();
        
        // Test quads with same strength and lower kicker loses
        assertThat(quads_jt.losesTo(quads_jq)).isTrue();
        
        // Test quads with same strength and same kicker ties
        assertThat(quads_jq.tiesWith(quads_jq_alt)).isTrue();
        assertThat(quads_jq_alt.tiesWith(quads_jq)).isTrue();
        assertThat(quads_jq.beats(quads_jq_alt)).isFalse();
        
        // Test quads loses to straight flush
        assertThat(quads_jq.losesTo(sf_a2345_h)).isTrue();
        
        // Test quads beats all other hands
        assertThat(quads_jq.beats(fh_7q)).isTrue();
        assertThat(quads_jq.beats(flush_ajt54_s)).isTrue();
        assertThat(quads_2j.beats(straight_jqkta)).isTrue();
        assertThat(quads_jt.beats(trips_7q2)).isTrue();
        assertThat(quads_qj.beats(tp_76q)).isTrue();
        assertThat(quads_jq.beats(p_taj2)).isTrue();
        assertThat(quads_2j.beats(hc_aj542)).isTrue();
    }
    
    @Test
    public void fullHouseTest() {
        // Test full house with higher strength wins
        assertThat(fh_7q.beats(fh_6k)).isTrue();
        
        // Test full house with lower strength loses
        assertThat(fh_6k.losesTo(fh_7q)).isTrue();
        
        // Test full house with same strength and higher secondary strength wins
        assertThat(fh_7q.beats(fh_78)).isTrue();
        
        // Test full house with same strength and lower secondary strength loses
        assertThat(fh_78.losesTo(fh_7q)).isTrue();
        
        // Test full house with same strength and same secondary strength ties
        assertThat(fh_a2.tiesWith(fh_a2_alt)).isTrue();
        assertThat(fh_a2_alt.tiesWith(fh_a2)).isTrue();
        assertThat(fh_a2.beats(fh_a2_alt)).isFalse();
        
        // Test full house loses to straight flush and quads
        assertThat(fh_7q.losesTo(sf_a2345_h)).isTrue();
        assertThat(fh_6k.losesTo(quads_jq)).isTrue();
        
        // Test full house beats all other hands
        assertThat(fh_7q.beats(flush_ajt54_s)).isTrue();
        assertThat(fh_6k.beats(straight_jqkta)).isTrue();
        assertThat(fh_78.beats(trips_7q2)).isTrue();
        assertThat(fh_a2.beats(tp_76q)).isTrue();
        assertThat(fh_a2_alt.beats(p_taj2)).isTrue();
        assertThat(fh_6k.beats(hc_aj542)).isTrue();
    }
    
    @Test
    public void flushTest() {
        // Test flush with higher strength wins
        assertThat(flush_ajt54_s.beats(flush_2k39j_c)).isTrue();
        
        // Test flush with lower strength loses
        assertThat(flush_2k39j_c.losesTo(flush_ajt54_s)).isTrue();
        
        // Test flush with same strength and higher kicker wins
        assertThat(flush_aqkj9_h.beats(flush_ajt54_s)).isTrue();
        assertThat(flush_3k49j_d.beats(flush_2k39j_c)).isTrue();
        
        // Test flush with same strength and lower kicker loses
        assertThat(flush_ajt54_s.losesTo(flush_aqkj9_h)).isTrue();
        assertThat(flush_2k39j_c.losesTo(flush_3k49j_d)).isTrue();

        // Test flush with same strength and same kicker ties
        assertThat(flush_aqkj9_h.tiesWith(flush_aqkj9_h)).isTrue();
        assertThat(flush_3k49j_d.tiesWith(flush_3k49j_s)).isTrue();
        assertThat(flush_3k49j_d.beats(flush_3k49j_s)).isFalse();
        
        // Test flush loses to straight flush, quads, and full house
        assertThat(flush_ajt54_s.losesTo(sf_a2345_h)).isTrue();
        assertThat(flush_2k39j_c.losesTo(quads_jq)).isTrue();
        assertThat(flush_aqkj9_h.losesTo(fh_7q)).isTrue();
        
        // Test flush beats all other hands
        assertThat(flush_ajt54_s.beats(straight_jqkta)).isTrue();
        assertThat(flush_aqkj9_h.beats(trips_7q2)).isTrue();
        assertThat(flush_2k39j_c.beats(tp_76q)).isTrue();
        assertThat(flush_aqkj9_h.beats(p_taj2)).isTrue();
        assertThat(flush_ajt54_s.beats(hc_aj542)).isTrue();

    }
    
    @Test
    public void straightTest() {
        // Test straight with higher strength wins
        assertThat(straight_jqkta.beats(straight_a2345)).isTrue();
        
        // Test straight with lower strength loses
        assertThat(straight_a2345.losesTo(straight_jqkta)).isTrue();
        
        // Test straight with same strength ties
        assertThat(straight_jqkta.tiesWith(straight_jqkta_alt)).isTrue();
        assertThat(straight_jqkta_alt.tiesWith(straight_jqkta)).isTrue();
        assertThat(straight_jqkta.beats(straight_jqkta_alt)).isFalse();
        
        // Test straight loses to straight flush, quads, full house, and flush
        assertThat(straight_jqkta.losesTo(sf_a2345_h)).isTrue();
        assertThat(straight_23456.losesTo(quads_jq)).isTrue();
        assertThat(straight_74356.losesTo(fh_7q)).isTrue();
        assertThat(straight_a2345.losesTo(flush_ajt54_s)).isTrue();
        
        // Test straight beats all other hands
        assertThat(straight_jqkta.beats(trips_7q2)).isTrue();
        assertThat(straight_23456.beats(tp_76q)).isTrue();
        assertThat(straight_a2345.beats(p_taj2)).isTrue();
        assertThat(straight_74356.beats(hc_aj542)).isTrue();
    }
    
    @Test
    public void tripsTest() {
        // Test trips with higher strength wins
        assertThat(trips_7q2.beats(trips_6k3)).isTrue();
        
        // Test trips with lower strength loses
        assertThat(trips_6k3.losesTo(trips_ja7)).isTrue();
        
        // Test trips with same strength and higher kicker wins
        assertThat(trips_73q.beats(trips_7q2)).isTrue();
        
        // Test trips with same strength and lower kicker loses
        assertThat(trips_7q2.losesTo(trips_7q3)).isTrue();
        
        // Test trips with same strength and same kicker ties
        assertThat(trips_73q.tiesWith(trips_7q3)).isTrue();
        
        // Test trips loses to straight flush, quads, full house, flush, and straight
        assertThat(trips_7q2.losesTo(sf_a2345_h)).isTrue();
        assertThat(trips_6k3.losesTo(quads_jq)).isTrue();
        assertThat(trips_7q3.losesTo(fh_7q)).isTrue();
        assertThat(trips_73q.losesTo(flush_ajt54_s)).isTrue();
        assertThat(trips_7q2.losesTo(straight_jqkta)).isTrue();
        
        // Test trips beats all other hands
        assertThat(trips_6k3.beats(tp_76q)).isTrue();
        assertThat(trips_73q.beats(p_taj2)).isTrue();
        assertThat(trips_6k3.beats(hc_aj542)).isTrue();
    }
    
    @Test
    public void twoPairsTest() {
        // Test two pairs with higher strength wins
        assertThat(tp_6k3.beats(tp_76q)).isTrue();
        
        // Test two pairs with lower strength loses
        assertThat(tp_6k3.losesTo(tp_a67)).isTrue();
        
        // Test two pairs with same strength and higher kicker wins
        assertThat(tp_76a.beats(tp_76q)).isTrue();
        
        // Test two pairs with same strength and lower kicker loses
        assertThat(tp_76q.losesTo(tp_76a)).isTrue();
        
        // Test two pairs with same strength and same kicker ties
        assertThat(tp_a76.tiesWith(tp_7a6)).isTrue();
        
        // Test two pairs loses to straight flush, quads, full house, flush, and straight
        assertThat(tp_76q.losesTo(sf_a2345_h)).isTrue();
        assertThat(tp_6k3.losesTo(quads_jq)).isTrue();
        assertThat(tp_a67.losesTo(fh_7q)).isTrue();
        assertThat(tp_76a.losesTo(flush_ajt54_s)).isTrue();
        assertThat(tp_6k3.losesTo(straight_jqkta)).isTrue();
        assertThat(tp_76q.losesTo(trips_7q2)).isTrue();

        
        // Test two pairs beats all other hands
        assertThat(tp_a67.beats(p_taj2)).isTrue();
        assertThat(tp_6k3.beats(hc_aj542)).isTrue();
    }
    
    @Test
    public void pairTest() {
        // Test pair with higher strength wins
        assertThat(p_taj2.beats(p_2k39)).isTrue();
        
        // Test pair with lower strength loses
        assertThat(p_2k39.losesTo(p_aqk9)).isTrue();
        
        // Test pair with same strength and higher kicker wins
        assertThat(p_aqk9.beats(p_aqj9)).isTrue();
        
        // Test pair with same strength and lower kicker loses
        assertThat(p_tkj2.losesTo(p_taj2)).isTrue();
        
        // Test pair with same strength and same kicker ties
        assertThat(p_2k39.tiesWith(p_239k)).isTrue();
        
        // Test pair loses to every other hand except high card
        assertThat(p_taj2.losesTo(sf_a2345_h)).isTrue();
        assertThat(p_2k39.losesTo(quads_jq)).isTrue();
        assertThat(p_aqk9.losesTo(fh_7q)).isTrue();
        assertThat(p_taj2.losesTo(flush_ajt54_s)).isTrue();
        assertThat(p_2k39.losesTo(straight_jqkta)).isTrue();
        assertThat(p_aqk9.losesTo(trips_7q2)).isTrue();
        assertThat(p_taj2.losesTo(tp_76q)).isTrue();
        
        // Test pair beats high card
        assertThat(p_taj2.beats(hc_aj542)).isTrue();
    }
    
    @Test
    public void highCardTest() {
        // Test high card with higher strength wins
        assertThat(hc_aj542.beats(hc_75432)).isTrue();
        
        // Test high card with lower strength loses
        assertThat(hc_75432.losesTo(hc_j9543)).isTrue();
        
        // Test high card with same strength and higher kicker wins
        assertThat(hc_j9543.beats(hc_j9432)).isTrue();
        
        // Test high card with same strength and lower kicker loses
        assertThat(hc_j9432.losesTo(hc_j9543)).isTrue();
        
        // Test high card with same strength and same kicker ties
        assertThat(hc_aj542.tiesWith(hc_245ja)).isTrue();
        
        // Test high card loses to every other hand
        assertThat(hc_aj542.losesTo(sf_a2345_h)).isTrue();
        assertThat(hc_75432.losesTo(quads_jq)).isTrue();
        assertThat(hc_j9543.losesTo(fh_7q)).isTrue();
        assertThat(hc_aj542.losesTo(flush_ajt54_s)).isTrue();
        assertThat(hc_75432.losesTo(straight_jqkta)).isTrue();
        assertThat(hc_j9543.losesTo(trips_7q2)).isTrue();
        assertThat(hc_aj542.losesTo(tp_76q)).isTrue();
        assertThat(hc_j9543.losesTo(p_taj2)).isTrue();
    }

}
