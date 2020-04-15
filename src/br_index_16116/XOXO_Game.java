package br_index_16116;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class XOXO_Game  extends JFrame {
	// TODO Auto-generated method stub
	private int Num_of_rows = 3;
	private int Num_of_columns = 3;
	
	private int Field_size = 100;
	private int Board_width = Field_size * Num_of_columns;
	private int Board_height = Field_size * Num_of_rows;
	private int Grid_width = 2;
	
	private int SymbolDistance_of_field = Field_size / 5;
	private int Symbol_size = Field_size - SymbolDistance_of_field * 2;
	private int Symbol_width = 8;
	
	private enum Game_status{ Running, Xwin , Owin, Draw } 
	private enum Field_status { Empty, X , O }
	int a1, b1, a2, b2, win_x = 0,win_y=0;
	
	
	public class DrawField extends JPanel {

		@Override
		protected void paintComponent(Graphics object) {
			// TODO Auto-generated method stub
			super.paintComponent(object);
			setBackground(Color.WHITE);
			
			object.setColor(Color.GRAY);
			for(int i = 1; i < Num_of_rows;++i)
			{
				object.fillRoundRect(0, Field_size * i - (Grid_width / 2),
									 Board_width - 1, Grid_width,
									 Grid_width, Grid_width);
			}
			
			for(int i = 1; i < Num_of_columns; ++i) 
			{
				object.fillRoundRect(Field_size * i - (Grid_width / 2), 0,
									Grid_width, Board_height-1,
									Grid_width, Grid_width);
			}
			
			Graphics2D object2d = (Graphics2D) object;
			BasicStroke temp = new BasicStroke(Symbol_width,
								   BasicStroke.CAP_ROUND,
								   BasicStroke.JOIN_ROUND);
			object2d.setStroke(temp);
			
			/*
				Ovaj dio koda nam crata X ili O uzavisnosti ko je na potezu
			*/
			
			for(int i = 0; i<Num_of_rows; ++i)
			{
				for(int j = 0; j<Num_of_columns; ++j)
				{
					int x1 = j * Field_size + SymbolDistance_of_field;
					int y1 = i * Field_size + SymbolDistance_of_field;
					if(Board[i][j] == Field_status.X)
					{
						object2d.setColor(Color.BLACK);
						int x2 = (j+1) * Field_size - SymbolDistance_of_field;
						int y2 = (i+1) * Field_size - SymbolDistance_of_field;
						object2d.drawLine(x1, y1, x2, y2);
						object2d.drawLine(x2, y1, x1, y2);
					}
					else if(Board[i][j] == Field_status.O)
					{
						object2d.setColor(Color.BLACK);
						object2d.drawOval(x1, y1, Symbol_size, Symbol_size);
					}
				}
			}
		
		Status.setHorizontalAlignment(JLabel.CENTER);
		if(Game == Game_status.Running) 
		{
			if(Player == Field_status.X) 
				Status.setText("X je na potezu");
			else 
				Status.setText("O je na potezu");
		}
		else if(Game == Game_status.Draw) 
		{
			Status.setText("Nerijeseno");
		}
		else if(Game == Game_status.Owin)
		{	
			object2d = (Graphics2D) object;
			object2d.setColor(Color.RED);
			object2d.setStroke(new BasicStroke(25,BasicStroke.CAP_ROUND,
					   		   BasicStroke.JOIN_ROUND));
			object2d.drawLine(a1,b1,a2,b2);
			++win_y;
			Status.setText("Pobjeda igraca O");
		}
		else if(Game == Game_status.Xwin)
		{
			object2d = (Graphics2D) object;
			object2d.setColor(Color.RED);
			object2d.setStroke(new BasicStroke(25, BasicStroke.CAP_ROUND,
					   		   BasicStroke.JOIN_ROUND));;
			object2d.drawLine(a1,b1,a2,b2);
			++win_x;
			Status.setText("Pobjeda igraca X");
		}
		
		}
	}
	
	public class MouseAdapter implements MouseListener 
	{

		@Override
		public void mouseClicked(MouseEvent e) 
		{
			// TODO Auto-generated method stub
			int X = e.getX();
			int Y = e.getY();
			
			int current_row = Y / Field_size;
			int current_column = X / Field_size;
			
			if(Game == Game_status.Running)
			{
				if(cpu_bool!=1) 
				{
					if(current_row >= 0 && current_row < Num_of_rows &&
							current_column >= 0 && current_column < Num_of_columns &&
							Board[current_row][current_column] == Field_status.Empty) 
					{
						Board[current_row][current_column] = Player;
						update(Player,current_row,current_column);
					
						if(Player == Field_status.X)
						{
							Player = Field_status.O;
						}
						else
						{
							Player = Field_status.X;
						}
					}
				}
				else
				{
					if(current_row >= 0 && current_row < Num_of_rows &&
							current_column >= 0 && current_column < Num_of_columns &&
							Board[current_row][current_column] == Field_status.Empty) 
					{
						Board[current_row][current_column] = Player;
						update(Player,current_row,current_column);
					}
					
					int[] intArray = {0, 1, 2};
						current_column = new Random().nextInt(intArray.length);
						current_row = new Random().nextInt(intArray.length);
					
					while(Board[current_row][current_column] == Field_status.X 
					   || Board[current_row][current_column] == Field_status.O)
					{
						current_column = new Random().nextInt(intArray.length);
						current_row = new Random().nextInt(intArray.length);
					}
					if(Board[current_row][current_column] == Field_status.Empty) 
					{
						Board[current_column][current_row] = Field_status.O;	
						update(Field_status.O,current_row,current_column);	
					}
				}	
			}
			else
			{
				start();
			}
			repaint();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	private Game_status Game;
	private Field_status Player;
	private Field_status [][] Board;
	private DrawField Field;
	private JLabel Status;
	private int cpu_bool = 0;
	
	public XOXO_Game() 
	{
		Field = new DrawField();
		Dimension dim = new Dimension(Board_width,Board_height);
		Field.setPreferredSize(dim);
		Field.addMouseListener(new MouseAdapter());
		Status = new JLabel(" ");
		
		JButton result_button = new JButton("Result");
		result_button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String string = "The current result is: \n"
								+ "Player 'X' : " + win_x 
								+ "\nPlayer 'O' : " + win_y;
				JOptionPane.showMessageDialog(null,
				string, "Result", JOptionPane.PLAIN_MESSAGE );
			}
		});
		
		JButton restart = new JButton("Resart");
		restart.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						win_x = win_y = 0;
						cpu_bool=0;
						Game = Game_status.Draw;
						start();
						repaint();
						
					}
				});
		
		JButton CPU = new JButton("vs CPU");
		CPU.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						win_x = win_y = 0;
						cpu_bool=1;
						Game = Game_status.Draw;
						start();
						repaint();
					}
				});
		
		Container temp = getContentPane();
		temp.setLayout(new BorderLayout());
		temp.add(Field,BorderLayout.CENTER);
		temp.add(Status, BorderLayout.PAGE_END);
		Box box = Box.createVerticalBox();
		box.add(Box.createVerticalStrut(25));  
		box.add(result_button);
		box.add(Box.createVerticalStrut(25));  
		box.add(restart);
		box.add(Box.createVerticalStrut(25));  
		box.add(CPU);
		temp.add(box,BorderLayout.EAST);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setTitle("Igra X - O");
		setVisible(true);
		
		Board = new Field_status[Num_of_rows][Num_of_columns];
		start();
	}

	public void start() {
		// TODO Auto-generated method stub
		for(int i = 0; i < Num_of_rows; ++i)
		{
			for(int j = 0; j < Num_of_columns; ++j)
			{
				Board[i][j] = Field_status.Empty;
			}
		}
		
		Game = Game_status.Running;
		Player = Field_status.X;
	}

	public void update(Field_status player2, int current_row, int current_column) {
		// TODO Auto-generated method stub
		if(Win(player2, current_row, current_column))
		{
			if(player2 == Field_status.X)
			{
				Game = Game_status.Xwin;
			}
			else
			{
				Game = Game_status.Owin;
			}
		}
		else if(Draw()) 
		{
			Game = Game_status.Draw;
		}
	}

	private boolean Win(Field_status player2, int current_row, int current_column) {
		// TODO Auto-generated method stub
		if(Board[current_row][0] == player2 &&
		   Board[current_row][1] == player2 &&
		   Board[current_row][2] == player2) 
		{
			b1 = current_row*Field_size+50;
			a1 = 50;
			b2 = current_row*Field_size+50;
			a2 = 2*Field_size+50;
			return true;
		}
		else if(Board[0][current_column] == player2 &&
				Board[1][current_column] == player2 &&
				Board[2][current_column] == player2) 
		{
			b1 = 50;
			a1 = current_column*Field_size+50;
			b2 = 2*Field_size+50;
			a2 = current_column*Field_size+50;
			return true;
		}
		
		else if(Board[0][0] == player2 &&
				Board[1][1] == player2 &&
				Board[2][2] == player2) 
		{
			b1 = 50;
			a1 = 50;
			b2 = 2*Field_size+50;
			a2 = 2*Field_size+50;
			return true;
		}
		
		else if(((current_column + current_row) == 2) &&
				Board[0][2] == player2 &&
				Board[1][1] == player2 &&
				Board[2][0] == player2) 
		{
			b1 = 50;
			a1 = 2*Field_size+50;
			b2 = 2*Field_size+50;
			a2 = 50;
			return true;
		}
		else
		{
			return false;
		}
	}

	private boolean Draw() {
		// TODO Auto-generated method stub
		
		for( int i = 0; i < Num_of_rows; ++i)
		{
			for( int j = 0; j < Num_of_columns; ++j)
			{
				if(Board[i][j] == Field_status.Empty)
					return false;
			}
		}
		return true;
	}
}
