package com.adaptionsoft.games.uglytrivia;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

// SMELL Depends on package-visible properties of Game.
public class AddPlayersToGame {
    @Test
    public void one() {
        final Game game = new Game();
        game.add("player 1");

        Assert.assertEquals(Arrays.asList("player 1"), game.players);
        Assert.assertEquals(
                Arrays.asList("player 1"),
                game.playerStates
                        .stream()
                        .map(playerState -> playerState.name)
                        .collect(Collectors.toList()));
    }

    @Test
    public void six() {
        final Game game = new Game();
        for (int i = 1; i <= 5; i++) {
            game.add(String.format("player %d", i));
        }

        game.add("player 6");
        Assert.assertEquals(
                Arrays.asList("player 1", "player 2", "player 3", "player 4", "player 5", "player 6"),
                game.players);
        Assert.assertEquals(
                Arrays.asList("player 1", "player 2", "player 3", "player 4", "player 5", "player 6"),
                game.playerStates
                        .stream()
                        .map(playerState -> playerState.name)
                        .collect(Collectors.toList()));
    }
}
