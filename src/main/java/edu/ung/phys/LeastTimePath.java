package edu.ung.phys2212;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * @author naharrison
 */
public class LeastTimePath extends PApplet {

	public static void main(String[] args) {
		PApplet.main("edu.ung.phys2212.LeastTimePath");
	}

	
	// distance units: px   time units: ms
	public int frameRate;
	public double rescuerX, rescuerY, victimX, victimY, crossX, figX, figY;
	public double v1, v2;
	public int startTime, ellapsedTime;
	public boolean running;
	public PImage ready, run, duck, drowning, happy;

	
	public void settings() {
		size(1200, 640);
	}


	public void setup() {
		frameRate = 40;
		frameRate(frameRate);
		rescuerX = 75;
		rescuerY = 75;
		victimX = width - 250;
		victimY = height - 125;
		crossX = (width/3) - 10;
		figX = rescuerX;
		figY = rescuerY;
		v1 = 0.06;
		v2 = 0.015;
		startTime = 0;
		ellapsedTime = 0;
		running = false;
		ready = loadImage("figs/ready.png");
		run = loadImage("figs/run.png");
		duck = loadImage("figs/duck.png");
		drowning = loadImage("figs/drowning.jpg");
		happy = loadImage("figs/happy.png");
	}

	
	public void draw() {
		// sand and water:
		stroke(0);
		fill(162, 90, 10);
		rect(0, 0, width, height/2);
		fill(10, 100, 200);
		rect(0, height/2, width, height/2);
		
		// starting points:
		fill(0);
		ellipse((int) rescuerX, (int) rescuerY, 10, 10);
		ellipse((int) victimX, (int) victimY, 10, 10);
		image(drowning, (int) victimX, (int) victimY, 80, 105);
		ellipse((int) crossX, (int) height/2, 10, 10);
		
		// path lines:
		stroke(75);
		line((int) rescuerX, (int) rescuerY, (int) victimX, (int) victimY); // rescuer to victim
		line((int) rescuerX, (int) rescuerY, (int) crossX, height/2); // rescuer to cross
		line((int) victimX, (int) victimY, (int) crossX, height/2); // cross to victim
		line((int) rescuerX, (int) rescuerY, (int) rescuerX, height/2); // rescuer to water doca
		line((int) rescuerX, height/2, (int) victimX, (int) victimY); // rescuer to water doca to victim
		line((int) victimX, (int) victimY, (int) victimX, height/2); // victim to sand doca
		line((int) victimX, height/2, (int) rescuerX, (int) rescuerY); // victim to sand doca to rescuer
		
		// animation:
		if(running == true) {
			double theta1 = Math.atan2(height/2 - rescuerY, crossX - rescuerX);
			double theta2 = Math.atan2(victimY - height/2, victimX - crossX);
			if(figY < height/2) {
				figX += v1*Math.cos(theta1)*(1000.0/frameRate);
				figY += v1*Math.sin(theta1)*(1000.0/frameRate);
				ellapsedTime = millis() - startTime;
			}
			else if(!(Math.abs(figX - victimX) < 3.0)) {
				figX += v2*Math.cos(theta2)*(1000.0/frameRate);
				figY += v2*Math.sin(theta2)*(1000.0/frameRate);
				ellapsedTime = millis() - startTime;
			}
			else {
				image(happy, (int) victimX + 80, (int) victimY, 80, 115);
			}
			if(figY < height/2) image(run, (int) figX, (int) figY, 80, 105);
			else image(duck, (int) figX, (int) figY, 80, 105);
		}
		else {
			figX = rescuerX;
			figY = rescuerY;
			image(ready, (int) rescuerX, (int) rescuerY, 80, 115);
		}

		// data text:
		text(String.format("%3.2f", rescuerX), 10, height - 140);
		text(String.format("%3.2f", rescuerY), 10, height - 120);
		text(String.format("%3.2f", crossX), 10, height - 100);
		text(String.format("%3.2f", (double) height/2), 10, height - 80);
		text(String.format("%3.2f", victimX), 10, height - 60);
		text(String.format("%3.2f", victimY), 10, height - 40);
		text(ellapsedTime, 10, height - 20);

	}

	
	public void mouseClicked() {
		if(mouseY < height/2 - 10 && mouseButton == LEFT) {
			rescuerX = mouseX;
			rescuerY = mouseY;
		}
		else if(mouseY > height/2 + 10 && mouseButton == LEFT) {
			victimX = mouseX;
			victimY = mouseY;
		}
		else if(mouseButton == LEFT) {
			crossX = mouseX;
		}
		else if(mouseButton == RIGHT && running == false) {
			running = true;
			startTime = millis();
		}
		else if(mouseButton == RIGHT && running == true) {
			running = false;
		}
	}
	
}
