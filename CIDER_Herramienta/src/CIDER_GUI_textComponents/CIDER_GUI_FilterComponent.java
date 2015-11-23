/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_textComponents;

import CIDER_GUI_surfaces.CIDER_GUI_markerDisplaySurface;
import CIDER_GUI_Behaviors.Clickable;
import CIDER_GUI_Behaviors.Collidable;
import CIDER_GUI_Behaviors.Draggable;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_Object;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_FilterComponent extends CIDER_GUI_textBoxComponent implements Draggable, Clickable,Collidable{
 protected boolean isAttached;
 protected boolean isActive;
 public boolean isMovable;
 public CIDER_GUI_FilterComponent(int[] foregroundColor, int[] backgroundColor, PApplet parent) {
  super(foregroundColor, backgroundColor, parent);
  isAttached = false;
  isActive = true;
  textSize = 10;
  isMovable = false;
 }
 public CIDER_GUI_FilterComponent(int[] foregroundColor, int[] backgroundColor, PApplet parent, PVector position) {
  super(foregroundColor, backgroundColor, parent);
  this.position = position;
  isAttached = false;
  isActive = true;
  isMovable = false;
  textSize = 10;
 }
 public void fadeColor(){
  for(int i=0;i<backgroundColor.length;i++)
   backgroundColor[i] *=0.25f;
 }
 public void intensifyColor(){
  for(int i=0;i<backgroundColor.length;i++)
   backgroundColor[i] *= 4.0f;
 }
 @Override
 public void draw() {
   super.draw();
 }

 @Override
 public void draw(PGraphics g) {
   super.draw(g);
 }
 @Override
 public void onDrag() {
  if(isHover(parent.mouseX,parent.mouseY,objectWidth)>0){
   if(isActive)
    move();
  }
 }
 @Override
 public void move() {
  position = new PVector(parent.mouseX,parent.mouseY);
 }

 @Override
 public void displace() {
  if(isHover(parent.mouseX,parent.mouseY,objectWidth)>0){
   if(parent.mouseButton == PApplet.LEFT){
    PVector displacement = new PVector(parent.mouseX-parent.pmouseX,parent.mouseY-parent.pmouseY);
    position.add(PVector.mult(displacement, 0.05f));
   }
  }
 }

 @Override
 public void onClick() {
  if(isHover(parent.mouseX,parent.mouseY,objectWidth)>0){
   if(parent.mouseButton == PApplet.LEFT){
    if(!isActive){
     isActive = true;
     intensifyColor();
    }else{
     isActive = false;
     fadeColor();
    } 
   }
  }
 }

 @Override
 public void onDoubleClick() {
  if(isHover(parent.mouseX,parent.mouseY,objectWidth)>0){
   if(parent.mouseEvent.getClickCount()==2){
    if(!isActive){
     isActive = true;
    }else{
     isActive = false;
    } 
   }
  }else{
   isActive = false;
  }
 }

 @Override
 public boolean isCollision(CIDER_GUI_Object object) {
  if(object instanceof CIDER_GUI_markerDisplaySurface){
   return ((CIDER_GUI_markerDisplaySurface)object).isCollision(this);
  }else{
   return false;
  }
 }

 @Override
 public void onCollision(CIDER_GUI_Object object) {
  if(object instanceof CIDER_GUI_markerDisplaySurface){
   CIDER_GUI_markerDisplaySurface surface = (CIDER_GUI_markerDisplaySurface) object;
   surface.onCollision(this);
  }
 }
}
