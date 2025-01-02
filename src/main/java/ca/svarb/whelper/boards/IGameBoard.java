package ca.svarb.whelper.boards;

import java.util.List;

import ca.svarb.whelper.Path;

public interface IGameBoard extends List<Cell> {

	/**
	 * Return a list of paths each
	 * of which is a starting point
	 * for each Cell in the grid and
	 * each containing only that starting
	 * Cell.
	 * @return
	 */
	public List<Path> getInitialPaths();

	public void clearSelection();

	public Path findWord(String selectedValue);
}
