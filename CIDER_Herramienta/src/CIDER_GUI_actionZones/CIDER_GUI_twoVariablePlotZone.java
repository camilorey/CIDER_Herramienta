/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_actionZones;

import CIDER_DB.CIDER_DB;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_Object;
import CIDER_GUI_surfaces2.CIDER_GUI_StatPlotDisplaySurface;
import CIDER_GUI_surfaces2.CIDER_GUI_twoVariableStatPlotDisplaySurface;
import CIDER_GUI_textComponents.CIDER_GUI_variableFilter;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_twoVariablePlotZone extends CIDER_GUI_statPlotZone{
 CIDER_GUI_variableFilter[] variables;
 ArrayList<CIDER_GUI_variableFilter> vars;
 public CIDER_GUI_twoVariablePlotZone(CIDER_DB parentDB, PApplet parent) {
  super(parentDB, parent);
  variables = new CIDER_GUI_variableFilter[]{null,null};
  vars = new ArrayList<CIDER_GUI_variableFilter>();
 }

 public CIDER_GUI_twoVariablePlotZone(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth, PImage backgroundImage) {
  super(parentDB, parent, position, objectWidth, backgroundImage);
  variables = new CIDER_GUI_variableFilter[]{null,null};
  vars = new ArrayList<CIDER_GUI_variableFilter>();
 }
 int actionZoneState(){
  if(variables[0] == null && variables[1]==null){
   return -2;
  }else if(variables[0] != null && variables[1] == null){
   return -1;
  }else if(variables[0] == null && variables[1] != null){
   return 0;
  }else{
   return 1;
  }
 }
 void clearVariables(){
  variables[0] = null;
  variables[1] = null;
 }
 public boolean readyToCreatePlot(){
  return variables[0]!=null && variables[1]!=null;
 }
 public void createStatPlot(){
  resultingPlot = new CIDER_GUI_twoVariableStatPlotDisplaySurface(parentDB, parent, new PVector(position.x,position.y), parent.width*0.25f, parent.height*0.25f, parent.width, parent.height);
  resultingPlot.setBackgroundColor(new int[]{255,255,255});
  vars.get(0).intensifyColor();
  vars.get(1).intensifyColor();
  resultingPlot.addFilter(vars.get(0));
  resultingPlot.addFilter(vars.get(1));
  resultingPlot.applyFilter();
 }
 @Override
 public void onCollision(CIDER_GUI_Object object) {
  if(object instanceof CIDER_GUI_variableFilter){
   vars.add((CIDER_GUI_variableFilter) object);
   ((CIDER_GUI_variableFilter) object).fadeColor();
   if(vars.size() ==2){
    System.out.println("num variables:"+vars.size());
    System.out.println("creando stat plot: ");
    vars.get(0).intensifyColor();
    vars.get(1).intensifyColor();
    createStatPlot();
    vars.clear();
   }else if(vars.size()>2){
    vars.clear();
   }
  }
 }
}
