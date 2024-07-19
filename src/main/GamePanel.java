package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private float xDelta  = 100, yDelta = 100;
    private BufferedImage img;
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 15;

    public GamePanel() {
        mouseInputs = new MouseInputs(this);
        importImg();
        loadAnimations();
        
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void loadAnimations() {
        animations = new BufferedImage[12][6];

        for (int j = 0; j < animations.length; j++)
            for (int i = 0; i < animations[j].length; i++) {
            animations[j][i] = img.getSubimage(i *64, j *40, 64, 40);
        }
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/player_03.png");

        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280,800);
        setPreferredSize(size);
    }

    public void changeXDeltas(int value) {
        this.xDelta += value;
        repaint();
    }

    public void changeYDeltas(int value) {
        this.yDelta += value;
        repaint();
    }

    public void setRectPos(int x, int y){
        this.yDelta = y;
        this.xDelta = x;
        repaint();
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= 6) {
                aniIndex = 0;
            }
        }
    }

    public void paintComponent(Graphics g) {
       super.paintComponent(g);

       updateAnimationTick();

       g.drawImage(animations[1][aniIndex], (int)xDelta, (int)yDelta, 128,80,null);
    }


}
