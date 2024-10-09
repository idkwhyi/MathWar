import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedButton extends JButton {
    private int cornerRadius;
    private Color borderColor;
    private int borderThickness;

    public RoundedButton(String text) {
        super(text);
        this.cornerRadius = 0; // Default to 0
        this.borderColor = Color.BLACK; // Default border color
        this.borderThickness = 1; // Default border thickness
        initializeButton();
    }

    public RoundedButton(String text, int cornerRadius, Color borderColor, int borderThickness) {
        super(text);
        this.cornerRadius = cornerRadius;
        this.borderColor = borderColor;
        this.borderThickness = borderThickness;
        initializeButton();
    }

    private void initializeButton() {
        setFont(new Font("Montserrat", Font.PLAIN, 16)); // Set your default font
        setBackground(new Color(179, 133, 242)); // Set your default background color
        setForeground(Color.WHITE); // Set your default text color
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setBorderThickness(int borderThickness) {
        this.borderThickness = borderThickness;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        // Draw rounded background
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        // Draw colored border with adjusted stroke thickness
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(borderThickness));
        g2d.draw(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));

        super.paintComponent(g);

        g2d.dispose();
    }
}
