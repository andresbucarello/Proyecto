package com.proyecto1.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.Timer;

class Cell {
	int alive;
	int color;

	Cell(int alive, int color) {
		this.alive = alive;
		this.color = color;
	}
}

class GameOfLife extends JPanel {
    int nXCells;
    int nYCells;
    int cellWidth;
    int cellHeight;

    Cell[] world;
	Cell[] renderedWord;

    GameOfLife(int scrWidth, int scrHeight) {
		this.setSize(scrWidth, scrHeight);
        this.setPreferredSize(new Dimension(scrWidth, scrHeight));
        this.setMinimumSize(new Dimension(scrWidth, scrHeight));

        this.cellWidth = 5;
        this.cellHeight = 5;
        this.nXCells = scrWidth / this.cellWidth;
        this.nYCells = scrHeight / this.cellHeight;

        this.world = new Cell[this.nXCells * this.nYCells];
        Random random = new Random();
		for (int i = 0; i < this.world.length; i++) {
			int alive = random.nextBoolean() ? 1 : 0;
			this.world[i] = new Cell(alive, 0xff) ;
		}
		
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setCell(e.getX() / cellWidth, e.getY() / cellHeight, true);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
		});

		this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				setCell(e.getX() / cellWidth, e.getY() / cellHeight, true);
			}

			@Override
			public void mouseMoved(MouseEvent e) {
			}
		});
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.renderedWord = new Cell[this.nXCells * this.nYCells];
		for (int i = 0; i < this.world.length; i++)
			this.renderedWord[i] = new Cell(this.world[i].alive, this.world[i].color);

        for (int x = 0; x < this.nXCells; x++) {
            for (int y = 0; y < this.nYCells; y++) {
				int _x = x - 1 < 0 ? this.nXCells - 1 : x - 1;
				int x_ = x + 1 > this.nXCells ? 0 : x + 1;

				int _y = y - 1 < 0 ? this.nYCells - 1 : y - 1;
				int y_ = y + 1 > this.nYCells ? 0 : y + 1;

				int neighbors = getCell((_x) % this.nXCells, (_y) % this.nYCells, this.renderedWord).alive + getCell(( x) % this.nXCells, (_y) % this.nYCells, this.renderedWord).alive +
								getCell((x_) % this.nXCells, (_y) % this.nYCells, this.renderedWord).alive + getCell((x_) % this.nXCells, ( y) % this.nYCells, this.renderedWord).alive +
								getCell((x_) % this.nXCells, (y_) % this.nYCells, this.renderedWord).alive + getCell(( x) % this.nXCells, (y_) % this.nYCells, this.renderedWord).alive +
								getCell((_x) % this.nXCells, (y_) % this.nYCells, this.renderedWord).alive + getCell((_x) % this.nXCells, ( y) % this.nYCells, this.renderedWord).alive;
				Cell wCell = getCell(x, y, this.world);
				if (wCell.alive == 1)
					setCell(x, y, neighbors == 2 || neighbors == 3);
				else
					setCell(x, y, neighbors == 3);
				if (wCell.alive == 1)
					wCell.color = 0xff - 40;
				else
					wCell.color = wCell.color == 0 || (wCell.color - 2) < 0 ? wCell.color : wCell.color - 2;

				Cell cell = getCell(x, y, this.renderedWord);
				if (cell.alive == 1) {
					g.setColor(new Color(0x00, 0xff, 0x00));
					g.fillRect(this.cellWidth * x, this.cellHeight * y, this.cellWidth, this.cellHeight);
				}
				else {
					g.setColor(new Color(0x00, cell.color, 0x00));
					g.fillRect(this.cellWidth * x, this.cellHeight * y, this.cellWidth, this.cellHeight);
				}
            }
        }
    }

    Cell getCell(int x, int y, Cell[] a) {
        return a[y * this.nXCells + x];
    }

    void setCell(int x, int y, boolean v) {
        this.world[y * this.nXCells + x].alive = (v ? 1 : 0);
    }
}

public class HelpDialog {
    HelpDialog() {
        int width = 800;
        int height = 600;
        JDialog dialog = new JDialog();
        dialog.setModal(true);
		dialog.setResizable(false);

		GameOfLife game = new GameOfLife(width, height);

		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				game.repaint();
			}
		};
		Timer timer=new Timer(50,al);//create the timer which calls the actionperformed method for every 1000 millisecond(1 second=1000 millisecond)
		timer.setRepeats(true);
		timer.start();

		dialog.add(game);

		dialog.pack();
		dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
