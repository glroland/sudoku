import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.glroland.sudoku.game.PuzzleFactoryTest;
import com.glroland.sudoku.game.PuzzleTest;
import com.glroland.sudoku.game.SolverTest;
import com.glroland.sudoku.model.GameGridTest;
import com.glroland.sudoku.model.PlayableGameGridTest;

@RunWith(Suite.class)
@SuiteClasses({
	GameGridTest.class,
	PuzzleTest.class,
	PlayableGameGridTest.class,
	SolverTest.class,
	PuzzleFactoryTest.class
})
public class AllTests {

}
