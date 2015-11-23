/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_surfaces2;

import CIDER_DB.CIDER_DB;
import java.util.HashMap;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_MultiBarrasPlotSurface extends CIDER_GUI_twoVariableStatPlotDisplaySurface{

 public CIDER_GUI_MultiBarrasPlotSurface(CIDER_DB parentDB, PVector position, float objectWidth, float graphWidth) {
  super(parentDB, position, objectWidth, graphWidth);
 }

 public CIDER_GUI_MultiBarrasPlotSurface(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth, float objectHeight, float graphWidth, float graphHeight) {
  super(parentDB, parent, position, objectWidth, objectHeight, graphWidth, graphHeight);
 }
 void plotSimpleBar(PGraphics g, float xOffset, float startingY, float sectionHeight,float sectionWidth,HashMap<String,Float> varContent, HashMap<String,Integer> varNumbers){
  int numOption = 0;
  float barHeight = sectionHeight / (float) varContent.size();
  float currentY = startingY;
  g.textSize(10);
  g.textAlign(PApplet.LEFT,PApplet.BOTTOM);
  for(String key: varContent.keySet()){
    float colorFactor = (float) numOption  / (float) percentagePlotContent.size();
    float optionValue = varContent.get(key).floatValue();
    System.out.println(key+": "+optionValue);
    int optionNumber = varNumbers.get(key).intValue();
    float barWidth = optionValue*sectionWidth;
    String optionString = "("+optionNumber+")";
    g.fill(255*colorFactor,255,255);
    g.noStroke();
    g.rect(xOffset,currentY,barWidth,barHeight);
    g.fill(0);
    g.text(key,xOffset,currentY,sectionWidth,barHeight);
    if(optionNumber!=0){
     if(optionValue !=1.0){
      g.text(optionString,xOffset+barWidth-g.textWidth(optionString),currentY+g.textAscent()*1.5f);
     }else{
      g.text(optionString,sectionWidth-g.textWidth(optionString),currentY+g.textAscent()*1.5f);
     }
    }
    currentY+=barHeight;
    numOption++;
   }
 }
 void updateStatPlot(){
  float xOffset = statPlot.width*0.05f;
  float yOffset = 10f;
  float currentY = statPlot.height*0.01f;
  statPlot.beginDraw();
   statPlot.colorMode(PApplet.RGB);
   statPlot.background(backgroundColor[0],backgroundColor[1],backgroundColor[2]);
   statPlot.colorMode(PApplet.HSB);
   currentY+= statPlot.height*0.10f;
   statPlot.textSize(12);
   int var1Counter = 0;
   float barXOffset = xOffset+statPlot.width*0.2f;
   float bigBarHeight = statPlot.height*0.8f / (float) percentagePlotContent.size();
   statPlot.stroke(0);
   statPlot.fill(150);
   statPlot.rect(barXOffset,currentY,statPlot.width*0.98f,statPlot.height*0.98f);
   for(String var1Names: percentagePlotContent.keySet()){
     HashMap<String,Float> subBarContent = percentagePlotContent.get(var1Names);
     HashMap<String,Integer> subBarNumbers = numberPlotContent.get(var1Names);
     statPlot.stroke(0);
     statPlot.line(statPlot.width*0.01f, currentY, barXOffset, currentY);
     plotSimpleBar(statPlot,barXOffset,currentY,bigBarHeight,statPlot.width*0.97f,subBarContent,subBarNumbers);
     statPlot.fill(0);
     statPlot.text(var1Names, xOffset,currentY+bigBarHeight/2.0f);
     currentY+=bigBarHeight;
   }
   
  statPlot.endDraw();
 }
}
