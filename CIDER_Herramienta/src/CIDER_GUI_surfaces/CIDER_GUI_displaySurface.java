/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_surfaces;

import CIDER_DB.CIDER_DB;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_EntityMarker;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_Object;
import CIDER_GUI_textComponents.CIDER_GUI_FilterComponent;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_displaySurface extends CIDER_GUI_Object{
 public String surfaceName;
 protected PGraphics surface;
 protected int[] backgroundColor;
 protected PVector[] corners;
 public CIDER_GUI_displaySurface(PApplet parent) {
  super(parent);
  surface = null;
 }
 public CIDER_GUI_displaySurface(PApplet parent, PVector position, float objectWidth, float objectHeight) {
  super(parent, position, objectWidth, objectHeight);
  surface = parent.createGraphics(parent.round(objectWidth), parent.round(objectHeight));
  
 }
 public CIDER_GUI_displaySurface(CIDER_DB parentDB) {
  super(parentDB);
  surface = null;
 }
 public CIDER_GUI_displaySurface(CIDER_DB parentDB, PApplet parent) {
  super(parentDB, parent);
  surface = null;
 }
 public CIDER_GUI_displaySurface(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth, float objectHeight) {
  super(parentDB, parent, position, objectWidth, objectHeight);
  surface = parent.createGraphics(parent.round(objectWidth), parent.round(objectHeight));
 }
 public int[] getBackgroundColor() {
  return backgroundColor;
 }
 public void setBackgroundColor(int[] backgroundColor) {
  this.backgroundColor = backgroundColor;
 }
 public PGraphics getSurface() {
  return surface;
 }
 public PVector mouseToSurfaceCoordinates(){
  PVector[] corners = getCorners();
  return new PVector(parent.mouseX-corners[0].x,parent.mouseY-corners[0].y);
 }
 public void setSurface(PGraphics surface) {
  this.surface = surface;
 }
 public void update(){
  
 }
 @Override
 public void draw() {
  update();
  parent.image(surface,position.x-objectWidth/2.0f,position.y-objectHeight/2.0f,objectWidth, objectHeight);
 }
 @Override
 public void draw(PGraphics g) {
  float relativeWidth = g.width*(objectWidth / (float) parent.width);
  float relativeHeight = g.height*(objectHeight / (float) parent.height);
  float relativeX = position.x*((float) g.width/ (float) parent.width);
  float relativeY = position.y*((float) g.height/(float) parent.height);
  update();
  g.image(surface,relativeX-relativeWidth/2.0f,relativeY-relativeHeight/2.0f,
         (int) relativeWidth,(int) relativeHeight);
 }
}
