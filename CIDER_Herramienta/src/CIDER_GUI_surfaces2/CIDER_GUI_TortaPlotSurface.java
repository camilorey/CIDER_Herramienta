/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_surfaces2;

import CIDER_DB.CIDER_DB;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_TortaPlotSurface extends CIDER_GUI_singleVariableStatPlotDisplaySurface{
 public CIDER_GUI_TortaPlotSurface(CIDER_DB parentDB, PVector position, float objectWidth, float graphWidth) {
  super(parentDB, position, objectWidth, graphWidth);
 }

 public CIDER_GUI_TortaPlotSurface(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth, float objectHeight, float graphWidth, float graphHeight) {
  super(parentDB, parent, position, objectWidth, objectHeight, graphWidth, graphHeight);
 }
 void plotCircularSector(PGraphics g, PVector c, float theta0,float theta1,float r, int numPoints){
  g.beginShape(PApplet.TRIANGLE_FAN);
  g.vertex(c.x,c.y);
  for(int i=0;i<numPoints;i++){
   float u = (float) i / (float) (numPoints-1);
   float theta = (1-u)*theta0+u*theta1;
   float x = c.x + r*parent.cos(theta);
   float y = c.y + r*parent.sin(theta);
   g.vertex(x,y);
  }
  g.endShape(PApplet.CLOSE);
 }
 @Override
 void updateStatPlot() {
  statPlot.beginDraw();
   statPlot.colorMode(PApplet.RGB);
   statPlot.background(backgroundColor[0],backgroundColor[1],backgroundColor[2]);
   statPlot.stroke(0);
   statPlot.noFill();
   statPlot.ellipse(statPlot.width*0.5f,statPlot.height*0.5f,statPlot.width*0.4f,statPlot.width*0.4f);
   statPlot.colorMode(PApplet.HSB);
   float currentAngle = PApplet.HALF_PI;
   int numOption = 0;
   for(String key: percentagePlotContent.keySet()){
    float colorFactor = (float) numOption  / (float) percentagePlotContent.size();
    float optionValue = percentagePlotContent.get(key).floatValue();
    float angle = optionValue*PApplet.TWO_PI;
    statPlot.fill(255*colorFactor,255,255);
    statPlot.noStroke();
    plotCircularSector(statPlot,new PVector(statPlot.width*0.5f,statPlot.height*0.5f),currentAngle,currentAngle+angle,statPlot.height*0.45f,20);
    currentAngle += angle;
    numOption++;
   }
  statPlot.endDraw();
 }
}
