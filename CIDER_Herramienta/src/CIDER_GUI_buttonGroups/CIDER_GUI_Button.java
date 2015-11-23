/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_buttonGroups;

import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_Object;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_Button extends CIDER_GUI_Object{
 
 protected PImage buttonImage;
 protected boolean isActivated;
 public CIDER_GUI_Button(PApplet parent, PVector position, float objectWidth, float objectHeight) {
  super(parent, position, objectWidth, objectHeight);
 }
 public CIDER_GUI_Button(PApplet parent, PImage buttonImage, PVector position, float objectWidth, float objectHeight) {
  super(parent, position, objectWidth, objectHeight);
  this.buttonImage = buttonImage;
  isActivated = false;
 }
 public PImage getButtonImage() {
  return buttonImage;
 }

 public void setButtonImage(PImage buttonImage) {
  this.buttonImage = buttonImage;
 }
 public boolean isIsActivated() {
  return isActivated;
 }
 public void setIsActivated(boolean isActivated) {
  this.isActivated = isActivated;
 }
 public boolean isClicked(float x, float y){
  return (isHover(x,y,objectWidth)>0) && parent.mousePressed && (parent.mouseEvent.getClickCount()==1);
 }
 @Override
 public void draw() {
  PVector[] corners = getCorners();
  float w = corners[1].x-corners[0].x;
  float h = corners[3].y-corners[0].y;
  parent.image(buttonImage, corners[0].x,corners[0].y,w, h);
 }
}
