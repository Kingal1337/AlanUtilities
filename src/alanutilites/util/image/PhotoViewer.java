/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Alan Tsui
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 */
package alanutilites.util.image;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * 
 * @author Alan Tsui
 * @since 1.0
 * @version 1.0
 */
public class PhotoViewer extends JDialog implements KeyListener {
        private Photo photo;
        private ImageIcon image;
        private int zoomValue;
        private JScrollPane scroller;
        private JPanel panel;
        private JPanel photoPanel;

        private JMenuBar bar;
        private JMenu fileMenu;
        private JMenu export;
        public PhotoViewer(Photo photo) {
            this.photo = photo;
            this.image = this.photo.getIcon();
            setTitle(photo.getTitle());
            zoomValue = 100;

            int pictureWidth = this.photo.getWidth();
            int pictureHeight = this.photo.getHeight();

            int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
            int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
            Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
            int taskBarSize = scnMax.bottom;

            panel = new JPanel();
            panel.setLayout(null);

            bar = new JMenuBar();

            scroller = new JScrollPane();
            scroller.setPreferredSize(new Dimension(getContentPane().getWidth(), getContentPane().getHeight()));
            scroller.setBounds(0, 0, getContentPane().getWidth(), getContentPane().getHeight());
            scroller.getVerticalScrollBar().setUnitIncrement(16);
            scroller.getHorizontalScrollBar().setUnitIncrement(16);

            photoPanel = new JPanel() {
                @Override
                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D gd = (Graphics2D) g;

//                        System.out.println("Test");
                    double newZoomValue = (double) (zoomValue) / 100.0;
                    double newWidth = (double) (image.getIconWidth()) * (double) (newZoomValue);
                    double newHeight = (double) (image.getIconHeight()) * (double) (newZoomValue);
                    if(newWidth > image.getIconWidth() || newHeight > image.getIconHeight()){
                        gd.drawImage(image.getImage(),
                                ((photoPanel.getWidth() / 2) - ((int) (newWidth) / 2)),
                                ((photoPanel.getHeight() / 2) - ((int) (newHeight) / 2)),
                                (int) (newWidth), (int) (newHeight), null);
                    }
                    else{
                        ImageIcon tempImage = ImageUtil.scaleImage(image, (int)newWidth, (int)newHeight);
                        gd.drawImage(tempImage.getImage(),
                                ((photoPanel.getWidth() / 2) - ((int) (newWidth) / 2)),
                                ((photoPanel.getHeight() / 2) - ((int) (newHeight) / 2)),null);
                    }
                }
            };

            photoPanel.setPreferredSize(new Dimension(pictureWidth, pictureHeight));
            scroller.setViewportView(photoPanel);
            panel.add(scroller);

            add(panel);
            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    scrollRefresh();
                }
            });
            addKeyListener(this);

            setModal(true);
            setPreferredSize(new Dimension(screenWidth - getWidth(), screenHeight - taskBarSize - getHeight()));
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setResizable(true);
            pack();
            setLocationRelativeTo(null);
            setVisible(true);
        }

        private void scrollRefresh() {
            scroller.setBounds(0, 0, getContentPane().getWidth(), getContentPane().getHeight());
            repaint();
        }

        private void refresh() {
            double newZoomValue = (double) (zoomValue) / 100.0;
            double newWidth = (double) (image.getIconWidth()) * (double) (newZoomValue);
            double newHeight = (double) (image.getIconHeight()) * (double) (newZoomValue);
            photoPanel.setPreferredSize(new Dimension((int) (newWidth), (int) (newHeight)));
            photoPanel.setBounds(0, 0, (int) (newWidth), (int) (newHeight));
            repaint();
        }

        public void setPhoto(Photo photo) {
            this.photo = photo;
            refresh();
        }

        public void zoomInImage() {
            zoomValue += 10;
            if (zoomValue > 3600) {
                zoomValue = 3600;
            } else {
//                    System.out.println(image.getIconWidth());
//                    System.out.println(zoomValue);
//                    System.out.println(zoomValue/100);
//                    System.out.println(image.getIconHeight());
//                    double newZoomValue = (double) (zoomValue)/100.0;
//                    double newWidth = (double) (image.getIconWidth())*(double) (newZoomValue);
//                    double newHeight = (double) (image.getIconHeight())*(double) (newZoomValue);
//                    image = ImageUtil.resizeImage(image, (int) (newWidth), (int) (newHeight));
            }
            refresh();
        }

        public void zoomOutImage() {
            zoomValue -= 10;
            if (zoomValue < 10) {
                zoomValue = 10;
            } else {
//                    double newZoomValue = (double) (zoomValue)/100.0;
//                    double newWidth = (double) (image.getIconWidth())/(double) (newZoomValue);
//                    double newHeight = (double) (image.getIconHeight())/(double) (newZoomValue);
//                    image = ImageUtil.scaleImage(image, (int) (newWidth), (int) (newHeight));
            }
            refresh();
        }

        @Override
        public void keyTyped(KeyEvent ke) {

        }

        @Override
        public void keyPressed(KeyEvent ke) {
            int key = ke.getKeyCode();

            if (ke.isControlDown() && KeyEvent.VK_EQUALS == key) {
                zoomInImage();
            }

            if (ke.isControlDown() && KeyEvent.VK_MINUS == key) {
                zoomOutImage();
            }
        }

        @Override
        public void keyReleased(KeyEvent ke) {

        }

    }