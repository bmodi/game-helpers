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

	public static String[][] gridStrings = { { "c", "a", "t" },
                                             { "h", "o", "g" },
                                             { "d", "o", "g" } };

	private Grid wgrid;
	private Grid grid;
	private Cell cell00;
	private Cell cell01;

	@Before
	public void setup() {
		wgrid = new Grid(3);
		wgrid.getCell(2,1).setValue("A");
		grid = new Grid(gridStrings);
		cell00 = grid.getCell(0, 0);
		cell01 = grid.getCell(0, 1);
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
		assertEquals("h", cell01.getValue());
		assertEquals("c", cell00.getValue());
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
		assertEquals("A", wgrid.getCell(2,1).getValue());
		assertEquals("", wgrid.getCell(1, 1).getValue());
		assertEquals("", wgrid.getCell(0, 0).getValue());
		assertEquals(5, wgrid.getCell(1, 0).getNeighbours().size());
		assertEquals(3, wgrid.getCell(2, 0).getNeighbours().size());
		assertEquals(8, wgrid.getCell(1, 1).getNeighbours().size());
	}

	@Test(expected=IllegalArgumentException.class)
	public void getCellLowCol() {
		wgrid.getCell(-1,1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void getCellHighCol() {
		wgrid.getCell(3,1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void getCellLowRow() {
		wgrid.getCell(2,-1);
	}

	@Test(expected=IllegalArgumentException.class)
	public void getCellHighRow() {
		wgrid.getCell(2,3);
	}

	@Test
	public void setSize() {
		wgrid.setSize(4);
		assertEquals(4, wgrid.getSize());
		// Check that grid is reset to blanks and to new size
		assertEquals("", wgrid.getCell(3,3).getValue());
		assertEquals("", wgrid.getCell(2,1).getValue());
	}

	@Test
	public void getCells() {
		List<Cell> cells=wgrid.getCells();
		assertEquals(9, cells.size());
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void getCellsReturnsReadOnlyList() {
		List<Cell> cells=wgrid.getCells();
		cells.add(null);
	}

	@Test
	public void iterator() {
		Iterator<Cell> iterator = wgrid.iterator();
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
		Iterator<Cell> iterator = wgrid.iterator();
		for( int i=0; i<9; i++ ) {
			iterator.next();
		}
		assertFalse(iterator.hasNext());
		iterator.next();
	}

	@Test(expected=UnsupportedOperationException.class)
	public void iteratorRemove() {
		Iterator<Cell> iterator = wgrid.iterator();
		iterator.remove();
	}
	
	@Test
	public void getInitialPaths() {
		List<Cell> cells=wgrid.getCells();
		List<Cell> pathCells=new ArrayList<>();
		// Check that all paths contain only one cell
		// and that the cell is contained in the grid
		List<Path> initialPaths = wgrid.getInitialPaths();
		assertEquals(9, initialPaths.size());
		for (Path path : initialPaths) {
			assertEquals(1, path.getCells().size());
			// Get initial cell
			Cell cell=path.getCells().get(0);
			assertTrue(cells.contains(cell));
			pathCells.add(cell);
		}
		assertSame(wgrid.getCell(0, 0), initialPaths.get(0).getCells().get(0));
		assertSame(wgrid.getCell(2, 0), initialPaths.get(6).getCells().get(0));

		// Check that all the cells are found in the path cell list
		assertTrue(pathCells.containsAll(cells));
	}
	
	@Test
	public void findWord() {
		Grid grid = new Grid(gridStrings);
		Path wordPath = grid.findWord("hat");
		assertEquals(3, wordPath.getCells().size());
		assertSame(grid.getCell(0, 1), wordPath.getCells().get(0));
		assertSame(grid.getCell(1, 0), wordPath.getCells().get(1));
		assertSame(grid.getCell(2, 0), wordPath.getCells().get(2));
	}

	@Test
	public void findWordNotFound() {
		assertNull( wgrid.findWord("hop") );  // Partial word
		assertNull( wgrid.findWord("hats") ); // Incomplete word
		assertNull( wgrid.findWord("zim") );  // No letters match
	}
}
