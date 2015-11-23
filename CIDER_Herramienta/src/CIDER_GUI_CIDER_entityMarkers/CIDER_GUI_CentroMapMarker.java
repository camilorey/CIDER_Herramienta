/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_CIDER_entityMarkers;

import CIDER_DB.CIDER_DB;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import processing.core.PApplet;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_CentroMapMarker extends CIDER_GUI_CentroMarker{
 protected UnfoldingMap baseMap;
 protected PVector gpsPos;
 protected PVector[] pretendCorners;
 protected float pretendSurfaceWidth;
 protected float pretendSurfaceHeight;
 public CIDER_GUI_CentroMapMarker(CIDER_DB parentDB, PApplet parent) {
  super(parentDB, parent);
 }
 public CIDER_GUI_CentroMapMarker(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth) {
  super(parentDB, parent, position, objectWidth);
 }

 public CIDER_GUI_CentroMapMarker(CIDER_DB parentDB, PApplet parent, String name, PVector position, float objectWidth) {
  super(parentDB, parent, name, position, objectWidth);
 }
 public PVector[] getPretendCorners() {
  return pretendCorners;
 }
 
 public void setPretendCorners(PVector[] pretendCorners) {
  this.pretendCorners = pretendCorners;
 }

 public float getPretendSurfaceWidth() {
  return pretendSurfaceWidth;
 }

 public void setPretendSurfaceWidth(float pretendSurfaceWidth) {
  this.pretendSurfaceWidth = pretendSurfaceWidth;
 }

 public float getPretendSurfaceHeight() {
  return pretendSurfaceHeight;
 }

 public void setPretendSurfaceHeight(float pretendSurfaceHeight) {
  this.pretendSurfaceHeight = pretendSurfaceHeight;
 }
 public void setBaseMap(UnfoldingMap baseMap){
  this.baseMap = baseMap;
 }
 public PVector getGpsPos() {
  return gpsPos;
 }
 @Override
 public PVector getPosition() {
  return gpsToScreenCoordinates();
 }
 @Override
 public float dist(CIDER_GUI_CentroMarker c) {
  if(c instanceof CIDER_GUI_CentroMapMarker){
   return getPosition().dist(((CIDER_GUI_CentroMapMarker)c).getPosition());
  }else{
   return getPosition().dist(c.getPosition());
  }
 }
 public void setGpsPos(PVector gpsPos) {
  this.gpsPos = gpsPos;
 }
 public PVector gpsToScreenCoordinates(){
  return baseMap.getScreenPosition(new Location(gpsPos.x,gpsPos.y));
 }
 @Override
 public void drawObject() {
  if(position.x>= pretendCorners[0].x && position.x <= pretendCorners[0].x+pretendSurfaceWidth
   &&position.y>= pretendCorners[0].y && position.y <= pretendCorners[0].y+pretendSurfaceHeight){
   PVector screenPos = gpsToScreenCoordinates();
   parent.rect(screenPos.x-objectWidth/2.0f, screenPos.y-objectHeight/2.0f, objectWidth,objectWidth);
   
  }
 }
 public void drawName(){
  if(position.x>= pretendCorners[0].x && position.x <= pretendCorners[0].x+pretendSurfaceWidth
   &&position.y>= pretendCorners[0].y && position.y <= pretendCorners[0].y+pretendSurfaceHeight){
    PVector screenPos = gpsToScreenCoordinates();
    parent.text(objectName, screenPos.x+objectWidth/2.0f, screenPos.y+objectHeight/2.0f);
  }
 }
 @Override
 public void drawOnParent() {
  if(isHover(parent.mouseX,parent.mouseY,objectWidth*0.8f)>0){
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
    parent.fill(objectColor[0],objectColor[1],objectColor[2]); 
   }
   drawObject();
  }else{
   parent.fill(objectColor[0],objectColor[1],objectColor[2]); 
   drawObject();
  }
 }
 @Override
 public void draw() {
  drawOnParent();
 }
}
