/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_actionZones;

import CIDER_DB.CIDER_DB;
import CIDER_GUI_Behaviors.Collidable;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_Object;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_ActionZone extends CIDER_GUI_Object implements Collidable{
 PImage backgroundImage;
 public CIDER_GUI_ActionZone(CIDER_DB parentDB, PApplet parent) {
  super(parentDB, parent);
 }

 public CIDER_GUI_ActionZone(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth, PImage backgroundImage) {
  super(parentDB, parent, position, objectWidth);
  this.backgroundImage = backgroundImage;
 }
 
 @Override
 public boolean isCollision(CIDER_GUI_Object object) {
  return object.getPosition().dist(getPosition())<objectWidth/2.0f;
 }
 @Override
 public void onCollision(CIDER_GUI_Object object) {
 }
 @Override
 public void draw() {
  PVector[] corners = getCorners();
  parent.image(backgroundImage,corners[0].x,corners[0].y,objectWidth,objectWidth);
 }
}
