package com.inventory.spinner;

import com.formdev.flatlaf.ui.FlatProgressBarUI;
import com.formdev.flatlaf.util.Animator;
import com.formdev.flatlaf.util.UIScale;
import com.inventory.spinner.render.RingSpinner;
import com.inventory.spinner.render.SpinnerRender;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicGraphicsUtils;


/**
 *
 * @author Pinankh
 */
public class SpinnerProgressUI extends FlatProgressBarUI {

    protected SpinnerRender render;
    private PropertyChangeListener propertyChangeListener;
    private float lastAnimator;
    private float animateFrame;
    private boolean moreAnimation;

    @Override
    protected void installDefaults() {
        super.installDefaults();
        render = new RingSpinner(4);
    }

    @Override
    protected void installListeners() {
        super.installListeners();
        propertyChangeListener = (PropertyChangeEvent evt) -> {
            if (render.isPaintComplete()) {
                String name = evt.getPropertyName();
                switch (name) {
                    case "indeterminate":
                        checkIndeterminate(evt);
                        break;
                }
            }
        };
        progressBar.addPropertyChangeListener(propertyChangeListener);
    }

    private void checkIndeterminate(PropertyChangeEvent evt) {
        boolean oldValue = (boolean) evt.getOldValue();
        boolean newValue = (boolean) evt.getNewValue();
        if (oldValue && !newValue) {
            moreAnimation = true;
            Animator animator = new Animator(350, new Animator.TimingTarget() {
                @Override
                public void begin() {
                    moreAnimation = true;
                }

                @Override
                public void end() {
                    moreAnimation = false;
                }

                @Override
                public void timingEvent(float f) {
                    animateFrame = f;
                    progressBar.repaint();
                }
            });
            animator.start();
        }
    }

    @Override
    protected void uninstallDefaults() {
        super.uninstallDefaults();
        render = null;
    }

    @Override
    protected void uninstallListeners() {
        super.uninstallListeners();
        progressBar.removePropertyChangeListener(propertyChangeListener);
        propertyChangeListener = null;
    }

    private double getPercentOf(double val) {
        long span = progressBar.getMaximum() - progressBar.getMinimum();
        double currentValue = val;
        double pc = (currentValue - progressBar.getMinimum()) / span;
        return pc;
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        Dimension size = UIScale.scale(new Dimension(30, 30));
        Insets border = progressBar.getInsets();
        int renderInsets = render.getInsets() * 2;
        if (progressBar.isStringPainted()) {
            FontMetrics fontSizer = progressBar.getFontMetrics(progressBar.getFont());
            String progString = NumberFormat.getPercentInstance().format(getPercentOf(progressBar.getMaximum()));
            //int stringWidth = BasicGraphicsUtils.stringWidth(progressBar, fontSizer, progString);
            float stringWidth = BasicGraphicsUtils.getStringWidth(progressBar, fontSizer, progString);
            if (stringWidth > size.width) {
                size.width = (int) stringWidth;
            }
            int stringHeight = fontSizer.getHeight()
                    + fontSizer.getDescent();
            if (stringHeight > size.height) {
                size.height = stringHeight;
            }
        }
        size.width += renderInsets + border.left + border.right;
        size.height += renderInsets + border.top + border.bottom;
        int max = Math.max(size.width, size.height);
        size.width = max;
        size.height = max;
        return size;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Insets b = progressBar.getInsets();
        int barRectWidth = progressBar.getWidth() - (b.right + b.left);
        int barRectHeight = progressBar.getHeight() - (b.top + b.bottom);
        if (barRectWidth <= 0 || barRectHeight <= 0) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        if (progressBar.isIndeterminate()) {
            boxRect = getBox(boxRect);
            if (boxRect != null) {
                float f = getAnimation();
                render.paintIndeterminate(g2, c, boxRect, f);
            }
        } else {
            if (moreAnimation) {
                render.paintCompleteIndeterminate(g2, c, getBox(), lastAnimator, animateFrame, (float) progressBar.getPercentComplete());
            } else {
                Rectangle rec = getBox(new Rectangle(b.left, b.top, barRectWidth, barRectHeight));
                render.paintDeterminate(g2, c, rec, (float) progressBar.getPercentComplete());
            }
        }
        if (render.isDisplayStringAble() && progressBar.isStringPainted()) {
            paintString(g, b.left, b.top, barRectWidth, barRectHeight, 0, b);
        }
    }

    @Override
    protected void paintString(Graphics g, int x, int y, int width, int height, int amountFull, Insets b) {
        Graphics2D g2 = (Graphics2D) g;
        String progressString = progressBar.getString();
        Point renderLocation = getStringPlacement(g2, progressString, x, y, width, height);
        g2.setColor(getSelectionBackground());
        BasicGraphicsUtils.drawString(progressBar, g2, progressString, renderLocation.x, renderLocation.y);
    }

    private float getAnimation() {
        int index = super.getAnimationIndex();
        float animate = (index / (float) getFrameCount()) * 2f;
        lastAnimator = animate;
        return animate;
    }

    @Override
    protected Rectangle getBox(Rectangle r) {
        if (r == null) {
            return null;
        }
        Insets insets = progressBar.getInsets();
        int width = progressBar.getWidth() - (insets.right + insets.left);
        int height = progressBar.getHeight() - (insets.top + insets.bottom);
        int size = Math.min(width, height);
        int x = insets.left + (width - size) / 2;
        int y = insets.top + (height - size) / 2;
        r.setBounds(x, y, size, size);
        return r;
    }

    protected Rectangle getBox() {
        Insets insets = progressBar.getInsets();
        int width = progressBar.getWidth() - (insets.right + insets.left);
        int height = progressBar.getHeight() - (insets.top + insets.bottom);
        int size = Math.min(width, height);
        int x = insets.left + (width - size) / 2;
        int y = insets.top + (height - size) / 2;
        return new Rectangle(x, y, width, height);
    }
}
