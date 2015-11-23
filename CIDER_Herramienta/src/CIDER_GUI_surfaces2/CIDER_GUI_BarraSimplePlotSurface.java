/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_surfaces2;

import CIDER_DB.CIDER_DB;
import CIDER_GUI_surfaces2.CIDER_GUI_singleVariableStatPlotDisplaySurface;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_BarraSimplePlotSurface extends CIDER_GUI_singleVariableStatPlotDisplaySurface{
 public CIDER_GUI_BarraSimplePlotSurface(CIDER_DB parentDB, PVector position, float objectWidth, float graphWidth) {
  super(parentDB, position, objectWidth, graphWidth);
 }

 public CIDER_GUI_BarraSimplePlotSurface(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth, float objectHeight, float graphWidth, float graphHeight) {
  super(parentDB, parent, position, objectWidth, objectHeight, graphWidth, graphHeight);
 }
 @Override
 void updateStatPlot() {
  statPlot.beginDraw();
   statPlot.colorMode(PApplet.RGB);
   statPlot.textAlign(PApplet.RIGHT,PApplet.CENTER);
   statPlot.background(backgroundColor[0],backgroundColor[1],backgroundColor[2]);
   statPlot.colorMode(PApplet.HSB);
   int numOption = 0;
   float textBoxXOffset = statPlot.width*0.01f;
   float xOffset = statPlot.width*0.25f;
   float barHeight = statPlot.height*0.98f / (float) percentagePlotContent.size();
   float currentY = statPlot.height*0.01f;
   float maxBarWidth = statPlot.width*0.95f;
   statPlot.stroke(0);
   statPlot.fill(150);
   statPlot.rect(xOffset,statPlot.height*0.01f,maxBarWidth,statPlot.height*0.98f);
   for(String key: percentagePlotContent.keySet()){
    float colorFactor = (float) numOption  / (float) percentagePlotContent.size();
    float optionValue = percentagePlotContent.get(key).floatValue();
    float barWidth = optionValue*maxBarWidth;
    int optionNumber = numberPlotContent.get(key).intValue();
    statPlot.fill(255*colorFactor,255,255);
    statPlot.noStroke();
    statPlot.stroke(0);
    statPlot.rect(xOffset,currentY,barWidth,barHeight);
    //statPlot.line(textBoxXOffset, currentY, barWidth, currentY);
    statPlot.fill(0);
    statPlot.textSize(16);
    statPlot.text(optionNumber+"",textBoxXOffset,currentY,xOffset-textBoxXOffset,barHeight);
    //statPlot.textSize(16);
    //statPlot.text(optionNumber,xOffset+barWidth,currentY);
    currentY+=barHeight;
    numOption++;
   }
   statPlot.endDraw();
 }
}
