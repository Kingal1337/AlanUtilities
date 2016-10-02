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
 * SOFTWARE.
 */
package alanutilites.util.image;

import alanutilites.shape.ShapeUtil;
import alanutilites.util.text.Text;
import alanutilites.util.popup_window.Action;
import alanutilites.util.popup_window.ButtonWindow;
import java.awt.Point;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 * 
 * @author Alan Tsui
 * @since 1.0
 * @version 1.0
 */
public class PhotoAlbumViewer extends JFrame {
    public static void main(String[] args){
        ArrayList<Photo> photos = new ArrayList<>();
        photos.add(new Photo("Photo Title", new ImageIcon("Path-To-File")));
        photos.add(new Photo("Photo Title", new ImageIcon("Path-To-File")));
        Album info = new Album("My Album", photos);
        PhotoAlbumViewer viewer = new PhotoAlbumViewer(info);
        viewer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewer.setVisible(true);
    }
    
    private PhotoViewer photoViewer;
    private Panel panel;

    public PhotoAlbumViewer(Album info) {
        panel = new Panel(info);
        panel.setPreferredSize(new Dimension(500, 500));
        add(panel);
        setTitle("Photo Album - " + info.getAlbumTitle());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(true);

        addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
                panel.repaint();
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class Panel extends JPanel implements MouseListener {

        private ImageViewer imageViewer;

        private AddImages addImagesPanel;

        private JTextField titleField;
        private JMenuBar bar;
        private JMenu options;
        private JMenuItem addImages;

        public Panel(Album info) {
            imageViewer = new ImageViewer(info);
            DropTargetListener dragAndDropListenerAnimalImage = new DropTargetListener() {
                @Override
                public void drop(DropTargetDropEvent event) {
                    event.acceptDrop(DnDConstants.ACTION_COPY);
                    Transferable transferable = event.getTransferable();
                    DataFlavor[] flavors = transferable.getTransferDataFlavors();
                    for (DataFlavor flavor : flavors) {
                        try {
                            if (flavor.isFlavorJavaFileListType()) {
                                java.util.List<File> files = (java.util.List<File>) transferable.getTransferData(flavor);
                                addImages(info, files);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    event.dropComplete(true);
                    refresh();
                }

                @Override
                public void dragEnter(DropTargetDragEvent event) {
                }

                @Override
                public void dragExit(DropTargetEvent event) {

                }

                @Override
                public void dragOver(DropTargetDragEvent event) {

                }

                @Override
                public void dropActionChanged(DropTargetDragEvent event) {

                }
            };
            new DropTarget(imageViewer.getPanel(), dragAndDropListenerAnimalImage);

            addMouseListener(this);

            addImagesPanel = new AddImages();

            titleField = new JTextField(info.getAlbumTitle());
            titleField.setPreferredSize(new Dimension(332, 25));
            titleField.setBounds(0, 0, getSize().width, 25);
            add(titleField);

            bar = new JMenuBar();
            bar.setPreferredSize(new Dimension(332, 25));
            bar.setBounds(0, 25, getSize().width, 25);
            add(bar);

            options = new JMenu("Options");

            addImages = new JMenuItem("Add Images");
            addImages.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
            addImages.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    addImagesPanel.clear();
                    addImagesPanel.setVisible(true);
                }
            });
            add(imageViewer);

        }

        public void addImages(Album info, java.util.List<File> files) {
            for (File file : files) {
                for (String PHOTO_EXTENSIONS : Photo.PHOTO_EXTENSIONS) {
                    if (file.getName().toLowerCase().endsWith("." + PHOTO_EXTENSIONS)) {
                        info.add(new Photo(Text.removeExtension(file.getName()), new ImageIcon(file.getAbsolutePath())));
                        break;
                    }
                }
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            refresh();
        }

        public void refresh() {
            titleField.setBounds(0, 0, getSize().width, 25);
            bar.setBounds(0, 25, getSize().width, 25);
            imageViewer.setBounds(0, 50, getSize().width, getSize().height - 50);
            Dimension dimension = getSize();
            dimension.height -= 50;
            imageViewer.refresh(dimension);
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private class ImageViewer extends JPanel implements MouseListener, MouseMotionListener {
        
        private Font gothic_font1 = new Font("Gothic", 1, 24);
        private Font gothic_font2 = new Font("Gothic", 0, 12);
        
        private Album info;

        private int size;
        private int hoveringIndex;
        private ArrayList<Integer> selectedIndexes;
        private Color hoveringIndexColor = new Color(192, 192, 192, 100);
        private Color selectedIndexColor = new Color(192, 192, 192, 200);

        private ArrayList<Integer> rightClickSelectedIndexes;

        private ButtonWindow rightSingleClickMenu;
        private ButtonWindow multiSingleClickMenu;

        private JScrollPane scrollPane;
        private JPanel panel;

        public ImageViewer(Album info) {
            this.info = info;
            size = 110;
            panel = new JPanel() {
                @Override
                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D gd = (Graphics2D) g;
                    
                    gd.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    gd.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                    gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    ArrayList<Photo> photos = info.getPhotos();

                    int sizeToIncreaseBy = size + 5;
                    int ogX = 5;
                    int ogY = 5;
                    int x = 5;
                    int y = 5;

                    int screenWidth = getSize().width;
                    int screenHeight = getSize().height;

                    if (photos.isEmpty()) {
                        gd.setFont(gothic_font1);
                        Text.drawCenteredString("No Photos", 0, 0, screenWidth, screenHeight, gd);
                    }
                    for (int i = 0; i < photos.size(); i++) {
                        if (x + sizeToIncreaseBy >= screenWidth) {
                            x = ogX;
                            y += sizeToIncreaseBy;
                        }
                        if (hoveringIndex == i) {
                            gd.setColor(hoveringIndexColor);
                            gd.fillRect(x, y, size, size);
                            gd.setColor(Color.BLACK);
                            gd.drawRect(x, y, size, size);
                        }
                        for (int j = 0; j < selectedIndexes.size(); j++) {
                            if (selectedIndexes.get(j) == i) {
                                gd.setColor(selectedIndexColor);
                                gd.fillRect(x, y, size, size);
                                gd.setColor(Color.BLACK);
                                gd.drawRect(x, y, size, size);
                            }
                        }
                        gd.drawImage(photos.get(i).getResizedImage().getImage(), 
                                x + (110 / 2) - (photos.get(i).getResizedImage().getIconWidth()/ 2), 
                                y + (110 / 2) - (photos.get(i).getResizedImage().getIconHeight()/ 2),
                                null);
                        x += sizeToIncreaseBy;
                    }
                }
            };
            panel.addMouseListener(this);
            panel.addMouseMotionListener(this);
            selectedIndexes = new ArrayList<>();
            hoveringIndex = -1;

            scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setPreferredSize(new Dimension(332, 344));
            scrollPane.setBounds(0, 0, 332, 344);
            scrollPane.getVerticalScrollBar().setUnitIncrement(20);
            add(scrollPane);

            rightClickSelectedIndexes = new ArrayList<>();
            
            Action open = new Action(" Open                          ") {

                @Override
                public void action(ActionEvent event) {
                    rightSingleClickMenu.update(null, false);
                    photoViewer = new PhotoViewer(info.getPhotos().get(hoveringIndex));
                }
            };
            Action remove = new Action(" Remove                          ") {

                @Override
                public void action(ActionEvent event) {
                    rightSingleClickMenu.update(null, false);
                    info.remove(rightClickSelectedIndexes.get(0));
                }
            };
            ArrayList<Action> actions = new ArrayList<>();
            actions.add(open);
            actions.add(remove);
            rightSingleClickMenu = new ButtonWindow(gothic_font2, actions, 15);
            
            Action removeMulti = new Action(" Remove                          ") {
                @Override
                public void action(ActionEvent event) {
                    multiSingleClickMenu.update(null, false);
                    info.remove(rightClickSelectedIndexes);
                }
            };
            ArrayList<Action> multiActions = new ArrayList<>();
            multiActions.add(removeMulti);
            multiSingleClickMenu = new ButtonWindow(gothic_font2, multiActions, 15);
        }

        public void rightClick(int index) {

        }

        public void refresh(Dimension dimension) {
            panel.setPreferredSize(new Dimension(getSize().width, (info.getPhotos().size() * size / (getSize().width / size)) + (size * 2)));
            scrollPane.setPreferredSize(dimension);
            scrollPane.setBounds(0, 0, dimension.width, dimension.height);
        }

        public JPanel getPanel() {
            return panel;
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            int button = e.getButton();
            boolean ctrl = e.isControlDown();
            if (button == MouseEvent.BUTTON1) {
                if (hoveringIndex != -1) {
                    boolean bool = true;
                    for (int i = 0; i < selectedIndexes.size(); i++) {
                        if (hoveringIndex == selectedIndexes.get(i)) {
                            selectedIndexes.remove(new Integer(hoveringIndex));
                            bool = false;
                            break;
                        }
                    }
                    if (!ctrl) {
                        selectedIndexes.clear();
                    }
                    if (bool) {
                        selectedIndexes.add(hoveringIndex);
                    }
                } else {
                    selectedIndexes.clear();
                }
                if (e.getClickCount() >= 2) {
                    if (hoveringIndex != -1) {
                        photoViewer = new PhotoViewer(info.getPhotos().get(hoveringIndex));
                    }
                }
            }
            if (button == MouseEvent.BUTTON3) {
                System.out.println(selectedIndexes.size());
                if (!selectedIndexes.isEmpty()) {
                    rightClickSelectedIndexes.clear();
                    if (selectedIndexes.size() == 1) {
                        rightClickSelectedIndexes.add(selectedIndexes.get(0));
                        rightSingleClickMenu.setBackgroundColor(Color.GRAY);
                        rightSingleClickMenu.setHoverColor(Color.LIGHT_GRAY);
                        rightSingleClickMenu.setBorderColor(Color.BLACK);
                        rightSingleClickMenu.setOffsetX(-10);
                        rightSingleClickMenu.setOffsetY(-5);
                        rightSingleClickMenu.update(new Point(e.getLocationOnScreen().x, e.getLocationOnScreen().y), true);
                    } else {
                        for (int i = 0; i < selectedIndexes.size(); i++) {
                            rightClickSelectedIndexes.add(selectedIndexes.get(i));
                        }
                        multiSingleClickMenu.setBackgroundColor(Color.GRAY);
                        multiSingleClickMenu.setHoverColor(Color.LIGHT_GRAY);
                        multiSingleClickMenu.setBorderColor(Color.BLACK);
                        multiSingleClickMenu.setOffsetX(-10);
                        multiSingleClickMenu.setOffsetY(-5);
                        multiSingleClickMenu.update(new Point(e.getLocationOnScreen().x, e.getLocationOnScreen().y), true);
                    }
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            ArrayList<Photo> photos = info.getPhotos();

            int size = 110;
            int sizeToIncreaseBy = size + 5;
            int ogX = 5;
            int ogY = 5;
            int x = 5;
            int y = 5;

            int screenWidth = getSize().width;
            int screenHeight = getSize().height;

            for (int i = 0; i < photos.size(); i++) {
                if (x + sizeToIncreaseBy >= screenWidth) {
                    x = ogX;
                    y += sizeToIncreaseBy;
                }
                if (ShapeUtil.intersects(e.getX(), e.getY(), 1, 1, x, y, size, size)){
                    hoveringIndex = i;
                    break;
                } else {
                    hoveringIndex = -1;
                }
                x += sizeToIncreaseBy;
            }
            repaint();
        }
    }

    private class AddImages extends JDialog {

        private ArrayList<Photo> imagesAdded;

        public AddImages() {
            imagesAdded = new ArrayList<>();
        }

        public void clear() {
            imagesAdded.clear();
        }
    }

}
