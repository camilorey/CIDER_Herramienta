/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_CIDER_entityMarkers;

import CIDER_DB.CIDER_DB;
import CIDER_DB.CIDER_DB_Entity;
import CIDER_GUI_surfaces.CIDER_GUI_markerDisplaySurface;
import CIDER_GUI_surfaces2.CIDER_GUI_filterableSurface;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
/**
 *
 * @author laptop
 */
public class CIDER_GUI_EntityMarker extends CIDER_GUI_Object{
 protected CIDER_GUI_filterableSurface parentSurface;
 protected String objectName;
 protected int[] objectColor;
 protected int[] selectedColor;
 protected boolean isSelected;
 public CIDER_GUI_EntityMarker(CIDER_DB parentDB, PApplet parent) {
  super(parentDB, parent);
  this.objectName = "";
 }
 public CIDER_GUI_EntityMarker(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth) {
  super(parentDB,parent, position, objectWidth);
  this.objectName = "";
 }
 public CIDER_GUI_EntityMarker(CIDER_DB parentDB, PApplet parent, String name, PVector position, float objectWidth) {
  super(parentDB,parent, position, objectWidth);
  this.objectName = name;
 }
 public int[] getSelectedColor() {
  return selectedColor;
 }
 public void setSelectedColor(int[] selectedColor) {
  this.selectedColor = selectedColor;
 }
 public boolean isSelected() {
  return isSelected;
 }
 public void toggleSelection(){
  if(isSelected)
   isSelected = false;
  else
   isSelected = true;
 }
 public void zoomIn(){
  objectWidth  *= 1.2;
  objectHeight *= 1.2;
 }
 public void zoomOut(){
  objectWidth /= 1.2;
  objectHeight /= 1.2;
 }
 public void setIsSelected(boolean isSelected) {
  this.isSelected = isSelected;
 }
 public String getObjectName() {
  return objectName;
 }
 public void setObjectName(String objectName) {
  this.objectName = objectName;
 }
 public int[] getObjectColor() {
  return objectColor;
 }
 public void setObjectColor(int[] objectColor) {
  this.objectColor = objectColor;
 }
 public void addParentSurface(CIDER_GUI_filterableSurface parentSurface){
  this.parentSurface = parentSurface;
 }
 public void drawName(){
  if(parentSurface != null){
   PVector mousePos = parentSurface.mouseToSurfaceCoordinates();
   parentSurface.getSurface().fill(0);
   parentSurface.getSurface().textSize(10);
   parentSurface.getSurface().text(objectName,position.x,position.y);
  }else{
   parent.fill(0);
   parent.textSize(10);
   parent.text(objectName,position.x+objectWidth,position.y+objectHeight/2.0f);
  }
 }
 public void drawName(PGraphics g){
  g.fill(0);
  g.textSize(10);
  g.text(objectName,position.x+objectWidth,position.y);
 }
 public void drawObject(){
 
 }
 public void drawObject(PGraphics g){
 
 }
 
 public int hasConnection(CIDER_GUI_EntityMarker ent){
  return -1;
 }
 public void drawConnection(CIDER_GUI_EntityMarker entity){
  PVector pos0 = getPosition();
  PVector pos1 = entity.getPosition();
  if(parentSurface != null){
   parentSurface.getSurface().strokeWeight(1);
   parentSurface.getSurface().line(pos0.x+objectWidth*0.5f,pos0.y+objectHeight*0.5f,pos1.x+entity.getObjectWidth()*0.5f,pos1.y+entity.getObjectHeight()*0.5f);
  }else{
   parent.strokeWeight(1);
   parent.line(pos0.x+objectWidth*0.5f,pos0.y+objectHeight*0.5f,pos1.x+entity.getObjectWidth()*0.5f,pos1.y+entity.getObjectHeight()*0.5f);
  }
 }
 public void drawOnParentSurface(){
  PVector relativeMousePos = parentSurface.mouseToSurfaceCoordinates();
  if(isHover(relativeMousePos.x,relativeMousePos.y,objectWidth)>0){
   drawName();
   if(parent.mousePressed){
    if(!isSelected)
     isSelected = true;
    else
     isSelected = false;
   }
  }
  if(isSelected()){
   parentSurface.getSurface().noStroke();
   parentSurface.getSurface().fill(selectedColor[0],selectedColor[1],selectedColor[2]);
  }else{
   parentSurface.getSurface().noStroke();
   parentSurface.getSurface().fill(objectColor[0],objectColor[1],objectColor[2]);
  }
  drawObject();
 }
 public void drawOnParent(){
  if(isHover(parent.mouseX,parent.mouseY,objectWidth)>0){
   drawName();
   if(parent.mousePressed){
    toggleSelection();
   }
  }
  if(isSelected()){
   parent.noStroke();
   parent.fill(selectedColor[0],selectedColor[1],selectedColor[2]);
  }else{
   parent.noStroke();
   parent.fill(objectColor[0],objectColor[1],objectColor[2]);
  }
  drawObject(); 
 }
 @Override
 public void draw() {
  if(parentSurface != null){
   drawOnParentSurface();
  }else{
   drawOnParent();
  }
 }
 @Override
 public void draw(PGraphics g) {
  if(isHover(parent.mouseX, parent.mouseY, objectWidth)>0){
   g.fill(0);
   drawName(g);
  }
  g.noStroke();
  g.fill(objectColor[0],objectColor[1],objectColor[2]);
  drawObject(g);
 }
}
