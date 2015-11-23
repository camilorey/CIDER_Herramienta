/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_surfaces;

import CIDER_DB.CIDER_Variable;
import CIDER_GUI_textComponents.CIDER_GUI_variableFilter;
import CIDER_GUI_textComponents.CIDER_GUI_variableOptionFilter;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
 
/**
 *
 * @author laptop
 */
public class CIDER_GUI_variableDisplaySurface extends CIDER_GUI_displaySurface{
 protected ArrayList<CIDER_GUI_variableFilter> variables;
 protected ArrayList<CIDER_GUI_variableOptionFilter> filters;
 int variableGridX;
 int variableGridY;
 public CIDER_GUI_variableDisplaySurface(PApplet parent) {
  super(parent, new PVector(parent.width*0.5f,parent.height*0.11f), parent.width*0.95f, parent.height*0.2f);
  backgroundColor = new int[]{150,150,150};
  variables = new ArrayList<CIDER_GUI_variableFilter>();
  filters = new ArrayList<CIDER_GUI_variableOptionFilter>();
  variableGridX = 5;
  variableGridY = 4;
  setVariableSplitZone();
 }
 public CIDER_GUI_variableFilter createVariableFilter(CIDER_Variable var, int i, int j){
  PVector upperLeftCorner = getCorners()[0];
  float u = (float) i / (float) (variableGridX-1);
  float v = (float) j / (float) (variableGridY-1);
  float x = (1-u)*(objectWidth*0.05f)+u*(objectWidth*0.8f);
  float y = (1-u)*(objectHeight*0.05f)+u*(objectHeight*0.8f);
  return new CIDER_GUI_variableFilter(var, parent, new PVector(upperLeftCorner.x+x,upperLeftCorner.y+y));
 }
 public void setVariableSplitZone(){
  
 }
 public void setVariableGrid(ArrayList<CIDER_Variable> vars){
  int varIndex = 0;
  for(int i=0;i<variableGridX;i++){
   for(int j=0;j<variableGridY;j++){
    if(varIndex<vars.size()){
     variables.add(createVariableFilter(vars.get(varIndex),i,j)); 
     varIndex++;
    }
   }
  }
 }
 public void drawVariables(){
  for(int i=0;i<variables.size();i++){
   variables.get(i).draw(surface);
  }
 }
 public void drawFilters(){
  for(int i=0;i<filters.size();i++){
   filters.get(i).draw(surface);
  }
 }
 @Override
 public void update() {
  surface.beginDraw();
   surface.background(backgroundColor[0],backgroundColor[1],backgroundColor[2]);
   drawVariables();
   drawFilters();
  surface.endDraw();
 }
}
