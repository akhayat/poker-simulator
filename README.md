# poker-simulator

A set of classes intended to simulate poker hands. 

It's a work in progress. Currently it only evaluates 5 card poker hands for hand type and strength.

### To build the jar:

```
mvn clean install
```

### To evaluate a poker hand

Run the jar with pairs of rank and suit. For example, to evaluate a hand
that includes three aces of clubs, hearts, and spades and two eights of diamonds and spades, you would do this:

```
java -jar target/poker-simulator-1.0.0.jar A c A h 8 s 8 d A s
```

### Example output
```
[A♣, A♡, 8♠, 8♢, A♠] -> full house: A high
```