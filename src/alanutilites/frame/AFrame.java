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
package alanutilites.frame;

import alanutilites.util.Text;
import alanutilites.util.popup_window.ToolTip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.LayoutManager;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;

/**
 *
 * @author Alan Tsui
 * @version 1.0
 * @since 1.1
 */
public class AFrame extends JFrame{
    
    public static void main(String args[]){
        JPanel panel = new JPanel();
        JPanel pan = new JPanel();
        pan.setBackground(Color.blue);
        panel.setBackground(Color.GREEN);
//        panel.setBounds(10, 10, 50, 50);
        AFrame frame = new AFrame("TitleasfdasfasdfadsfashidasdfasdfasdfasdfasdfasdfadsfhhhkafkhkgkskgksgdkgdkhsgdkhsdksdksdgssgsdfgsdfgsdfgdfasdfasdfasdfasdfadsfadfsPOOP");
//        JFrame frame = new JFrame("Title");
//        frame.setLayout(null);
        frame.add(panel);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setPreferredSize(new Dimension(500,500));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    private static final Font DEFAULT_FONT = new Font("Arial",1,12);
    private static final Color DEFAULT_FRAME_COLOR = new Color(51, 51, 51);
    private static final Color DEFAULT_BORDER_COLOR = new Color(51, 51, 51);
    private static final Color DEFAULT_TOOLTIP_BACKGROUND_COLOR = new Color(150,150,150);
    private static final Color DEFAULT_TOOLTIP_FOREGROUND_COLOR = Color.BLACK;
    
    private Font currentFont = DEFAULT_FONT;
    private Color frameColor = DEFAULT_FRAME_COLOR;
    private Color borderColor = DEFAULT_BORDER_COLOR;
    private Color tooltipBackgroundColor = DEFAULT_TOOLTIP_BACKGROUND_COLOR;
    private Color tooltipForegroundColor = DEFAULT_TOOLTIP_FOREGROUND_COLOR;
    
    private final SideLabel left = new SideLabel(Side.W);
    private final SideLabel right = new SideLabel(Side.E);
    private final SideLabel top = new SideLabel(Side.N);
    private final SideLabel bottom = new SideLabel(Side.S);
    private final SideLabel topleft = new SideLabel(Side.NW);
    private final SideLabel topright = new SideLabel(Side.NE);
    private final SideLabel bottomleft = new SideLabel(Side.SW);
    private final SideLabel bottomright = new SideLabel(Side.SE);
    
    private JPanel titlePanel;
    private JPanel northPanel;
    private JPanel southPanel;
    
    private final JPanel contentPanel = new JPanel(new BorderLayout());
    private final JPanel resizePanel = new JPanel(new BorderLayout()){
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            int w = getWidth();
            int h = getHeight();
            
            g2.setPaint(frameColor);
            g2.fillRect(0, 0, w, h);
            
            g2.setPaint(borderColor);
            g2.drawRect(0, 0, w - 1, h - 1);

            g2.drawLine(0, 2, 2, 0);
            g2.drawLine(w - 3, 0, w - 1, 2);

            g2.clearRect(0, 0, 2, 1);
            g2.clearRect(0, 0, 1, 2);
            g2.clearRect(w - 2, 0, 2, 1);
            g2.clearRect(w - 1, 0, 1, 2);

            g2.dispose();
        }
    };
    private final WindowSnapper snap = new WindowSnapper();
    private final WindowDragger drag = new WindowDragger();
    
    private GraphicsEnvironment ge;
    
    private TitleBar titleBar;    
    public AFrame(){
        this("",null);
    }
    
    public AFrame(String title){
        this(title,null);
    }
    
    public AFrame(String title, ImageIcon icon){
        TitleBar bar = new TitleBar(title, icon, defaultButtons());
        bar.setSize(200,25);
        init(bar);
    }
    
    public AFrame(TitleBar bar){
        bar.setSize(200,25);
        init(bar);
    }
    
