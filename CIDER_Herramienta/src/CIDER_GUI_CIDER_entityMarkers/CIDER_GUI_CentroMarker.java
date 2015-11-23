/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_CIDER_entityMarkers;
import CIDER_DB.CIDER_Centro;
import CIDER_DB.CIDER_DB;
import CIDER_DB.CIDER_DB_Entity;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
/**
 *
 * @author laptop
 */
public class CIDER_GUI_CentroMarker extends CIDER_GUI_EntityMarker{
 protected boolean isInFilter;
 protected boolean isFilterableActivated;
 protected int[] filteredColor;
 public CIDER_GUI_CentroMarker(CIDER_DB parentDB, PApplet parent) {
  super(parentDB, parent);
  isInFilter = false;
  isFilterableActivated = false;
 }
 public CIDER_GUI_CentroMarker(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth) {
  super(parentDB, parent, position, objectWidth);
  isInFilter = false;
  isFilterableActivated = false;
 }
 public CIDER_GUI_CentroMarker(CIDER_DB parentDB, PApplet parent, String name, PVector position, float objectWidth) {
  super(parentDB, parent, name, position, objectWidth);
  isInFilter = false;
  isFilterableActivated = false;
 }
 public CIDER_GUI_CentroMarker(CIDER_GUI_CentroMarker c){
  super(c.parentDB, c.parent,c.getObjectName(),c.getPosition(),c.getObjectWidth());
  isInFilter = false;
  isFilterableActivated = false;
 }
 public CIDER_GUI_CentroMarker(CIDER_GUI_CentroMarker c, PVector pos){
  super(c.parentDB, c.parent,c.getObjectName(),pos,c.getObjectWidth());
  isInFilter = false;
  isFilterableActivated = false;
 }
 public int[] getFilteredColor() {
  return filteredColor;
 }
 public void setFilteredColor(int[] filteredColor) {
  this.filteredColor = filteredColor;
 }
 public float dist(CIDER_GUI_CentroMarker c){
  return getPosition().dist(c.getPosition());
 }
 public boolean isIntersect(CIDER_GUI_CentroMarker c){
  return dist(c)<objectWidth;
 }
 public void setIsInFilter(boolean value){
  isInFilter = value;
 }
 public boolean isInFilter(){
  return isInFilter;
 }
 public boolean satisfiesFilter(String filter){
  int response = parentDB.entitySatisfiesQuery(objectName, filter);
  if(response > 0)
   return true;
  else
   return false;
 }
 public void applyFilter(String filter){
  isInFilter = satisfiesFilter(filter);
 }
 public boolean isIsFilterableActivated() {
  return isFilterableActivated;
 }
 public void setIsFilterableActivated(boolean isFilterableActivated) {
  this.isFilterableActivated = isFilterableActivated;
 }
 @Override
 public int hasConnection(CIDER_GUI_EntityMarker ent) {
  if(ent instanceof CIDER_GUI_EntidadMarker || ent instanceof CIDER_GUI_CentroMarker){
   return parentDB.connectionBetweenEntities(objectName, ent.getObjectName());
  }else if(ent instanceof CIDER_GUI_ClusterTreeMarker){
   return ((CIDER_GUI_ClusterTreeMarker)ent).hasConnection(this);
  }else{
   return -1;
  }
 }
 @Override
 public void drawOnParentSurface() {
  PVector mousePos = parentSurface.mouseToSurfaceCoordinates();
  if(isHover(mousePos.x,mousePos.y,objectWidth)>0){
   drawName();
   if(parent.mousePressed){
    toggleSelection();
   }
  }
  if(isFilterableActivated){
   if(isInFilter){
    parentSurface.getSurface().fill(filteredColor[0],filteredColor[1],filteredColor[2]);
   }else{
    if(isSelected)
     parentSurface.getSurface().fill(selectedColor[0],selectedColor[1],selectedColor[2]); 
    else
     parentSurface.getSurface().fill(objectColor[0],objectColor[1],objectColor[2]);
   }
  }else{
   if(isSelected){
    parentSurface.getSurface().fill(selectedColor[0],selectedColor[1],selectedColor[2]);
   }else{
    parentSurface.getSurface().fill(objectColor[0],objectColor[1],objectColor[2]); 
   }
  }
  parentSurface.getSurface().noStroke();
  drawObject();
 }

 @Override
 public void drawOnParent() {
  PVector mousePos = parentSurface.mouseToSurfaceCoordinates();
  if(isHover(mousePos.x,mousePos.y,objectWidth)>0){
   drawName();
   if(parent.mousePressed){
    toggleSelection();
   }
  }
  parent.noStroke();
  if(isFilterableActivated){
   if(isInFilter){
    parent.fill(filteredColor[0],filteredColor[1],filteredColor[2]);
   }else{
    if(isSelected)
     parent.fill(selectedColor[0],selectedColor[1],selectedColor[2]);
    else
     parent.fill(objectColor[0],objectColor[1],objectColor[2]); 
   }
   drawObject();
  }else{
   if(isSelected)
    parent.fill(selectedColor[0],selectedColor[1],selectedColor[2]);
   else
    parent.fill(objectColor[0],objectColor[1],objectColor[2]); 
   drawObject();
  }
 }
 @Override
 public void drawObject() {
  if(parentSurface != null){
   parentSurface.getSurface().rect(position.x, position.y, objectWidth,objectWidth);
  }else{
   parent.rect(position.x+objectWidth/2.0f, position.y+objectHeight/2.0f, objectWidth,objectWidth);
  }
 }
 @Override
 public void drawObject(PGraphics g) {
  g.rect(position.x, position.y,objectWidth,objectWidth);
 }
}
