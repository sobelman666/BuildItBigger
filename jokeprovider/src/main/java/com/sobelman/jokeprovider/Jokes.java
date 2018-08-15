package com.sobelman.jokeprovider;

/**
 * Simple class for getting a random joke from a hard-coded array of jokes.
 */
public class Jokes {

    private String[] jokeList = {
            "Why is a chair?\nBecause towels eat eggs.",
            "What do you call a psychic midget on the run from the law?\nA small medium at large.",
            "How many nuns does it take to screw in a lightbulb?\nNone."
    };

    public String getJoke() {
        int numJokes = jokeList.length;
        int index = (int) Math.floor(Math.random() * numJokes);
        return jokeList[index];
    }
}
