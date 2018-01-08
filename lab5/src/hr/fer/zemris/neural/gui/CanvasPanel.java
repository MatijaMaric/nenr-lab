package hr.fer.zemris.neural.gui;

import hr.fer.zemris.neural.support.Curve;
import hr.fer.zemris.neural.support.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;

public class CanvasPanel extends JPanel {

    private List<Point> points;
    private Curve curve;
    private List<CanvasObserver> observers;

    public CanvasPanel() {
        points = new ArrayList<>();
        observers = new ArrayList<>();
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(motionListener);
    }

    public void addObserver(CanvasObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(CanvasObserver observer) {
        observers.remove(observer);
    }

    private void triggerObserversDrawn(Curve curve) {
        for (CanvasObserver observer : observers) {
            observer.drawn(curve);
        }
    }

    public List<Point> getPoints() {
        return points;
    }

    private MouseAdapter mouseListener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            points = new ArrayList<>();
            points.add(new Point(e.getX(), e.getY()));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            points.add(new Point(e.getX(), e.getY()));
            repaint();

            curve = new Curve(points);
            triggerObserversDrawn(curve);
        }
    };

    private MouseMotionAdapter motionListener = new MouseMotionAdapter() {
        @Override
        public void mouseDragged(MouseEvent e) {
            points.add(new Point(e.getX(), e.getY()));
            repaint();
        }
    };

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (points.size() > 1) {
            Point last = points.get(0);
            for (int i = 1; i < points.size(); ++i) {
                Point cur = points.get(i);
                g.drawLine((int) last.getX(), (int) last.getY(), (int) cur.getX(), (int) cur.getY());
                last = cur;
            }
        }
    }

    public abstract static class CanvasObserver {
        abstract void drawn(Curve curve);
    }

}
