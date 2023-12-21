package brigero.board;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GridBoardRenderer extends JPanel {
    public List<List<Integer>> data = new ArrayList<>();
    public List<int[]> extraData = new ArrayList<int[]>();

    private final int boxWidth, boxHeight, pixWidth, pixHeight, mSize;

    private final double[] playerPosInfo;

    public GridBoardRenderer(int maxX, int maxY, int pixWidth, int pixHeight, int mSize) {
        this.boxWidth = pixWidth;
        this.boxHeight = pixHeight;

        this.pixWidth = maxX;
        this.pixHeight = maxY;
        this.mSize = mSize;

        this.playerPosInfo = new double[]{0, 0, 0, 0, 0};

        playerPosInfo[3] = (double) (maxX * boxWidth) / mSize;
        playerPosInfo[4] = (double) (maxY * boxHeight) / mSize;

        JFrame frame = new JFrame("Grid Map");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setSize(pixWidth * maxX, pixHeight * maxY);
        frame.setVisible(true);

        extendData(maxY);
    }

    public void clearData() {
        for (int i = 0; i < data.size(); ++i) {
            for (int j = 0; j < data.get(i).size(); ++j) {
                data.get(i).set(j, 0);
            }
        }
    }

    public void updatePosition(double x, double y) {
        playerPosInfo[0] = x / 1000;
        playerPosInfo[1] = y / 1000;
    }

    public void updateDeg(double cur) {
        playerPosInfo[2] = cur;
    }

    public void putData(int x, int y, int newVal) {
        if (y >= data.size() || x >= data.get(y).size()) {
            return;
        }

        data.get(y).set(x, newVal);
    }

    public void reDraw() {
        this.repaint();
    }

    public void extendData(int amountY) {
        for (int y = 0; y < amountY; y++) {
            List<Integer> tmp = new ArrayList<>();

            for (int x = 0; x < pixWidth; x++) {
                tmp.add(0);
            }

            data.add(tmp);
        }
    }

    public int[] getAbsPlayerPos() {
        return new int[]{(int) (this.playerPosInfo[1] * this.playerPosInfo[4]), (int) (this.playerPosInfo[0] * this.playerPosInfo[3])};
    }

    public int[] getWH() {
        return new int[] {boxWidth, boxHeight};
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < this.pixHeight; ++row) {
            for (int col = 0; col < this.pixHeight; ++col) {
                int color = data.get(row).get(col);
                int x = col * boxWidth;
                int y = row * boxHeight;

                g.setColor(new Color(color, color, color));
                g.fillRect(x, y, boxWidth, boxHeight);

                /*if (color < 180) {
                    // Blocked

                    g.setColor(Color.RED);
                    g.fillRect(x, y, boxWidth, boxHeight);
                } else if (color > 180) {
                    // Unobstructed

                    g.setColor(Color.WHITE);
                    g.fillRect(x, y, boxWidth, boxHeight);
                } else if (color == 3) {
                    // Unknown

                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, boxWidth, boxHeight);
                }*/
            }
        }

        g.setColor(Color.BLUE);
        //System.out.println(this.playerPosInfo[0] * this.playerPosInfo[3] + " | " + this.playerPosInfo[1] * this.playerPosInfo[4]);
        g.fillRect(getAbsPlayerPos()[0], getAbsPlayerPos()[1], 5, 5);
        //g.drawLine(getAbsPlayerPos()[0], getAbsPlayerPos()[1], (int) (getAbsPlayerPos()[0] + 30 * Math.cos(Math.toRadians(playerPosInfo[2]))), (int) (getAbsPlayerPos()[1] + 30 * Math.sin(Math.toRadians(playerPosInfo[2]))));

        for (int[] integers : this.extraData) {
            g.setColor(Color.BLUE);
            g.fillRect(integers[0] * boxWidth, integers[1] * boxHeight, this.boxWidth, this.boxHeight);
        }
    }
}
