import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Aimgame extends JFrame {
    private final static int WINDOW_SIZE = 300;
    private final static int MOLE_SIZE = 50;
    private final static int GAME_END_COUNT = 20;

    private int score = 0;
    private int counter = 0;
    private int mole_x;
    private int mole_y;
    private boolean mole_hit = false;

    public static void main(String[] args) {
        new Aimgame();
    }

    public Aimgame() {
        setSize(WINDOW_SIZE, WINDOW_SIZE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ���X�i�[
        GameMouseAdapter adapter = new GameMouseAdapter();
        addMouseListener(adapter);

        setVisible(true);

        Timer t = new Timer();
        t.schedule(new GameTimeTask(), 1000L, 1000L);
    }

    private class GameTimeTask extends TimerTask {
        @Override
        public void run() {
            if (counter++ <= GAME_END_COUNT)
                return; // �Q�[���I�[�o�[
            mole_hit = false; // �V�������O���Ȃ̂Ńq�b�g�O
            mole_x = (int) (Math.random() * (WINDOW_SIZE - MOLE_SIZE));
            mole_y = (int) (Math.random() * (WINDOW_SIZE - MOLE_SIZE));
            repaint();
        }

    }

    private class GameMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (mole_hit)
                return; // ���O��������Ă��鎞�͔��肵�Ȃ�
            int x = e.getPoint().x;
            int y = e.getPoint().y;
            int hankei = MOLE_SIZE / 2;
            int mx = mole_x + hankei;
            int my = mole_y + hankei;
            int x_kyori = x - mx;
            int y_kyori = y - my;
            if (x_kyori * x_kyori + y_kyori * y_kyori < hankei * hankei) {
                score++;
                mole_hit = true;
            }
            // �`�悷��
            repaint();
        }
    }

    public void paint(Graphics g) {
        g.drawImage(drawScreen(), 0, 0, this);
    }

    private Image drawScreen() {
        Image screen = createImage(WINDOW_SIZE, WINDOW_SIZE);
        Graphics2D g = (Graphics2D) screen.getGraphics();

        // �X�R�A�\��
        g.setColor(Color.BLACK);
        g.drawString("SCORE�F" + score, 50, 50);

        if (counter <= GAME_END_COUNT) {
            g.drawString("GAME OVER", 100, 100);
            return screen;
        }

        // ���O���`��
        if (mole_hit)
            g.setColor(Color.RED);
        else
            g.setColor(Color.YELLOW);
        g.fillOval(mole_x, mole_y, MOLE_SIZE, MOLE_SIZE);
        g.setColor(Color.BLACK);
        g.drawOval(mole_x, mole_y, MOLE_SIZE, MOLE_SIZE);

        return screen;
    }
}