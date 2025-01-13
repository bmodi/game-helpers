package ca.svarb.whelper.boards;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import ca.svarb.whelper.Path;

public class GridTest {

	private Grid sparseGrid;
	private Grid fullGrid;

	@Before
	public void setup() {
		sparseGrid = new Grid(3);
		sparseGrid.getCell(2,1).setValue("A");
		
		String[][] gridStrings = {
				{ "c", "a", "t" },
                { "h", "o", "g" },
                { "d", "o", "g" } };

		fullGrid = new Grid(gridStrings);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructZero() {
		new Grid(0);
	}

	@Test(expected=IllegalArgumentException.class)
	public void constructNegative() {
		new Grid(-1);
	}

	@Test
	public void constructFromStrings2D() {
		assertEquals("h", fullGrid.getCell(0, 1).getValue());
		assertEquals("c", fullGrid.getCell(0, 0).getValue());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructFromStrings2DNotSquare() {
		new Grid(new String[2][3]);
	}	
	
	/**
	 * Check that values and neighbours are set correctly
	 */
	@Test
	public void getCell() {
		assertEquals("A", sparseGrid.getCell(2,1).getValue());
		assertEquals("", sparseGrid.getCell(1, 1).getValue());
		assertEquals("", sparseGrid.getCell(0, 0).getValue());
		assertEquals(5, sparseGrid.getCell(1, 0).getNeighbours().size());
		assertEquals(3, sparseGrid.getCell(2, 0).getNeighbours().size());
		assertEquals(8, sparseGrid.getCell(1, 1).getNeighbours().size());
	}

	@Test(expected=IllegalArgumentException.class)
	public void getCellLowCol() {
		sparseGrid.getCell(-1,1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void getCellHighCol() {
		sparseGrid.getCell(3,1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void getCellLowRow() {
		sparseGrid.getCell(2,-1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void getCellHighRow() {
		sparseGrid.getCell(2,3);
	}

	@Test
	public void setSize() {
		sparseGrid.setSize(4);
		assertEquals(4, sparseGrid.getSize());
		// Check that grid is reset to blanks and to new size
		assertEquals("", sparseGrid.getCell(3,3).getValue());
		assertEquals("", sparseGrid.getCell(2,1).getValue());
	}

	@Test
	public void getCells() {
		List<Cell> cells=sparseGrid.getCells();
		assertEquals(9, cells.size());
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void getCellsReturnsReadOnlyList() {
		List<Cell> cells=sparseGrid.getCells();
		cells.add(null);
	}

	@Test
	public void iterator() {
		Iterator<Cell> iterator = sparseGrid.iterator();
		Cell cell=iterator.next();
		assertEquals("", cell.getValue());
		iterator.next();
		iterator.next();
		iterator.next();
		iterator.next();
		iterator.next();
		iterator.next();
		cell=iterator.next();
		assertEquals("A", cell.getValue());
		iterator.next();
		assertFalse(iterator.hasNext());
	}

	@Test(expected=NoSuchElementException.class)
	public void iteratorFinished() {
		Iterator<Cell> iterator = sparseGrid.iterator();
		for( int i=0; i<9; i++ ) {
			iterator.next();
		}
		assertFalse(iterator.hasNext());
		iterator.next();
	}

	@Test(expected=UnsupportedOperationException.class)
	public void iteratorRemove() {
		Iterator<Cell> iterator = sparseGrid.iterator();
		iterator.remove();
	}
	
	@Test
	public void getInitialPaths() {
		List<Cell> cells=fullGrid.getCells();
		List<Cell> pathCells=new ArrayList<>();
		// Check that all paths contain only one cell
		// and that the cell is contained in the grid
		List<Path> initialPaths = fullGrid.getInitialPaths();
		assertEquals(9, initialPaths.size());
		for (Path path : initialPaths) {
			assertEquals(1, path.getCells().size());
			// Get initial cell
			Cell cell=path.getCells().get(0);
			assertTrue(cells.contains(cell));
			pathCells.add(cell);
		}
		assertSame(fullGrid.getCell(0, 0), initialPaths.get(0).getCells().get(0));
		assertSame(fullGrid.getCell(2, 0), initialPaths.get(6).getCells().get(0));

		// Check that all the cells are found in the path cell list
		assertTrue(pathCells.containsAll(cells));
	}

	@Test
	public void getInitialPathsDoesNotContainEmptyCells() {
		List<Path> initialPaths = sparseGrid.getInitialPaths();
		assertEquals(1, initialPaths.size());
	}

	@Test
	public void pathsDoNotAllowRepeatCells() {
		List<Path> initialPaths = sparseGrid.getInitialPaths();
		assertFalse(initialPaths.get(0).getRepeatCellsAllowed());
	}
	
	@Test
	public void findWord() {
		Path wordPath = fullGrid.findWord("hat");
		assertEquals(3, wordPath.getCells().size());
		assertSame(fullGrid.getCell(0, 1), wordPath.getCells().get(0));
		assertSame(fullGrid.getCell(1, 0), wordPath.getCells().get(1));
		assertSame(fullGrid.getCell(2, 0), wordPath.getCells().get(2));
	}

	@Test
	public void findWordNotFound() {
		assertNull( sparseGrid.findWord("hop") );  // Partial word
		assertNull( sparseGrid.findWord("hats") ); // Incomplete word
		assertNull( sparseGrid.findWord("zim") );  // No letters match
	}
}
