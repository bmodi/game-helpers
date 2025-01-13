package ca.svarb.whelper.boards;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ca.svarb.whelper.Path;

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
	 * Check that navigation are set correctly
	 */
	@Test
	public void getCellNavigation() {
		assertSame( circleBoard.getCell(0, 0), circleBoard.getCell(1, 0).getLeftCell() );
		assertSame( circleBoard.getCell(1, 0), circleBoard.getCell(0, 0).getRightCell() );

		// Last cell on the row wraps around to the left
		assertSame( circleBoard.getCell(0, 0), circleBoard.getCell(4, 0).getRightCell() );

		// Last cell on the col wraps around to top
		assertSame( circleBoard.getCell(0, 0), circleBoard.getCell(0, 4).getDownCell() );
	}

	/**
	 * Check that neighbours are set correctly
	 */
	@Test
	public void getCellNeighbours() {
		Cell cell0 = circleBoard.getCell(0, 0);
		Cell cell1 = circleBoard.getCell(1, 0);
		Cell cell2 = circleBoard.getCell(2, 0);
		Cell cell3 = circleBoard.getCell(3, 0);
		Cell cell4 = circleBoard.getCell(4, 0);
		
		assertEquals(4, cell0.getNeighbours().size());
		assertEquals(4, cell1.getNeighbours().size());
		assertEquals(4, cell2.getNeighbours().size());
		assertEquals(4, cell3.getNeighbours().size());
		assertEquals(4, cell4.getNeighbours().size());
	}

	@Test
	public void pathsDoNotAllowRepeatCells() {
		List<Path> initialPaths = circleBoard.getInitialPaths();
		assertTrue(initialPaths.get(0).getRepeatCellsAllowed());
	}

}
