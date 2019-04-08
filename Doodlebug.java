/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package predatorpreysimulator;


import java.awt.Point;

/**
 *
 * @author Honigmaster
 */

//This class contains all the methods of organism and specialized methods 
//specific to how doodlebugs behave. This class adds the variable lastEaten,
//in order to keep track of when this doodlebug last ate.
public class Doodlebug extends Organism{
    int lastEaten;
    
    //Constructor that takes the same args as organsim. 
    //This constructor sets the icon in the grid.
    public Doodlebug(PredatorPreySimulator sim, Point pos, int lastTimeStep){
        super(sim, pos, lastTimeStep);
        
    }
    
    @Override
    //Overriden method that moves the doodlebug. This method acts
    //moostly the same as organism's move method, this one just takes care
    //of setting the icon in the previous position to null and setting
    //the next one to doodlebug.
    public void move(){
        Point nextPoint = nextPoint();
        sim.orgArr[posX][posY] = null;
        
        this.posX = nextPoint.x;
        this.posY = nextPoint.y;
        sim.orgArr[posX][posY] = this; 
        
    }
    
    @Override
    //This method is an overriden version of the nextPoint method in organism.
    //This method adds posibility that the doodlebug could move to an occupied space
    //and eat an ant.
    public Point nextPoint(){
        Point nextPoint = new Point(0, 0);
        boolean moved = false;
        if(sim.orgArr[this.posX - 1][this.posY].inGrid(this.posX - 1, this.posY) == true){
            if(sim.orgArr[this.posX - 1][this.posY] instanceof Ant && moved == false){
                sim.orgArr[this.posX - 1][this.posY].die();
                sim.numAnt -= 1;
                lastEaten = sim.timeStep;
                nextPoint.x = this.posX - 1;
                nextPoint.y = this.posY;
                moved = true;
            }
        }
        else if(sim.orgArr[this.posX + 1][this.posY].inGrid(this.posX + 1, this.posY) == true){
            if(sim.orgArr[this.posX + 1][this.posY] instanceof Ant && moved == false){
                sim.orgArr[this.posX + 1][this.posY].die();
                sim.numAnt -= 1;
                lastEaten = sim.timeStep;
                nextPoint.x = this.posX + 1;
                nextPoint.y = this.posY;
                moved = true;
            }  
        }
        else if(sim.orgArr[this.posX][this.posY - 1].inGrid(this.posX, this.posY - 1) == true){
            if(sim.orgArr[this.posX][this.posY - 1] instanceof Ant && moved == false){
                sim.orgArr[this.posX][this.posY - 1].die();
                sim.numAnt -= 1;
                lastEaten = sim.timeStep;
                nextPoint.x = this.posX;
                nextPoint.y = this.posY - 1;
                moved = true;
            }
        }
        else if(sim.orgArr[this.posX][this.posY + 1].inGrid(this.posX, this.posY + 1) == true){
            if(sim.orgArr[this.posX][this.posY + 1] instanceof Ant && moved == false){
                sim.orgArr[this.posX][this.posY - 1].die();
                sim.numAnt -= 1;
                lastEaten = sim.timeStep;
                nextPoint.x = this.posX;
                nextPoint.y = this.posY + 1;
                moved = true;
            }
        }
        else if(sim.orgArr[this.posX - 1][this.posY].inGrid(this.posX - 1, this.posY)){
            if(sim.orgArr[this.posX - 1][this.posY] == null && moved == false){
                nextPoint.x = this.posX - 1;
                nextPoint.y = this.posY;
                moved = true;
            }
        }
        else if(sim.orgArr[this.posX + 1][this.posY].inGrid(this.posX + 1, this.posY)){
            if(sim.orgArr[this.posX + 1][this.posY] == null && moved == false){
                nextPoint.x = this.posX + 1;
                nextPoint.y = this.posY;
                moved = true;
            } 
        }
        else if(sim.orgArr[this.posX][this.posY - 1].inGrid(this.posX, this.posY - 1)){
            if(sim.orgArr[this.posX][this.posY - 1] == null && moved == false){
                nextPoint.x = this.posX;
                nextPoint.y = this.posY - 1;
                moved = true;
            }
        }
        else if(sim.orgArr[this.posX][this.posY + 1].inGrid(this.posX, this.posY + 1)){
            if(sim.orgArr[this.posX][this.posY + 1] == null && moved == false){
                nextPoint.x = this.posX;
                nextPoint.y = this.posY + 1;
                moved = true;
            }  
        }
        else{
            nextPoint.x = this.posX;
            nextPoint.y = this.posY;
        }
        return nextPoint;
    }
    
    @Override
    //This method creates a new anonymous Doodlebug object if the 
    //doodlebug hadn't bred in 8 time steps.
    public void breed(){
        if(lastTimeStep - lastBreed == 8){
            Doodlebug newBug = new Doodlebug(sim, sim.startSpot(), -1);
            sim.numBug += 1;
        }
    }
    
    //This method removes the doodlebug from the game, by calling the die 
    //method of organism, if it hasn't eaten in 3 time steps.
    public void starve(){
        if(lastTimeStep - lastEaten == 3){
            super.die();
            sim.numBug -= 1;
        }
    }
}
