package ca.jbrains.trivia.test;

import com.adaptionsoft.games.uglytrivia.Game;
import org.approvaltests.Approvals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Random;

public class LockdownJunkDrawer {
    private PrintStream stdout;

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

    @Before
    public void rememberSystemOut() throws Exception {
        stdout = System.out;
    }

    @Test
    public void lockdownSample() throws Exception {
        final ByteArrayOutputStream interceptedOutputStream = new ByteArrayOutputStream(1000000);
        System.setOut(new PrintStream(interceptedOutputStream));

        new GameRunner(new GameRunner.RandomizedGameEventSimulator(new Random(762))).runGame();

        final String interceptedOutput = interceptedOutputStream.toString(Charset.forName("UTF-8").name());
        Approvals.verify(interceptedOutput);
    }

    @After
    public void resetSystemOut() throws Exception {
        System.setOut(stdout);
    }
}
