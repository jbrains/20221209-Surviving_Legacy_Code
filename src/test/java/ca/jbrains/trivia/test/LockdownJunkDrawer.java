package ca.jbrains.trivia.test;

import com.adaptionsoft.games.uglytrivia.Game;
import org.junit.Test;

import java.util.Random;

public class LockdownJunkDrawer {
    public static class GameRunner {
        private final RandomizedGameEventSimulator randomizedGameEventSimulator;

        public GameRunner(RandomizedGameEventSimulator randomizedGameEventSimulator) {
            this.randomizedGameEventSimulator = randomizedGameEventSimulator;
        }

        private void runGame() {
            Game aGame = new Game();

            aGame.add("Chet");
            aGame.add("Pat");
            aGame.add("Sue");

            boolean notAWinner;
            do {
                aGame.roll(randomizedGameEventSimulator.nextDieRoll());

                if (randomizedGameEventSimulator.nextPlayerAnswersIncorrectly()) {
                    notAWinner = aGame.wrongAnswer();
                } else {
                    notAWinner = aGame.wasCorrectlyAnswered();
                }
            } while (notAWinner);
        }

        public static class RandomizedGameEventSimulator {
            private final Random rand;

            public RandomizedGameEventSimulator(Random rand) {
                this.rand = rand;
            }

            public int nextDieRoll() {
                return rand.nextInt(5) + 1;
            }

            public boolean nextPlayerAnswersIncorrectly() {
                return rand.nextInt(9) == 7;
            }
        }
    }

    @Test
    public void lockdownSample() {
        new GameRunner(new GameRunner.RandomizedGameEventSimulator(new Random(762))).runGame();
    }
}
