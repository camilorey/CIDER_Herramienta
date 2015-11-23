/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI;

import CIDER_DB.CIDER_DB;
import CIDER_GUI_actionZones.CIDER_GUI_ActionZone;
import CIDER_GUI_actionZones.CIDER_GUI_singleVariablePlotZone;
import CIDER_GUI_actionZones.CIDER_GUI_statPlotZone;
import CIDER_GUI_actionZones.CIDER_GUI_twoVariablePlotZone;
import CIDER_GUI_actionZones.CIDER_GUI_variableSplitActionZone;
import CIDER_GUI_surfaces2.CIDER_GUI_StatPlotDisplaySurface;
import CIDER_GUI_surfaces2.CIDER_GUI_filterableSurface;
import CIDER_GUI_textComponents.CIDER_GUI_FilterComponent;
import CIDER_GUI_textComponents.CIDER_GUI_variableFilter;
import CIDER_GUI_textComponents.CIDER_GUI_variableOptionFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_variableLayer {
 protected PApplet parent;
 protected CIDER_DB parentDB;
 protected int selectedVariable;
 protected String selectedVariableName;
 protected CIDER_GUI_variableFilter splitVariableSelected;
 protected CIDER_GUI_variableSplitActionZone variableSplitZone;
 protected HashMap<String, PVector> originalVariablePositions;
 protected HashMap<String, CIDER_GUI_variableFilter> vars;
 protected HashMap<String, CIDER_GUI_variableFilter> selectedVars;
 protected HashMap<CIDER_GUI_variableFilter,ArrayList<CIDER_GUI_variableOptionFilter>> selectedVarOptions;
 protected ArrayList<CIDER_GUI_variableFilter> variables;
 public CIDER_GUI_variableLayer(){
  parent = null;
  parentDB = null;
 }
 public CIDER_GUI_variableLayer(PApplet parent, CIDER_DB parentDB) {
  this.parent = parent;
  this.parentDB = parentDB;
  originalVariablePositions = new HashMap<String, PVector>();
  variables = new ArrayList<CIDER_GUI_variableFilter>();
  vars = new HashMap<String, CIDER_GUI_variableFilter>();
  selectedVars = new HashMap<String, CIDER_GUI_variableFilter>();
  selectedVarOptions = new HashMap<CIDER_GUI_variableFilter,ArrayList<CIDER_GUI_variableOptionFilter>>();
  selectedVariable = -1;
  createVariableFilters();
 }
 public CIDER_GUI_variableLayer(PApplet parent, CIDER_DB parentDB, PImage variableSplitImage) {
  this.parent = parent;
  this.parentDB = parentDB;
  variableSplitZone = new CIDER_GUI_variableSplitActionZone(parentDB, parent, new PVector(parent.width*0.9f,parent.height*0.10f), parent.width*0.10f, variableSplitImage);
  originalVariablePositions = new HashMap<String, PVector>();
  variables = new ArrayList<CIDER_GUI_variableFilter>();
  vars = new HashMap<String, CIDER_GUI_variableFilter>();
  selectedVars = new HashMap<String, CIDER_GUI_variableFilter>();
  selectedVarOptions = new HashMap<CIDER_GUI_variableFilter,ArrayList<CIDER_GUI_variableOptionFilter>>();
  selectedVariable = -1;
  createVariableFilters();
 }
 public void createVariableFilters(){
  ArrayList<String> varsFromDB = parentDB.getVariablesRegistradas();
  float varLayerWidth = parent.width*0.8f;
  float varLayerHeight = parent.height*0.3f;
  int gridX = 4;
  int gridY = 4;
  int numVar = 0;
  float xSpacing = 200;
  float ySpacing = 30;
  float x0 = parent.width*0.1f;
  float y0 = parent.height*0.05f;
  for(int i=0;i<gridX;i++){
   for(int j=0;j<gridY;j++){
    if(numVar<varsFromDB.size()){
     float x = x0+xSpacing*i;
     float y = y0+ySpacing*j;
     PVector variablePos = new PVector(x,y);
     String variableName = varsFromDB.get(numVar);
     originalVariablePositions.put(variableName,variablePos);
     CIDER_GUI_variableFilter varFilter = new CIDER_GUI_variableFilter(parentDB.getVariable(varsFromDB.get(numVar)), parent, variablePos);
     variables.add(varFilter);
     vars.put(variableName,varFilter);
    }
    numVar++;
   }
  }
 }
 void returnVariableToOriginalPosition(String varName){
  PVector originalPos = originalVariablePositions.get(varName);
  vars.get(varName).setPosition(originalPos);
 }
 void collisionWithActionZone(){
  if(selectedVariableName != null){
   if(variableSplitZone.isCollision(vars.get(selectedVariableName))){
    CIDER_GUI_variableFilter varToSplit = vars.get(selectedVariableName);
    selectedVars.put(selectedVariableName, varToSplit);
    returnVariableToOriginalPosition(selectedVariableName);
    varToSplit.fadeColor();
    variableSplitZone.onCollision(varToSplit);
    selectedVarOptions.put(varToSplit, variableSplitZone.getAvailableFilters());
   }
  }
 }
 void collisionWithSurface2(CIDER_GUI_filterableSurface surface){
  if(selectedVariableName !=null){
   CIDER_GUI_variableFilter selectedVar = new CIDER_GUI_variableFilter(vars.get(selectedVariableName));
   if(surface.isCollision(selectedVar)){
    selectedVars.put(selectedVariableName,vars.get(selectedVariableName));
    returnVariableToOriginalPosition(selectedVariableName);
    surface.onCollision(selectedVar);
   }
  }
  filterCollisionWithSurface(surface);
 }
 void filterCollisionWithSurface(CIDER_GUI_filterableSurface surface){
  CIDER_GUI_variableFilter varOwningFilter = null;
  CIDER_GUI_variableOptionFilter collidedFilter = null;
  for(Map.Entry optionEntry: selectedVarOptions.entrySet()){
   ArrayList<CIDER_GUI_variableOptionFilter> options = (ArrayList<CIDER_GUI_variableOptionFilter>) optionEntry.getValue();
   for(int i=0;i<options.size();i++){
    if(surface.isCollision(options.get(i))){
     collidedFilter = options.get(i);
     varOwningFilter = (CIDER_GUI_variableFilter) optionEntry.getKey();
    }
   }
  }
  if(varOwningFilter!=null){
   if(collidedFilter != null){
    selectedVarOptions.get(varOwningFilter).remove(collidedFilter);
    surface.onCollision(collidedFilter);
   }
  }
 }
 public CIDER_GUI_StatPlotDisplaySurface interactionWithStatZone(CIDER_GUI_statPlotZone statZone){
  CIDER_GUI_StatPlotDisplaySurface resultingPlot = null;
  if(selectedVariableName != null){
   CIDER_GUI_variableFilter selectedVar = new CIDER_GUI_variableFilter(vars.get(selectedVariableName));
   if(statZone.isCollision(selectedVar)){
    selectedVars.put(selectedVariableName,vars.get(selectedVariableName));
    returnVariableToOriginalPosition(selectedVariableName);
    statZone.onCollision(selectedVar);
    resultingPlot = statZone.getResultingPlot();
    if(statZone instanceof CIDER_GUI_twoVariablePlotZone){
     if(resultingPlot == null)
      vars.get(selectedVariableName).fadeColor(); 
     else
      resetVariableColors();
    }
   }
  }
  return resultingPlot;
 }
 public void resetVariableColors(){
  for(Map.Entry varEntry: vars.entrySet()){
   ((CIDER_GUI_variableFilter) varEntry.getValue()).setBackgroundColor(new int[]{0,0,150});
  }
 }
 void collisionWithSurface(CIDER_GUI_filterableSurface surface){
  CIDER_GUI_variableFilter collidedVar = null;
  CIDER_GUI_variableOptionFilter collidedFilter = null;
  for(Map.Entry varEntry: vars.entrySet()){
   CIDER_GUI_variableFilter varFilter = (CIDER_GUI_variableFilter) varEntry.getValue();
   if(surface.isCollision(varFilter)){
    collidedVar = varFilter;
   }
  }
  CIDER_GUI_variableFilter varOwningFilter = null;
  for(Map.Entry optionEntry: selectedVarOptions.entrySet()){
   ArrayList<CIDER_GUI_variableOptionFilter> options = (ArrayList<CIDER_GUI_variableOptionFilter>) optionEntry.getValue();
   for(int i=0;i<options.size();i++){
    if(surface.isCollision(options.get(i))){
     collidedFilter = options.get(i);
     varOwningFilter = (CIDER_GUI_variableFilter) optionEntry.getKey();
    }
   }
  }
  if(collidedVar != null){
   surface.onCollision(collidedVar);
   returnVariableToOriginalPosition(collidedVar.getContent());
   collidedVar.fadeColor();
   
  }
  if(collidedFilter != null){
   if(varOwningFilter != null)
    selectedVarOptions.get(varOwningFilter).remove(collidedFilter);
   surface.onCollision(collidedFilter);
  }
 }
 void returnVariableToGrid(){
  if(splitVariableSelected != null){
   selectedVarOptions.remove(splitVariableSelected);
   String varName = splitVariableSelected.getContent();
   splitVariableSelected.setPosition(originalVariablePositions.get(varName));
   splitVariableSelected.intensifyColor();
   vars.put(varName, splitVariableSelected);
  }
 }
 void moveSelectedVariable(){
  if(selectedVariableName != null){
   vars.get(selectedVariableName).move();
   for(Map.Entry varEntry: vars.entrySet()){
    if(!((CIDER_GUI_variableFilter) varEntry.getValue()).equals(selectedVariableName)){
     ((CIDER_GUI_variableFilter) varEntry.getValue()).isMovable = false;
    }
   }
  }
 }
 void drawVariableGrid(){
  selectedVariableName = null;
  for(Map.Entry varEntry: vars.entrySet()){
   CIDER_GUI_variableFilter varFilter = (CIDER_GUI_variableFilter) varEntry.getValue();
   varFilter.draw();
   if(varFilter.isHover(parent.mouseX, parent.mouseY,varFilter.objectHeight*0.75f)>0){
    if(parent.mousePressed){
     selectedVariableName = varFilter.getContent();
    }
   }
  }
 }
 void drawSelectedVariableOptions(){
  splitVariableSelected = null;
  for(Map.Entry optionsEntry: selectedVarOptions.entrySet()){
   CIDER_GUI_variableFilter originalVar = (CIDER_GUI_variableFilter) optionsEntry.getKey();
   if(originalVar.isHover(parent.mouseX, parent.mouseY,originalVar.objectHeight*0.75f)>0){
    if(parent.mousePressed && parent.mouseButton == PApplet.RIGHT){
     splitVariableSelected = originalVar;
    }
   }
   originalVar.draw();
   ArrayList<CIDER_GUI_variableOptionFilter> varOptions = (ArrayList<CIDER_GUI_variableOptionFilter>) optionsEntry.getValue();
   for(int i=0;i<varOptions.size();i++){
    varOptions.get(i).draw();
    if(varOptions.get(i).isHover(parent.mouseX, parent.mouseY,varOptions.get(i).objectHeight*0.75f)>0){
     if(parent.mousePressed && parent.mouseButton == PApplet.LEFT){
      varOptions.get(i).move();
     }
    }
   }
  }
 }
 void draw(){
  variableSplitZone.draw();
  drawVariableGrid();
  moveSelectedVariable();
  collisionWithActionZone();
  drawSelectedVariableOptions();
  returnVariableToGrid();
 }
}
