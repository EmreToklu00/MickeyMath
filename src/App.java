import javax.swing.*;
import java.awt.*;

public class App  extends JFrame {
    public App() {
        initUI();
        setVisible(true);
    }
    public static final int WIDTH = 800;
    public static final int HEIGHT = 650;

    private void initUI() {
        add(new MainGameEngine());
        setTitle("Mickey Math");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("resources/images/fruits/elma.png").getImage());

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(App::new);
    }
}
