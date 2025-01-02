package ca.svarb.whelper.boards;

public class GameBoardFactory {

	public enum BoardType { GRID, OFFSET_GRID, CIRCLE, SQUARE_EDGE };
	
	// Hide constructor to enforce singleton
	private GameBoardFactory() {}
	
	private static GameBoardFactory factory=null;
	
	public static GameBoardFactory getInstance() {
		if ( factory==null ) factory=new GameBoardFactory();
		return factory;
	}

	public IGameBoard getGameBoard(BoardType boardType, int size) {
		IGameBoard board=null;
		switch (boardType) {
		case GRID:
			board=new Grid(size);
			break;
		case OFFSET_GRID:
			board=new OffsetGrid(size);
			break;
		case CIRCLE:
			board=new Circle(size);
			break;
		case SQUARE_EDGE:
			board=new SquareEdge(size);
			break;
		}
		return board;
	}
}
