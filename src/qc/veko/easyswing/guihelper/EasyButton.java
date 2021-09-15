package qc.veko.easyswing.guihelper;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.JButton;
import qc.veko.easyswing.engine.EasyPanel;
import qc.veko.easyswing.utils.Utils;

public class EasyButton {
	private JButton button;
	private int id;
	private String path;
	
	private EasyPanel panel;
	public static Font font = new Font("Arial", Font.PLAIN, 10);
	private EasyRectangle rect;
	private int x;
	private int y;
	
	public EasyButton (EasyPanel panel, int minX, int minY, int maxX, int maxY, String name, int id, String path) {
		this.panel = panel;
		button = new JButton(name);
		button.setBounds(minX, minY, maxX, maxY);
		button.addActionListener(onClick(this));
		button.addMouseListener(onRightClick(this));
		button.setBorderPainted(false);
		button.setFont(font);
		button.setFocusPainted(false);
		x = minX;
		y = minY;
		this.id = id;
		this.path = path;
	}

	public EasyButton addCommentary(EasyRectangle rect) {
		this.rect = rect;
		button.addMouseListener(addCommentary());
		return this;
	}

	private MouseListener addCommentary() {
		MouseListener mouseListener = new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {
				if(rect != null) {
					rect.setPosition(x, y-50);
					rect.add();
					panel.repaint();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if(rect != null) {
					rect.remove();
					panel.repaint();
				}
			}
		};
		return mouseListener;
	}

	private MouseAdapter onRightClick(EasyButton button) {
		MouseAdapter mouseAdapter = new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					panel.onButtonRightClick(button);
				}
			}
		};
		return mouseAdapter;
	}
	
	public EasyButton setColored(Color normal, Color hover) {
		button.setBackground(normal);
		button.addMouseListener(new MouseAdapter() {
		    public void mouseEntered(MouseEvent evt) {
		        button.setBackground(hover);
		    }
		    public void mouseExited(MouseEvent evt) {
		        button.setBackground(normal);
		    }
		    public void mouseClicked(MouseEvent evt) {
		    	button.setBackground(hover);
		    }
		});
		return this;
	}
	
	public EasyButton setTextured(Image normal, Image hover) {
		button.setIcon(Utils.convertImageToIcon(normal));
		button.setRolloverIcon(Utils.convertImageToIcon(hover));
		button.setPressedIcon(Utils.convertImageToIcon(hover));
		return this;
	}
	
	@SuppressWarnings("static-access")
	public EasyButton setFont(String path, float size) {
		Font font = null;
	    try
	    {
	      font = Font.createFont(0, getClass().getResource(path).openStream());
		    GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    genv.registerFont(font);
		    font = font.deriveFont(size);
		    button.setFont(font);
	    }
	    catch (FontFormatException e)
	    {
	      e.printStackTrace();
	    }
	    catch (IOException e)
	    {
	      e.printStackTrace();
	    }
	    this.font = font;
		return null;
	}
	
	public EasyButton setFontSize(float size) {
		font = font.deriveFont(size);
		button.setFont(font);
		return this;
	}
	
	public EasyButton setTextColor(Color color) {
		button.setForeground(color);
		return this;
	}
	
	private ActionListener onClick(EasyButton button) {
		ActionListener action = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.onButtonClick(button);
			}
		};
		return action;
	}
	
	public EasyButton addButton() {
		panel.add(button);
		return this;
	}
	
	public int getId() {
		return id;		
	}
	
	public String getPath() {
		return path;		
	}
	
	public void SetPath(String path) {
		this.path = path;
	}
	
	public JButton getJButton() {
		return button;
	}
	
	public String getText() {
		return button.getText();
	}
	
	public void setText(String name) {
		button.setText(name);
	}
	
}
