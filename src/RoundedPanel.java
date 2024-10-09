import java.awt.Graphics;
import javax.swing.JPanel;

class RoundedPanel extends JPanel {
    private int radius;

    public RoundedPanel(int radius) {
        this.radius = radius;
        setOpaque(false); // Make sure to setOpaque(false) for the background to be visible
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, width, height, radius, radius);
    }
}