    private void init(TitleBar bar){
        ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        setMaximizedBounds(ge.getMaximumWindowBounds());
        
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowDeiconified(WindowEvent e) {
                if(getExtendedState() == JFrame.MAXIMIZED_BOTH){
                    setExtendedState(Frame.MAXIMIZED_BOTH);
                }
                else{
                    setExtendedState(JFrame.NORMAL);
                }
                AFrame.this.changeTitleSize(AFrame.this.getSize());
            }
        });
        
        titleBar = bar;
//        titleBar.setOpaque(false);
        titleBar.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
        titleBar.addMouseListener(drag);
        titleBar.addMouseMotionListener(drag);
        titleBar.addMouseListener(snap);
        titleBar.setBackground(DEFAULT_FRAME_COLOR);
        
        setMinimumSize(new Dimension(200,100));
        setUndecorated(true);
        setBackground(new Color(0,0,0,0));
        setBorderColor(borderColor);
        
        ResizeWindowListener rwl = new ResizeWindowListener();
        for (SideLabel l: Arrays.asList(left, right, top, bottom, topleft, topright, bottomleft, bottomright)) {
            l.addMouseListener(rwl);
            l.addMouseMotionListener(rwl);
            l.setOpaque(true);
        }
        
        titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(top, BorderLayout.NORTH);
        titlePanel.add(titleBar, BorderLayout.CENTER);
        
        northPanel = new JPanel(new BorderLayout());
        northPanel.add(topleft, BorderLayout.WEST);
        northPanel.add(titlePanel, BorderLayout.CENTER);
        northPanel.add(topright, BorderLayout.EAST);

        southPanel = new JPanel(new BorderLayout());
        southPanel.add(bottomleft, BorderLayout.WEST);
        southPanel.add(bottom, BorderLayout.CENTER);
        southPanel.add(bottomright, BorderLayout.EAST);

        resizePanel.add(left, BorderLayout.WEST);
        resizePanel.add(right, BorderLayout.EAST);
        resizePanel.add(northPanel, BorderLayout.NORTH);
        resizePanel.add(southPanel, BorderLayout.SOUTH);
        resizePanel.add(contentPanel, BorderLayout.CENTER);

        titlePanel.setOpaque(false);
        northPanel.setOpaque(false);
        southPanel.setOpaque(false);
        
        contentPanel.setOpaque(false);
        resizePanel.setOpaque(false);
        setContentPane(resizePanel);

    }

    @Override
    public void setPreferredSize(Dimension size) {
        boolean resizable = isResizable();
        setResizable(true);
        super.setPreferredSize(size);
        titleBar.setSize(size.width, titleBar.getSize().height);
        size.setSize(size.width, size.height+titleBar.getSize().height);
        super.setSize(size);
        setResizable(resizable);
    }

    @Override
    public void setBounds(Rectangle r) {
        if(isResizable()){
            setBounds(r.x,r.y,r.width,r.height);
        }
        else{
            setBounds(r.x,r.y,getBounds().width,getBounds().height);
        }
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        if(isResizable()){
            if(y < 0){
                super.setBounds(x, 0, width, height);
            }
            else if(y > ge.getMaximumWindowBounds().height-15){
                super.setBounds(x, ge.getMaximumWindowBounds().height-15, width, height);
            }
            else{
                super.setBounds(x, y, width, height);
            }
            changeTitleSize(width);
        }
        else{
            if(y < 0){
                super.setBounds(x, 0, getBounds().width, getBounds().height);
            }
            else if(y > ge.getMaximumWindowBounds().height-15){
                super.setBounds(x, ge.getMaximumWindowBounds().height-15, getBounds().width, getBounds().height);
            }
            else{
                super.setBounds(x, y, getBounds().width, getBounds().height);
            }
        }
    }
    
    @Override
    public void setSize(int width, int height){
        if(isResizable()){
            height += titleBar.getSize().height;
            super.setSize(width,height);
            changeTitleSize(width);
        }
    }
    
    @Override
    public void setSize(Dimension dimensions){
        if(isResizable()){
            setSize(dimensions.width, dimensions.height);
        }
    }

    @Override
    public Container getContentPane() {
        return contentPanel;
    }
    
    public void defaultLayout(){
        currentFont = DEFAULT_FONT;
        frameColor = DEFAULT_FRAME_COLOR;
        borderColor = DEFAULT_BORDER_COLOR;
        tooltipBackgroundColor = DEFAULT_TOOLTIP_BACKGROUND_COLOR;
        tooltipForegroundColor = DEFAULT_TOOLTIP_FOREGROUND_COLOR;
        
        setBackground(new Color(0,0,0,0));
        setBorderColor(borderColor);
        
        TitleBar bar = new TitleBar(titleBar.getTitle(), titleBar.getIcon(), defaultButtons());
        bar.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
        
        bar.setBackground(DEFAULT_FRAME_COLOR);
        
        bar.addMouseListener(drag);
        bar.addMouseMotionListener(drag);
        bar.addMouseListener(snap);
        setTitleBar(bar);
        
    }
    
    public TitleButton[] defaultButtons(){
        Dimension size = getSize();
        TitleButton exit = new TitleButton("Close", "X", null, new Rectangle(size.width-25,0,25,25));
        exit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });

        TitleButton maximize = new TitleButton("Maximize", "⯦", null, new Rectangle(size.width-50,0,25,25));
        maximize.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                maximize();
            }
        });

        TitleButton minimize = new TitleButton("Minimize", "-", null, new Rectangle(size.width-75,0,25,25));
        minimize.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                minimize();
            }
        });
        return new TitleButton[]{exit,maximize,minimize};
    }
    
    public void maximize(){
        if(isResizable()){
            if(getExtendedState() != JFrame.MAXIMIZED_BOTH){
                setExtendedState(Frame.MAXIMIZED_BOTH);
                for (SideLabel l: Arrays.asList(left, right, top, bottom, topleft, topright, bottomleft, bottomright)) {
                    l.setVisible(false);
                }
                snap.setDisabled(true);
                drag.setDisabled(true);
            }
            else{
                setExtendedState(JFrame.NORMAL);
                for (SideLabel l: Arrays.asList(left, right, top, bottom, topleft, topright, bottomleft, bottomright)) {
                    l.setVisible(true);
                }
                snap.setDisabled(false);
                drag.setDisabled(false);
            }
            AFrame.this.changeTitleSize(AFrame.this.getSize());
        }
    }
    
    public void minimize(){
        setExtendedState(JFrame.ICONIFIED);
    }
    
    public void exit(){
        switch (getDefaultCloseOperation()) {
            case HIDE_ON_CLOSE:
                setVisible(false);
                break;
            case DISPOSE_ON_CLOSE:
                dispose();
                break;
            case DO_NOTHING_ON_CLOSE:
            default:
                break;
            case EXIT_ON_CLOSE:
                System.exit(0);
                break;
        }         
    }
    
    @Override
    public void setTitle(String title){
        titleBar.setTitle(title);
    }
    
    @Override
    public String getTitle(){
        return titleBar.getTitle();
    }

    public void setTitleBar(TitleBar titleBar) {
        titlePanel.remove(this.titleBar);
        this.titleBar = titleBar;
//        this.titleBar.setOpaque(false);
        this.titleBar.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));
        this.titleBar.addMouseListener(drag);
        this.titleBar.addMouseMotionListener(drag);
        this.titleBar.addMouseListener(snap);
        titlePanel.add(this.titleBar, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    public void addComponent(Component comp){
        add(comp);
        revalidate();
        repaint();
    }
    
    public void removeComponent(Component comp){
        remove(comp);
        revalidate();
        repaint();
    }

    public TitleBar getTitleBar() {
        return titleBar;
    }
    
    public void changeTitleSize(int width){
        if(isResizable()){
            titleBar.setSize(width-Side.E.dim.width-Side.W.dim.width, titleBar.getSize().height);
        }
    }
    
    public void changeTitleSize(Dimension dimensions){
        if(isResizable()){
            titleBar.setSize(dimensions.width-Side.E.dim.width-Side.W.dim.width, titleBar.getSize().height);
        }        
    }
    
    public void setCurrentFont(Font font){
        this.currentFont = font;
    }
    
    public Font getCurrentFont(){
        return currentFont;
    }

    @Override
    public void setResizable(boolean resizable) {
        super.setResizable(resizable);
        for (SideLabel l: Arrays.asList(left, right, top, bottom, topleft, topright, bottomleft, bottomright)) {
            if(!resizable){
                l.setCursor(Cursor.getDefaultCursor());
            }
            else{
                l.setCursor(Cursor.getPredefinedCursor(l.side.cursor));
            }
        }
    }

    public void setFrameColor(Color frameColor) {
        this.frameColor = frameColor;
    }

    public Color getFrameColor() {
        return frameColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        for(SideLabel l : Arrays.asList(left, right, top, bottom, topleft, topright, bottomleft, bottomright)){
            l.setBackground(borderColor);
        }
        repaint();
    }

    public Color getBorderColor() {
        return borderColor;
    }
    
    public class TitleBar extends JPanel implements MouseListener, MouseMotionListener{
        private ImageIcon icon;
        private String title;
        private TitleButton[] buttons;
        private Font currentFont = DEFAULT_FONT;
        private Color textColor;
        private ToolTip tip;
        public TitleBar(String title){
            this(title, null, defaultButtons());
        }
        
        public TitleBar(String title, ImageIcon icon, TitleButton... buttons){
            this.title = title;
            this.icon = icon;
            this.buttons = buttons;
            tip = new ToolTip("", currentFont, 1);
            init();
        }
        
        private void init(){
            textColor = Color.BLACK;
            setSize(200,25);
            addMouseListener(this);
            addMouseMotionListener(this);
        }
        
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D gd = (Graphics2D)g;
            
            gd.setColor(textColor);
            gd.setFont(currentFont);
            
            gd.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            
            if(icon != null){
                gd.drawImage(icon.getImage(), 0, 0, getHeight()-5, getHeight()-5, null);
            }
            
            gd.setFont(currentFont);
            
            int offSet = 0;
            for(TitleButton button : buttons){
                if(button != null){
                    button.render(gd);
                    offSet += button.getBounds().width;
                }
            }
            
            gd.setColor(textColor);
            gd.setFont(currentFont);
            if(icon == null){
                String text = Text.ellipsisText(title, getWidth()-offSet, currentFont);
                System.out.println(getSize().width+" "+offSet+" "+(getBounds().width-offSet)+" "+text);
                gd.drawString(text, 0, getHeight()-5);
            }
            else{
                gd.drawString(Text.ellipsisText(title, getWidth()-(getHeight()+5)-offSet, currentFont), getHeight()+5, getHeight()-5);
            }
        }
        
        @Override
        public void setSize(Dimension dimensions){
            setSize(dimensions.width, dimensions.height);
        }
        
        @Override
        public void setSize(int width, int height){
            super.setSize(width, height);
            setPreferredSize(new Dimension(width,height));
            int x = width;
            for(int i=0;i<buttons.length;i++){
                x = (x-(buttons[i].bounds.width))-Side.E.dim.width;
                buttons[i].setBounds(new Rectangle(x, buttons[i].getBounds().y, buttons[i].getBounds().width, buttons[i].getBounds().height));
            }
            repaint();
        }

        public TitleButton[] getButtons() {
            return buttons;
        }

        public void setButtons(TitleButton[] buttons) {
            this.buttons = buttons;
        }
        
        public void setTitleBorder(Color color){
            top.setBackground(color);
            topleft.setBackground(color);
            topright.setBackground(color);
        }

        public Color getTextColor() {
            return textColor;
        }

        public void setTextColor(Color textColor) {
            this.textColor = textColor;
        }
    
        public void setCurrentFont(Font font){
            this.currentFont = font;
        }

        public Font getCurrentFont(){
            return currentFont;
        }
        
        public ImageIcon getIcon() {
            return icon;
        }

        public void setIcon(ImageIcon icon) {
            this.icon = icon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        
        private void fireToolTip(String string){
            tip.setMessage(string);
            tip.setOffsetX(10);
            tip.setBackgroundColor(tooltipBackgroundColor);
            tip.setForegroundColor(tooltipForegroundColor);
        }
        
        @Override
        public void mousePressed(MouseEvent e) {
            for(TitleButton button : buttons){
                if(button.getBounds().contains(e.getPoint())){
                    button.setState(3);
                }
                else{
                    button.setState(1);
                }
            }
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
            for(TitleButton button : buttons){
                if(button.getBounds().contains(e.getPoint())){
                    button.setState(4);
                }
                else{
                    button.setState(1);
                }
            }
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            for(TitleButton button : buttons){
                button.setState(1);
                tip.update(null, false);
            }
        }
        
        @Override
        public void mouseMoved(MouseEvent e) {
            boolean bool = false;
            for(TitleButton button : buttons){
                if(button.getBounds().contains(e.getPoint())){
                    button.setState(2);
                    fireToolTip(button.getName());
                    tip.update(e.getLocationOnScreen(), true);
                    bool = true;
                }
                else{
                    button.setState(1);
                }
            }
            if(!bool){
                tip.update(null, false);                
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseDragged(MouseEvent e) {}
    }
    
    public class TitleButton {
        private String name;//Name of action
        private ImageIcon icon;
        private String stringIcon;//Use if no icon
        private Rectangle bounds;
        
        private Color textColor;
        private Color clickColor;
        private Color color;
        private Color hoverColor;
        private Font font = DEFAULT_FONT;
        
        private ArrayList<ActionListener> actionListeners;
        
        private String actionCommand;
        
        /**
         * 1 = no state;
         * 2 = hovering;
         * 3 = pressing;
         * 4 = released;
         */
        private int state;
        public TitleButton(String name, String stringIcon){
            this(name, stringIcon, null, new Rectangle(0,0,25,25));
        }
        
        public TitleButton(String name, String stringIcon, ImageIcon icon){
            this(name, stringIcon, icon, new Rectangle(0,0,25,25));
        }
        
        public TitleButton(String name, String stringIcon, ImageIcon icon, Rectangle bounds){
            this.name = name;
            this.stringIcon = stringIcon != null ? stringIcon : "";
            this.icon = icon;
            this.bounds = bounds != null ? bounds : new Rectangle(0,0,25,25);
            actionListeners = new ArrayList<>();
            textColor = Color.BLACK;
            color = new Color(0,0,0,0);
            clickColor = new Color(128,128,128,230);
            hoverColor = new Color(128,128,128,125);
        }
        
        public void render(Graphics2D gd){
            gd.setColor(textColor);
            gd.setFont(font);
            Text.drawCenteredString(stringIcon, bounds.x, bounds.y, bounds.width, bounds.height, gd);
            gd.setColor(color);
            gd.fill(bounds);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Font getFont() {
            return font;
        }

        public void setFont(Font font) {
            this.font = font;
        }

        public ImageIcon getIcon() {
            return icon;
        }

        public void setIcon(ImageIcon icon) {
            this.icon = icon;
            repaint();
        }

        public String getStringIcon() {
            return stringIcon;
        }

        public void setStringIcon(String stringIcon) {
            this.stringIcon = stringIcon;
            repaint();
        }

        public Rectangle getBounds() {
            return bounds;
        }

        public void setBounds(Rectangle bounds) {
            this.bounds = bounds;
            repaint();
        }

        public String getActionCommand() {
            return actionCommand;
        }

        public void setActionCommand(String actionCommand) {
            this.actionCommand = actionCommand;
        }

        public Color getTextColor() {
            return textColor;
        }

        public void setTextColor(Color textColor) {
            this.textColor = textColor;
        }

        public Color getClickColor() {
            return clickColor;
        }

        public void setClickColor(Color clickColor) {
            this.clickColor = clickColor;
        }

        public Color getHoverColor() {
            return hoverColor;
        }

        public void setHoverColor(Color hoverColor) {
            this.hoverColor = hoverColor;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            if(state >= 1 && state <= 4){
                this.state = state;
                if(state == 1){
                    color = new Color(0,0,0,0);
                }
                else if(state == 2){
                    color = hoverColor;
                }
                else if(state == 3){
                    color = clickColor;
                }
                else if(state == 4){
                    fireActionPerformed();
                    color = hoverColor;
                }
                repaint();
            }
        }
        
        private void fireActionPerformed(){
            for(ActionListener al : actionListeners){
                al.actionPerformed(
                        new ActionEvent(
                                TitleButton.this, 0, getActionCommand(),
                                System.currentTimeMillis(),
                                0));
            }
        }
        
        public void addActionListener(ActionListener al){
            actionListeners.add(al);
        }
        
        public void removeActionListener(ActionListener al){
            actionListeners.remove(al);
        }
    }
    
    private class WindowSnapper extends MouseAdapter{
        private boolean disabled;
        private GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        @Override
        public void mouseReleased(MouseEvent e){
            if(!disabled){
                Component c = SwingUtilities.getRoot(e.getComponent());
                if(c instanceof Frame){
                    Frame frame = (Frame)c;
                    if(frame.isResizable()){
                        boolean changed = false;
                        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
                        Point point = e.getLocationOnScreen();            
                        Rectangle rect = new Rectangle(frame.getX(), frame.getY(), frame.getWidth(), frame.getHeight());

                        if (point.y <= ge.getMaximumWindowBounds().y) {
                            changed = false;
                            maximize();
                        }
                        if (point.x <= ge.getMaximumWindowBounds().x) {
                            changed = true;
                            rect.setBounds(ge.getMaximumWindowBounds().x, ge.getMaximumWindowBounds().y, size.width/2, ge.getMaximumWindowBounds().height);
                        }
                        if (point.x >= ge.getMaximumWindowBounds().width-1) {
                            changed = true;
                            rect.setBounds(size.width/2, ge.getMaximumWindowBounds().y, size.width/2, ge.getMaximumWindowBounds().height);
                        }            
                        if (point.y >= ge.getMaximumWindowBounds().height-1) {
                            changed = true;
                            rect.setBounds(ge.getMaximumWindowBounds().x, ge.getMaximumWindowBounds().height/2, size.width, ge.getMaximumWindowBounds().height/2);
                        }
                        if(changed){
                            frame.setBounds(rect);
                            repaint();
                        }
                    }
                }
            }
        }

        public boolean isDisabled() {
            return disabled;
        }

        public void setDisabled(boolean disabled) {
            this.disabled = disabled;
        }
    }

    private class WindowDragger extends MouseAdapter{
        private boolean disabled;
        private final Point start = new Point();

        @Override
        public void mousePressed(MouseEvent e){
            if(!disabled){
                if(SwingUtilities.isLeftMouseButton(e)){
                    start.setLocation(e.getPoint());
                }
            }
        }

        @Override
        public void mouseDragged(MouseEvent e){
            if(!disabled){
                Component c = SwingUtilities.getRoot(e.getComponent());
                if (c instanceof Frame && SwingUtilities.isLeftMouseButton(e)) {
                    Frame frame = (Frame) c;
                    Point pt = frame.getLocation();
                    frame.setLocation(pt.x - start.x + e.getX(), pt.y - start.y + e.getY());
                }
            }
        }

        public boolean isDisabled() {
            return disabled;
        }

        public void setDisabled(boolean disabled) {
            this.disabled = disabled;
        }
    }
    
    private enum Side {

        N(Cursor.N_RESIZE_CURSOR, new Dimension(0, 3)),
        W(Cursor.W_RESIZE_CURSOR, new Dimension(3, 0)),
        E(Cursor.E_RESIZE_CURSOR, new Dimension(3, 0)),
        S(Cursor.S_RESIZE_CURSOR, new Dimension(0, 3)),
        NW(Cursor.NW_RESIZE_CURSOR, new Dimension(3, 3)),
        NE(Cursor.NE_RESIZE_CURSOR, new Dimension(3, 3)),
        SW(Cursor.SW_RESIZE_CURSOR, new Dimension(3, 3)),
        SE(Cursor.SE_RESIZE_CURSOR, new Dimension(3, 3));
        public final Dimension dim;
        public final int cursor;

        Side(int cursor, Dimension dim) {
            this.cursor = cursor;
            this.dim = dim;
        }
    }

    private class SideLabel extends JLabel {

        public final Side side;

        public SideLabel(Side side) {
            super();
            this.side = side;
            setCursor(Cursor.getPredefinedCursor(side.cursor));
        }

        @Override
        public Dimension getPreferredSize() {
            return side.dim;
        }

        @Override
        public Dimension getMinimumSize() {
            return side.dim;
        }

        @Override
        public Dimension getMaximumSize() {
            return side.dim;
        }
    }

    class ResizeWindowListener extends MouseAdapter {

        private final Rectangle rect = new Rectangle();

        @Override
        public void mousePressed(MouseEvent e) {
            Component p = SwingUtilities.getRoot(e.getComponent());
            if (p instanceof Frame) {
                rect.setBounds(((Frame) p).getBounds());
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            Component c = e.getComponent();
            Component p = SwingUtilities.getRoot(c);
            if (!rect.isEmpty() && c instanceof SideLabel && p instanceof Frame) {
                Frame frame = (Frame)p;
                if(frame.isResizable()){
                    Side side = ((SideLabel) c).side;
                    Rectangle rectangle = getResizedRect(rect, side, e.getX(), e.getY());
                    frame.setBounds(rectangle);
                }
            }
        }

        private Rectangle getResizedRect(Rectangle r, Side side, int dx, int dy) {
            switch (side) {
                case NW:
                    if(r.height-dy > getMinimumSize().height){
                        r.y += dy;
                        r.height -= dy;
                    }
                    else{
                        r.y += r.height-getMinimumSize().height;
                        r.height = getMinimumSize().height;
                    }
                    if(r.width-dx > getMinimumSize().width){
                        r.x += dx;
                        r.width -= dx;
                    }
                    else{
                        r.x += r.width-getMinimumSize().width;
                        r.width = getMinimumSize().width;
                    }
                    break;
                case N:
                    if(r.height-dy > getMinimumSize().height){
                        r.y += dy;
                        r.height -= dy;
                    }
                    else{
                        r.y += r.height-getMinimumSize().height;
                        r.height = getMinimumSize().height;
                    }
                    break;
                case NE:
                    if(r.height-dy > getMinimumSize().height){
                        r.y += dy;
                        r.height -= dy;
                    }
                    else{
                        r.y += r.height-getMinimumSize().height;
                        r.height = getMinimumSize().height;
                    }
                    if(r.width+dx > getMinimumSize().width){
                        r.width += dx;
                    }
                    else{
                        r.width = getMinimumSize().width;
                    }
                    break;
                case W:
                    if(r.width-dx > getMinimumSize().width){
                        r.x += dx;
                        r.width -= dx;
                    }
                    else{
                        r.x += r.width-getMinimumSize().width;
                        r.width = getMinimumSize().width;
                    }
                    break;
                case E:
                    if(r.width+dx > getMinimumSize().width){
                        r.width += dx;
                    }
                    else{
                        r.width = getMinimumSize().width;
                    }
                    break;
                case SW:
                    if(r.height+dy > getMinimumSize().height){
                        r.height += dy;
                    }
                    else{
                        r.height = getMinimumSize().height;
                    }
                    if(r.width-dx > getMinimumSize().width){
                        r.x += dx;
                        r.width -= dx;
                    }
                    else{
                        r.x += r.width-getMinimumSize().width;
                        r.width = getMinimumSize().width;
                    }
                    break;
                case S:
                    if(r.height+dy > getMinimumSize().height){
                        r.height += dy;
                    }
                    else{
                        r.height = getMinimumSize().height;
                    }
                    break;
                case SE:
                    if(r.height+dy > getMinimumSize().height){
                        r.height += dy;
                    }
                    else{
                        r.height = getMinimumSize().height;
                    }
                    if(r.width+dx > getMinimumSize().width){
                        r.width += dx;
                    }
                    else{
                        r.width = getMinimumSize().width;
                    }
                    break;
                default:
                    throw new Error("Unknown SideLabel");
            }
//            System.out.println("11 : "+titleBar.getWidth()+" "+side);
            repaint();
            return r;
        }
    }
}
