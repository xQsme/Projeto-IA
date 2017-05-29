package gui;

import utils.ImageLoader;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class PuzzleTileCellRenderer extends JLabel implements TableCellRenderer {

    public PuzzleTileCellRenderer() {
        setBackground(Color.WHITE);
        setOpaque(true);
        setFont(new Font("Monospaced", Font.BOLD, 49));
        this.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {

        table.setShowGrid(false);

        ImageLoader loader = ImageLoader.getLoader();
        setText("");
        if (((Integer) value).intValue() == 0) {
            setIcon(loader.getIcon(Properties.EMPTY_IMAGE));
        } else {
            setIcon(loader.getIcon(((Integer) value).intValue() + Properties.IMAGE_SUFFIX));
        }

        return this;
    }
}
