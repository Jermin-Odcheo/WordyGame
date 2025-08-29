package Client_Java.util;

import javax.swing.*;
import java.awt.*;

/**
 * A panel that lays out its children in a grid with square cells that scale to available space.
 * Set rows/cols or let it auto-calc based on child count (nearly square).
 */
public class ResponsiveGrid extends JPanel {
    private int rows = -1;
    private int cols = -1;
    private int gap = 6;

    public ResponsiveGrid() {
        setLayout(null); // we'll place children manually to control square sizing
    }

    public void setGap(int gap) { this.gap = gap; revalidate(); repaint(); }

    public void setGrid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        revalidate();
        repaint();
    }

    private int ceilDiv(int a, int b) { return (a + b - 1) / b; }

    private void autoGrid() {
        int n = getComponentCount();
        if (n == 0) { rows = cols = 0; return; }
        int c = (int)Math.ceil(Math.sqrt(n));
        int r = ceilDiv(n, c);
        rows = r; cols = c;
    }

    @Override
    public void doLayout() {
        if (rows <= 0 || cols <= 0) autoGrid();
        Insets in = getInsets();
        int w = getWidth() - in.left - in.right;
        int h = getHeight() - in.top - in.bottom;
        int totalGapX = gap * Math.max(0, cols - 1);
        int totalGapY = gap * Math.max(0, rows - 1);
        int cellW = (w - totalGapX) / Math.max(1, cols);
        int cellH = (h - totalGapY) / Math.max(1, rows);
        int cell = Math.min(cellW, cellH); // square cell

        int startX = in.left + (w - (cell * cols + totalGapX)) / 2;
        int startY = in.top + (h - (cell * rows + totalGapY)) / 2;

        int i = 0;
        for (Component comp : getComponents()) {
            int r = i / cols;
            int c = i % cols;
            int x = startX + c * (cell + gap);
            int y = startY + r * (cell + gap);
            comp.setBounds(x, y, cell, cell);
            i++;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        int n = Math.max(1, getComponentCount());
        int c = (int)Math.ceil(Math.sqrt(n));
        int r = ceilDiv(n, c);
        int cell = 64;
        int w = c * cell + (c - 1) * gap;
        int h = r * cell + (r - 1) * gap;
        Insets in = getInsets();
        return new Dimension(w + in.left + in.right, h + in.top + in.bottom);
    }
}
