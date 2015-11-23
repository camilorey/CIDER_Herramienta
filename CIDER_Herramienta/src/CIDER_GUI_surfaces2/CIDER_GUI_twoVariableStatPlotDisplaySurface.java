/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_surfaces2;

import CIDER_DB.CIDER_DB;
import CIDER_DB.CIDER_Variable;
import java.util.ArrayList;
import java.util.HashMap;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_twoVariableStatPlotDisplaySurface extends CIDER_GUI_StatPlotDisplaySurface{
 protected CIDER_Variable statPlotVar1;
 protected CIDER_Variable statPlotVar2;
 protected boolean isToDestroy;

 
 HashMap<String,HashMap<String,Float>> percentagePlotContent;
 HashMap<String,HashMap<String,Integer>> numberPlotContent;
 public CIDER_GUI_twoVariableStatPlotDisplaySurface(CIDER_DB parentDB, PVector position, float objectWidth, float graphWidth) {
  super(parentDB, position, objectWidth, graphWidth);
  statPlot = parent.createGraphics(parent.round(graphWidth*0.7f), parent.round(graphWidth*0.98f));
 }

 public CIDER_GUI_twoVariableStatPlotDisplaySurface(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth, float objectHeight, float graphWidth, float graphHeight) {
  super(parentDB, parent, position, objectWidth, objectHeight, graphWidth, graphHeight);
  statPlot = parent.createGraphics(parent.round(graphWidth), parent.round(graphHeight));
 }
 public boolean isIsToDestroy() {
  return isToDestroy;
 }

 public void setIsToDestroy(boolean isToDestroy) {
  this.isToDestroy = isToDestroy;
 }
 @Override
 public void createPlotInfo(ArrayList<String> filters){
  percentagePlotContent = parentDB.makeDoubleVariableStatPercentQuery(statPlotVar1, statPlotVar2, filters);
  numberPlotContent = parentDB.makeDoubleVariableStatNumberQuery(statPlotVar1, statPlotVar2, filters);
 }
 @Override
 public void applyFilter(){
  String filterString = createFilterString();
  if(!filterString.equals("")){
   String[] tokens = parseFilterString(filterString);
   if(!tokens[0].equals("")){
    String[] varTokens = tokens[0].split(",");
    if(varTokens.length == 2){
     statPlotVar1 = parentDB.getVariable(varTokens[0]);
     statPlotVar2 = parentDB.getVariable(varTokens[1]);
     if(tokens[1].equals("")){
      createPlotInfo(null);
     }
     toDelete = false;
    }else if(varTokens.length<2){
     toDelete = true;
    }
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
 void updateStatPlot(){
  float xOffset = statPlot.width*0.05f;
  float yOffset = 10f;
  float currentY = statPlot.height*0.01f+yOffset;
  float total = 0.0f;
  statPlot.beginDraw();
   statPlot.background(150,150,150);
   statPlot.fill(0);
   statPlot.textSize(22);
   statPlot.text("double variable stat plot",statPlot.width*0.5f,currentY);
   currentY+= statPlot.height*0.2f;
   statPlot.textSize(12);
   int var1Counter = 0;
   int numVar1Options = percentagePlotContent.size();
   for(String var1Name: percentagePlotContent.keySet()){
    HashMap<String,Float> row = percentagePlotContent.get(var1Name);
    int numVar2Options = row.size();
    int var2Counter = 0;
    for(String var2Name: row.keySet()){
     float u = (float) var1Counter / (float) numVar1Options;
     float v = (float) var2Counter / (float) numVar2Options;
     float x = (1-u)*(statPlot.width*0.05f)+u*(statPlot.width*0.95f);
     float y = (1-v)*(statPlot.height*0.25f)+v*(statPlot.height*0.8f);
     float optionValue = row.get(var2Name).floatValue(); 
     statPlot.text(optionValue,x,y-statPlot.textAscent());
     statPlot.text("("+var1Name+","+var2Name+")",x,y);
     var2Counter++;
    }
    var1Counter++;
   }
   
  statPlot.endDraw();
 }
}
