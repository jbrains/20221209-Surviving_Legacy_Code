package com.adaptionsoft.games.uglytrivia;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

// SMELL Depends on package-visible properties of Game.
public class AddPlayersToGame {
    @Test
    public void one() {
        final Game game = new Game();
        game.add("player 1");

        Assert.assertEquals(Arrays.asList("player 1"), game.players);
    }
}
