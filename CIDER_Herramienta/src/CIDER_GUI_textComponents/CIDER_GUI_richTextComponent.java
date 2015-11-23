/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_textComponents;

import CIDER_GUI_Behaviors.Draggable;
import CIDER_GUI_Behaviors.Resizable;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_richTextComponent extends CIDER_GUI_textBoxComponent implements Draggable,Resizable{
 protected PGraphics informationLayer;
 protected float informationLayerWidth;
 protected float informationLayerHeight;
 public CIDER_GUI_richTextComponent(int[] foregroundColor, int[] backgroundColor, PApplet parent, float objectWidth, float objectHeight) {
  super(foregroundColor, backgroundColor, parent);
  informationLayer = parent.createGraphics((int) objectWidth,(int) objectHeight);
  informationLayerWidth = objectWidth;
  informationLayerHeight = objectHeight;
 }

 public CIDER_GUI_richTextComponent(int[] foregroundColor, int[] backgroundColor, PApplet parent, PVector position, float objectWidth, float objectHeight) {
  super(foregroundColor, backgroundColor, parent, position, objectWidth, objectHeight);
  informationLayerWidth = objectWidth;
  informationLayerHeight = objectHeight;
 }
 public void updateInformationLayer(){
  
 }
 @Override
 public PVector[] getCorners() {
  PVector c = getPosition();
  float x1 = c.x-informationLayerWidth*0.5f;
  float x2 = c.x+informationLayerWidth*0.5f;
  float y1 = c.y-informationLayerHeight*0.5f;
  float y2 = c.y+informationLayerHeight*0.5f;
  return new PVector[]{new PVector(x1,y1), new PVector(x2,y1),new PVector(x2,y2),new PVector(x1,y2)};
 }
 @Override
 public void draw() {
  updateInformationLayer();
  parent.image(informationLayer,position.x-informationLayerWidth*0.5f,position.y-informationLayerHeight*0.5f,informationLayerWidth,informationLayerHeight);
 }

 @Override
 public void draw(PGraphics g) {
  updateInformationLayer();
  g.image(informationLayer,position.x-informationLayerWidth*0.5f,position.y-informationLayerHeight*0.5f,informationLayerWidth,informationLayerHeight);
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
  if(isHover(parent.mouseX,parent.mouseY,informationLayerWidth*0.4f)>0){
   if(parent.mouseButton == PApplet.LEFT){
    setPosition(new PVector(parent.mouseX,parent.mouseY));
   }
  }
 }

 @Override
 public void displace() {
 }

 @Override
 public void onResize(float dx, float dy) {
  int selectedCorner = isOnCorner(parent.mouseX, parent.mouseY);
 }

 @Override
 public void resize(float dx, float dy) {
  informationLayerWidth += dx;
  informationLayerHeight += dy;
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
   if(corners[i].dist(new PVector(parent.mouseX,parent.mouseY))<20){
      selectedCorner = i;
   }
  }
  return selectedCorner;
 }
}
