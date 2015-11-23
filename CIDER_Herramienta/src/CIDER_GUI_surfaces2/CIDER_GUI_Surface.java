/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_surfaces2;

import CIDER_DB.CIDER_DB;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_Object;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_Surface extends CIDER_GUI_Object{
 protected PVector[] corners;
 protected PGraphics surface;
 protected int[] backgroundColor;
 public CIDER_GUI_Surface(PApplet parent, PVector position, float objectWidth, float graphWidth){
  super(parent, position, objectWidth, objectWidth);
  corners = createCorners(position,objectWidth,objectWidth); 
  surface = parent.createGraphics(parent.round(graphWidth), parent.round(graphWidth));
  backgroundColor = new int[]{150,150,150};
 }
 public CIDER_GUI_Surface(PApplet parent, PVector position, float surfaceWidth, float surfaceHeight, float graphWidth, float graphHeight){
  super(parent, position, surfaceWidth, surfaceHeight);
  corners = createCorners(position,objectWidth,objectWidth);
  surface = parent.createGraphics(parent.round(graphWidth), parent.round(graphHeight));
  backgroundColor = new int[]{150,150,150};
 }
 public CIDER_GUI_Surface(CIDER_DB parentDB, PVector position, float objectWidth,float graphWidth) {
  super(parentDB, position, objectWidth,objectWidth);
  corners = createCorners(position,objectWidth,objectWidth); 
  surface = parent.createGraphics(parent.round(graphWidth), parent.round(graphWidth));
  backgroundColor = new int[]{150,150,150};
 }
 public CIDER_GUI_Surface(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth, float objectHeight, float graphWidth, float graphHeight) {
  super(parentDB, parent, position, objectWidth, objectHeight);
  corners = createCorners(position,objectWidth,objectHeight);
  surface = parent.createGraphics(parent.round(graphWidth), parent.round(graphHeight));
  backgroundColor = new int[]{150,150,150};
 }
 public PVector[] createCorners(PVector c, float w, float h){
  return new PVector[]{new PVector(c.x-w/2.0f,c.y-h/2.0f),new PVector(c.x+w/2.0f,c.y-h/2.0f),
                       new PVector(c.x+w/2.0f,c.y+h/2.0f),new PVector(c.x-w/2.0f,c.y+h/2.0f),};
 }
 public int[] getBackgroundColor() {
  return backgroundColor;
 }
 public void setBackgroundColor(int[] backgroundColor) {
  this.backgroundColor = backgroundColor;
 }
 public PVector[] getCorners(){
  return corners;
 }
 public float getSurfaceWidth(){
  return surface.width;
 }
 public float getSurfaceHeight(){
  return surface.height;
 }
 public PVector mouseToSurfaceCoordinates(){
  return new PVector(parent.mouseX-corners[0].x,parent.mouseY-corners[0].y);
 }
 public PGraphics getSurface() {
  return surface;
 }
 public void setSurface(PGraphics surface) {
  this.surface = surface;
 }
 public void update(){
  surface.beginDraw();
   surface.background(backgroundColor[0],backgroundColor[1],backgroundColor[2]);
  surface.endDraw();
 }
 public void draw(){
  PVector upperLeft = corners[0];
  int w = (int) corners[0].dist(corners[1]);
  int h = (int) corners[0].dist(corners[3]);
  update();
  parent.image(surface,upperLeft.x, upperLeft.y, w, h);
 }
 public void draw(PGraphics g){
  float w = corners[0].dist(corners[1]);
  float h = corners[0].dist(corners[3]);
  float relativeWidth = g.width*(w / (float) parent.width);
  float relativeHeight = g.height*(h / (float) parent.height);
  float relativeUpperX = corners[0].x*((float) g.width/ (float) parent.width);
  float relativeUpperY = corners[0].y*((float) g.height/(float) parent.height);
  update();
  g.image(surface,relativeUpperX, relativeUpperY, (int) relativeWidth, (int) relativeHeight);
 }
}
