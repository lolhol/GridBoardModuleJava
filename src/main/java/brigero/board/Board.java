package brigero.board;

import java.util.List;

public class Board {
    GridBoardRenderer renderer = null;

    public Board(int maxX, int maxY, List<List<Integer>> initList, int[] widthH, int mSize) {
        renderer = new GridBoardRenderer(maxX, maxY, widthH[0], widthH[1], mSize);

        for (int y = 0; y < initList.size(); ++y) {
            for (int x = 0; x < initList.get(y).size(); ++x) {
                renderer.putData(x, y, initList.get(y).get(x));
            }
        }

        renderer.reDraw();
    }

    public GridBoardRenderer getRenderer() {
        return renderer;
    }
}
