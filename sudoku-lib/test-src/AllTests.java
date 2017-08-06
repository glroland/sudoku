import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.glroland.sudoku.model.GameGridTest;
import com.glroland.sudoku.model.PlayableGameGridTest;
import com.glroland.sudoku.model.PuzzleTest;

@RunWith(Suite.class)
@SuiteClasses({
	GameGridTest.class,
	PuzzleTest.class,
	PlayableGameGridTest.class
})
public class AllTests {

}
