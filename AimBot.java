package jjlee_iestevez;
import robocode.Robot;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;
import robocode.HitByBulletEvent;
import robocode.*;
import java.awt.geom.Point2D;
import java.awt.Color;
import java.io.*;

/**

 * AimBot - a robot by (Joseph Lee and Ivan Estevez)

 */

public class AimBot extends AdvancedRobot
{
	double enemyEnergy = 100;
 	int movementDirection = 1;
 	int gunDirection = 1;
	public final double constant = 50;
	private double distance = 0;
	double move = 1;
	
	public void run() 
	{
		setBodyColor(Color.black);
		setGunColor(Color.black);
		setRadarColor(Color.white);
		
		while(true)
		{
			turnRadarRight(360);
			turnGunRight(360);
			turnGunLeft(360);				
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) 
	{
		// Will strafe robots from an angle
      	setTurnRight(e.getBearing()+90-30 * movementDirection);

    	// If statement should read energy drops and move away accordingly
   		double scannedEnergy = enemyEnergy-e.getEnergy();
	
		if (scannedEnergy>0 && scannedEnergy<=3) 
		{
         	movementDirection = -movementDirection;
         	setAhead((e.getDistance()/4+25)) ;
     	}
		
		// Robot will fire accordingly to how far other robots are
		gunDirection = -gunDirection;
    	setTurnGunRight(99999*gunDirection);

		distance = e.getDistance();
		if (distance < 50) 
		{
      		fire(10);
    	} 

   	 	else if (distance < 100) 
		{
     		fire(2);
    	} 

   		else if (distance < 150) 
		{
      		fire(1);
		} 

   		else 
		{
     		fire(0.1);
		}
		
		enemyEnergy = e.getEnergy();
	}

	// robot shifts back 100 when hit by bullet
	public void onHitByBullet(HitByBulletEvent e) 
	{
		back(100);
	}

	// robot shift back towards the middle when hitting a wall.
	public void onHitWall(HitWallEvent e) 
	{
		back(300);
	}

	// allows us to avoid walls using x,y coordinates (not fully)
	public void wallAvoid()
	{
		double down = getY();
		double up = getBattleFieldHeight() - getY();
		double right = getBattleFieldWidth() - getX();
		double left = getX();
		double reference = Math.min(Math.min(right , left),Math.min(up , down));
		if (reference == down)
		{
			moveDown(reference);
		}
		else if (reference == right)
		{
			moveRight(reference);
		}
		else if (reference == down)
		{
			moveUp(reference);
		}
		else 
		{
			moveLeft(reference);
		}
	}

	public void moveDown(double distance)
	{
		if (getHeading() < 180)
		{
			turnRight(180 - getHeading());
		}
		else if (getHeading() > 180)
		{
			turnLeft(getHeading() - 180);
			ahead(distance - constant);
			turnLeft(90);
		}
	}

	public void moveUp(double distance)
	{
		if (getHeading() < 180)
		{
			turnLeft(getHeading());
		}
		else if (getHeading() > 180)
		{
			turnRight(360 - getHeading());
		}
		else turnLeft(180);
		{
			ahead(distance - constant);
			turnLeft(90);
		}
	}

	public void moveRight(double distance)
	{
		if (getHeading() < 270 && getHeading() > 90)
		{
			turnLeft(getHeading() - 90);
		}
		else if (getHeading() > 270 && getHeading() < 360)
		{
			turnRight(450 - getHeading());
		}
		else if (getHeading() > 0 && getHeading()< 90)
		{
			turnRight(90 - getHeading());
		}
		else if(getHeading() == 270)
		{
			turnRight(180);
			ahead(distance - constant);
			turnLeft(90);
		}
	}

	public void moveLeft(double distance)
	{
		if (getHeading() > 0 && getHeading() < 90)
		{
			turnLeft(90 + getHeading());
		}
		else if (getHeading() > 270 && getHeading() < 360)
		{
			turnLeft(getHeading() - 270);
		}
		else if (getHeading() > 90 && getHeading() < 270)
		{
			turnRight(270 - getHeading());
		}
		else if (getHeading() == 90)
		{
			turnRight(180);
			ahead(distance - constant);
			turnLeft(90);
		}
	}

	// Bot spins around if it wins
	public void onWin(WinEvent e) 
	{
		turnRight(360);
		turnLeft(360);
	}
}
