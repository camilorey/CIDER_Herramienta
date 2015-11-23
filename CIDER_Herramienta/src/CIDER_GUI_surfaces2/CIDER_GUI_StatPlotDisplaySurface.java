/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_surfaces2;

import CIDER_DB.CIDER_DB;
import CIDER_GUI_textComponents.CIDER_GUI_FilterComponent;
import CIDER_GUI_Behaviors.*;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_Object;
import CIDER_GUI_textComponents.CIDER_GUI_variableOptionFilter;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_StatPlotDisplaySurface extends CIDER_GUI_filterableSurface implements Draggable,Resizable{

 
 protected PGraphics statPlot;
 protected boolean toSave;
 protected boolean toDelete;
 public CIDER_GUI_StatPlotDisplaySurface(CIDER_DB parentDB, PVector position, float objectWidth, float graphWidth) {
  super(parentDB, position, objectWidth, graphWidth);
 }
 public CIDER_GUI_StatPlotDisplaySurface(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth, float objectHeight, float graphWidth, float graphHeight) {
  super(parentDB, parent, position, objectWidth, objectHeight, graphWidth, graphHeight);
 }
 public boolean isToSave() {
  return toSave;
 }
 public void setToSave(boolean toSave) {
  this.toSave = toSave;
 }
 public boolean isToDelete() {
  return toDelete;
 }
 public void setToDelete(boolean toDelete) {
  this.toDelete = toDelete;
 }
 public void createPlotInfo(ArrayList<String> filters){
  
 }
 @Override
 public void applyFilter(){
  
 }
 public void drawFilters(){
  float yPosition = surface.height*0.05f;
  float xOffset = surface.width*0.01f;
  float assignedX = xOffset;
  int selectedFilter = -1;
  PVector mousePos = mouseToSurfaceCoordinates();
  float hoverScalingFactor = 0.5f;
  for(int i=0;i<filters.size();i++){
   filters.get(i).setPosition(new PVector(assignedX+filters.get(i).getObjectWidth()*0.5f,yPosition));
   filters.get(i).draw(surface);
   assignedX += filters.get(i).getObjectWidth()+xOffset;
   if(filters.get(i).isHover(mousePos.x,mousePos.y,filters.get(i).objectWidth*hoverScalingFactor)>0){
    if(parent.mousePressed && parent.mouseEvent.getClickCount()==2){
     selectedFilter = i;
    }
   }
  }
  if(selectedFilter!=-1){
   filters.remove(selectedFilter);
  }
 }
 public void saveToFile(){
  String filters = createFilterString();
  if(!filters.equals("")){
   update();
   surface.fill(150);
   surface.text("(C) CIDER-Uniandes 2015", surface.width*0.5f,surface.height*0.98f);
   String[] filterTokens = parseFilterString(filters);
   surface.save("estadistico_"+filterTokens[0]+"_"+filterTokens[1]+".png");
   System.out.println("estadistico_"+filterTokens[0]+"_"+filterTokens[1]+".png saved");
  }
 }
 void updateStatPlot(){
  statPlot.beginDraw();
   statPlot.background(150,150,150);
   statPlot.fill(0);
   statPlot.textSize(22);
   statPlot.text("stat plot",statPlot.width*0.5f,statPlot.height*0.5f);
  statPlot.endDraw();
 }
 public void updateContent(){
  updateStatPlot();
  surface.beginDraw();
   surface.background(backgroundColor[0],backgroundColor[1],backgroundColor[2]);
   drawFilters();
   surface.image(statPlot,surface.width*0.01f,surface.height*0.15f,surface.width*0.98f,surface.height*0.8f);
  surface.endDraw();
 }
 @Override
 public void update() {
  applyFilter();
  updateContent();
 }
 @Override
 public void draw() {
  if(!toDelete){
   corners = createCorners(getPosition(),objectWidth,objectHeight);
   parent.image(surface,corners[0].x,corners[0].y,objectWidth,objectHeight);
  }
 }
 @Override
 public void onDrag() {
  move();
  float dX = parent.mouseX-parent.pmouseX;
  float dY = parent.mouseY-parent.pmouseY;
  onResize(dX,dY);
 }
 @Override
 public void move() {
  if(isHover(parent.mouseX,parent.mouseY,objectWidth*0.15f)>0){
   if(parent.mouseButton == PApplet.LEFT){
    setPosition(new PVector(parent.mouseX,parent.mouseY));
    if(parent.mouseEvent.getClickCount() ==2){
     saveToFile();
    }
   }else if(parent.mouseButton == PApplet.RIGHT){
    if(parent.mouseEvent.getClickCount() == 2){
     toDelete = true;
    }else{
     if(filters.size()>1){
      if(filters.get(filters.size()-1) instanceof CIDER_GUI_variableOptionFilter)
      filters.remove(filters.size()-1);
      update(); 
     }
    }
   }
  }
 }
 @Override
 public void displace() {
 }
 @Override
 public void onResize(float dx, float dy) {
  int selectedCorner = isOnCorner(parent.mouseX, parent.mouseY);
  int[] fixed = new int[]{2,3,0,1};
  int[] moveX = new int[]{3,2,1,0};
  int[] moveY = new int[]{1,0,3,2};
  if(selectedCorner != -1){
   PVector[] corners = getCorners();
   corners[selectedCorner].add(new PVector(dx,dy));
   corners[moveX[selectedCorner]].add(new PVector(dx,0));
   corners[moveY[selectedCorner]].add(new PVector(0,dy));
   PVector newCenter = new PVector();
   for(int i=0;i<corners.length;i++){
    newCenter.add(corners[i]);
   }
   newCenter.div(4.0f);
   float newWidth = parent.abs(corners[fixed[selectedCorner]].x-corners[moveX[selectedCorner]].x);
   float newHeight= parent.abs(corners[fixed[selectedCorner]].y - corners[moveY[selectedCorner]].y);
   setPosition(newCenter);
   setObjectWidth(newWidth);
   setObjectHeight(newHeight);
  }
 }
 @Override
 public void resize(float dx, float dy) {
  objectWidth += dx;
  objectHeight += dy;
  PVector[] corners = getCorners();
  PVector newCenter = new PVector();
  for(int i=0;i<corners.length;i++){
   newCenter.add(corners[i]);
  }
  newCenter.div(4.0f);
  setPosition(newCenter);
 }
 @Override
 public int isOnCorner(float x, float y) {
  PVector[] corners = getCorners();
  int selectedCorner = -1;
  for(int i=0;i<corners.length;i++){
   if(corners[i].dist(new PVector(parent.mouseX,parent.mouseY))<50){
      selectedCorner = i;
   }
  }
  return selectedCorner;
 }
 @Override
 public void onCollision(CIDER_GUI_Object object) {
  if(object instanceof CIDER_GUI_FilterComponent){
   if(isCollision(object)){
    if(object instanceof CIDER_GUI_variableOptionFilter){
     addFilter((CIDER_GUI_variableOptionFilter) object);
     update();
    }
   }
  }
 }
}
