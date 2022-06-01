package com.mycompany.tictactoe;

import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

/**
 * PRimary controller for tic tac toe game
 *
 * @author Jamie
 */
public class PrimaryController {

    //decalres all rectangle tags
    @FXML
    private Rectangle rect0;
    @FXML
    private Rectangle rect1;
    @FXML
    private Rectangle rect2;
    @FXML
    private Rectangle rect3;
    @FXML
    private Rectangle rect4;
    @FXML
    private Rectangle rect5;
    @FXML
    private Rectangle rect6;
    @FXML
    private Rectangle rect7;
    @FXML
    private Rectangle rect8;

    //declares all circle tags
    @FXML
    private Circle circ0;
    @FXML
    private Circle circ1;
    @FXML
    private Circle circ2;
    @FXML
    private Circle circ3;
    @FXML
    private Circle circ4;
    @FXML
    private Circle circ5;
    @FXML
    private Circle circ6;
    @FXML
    private Circle circ7;
    @FXML
    private Circle circ8;

    private boolean gameOver;

    public enum Space {
        CIRCLE, RECTANGLE, CLEAR
    };

    private boolean isCirc;

    private static Shape[] squares;
    private static Shape[] circles;
    private static Space[] tiles;

    private final int SIZE = 9;

    /**
     * initializes arrays and clears the board
     */
    public void initialize() {
        isCirc = false;
        //initalize squares array
        squares = new Shape[SIZE];
        squares[0] = rect0;
        squares[1] = rect1;
        squares[2] = rect2;
        squares[3] = rect3;
        squares[4] = rect4;
        squares[5] = rect5;
        squares[6] = rect6;
        squares[7] = rect7;
        squares[8] = rect8;

        //initalizes circles array
        circles = new Shape[SIZE];
        circles[0] = circ0;
        circles[1] = circ1;
        circles[2] = circ2;
        circles[3] = circ3;
        circles[4] = circ4;
        circles[5] = circ5;
        circles[6] = circ6;
        circles[7] = circ7;
        circles[8] = circ8;

        tiles = new Space[SIZE];
        clearBoard();
    }

    /**
     * makes a blank tic tac toe board
     */
    public void clearBoard() {
        for (int i = 0; i < SIZE; i++) {
            tiles[i] = Space.CLEAR;
            squares[i].setFill(Color.TRANSPARENT);
            circles[i].setFill(Color.TRANSPARENT);
            squares[i].setStroke(Color.TRANSPARENT);
            circles[i].setStroke(Color.TRANSPARENT);
            
        }
        gameOver = false;
        isCirc = false;
    }

    /**
     * sets the tile to mark if it's a circle or square
     *
     * @param event the user clicking a square
     */
    @FXML
    protected void setTile(Event event) {
        if (!gameOver) {
            //hit box is linked to rectangle
            Shape s = (Shape) event.getSource();
            int i = getIndex(s, squares);
            Shape[] type;
            //makes space for tile
            if (tiles[i] == Space.CLEAR) {
                if (isCirc) {
                    s = circles[i];
                    type = circles;
                    tiles[i] = Space.CIRCLE;
                } else {
                    tiles[i] = Space.RECTANGLE;
                    type = squares;
                }
                s.setFill(Color.LIGHTBLUE);
                s.setStroke(Color.BLACK);
                isCirc = !isCirc;
                isWin(tiles[i], s, type);

            }
        }
    }

    /**
     * returns the index of a shape in the array
     *
     * @param s the shape
     * @param shapes array of either circles of rectanges
     * @return the index of the shape
     */
    private int getIndex(Shape s, Shape[] shapes) {
        for (int i = 0; i < SIZE; i++) {
            if (s == shapes[i]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * plays the correct win animation
     *
     * @param type circle or rectangle to be played
     * @param shapes array for circle or rectangle
     */
    private void winAnimation(Space type, Shape[] shapes) {
        ParallelTransition par = new ParallelTransition();
        RotateTransition rotate;
        //loop through tiles to get all win shape
        //cicle win scale all circles
        if (type == Space.CIRCLE) {
            for (int i = 0; i < SIZE; i++) {
                //in loop add each shape to animation stream
                if (tiles[i] == type) {
                    ScaleTransition st = new ScaleTransition(Duration.seconds(1), shapes[i]);
                    st.setFromX(1);
                    st.setFromY(1);
                    st.setByX(1.05);
                    st.setByY(1.05);
                    st.setAutoReverse(true);
                    st.setCycleCount(2);
                    par.getChildren().add(st);
                }
            }
        } //rect win rotate all rectangles
        else {
            for (int i = 0; i < SIZE; i++) {
                //in loop add each shape to animation stream
                if (tiles[i] == type) {
                    rotate = new RotateTransition(Duration.seconds(1), shapes[i]);
                    rotate.setByAngle(360);
                    rotate.setAutoReverse(true);
                    rotate.setCycleCount(2);
                    par.getChildren().add(rotate);
                }
            }
        }
        //play animation
        par.play();
        gameOver = true;
    }

    /**
     * determines if a game is over
     * @param type type of shape (rectangle or circle
     * @param s instance of tile
     * @param shapes array or shapes
     */
    private void isWin(Space type, Shape s, Shape[] shapes) {
        //intializes
        final int LEN = 3;
        int index = getIndex(s, shapes);
        int quotion = index / LEN;
        int mod = index % LEN;
        boolean win = true;

        //let me know what you think of these equations
        //when you get the chance
        //vertical
        //036, 147, 258      
        for (int i = 0; i < LEN; i++) {
            //checks if each column matches that has index
            if (tiles[mod + (i * LEN)] != type) {
                win = false;
            }
        }
        if (win) {
            winAnimation(type, shapes);
        }

        win = true;
        //accross
        //012, 345, 678  
        for (int i = 0; i < LEN; i++) {
            //checks if each tile matches in row
            if (tiles[(quotion * LEN) + i] != type) {
                win = false;
            }
        }
        if (win) {
            winAnimation(type, shapes);
        }
        win = true;
        //diagnole
        if (index == 0 || index == 4 || index == 8) {
            //checks left to right diagnoal
            for (int i = 0; i < LEN; i++) {
                if (tiles[i * 4] != type) {
                    win = false;
                }
            }
            if (win) {
                winAnimation(type, shapes);
            }
            win = true;
        }

        if (index == 2 || index == 4 || index == 6) {
            for (int i = 0; i < LEN; i++) {
                if (tiles[(i + 1) * 2] != type) {
                    win = false;
                }
            }
            if (win) {
                winAnimation(type, shapes);
            }
        }
    }
    /**
     * exits the program
     * @param event 
     */
    @FXML
    protected void exit(ActionEvent event) {
        Platform.exit();
    }
    
    /**
     * creates a new game
     * @param event 
     */
    @FXML
    protected void newGame(ActionEvent event) {
        clearBoard();
    }
    
    
}
