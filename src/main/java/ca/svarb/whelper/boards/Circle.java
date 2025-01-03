package ca.svarb.whelper.boards;


/**
 * A Circle stores a set of Cells in a circular arrangement.
 * All Cells by default contain blank string ("").
 * Cells can be accessed by [col,row] values (0 indexed)
 * or iterated through.
 * The row value will always be ignored and col will refer to
 * the index of the cell in the single ring they are arranged.
 */
public class Circle extends AbstractGridGameBoard {

	/**
	 * Makes a Circle with default size of 5
	 */
	public Circle() {
		this(5);
	}
	
	/**
	 * Make a Circle of blank Cells.
	 * Cells will be initialized with neighbours according
	 * to moving around the circle.
	 * @param size
	 */
	public Circle(int size) {
		super();
		setSize(size);
	}

	/**
	 * Create a Circle filled with given strings into the cells.
	 * Only the first row of values will be used.
	 * @param gridStrings
	 */
	public Circle(String[][] gridStrings) {
		super(gridStrings);
	}

	/**
	 * Set Cell neighbours and left/right/up/down navigation cells
	 * @param col
	 * @param row
	 */
	@Override
	protected void initCell(int col, int row) {
		Cell currentCell=this.getCell(col, row);
		
		if(col>0) {
			Cell left=this.getCell(col-1, row);
			currentCell.addNeighbour(left);
			currentCell.setLeftCell(left);
			if(row<this.size-1) {
				Cell belowLeft=this.getCell(col-1, row+1);
				currentCell.addNeighbour(belowLeft);
			}
		}
		if(row>0) {
			Cell above=this.getCell(col, row-1);
			currentCell.addNeighbour(above);
			currentCell.setUpCell(above);
			if(col>0) {
				Cell aboveLeft=this.getCell(col-1, row-1);
				currentCell.addNeighbour(aboveLeft);
			}
		}

		if (row==this.size-1) {
			Cell topCell=this.getCell(col, 0);
			currentCell.setDownCell(topCell);
		}
		if (col==this.size-1) {
			Cell leftEdgeCell=this.getCell(0, row);
			currentCell.setRightCell(leftEdgeCell);
		}

	}
}
