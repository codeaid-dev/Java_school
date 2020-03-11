import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Painter_Ikeda extends JFrame{
	Face face[] = new Face[10];
	int count = -1;
	int flag = 0;
	int mode = 0;
	boolean max = false;
	
	JButton button1,button2;
	JTextField text;
	
	public Painter_Ikeda() {
		setTitle("Painter");
		setSize(500,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		MyListener mylistener = new MyListener();
		addMouseListener(mylistener);
		addMouseMotionListener(mylistener);
		
		button1 = new JButton();
		button1.addMouseListener(new ButtonListener1());
		button2 = new JButton();
		button2.addMouseListener(new ButtonListener2());
		text = new JTextField();
		text.setColumns(16);
		JPanel p = new JPanel();
		p.add(button2);
		p.add(button1);
		p.add(text);
		getContentPane().add(p, BorderLayout.SOUTH);
		
		button1.setText("Pacman");
		button2.setText("create mode");
		text.setText("Press a Pac-Man");
		setVisible(true);
		
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.clearRect(0, 0, 500, 450);
		if(count>=0) {
			if(count>9) {
				count = 9;
			}
			for (int i = 0; i<count+1; i++) {
				face[i].make(g);
			}
		}
	}
	
	public static void main(String[] args) {
		new Painter_Ikeda();
	}
	
	class MyListener extends MouseAdapter{
		int distance, xCenter, yCenter;
		
		public void mousePressed(MouseEvent e) {
			if(mode == 0) {
			if (++count < 10) {
				if(flag==0) {
					face[count] = new PacMan(0, e.getX(), e.getY(), 60);
				}else if(flag == 1) {
					face[count] = new PacManWithEye(0, e.getX(), e.getY(), 60 );
				}else if(flag == 2) {
					face[count] = new PacManWithAngry(0, e.getX(), e.getY(), 60 );
				}
				repaint();
				text.setText("The center is (" +e.getX() + ", "+e.getY() + ")");
			}else {
				max = true;
				count = 9;
				text.setText("No more Pacman");
			}
		}else if(mode == 1) {
		Boolean found = false;
			for(int num = 0; num < count + 1; num++) {
				if(Math.hypot(e.getX()-face[num].getXCenter(),
						e.getY()-face[num].getYCenter())<face[num].getSize()/2) {
					found = true;
					if(face[num]instanceof PacMan) {
						text.setText("This is PacMan");
					}else if(face[num]instanceof PacManWithEye) {
						text.setText("This is PacManWithEye");
					}else if(face[num]instanceof PacManWithAngry) {
						text.setText("This is PacManWithAngry");
					}else {
						text.setText("Type is not found");
					}
				}
			}
			if(!found) {
				text.setText("Press a Pac-Man");
			}
		}else if(mode == 2) {
		Boolean found = false;
			for(int num = 0; num < count + 1; num++) {
				if(Math.hypot(e.getX()-face[num].getXCenter(),
						e.getY()-face[num].getYCenter())<face[num].getSize()/2) {
					found = true;
					if(face[num]instanceof PacMan) {
						if(flag==1) {
							face[num] = new PacManWithEye(face[num].getSize(), face[num].getXCenter(),face[num].getYCenter(), 60 );
							text.setText("Changing to PacManWithEye");
						}else if(flag==2) {
								face[num] = new PacManWithAngry(face[num].getSize(), face[num].getXCenter(),face[num].getYCenter(), 60 );
								text.setText("Changing to PacManWithangry");
							}else  {
							text.setText("Already PacMan");
						}
					}else if(face[num]instanceof PacManWithEye) {
						if(flag==0) {
							face[num] = new PacMan(face[num].getSize(), face[num].getXCenter(),face[num].getYCenter(), 60 );
							text.setText("Changing to PacMan");
						}else if(flag==2) {
							face[num] = new PacManWithAngry(face[num].getSize(), face[num].getXCenter(),face[num].getYCenter(), 60 );
							text.setText("Changing to PacManWithangry");
						}else {
							text.setText("Already PacManWithEye");	
						}
						
					}else if(face[num]instanceof PacManWithAngry) {
						if(flag==0) {
							face[num] = new PacMan(face[num].getSize(), face[num].getXCenter(),face[num].getYCenter(), 60 );
							text.setText("Changing to PacMan");
						}else if(flag == 1) {
							face[num] = new PacManWithEye(face[num].getSize(), face[num].getXCenter(),face[num].getYCenter(), 60 );
							text.setText("Changing to PacManWithEye");
					    }else {
					    	text.setText("Already PacManWithAngry");
					    }
					}else {
						text.setText("Type is not found");
					}
					repaint();
				}
			}
			if (!found) {
				text.setText("Press a Pac-Man");
			}
		}
		}
		
		public void mouseDragged(MouseEvent e) {
			if(count < 10 && mode == 0 && max == false) {
				distance = (int)Math.hypot(e.getX()-face[count].getXCenter(), 
						                    e.getY()- face[count].getYCenter());
				face[count].setSize(distance * 2);
				text.setText(Integer.toString(distance * 2));
				repaint();
			}
		}
	}
	
	class ButtonListener1 extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			//if(++flag > 1) { flag = 0;} 1より大きくになると０に戻ってしまうから
			if(++flag > 2) {
				flag = 0;
			}
			if(flag == 0) {
				button1.setText("Pacman");
			}else if(flag == 1) {
				button1.setText("PacManWithEye");
			}else if(flag == 2) {
				button1.setText("PacManWithAngry");
			}
			}
		}
	
	class ButtonListener2 extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			
		if(++mode>2) {
			mode = 0;
		}
		if(mode == 0) {
			button2.setText("create mode");
		}else if(mode == 1) {
			button2.setText("check mode");
		}else if(mode == 2) {
			button2.setText("change mode");
	}
	}
}
}
	
	
	
	
	
	
	
	