/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_textComponents;

import CIDER_DB.CIDER_Variable;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_variableFilter extends CIDER_GUI_FilterComponent{
 protected CIDER_Variable variable;
 public CIDER_GUI_variableFilter(CIDER_Variable variable,PApplet parent) {
  super(new int[]{255,255,255}, new int[]{0,0,150}, parent);
  this.variable = variable;
  content = variable.getVariableName();
  objectWidth = 150;
  objectHeight = 25;
 }
 public CIDER_GUI_variableFilter(CIDER_Variable variable, PApplet parent, PVector position) {
  super(new int[]{255,255,255},new int[]{0,0,150}, parent, position);
  this.variable = variable;
  content = variable.getVariableName();
  objectWidth = 150;
  objectHeight = 25;
 }
 public CIDER_GUI_variableFilter(CIDER_GUI_variableFilter varFilter){
  super(new int[]{255,255,255},new int[]{0,0,150}, varFilter.parent, varFilter.getPosition());
  this.variable = varFilter.getVariable();
  content = variable.getVariableName();
  objectWidth = varFilter.getObjectWidth();
  objectHeight = varFilter.getObjectHeight();
 }
 public CIDER_Variable getVariable() {
  return variable;
 }
 public void setVariable(CIDER_Variable variable) {
  this.variable = variable;
 }
 public ArrayList<CIDER_GUI_variableOptionFilter> toVariableOptionFilters(float radius, PVector center){
  ArrayList<CIDER_GUI_variableOptionFilter>  variableOptions = new ArrayList<CIDER_GUI_variableOptionFilter>();
  ArrayList<String> options = variable.getVariableOptions();
  for(int i=0;i<options.size();i++){
   float theta = parent.TWO_PI*((float) i / (float) options.size());
   float x = center.x + radius*parent.cos(theta);
   float y = center.y + radius*parent.sin(theta);
   PVector filterPos = new PVector(x,y);
   CIDER_GUI_variableOptionFilter variableOption = new CIDER_GUI_variableOptionFilter(parent,options.get(i), filterPos);
   variableOptions.add(variableOption);
  }
  return variableOptions;
 }
}
