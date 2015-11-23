/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_CIDER_entityMarkers;

import CIDER_DB.CIDER_DB;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_ClusterTreeMapMarker extends CIDER_GUI_ClusterTreeMarker{
 protected PVector gpsPos;
 protected UnfoldingMap baseMap;
 protected PVector[] pretendCorners;
 protected float pretendSurfaceWidth;
 protected float pretendSurfaceHeight;
 public CIDER_GUI_ClusterTreeMapMarker(CIDER_DB parentDB, PApplet parent, String name, PVector position, float objectWidth) {
  super(parentDB, parent, name, position, objectWidth);
 }

 public CIDER_GUI_ClusterTreeMapMarker(ArrayList<CIDER_GUI_EntityMarker> content, CIDER_DB parentDB, PApplet parent, String name, PVector position, float objectWidth) {
  super(content, parentDB, parent, name, position, objectWidth);
 }

 public CIDER_GUI_ClusterTreeMapMarker(float clusterRadius, CIDER_DB parentDB, PApplet parent, String name, PVector position, float objectWidth) {
  super(clusterRadius, parentDB, parent, name, position, objectWidth);
 }

 public CIDER_GUI_ClusterTreeMapMarker(ArrayList<CIDER_GUI_EntityMarker> content, float clusterRadius, CIDER_DB parentDB, PApplet parent, String name, PVector position, float objectWidth) {
  super(content, clusterRadius, parentDB, parent, name, position, objectWidth);
 }
 public PVector getGpsPos() {
  return gpsPos;
 }

 public void setGpsPos(PVector gpsPos) {
  this.gpsPos = gpsPos;
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
 public PVector gpsToScreenCoordinates(){
  return baseMap.getScreenPosition(new Location(gpsPos.x,gpsPos.y));
 }
 
 @Override
 public void drawObject() {
  if(getPosition().x>= pretendCorners[0].x && getPosition().x <= pretendCorners[0].x+pretendSurfaceWidth
   &&getPosition().y>= pretendCorners[0].y && getPosition().y <= pretendCorners[0].y+pretendSurfaceHeight){
   super.drawObject();
  }
 }
 
}
