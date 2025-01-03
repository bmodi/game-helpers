package ca.svarb.whelper.boards;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CircleTest {

	private Circle circleBoard;

	@Before
	public void setup() {
		circleBoard = new Circle(5);
		circleBoard.getCell(2,0).setValue("A");
		circleBoard.getCell(4,0).setValue("");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructZero() {
		new Circle(0);
	}

	@Test(expected=IllegalArgumentException.class)
	public void constructNegative() {
		new Circle(-1);
	}

	/**
	 * Check that values and neighbours are set correctly
	 */
	@Test
	public void getCell() {
		assertSame( circleBoard.getCell(0, 0), circleBoard.getCell(1, 0).getLeftCell() );
		assertSame( circleBoard.getCell(1, 0), circleBoard.getCell(0, 0).getRightCell() );

		// Last cell on the row wraps around to the left
		assertSame( circleBoard.getCell(0, 0), circleBoard.getCell(4, 0).getRightCell() );

		// Last cell on the col wraps around to top
		assertSame( circleBoard.getCell(0, 0), circleBoard.getCell(0, 4).getDownCell() );
	}
}
