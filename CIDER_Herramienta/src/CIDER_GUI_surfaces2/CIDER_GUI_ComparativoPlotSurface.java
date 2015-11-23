/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_surfaces2;

import CIDER_DB.CIDER_DB;
import java.util.ArrayList;
import java.util.HashMap;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_ComparativoPlotSurface extends CIDER_GUI_twoVariableStatPlotDisplaySurface{

 public CIDER_GUI_ComparativoPlotSurface(CIDER_DB parentDB, PVector position, float objectWidth, float graphWidth) {
  super(parentDB, position, objectWidth, graphWidth);
 }

 public CIDER_GUI_ComparativoPlotSurface(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth, float objectHeight, float graphWidth, float graphHeight) {
  super(parentDB, parent, position, objectWidth, objectHeight, graphWidth, graphHeight);
 }
 public ArrayList<String> var1Options(){
  return statPlotVar1.getVariableOptions();
 }
 void plotCircleBox(PGraphics g, float xCorner, float yCorner, float boxWidth, float boxHeight,String boxName,int[] circleColors){
  g.stroke(0);
  g.fill(circleColors[0],circleColors[1],circleColors[2]);
  g.rect(xCorner,yCorner,boxWidth,boxHeight);
  g.fill(0);
  g.text(boxName,xCorner+boxWidth/2.0f-g.textWidth(boxName)/2.0f,yCorner+boxHeight*0.01f+g.textAscent());
 }
 PGraphics createCircleBox(float boxWidth, float boxHeight, String boxName, int[] circleColors){
  PGraphics circleBox = parent.createGraphics((int) boxWidth,(int) boxHeight);
  ArrayList<String> var2Names = statPlotVar2.getVariableOptions();
  HashMap<String,Float> var2Values = percentagePlotContent.get(boxName);
  HashMap<String, Integer> var2Numbers = numberPlotContent.get(boxName);
  int circleGridSize = parent.ceil(parent.sqrt(var2Names.size()));
   float circleLayerWidth = circleBox.width-10;
   float circleLayerHeight = circleBox.height-10;
   float gridX0 = circleBox.width*0.01f;
   float gridX1 = circleBox.width*0.98f;
   float gridY0 = circleBox.height*0.0125f+circleBox.textAscent();
   float gridY1 = circleBox.height*0.98f;
   float gridWidth = gridX1-gridX0;
   float gridHeight = gridY1-gridY0;
   circleBox.beginDraw();
    circleBox.colorMode(PApplet.RGB);
    circleBox.background(backgroundColor[0],backgroundColor[1],backgroundColor[2]);
    circleBox.fill(0);
    circleBox.textSize(10);
    circleBox.text(boxName,circleBox.width/2.0f-circleBox.textWidth(boxName)/2.0f,circleBox.height*0.01f+circleBox.textAscent());
    circleBox.noFill();
    circleBox.stroke(0);
    circleBox.rect(gridX0,gridY0,gridWidth,gridHeight);
    circleBox.colorMode(PApplet.HSB);
    circleBox.ellipseMode(PApplet.CORNER);
    int numOptions = var2Names.size();
    int optionCounter = 0;
    circleBox.textSize(8);
    for(int i=0;i<circleGridSize;i++){
     for(int j=0;j<circleGridSize;j++){
      float u0 = (float) i / (float) circleGridSize;
      float v0 = (float) j / (float) circleGridSize;
      float u1 = (float) (i+1) / (float) circleGridSize;
      float v1 = (float) (j+1) / (float) circleGridSize;
      float x0 = (1-u0)*gridX0+u0*gridX1;
      float y0 = (1-v0)*gridY0+v0*gridY1;
      float x1 = (1-u1)*gridX0+u1*gridX1;
      float y1 = (1-v1)*gridY0+v1*gridY1;
      float gridBoxWidth = x1-x0;
      float gridBoxHeight = y1-y0;
      float gridBoxRadius = parent.min(gridBoxWidth,gridBoxHeight)*0.5f;
      if(optionCounter<numOptions){
       String gridBoxName = var2Names.get(optionCounter);
       float radiusFactor = var2Values.get(gridBoxName);
       int numberInBox = var2Numbers.get(gridBoxName);
       if(radiusFactor !=0){
        float radius = gridBoxRadius*radiusFactor;
        circleBox.fill(circleColors[0],circleColors[1],circleColors[2]);
        circleBox.ellipse(x0+gridBoxRadius*0.5f,y0+gridBoxRadius*0.75f,radius,radius);
        circleBox.fill(0);
        circleBox.text(numberInBox,x0+gridBoxRadius*0.5f,y0+gridBoxRadius*0.75f);
        circleBox.text(gridBoxName,x0,y0,gridBoxWidth,gridBoxHeight*0.5f);
       }
       optionCounter++;
      }
     }
    }
   circleBox.endDraw();
  return circleBox;
 }
 @Override
 void updateStatPlot(){
  float xOffset = statPlot.width*0.01f;
  float yOffset = 10f;
  float currentY = statPlot.height*0.01f+yOffset;
  float total = 0.0f;
  ArrayList<String> boxTitles = statPlotVar1.getVariableOptions();
  statPlot.beginDraw();
   statPlot.colorMode(PApplet.RGB);
   statPlot.background(backgroundColor[0],backgroundColor[1],backgroundColor[2]);
   int numVar1Options = percentagePlotContent.size();
   int boxGridSize = parent.ceil(parent.sqrt(numVar1Options));
   float boxGridWidth = statPlot.width*0.98f / (float) boxGridSize;
   float boxGridHeight = statPlot.height*0.75f / (float) boxGridSize;
   float minXGrid = statPlot.width*0.01f;
   float minYGrid = statPlot.height*0.15f;
   float maxXGrid = statPlot.width*0.98f;
   float maxYGrid = statPlot.height*0.98f;
   int optionNumber = 0;
   statPlot.colorMode(PApplet.HSB);
   for(int i=0;i<boxGridSize;i++){
    for(int j=0;j<boxGridSize;j++){
     float u0 = (float) i / (float) (boxGridSize);
     float v0 = (float) j / (float) (boxGridSize);
     float u1 = (float) (i+1) / (float) (boxGridSize);
     float v1 = (float) (j+1) / (float) (boxGridSize);
     float x0 = (1-u0)*minXGrid+u0*maxXGrid;
     float y0 = (1-v0)*minYGrid+v0*maxYGrid;
     float x1 = (1-u1)*minXGrid+u1*maxXGrid;
     float y1 = (1-v1)*minYGrid+v1*maxYGrid;
     float boxWidth = x1-x0;
     float boxHeight = y1-y0;
     if(optionNumber < boxTitles.size()){
      float colorFactor = (float) optionNumber / (float) boxTitles.size();
      PGraphics subGraph = createCircleBox(boxGridWidth,boxGridHeight,boxTitles.get(optionNumber),new int[]{parent.round(255*colorFactor),255,255});
      statPlot.image(subGraph,x0,y0,(int) boxGridWidth,(int) boxGridHeight);
      optionNumber++;
     }
    }
   }
     
  statPlot.endDraw();
 }
}
