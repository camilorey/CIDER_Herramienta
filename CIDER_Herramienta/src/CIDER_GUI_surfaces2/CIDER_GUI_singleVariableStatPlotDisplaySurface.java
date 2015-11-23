/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_surfaces2;

import CIDER_DB.CIDER_DB;
import CIDER_DB.CIDER_Variable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_singleVariableStatPlotDisplaySurface extends CIDER_GUI_StatPlotDisplaySurface{

 

 
 protected PGraphics legend;
 protected CIDER_Variable plotVariable;
 protected String legendTitle;
 protected ArrayList<String> legendItems;
 protected HashMap<String,Float> percentagePlotContent;
 protected HashMap<String, Integer> numberPlotContent;
 public CIDER_GUI_singleVariableStatPlotDisplaySurface(CIDER_DB parentDB, PVector position, float objectWidth, float graphWidth) {
  super(parentDB, position, objectWidth, graphWidth);
  statPlot = parent.createGraphics(parent.round(graphWidth*0.7f), parent.round(graphWidth*0.9f));
  legend = parent.createGraphics(parent.round(graphWidth*0.2f), parent.round(graphWidth*0.9f));
 }
 public CIDER_GUI_singleVariableStatPlotDisplaySurface(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth, float objectHeight, float graphWidth, float graphHeight) {
  super(parentDB, parent, position, objectWidth, objectHeight, graphWidth, graphHeight);
  statPlot = parent.createGraphics(parent.round(graphWidth), parent.round(graphHeight));
  legend = parent.createGraphics(parent.round(graphWidth*0.2f), parent.round(graphHeight*0.9f));
 }
 @Override
 public void createPlotInfo(ArrayList<String> filters) {
  percentagePlotContent = parentDB.makeSingleVariableStatPercentQuery(plotVariable,filters);
  numberPlotContent = parentDB.makeSingleVariableStatNumberQuery(plotVariable, filters);
  legendTitle = plotVariable.getVariableName();
  legendItems = new ArrayList<String>();
  for(String optionName: percentagePlotContent.keySet()){
   legendItems.add(optionName);
  }
  
 }
 @Override
 public void applyFilter(){
  String filterString = createFilterString();
  if(!filterString.equals("")){
   String[] tokens = parseFilterString(filterString);
   if(!tokens[0].equals("")){
    plotVariable = parentDB.getVariable(tokens[0]);
    if(tokens[1].equals("")){
     createPlotInfo(null);
    }
    toDelete = false;
   }else{
    toDelete = true;
   }
   if(!tokens[1].equals("")){
    String[] filterTokens = tokens[1].split(",");
    ArrayList<String> filters = new ArrayList<String>();
    for(int i=0;i<filterTokens.length;i++){
     filters.add(filterTokens[i]);
    }
    createPlotInfo(filters);
   }
  }else{
   toDelete = true;
  }
 }
 @Override
 void updateStatPlot() {
  float xOffset = statPlot.width*0.05f;
  float yOffset = 10f;
  float currentY = statPlot.height*0.01f+yOffset;
  float total = 0.0f;
  statPlot.beginDraw();
   statPlot.background(150,150,150);
   statPlot.fill(0);
   statPlot.textSize(22);
   statPlot.text(legendTitle+" results",legend.width*0.5f-legend.textWidth(legendTitle)*0.5f,currentY);
   currentY+= statPlot.height*0.2f;
   statPlot.textSize(12);
   for(String key: percentagePlotContent.keySet()){
    float optionValue = percentagePlotContent.get(key).floatValue();
    statPlot.text(key+": "+optionValue,xOffset,currentY);
    currentY+=yOffset+statPlot.textAscent();
    total += optionValue;
   }
   currentY+=yOffset;
   statPlot.textSize(22);
   statPlot.text("total: "+total,legend.width*0.5f-legend.textWidth(legendTitle)*0.5f,currentY);
  statPlot.endDraw();
 }
 void updateLegend(){
  float xOffset = legend.width*0.01f;
  float yOffset = 10f;
  float legendItemWidth = 20;
  float currentY = legend.height*0.01f;
  legend.beginDraw();
    legend.colorMode(PApplet.RGB);
    legend.background(backgroundColor[0],backgroundColor[1],backgroundColor[2]);
    legend.fill(0);
    legend.textSize(22);
    currentY += legend.textAscent();
    legend.text(legendTitle,legend.width*0.5f-legend.textWidth(legendTitle)*0.5f,currentY);
    currentY += legendItemWidth;
    legend.textSize(10);
    legend.colorMode(PApplet.HSB);
    legend.textAlign(PApplet.LEFT,PApplet.CENTER);
    for(int i=0;i<legendItems.size();i++){
     float u = (float) i / (float) (legendItems.size());
     legend.fill(255*u,255,255);
     legend.rect(xOffset,currentY,legendItemWidth,legendItemWidth);
     legend.stroke(0);
     legend.noFill();
     legend.rect(xOffset+legendItemWidth,currentY,legend.width*0.95f-legendItemWidth,legendItemWidth);
     legend.fill(0);
     legend.text(legendItems.get(i),xOffset+legendItemWidth,currentY,legend.width*0.95f-legendItemWidth,legendItemWidth);
     currentY+= 0.8f*legendItemWidth+legend.textAscent();
    }
   legend.endDraw();
 }
 public void updateContent(){
  updateLegend();
  updateStatPlot();
  surface.beginDraw();
   surface.background(backgroundColor[0],backgroundColor[1],backgroundColor[2]);
   drawFilters();
   surface.image(statPlot,surface.width*0.01f,surface.height*0.15f,surface.width*0.75f,surface.height*0.8f);
   surface.image(legend,surface.width*0.77f,surface.height*0.15f,surface.width*0.2f,surface.height*0.8f);
  surface.endDraw();
  
 }
}
