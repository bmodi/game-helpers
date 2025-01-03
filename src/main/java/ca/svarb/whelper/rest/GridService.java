package ca.svarb.whelper.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.SortedSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ca.svarb.whelper.Dictionary;
import ca.svarb.whelper.DictionaryLoader;
import ca.svarb.whelper.WordSearcher;
import ca.svarb.whelper.boards.IGameBoard;
import ca.svarb.whelper.WhelperException;

@RestController
@RequestMapping("/api/grid")
public class GridService {

    private Dictionary dictionary = null;

    @Autowired
    private WordSearcher wordSearcher;

    public GridService() {
        try {
            loadDefaultDictionary();
        } catch (WhelperException e) {
            System.out.println("Could not load dictionary: " + e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public SortedSet<String> getWords(@RequestBody Grid grid) {
        System.out.println("grid=" + grid);
        IGameBoard board = null;
        if (grid.getGridType() == Grid.GridType.GRID) {
            board = new ca.svarb.whelper.boards.Grid(grid.getCells());
        } else {
            board = new ca.svarb.whelper.boards.OffsetGrid(grid.getCells());
        }
        return wordSearcher.findWords(dictionary, board);
    }

    private void loadDefaultDictionary() throws WhelperException {
        String dictionaryName = "TWL06.txt";

        try {
            InputStream dictionaryStream = GridService.class.getClassLoader().getResourceAsStream(dictionaryName);
            if (dictionaryStream == null) throw new WhelperException("Could not find dictionary file: " + dictionaryName);
            dictionary = DictionaryLoader.getInstance().loadFromReader(new InputStreamReader(dictionaryStream));
        } catch (IOException e) {
            throw new WhelperException("Error reading from dictionary file: " + e.getMessage());
        }
    }
}
