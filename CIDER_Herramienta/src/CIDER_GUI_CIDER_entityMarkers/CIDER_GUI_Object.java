/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_CIDER_entityMarkers;
import CIDER_DB.CIDER_DB;
import processing.core.*;
/**
 *
 * @author laptop
 */
public class CIDER_GUI_Object {
 protected CIDER_DB parentDB;
 public PApplet parent;
 public PVector position;
 public float objectWidth;
 public float objectHeight;

 public CIDER_GUI_Object() {
  parent = null;
  parentDB = null;
  position = null;
  objectWidth = -1;
  objectHeight = -1;
 }
 public CIDER_GUI_Object(PApplet parent) {
  this.parent = parent;
  this.parentDB = null;
  this.position = null;
  this.objectWidth = -1;
  this.objectHeight = -1;
 }
 public CIDER_GUI_Object(PApplet parent, PVector position, float objectWidth) {
  this.parent = parent;
  this.parentDB = null;
  this.position = position;
  this.objectWidth = objectWidth;
  this.objectHeight = objectWidth;
 }
 public CIDER_GUI_Object(PApplet parent, PVector position, float objectWidth, float objectHeight) {
  this.parent = parent;
  this.parentDB = null;
  this.position = position;
  this.objectWidth = objectWidth;
  this.objectHeight = objectHeight;
 }
 public CIDER_GUI_Object(PVector position, float objectWidth) {
  this.parent = null;
  this.parentDB = null;
  this.position = position;
  this.objectWidth = objectWidth;
  this.objectHeight = objectWidth;
 }
 public CIDER_GUI_Object(PVector position, float objectWidth, float objectHeight) {
  this.parent = null;
  this.parentDB = null;
  this.position = position;
  this.objectWidth = objectWidth;
  this.objectHeight = objectHeight;
 }
 public CIDER_GUI_Object(CIDER_DB parentDB) {
  parent = null;
  parentDB = parentDB;
  position = null;
  objectWidth = -1;
  objectHeight = -1;
 }
 public CIDER_GUI_Object(CIDER_DB parentDB, PApplet parent) {
  this.parent = parent;
  this.parentDB = parentDB;
  this.position = null;
  this.objectWidth = -1;
  this.objectHeight = -1;
 }
 public CIDER_GUI_Object(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth) {
  this.parent = parent;
  this.parentDB = parentDB;
  this.position = position;
  this.objectWidth = objectWidth;
  this.objectHeight = objectWidth;
 }
 public CIDER_GUI_Object(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth, float objectHeight) {
  this.parent = parent;
  this.parentDB = parentDB;
  this.position = position;
  this.objectWidth = objectWidth;
  this.objectHeight = objectHeight;
 }
 public CIDER_GUI_Object(CIDER_DB parentDB, PVector position, float objectWidth) {
  this.parent = null;
  this.parentDB = parentDB;
  this.position = position;
  this.objectWidth = objectWidth;
  this.objectHeight = objectWidth;
 }
 public CIDER_GUI_Object(CIDER_DB parentDB, PVector position, float objectWidth, float objectHeight) {
  this.parent = null;
  this.parentDB = parentDB;
  this.position = position;
  this.objectWidth = objectWidth;
  this.objectHeight = objectHeight;
 }
 public PVector getPosition() {
  return position;
 }
 public void setPosition(PVector position) {
  this.position = position;
 }
 public float getObjectWidth() {
  return objectWidth;
 }
 public void setObjectWidth(float objectWidth) {
  this.objectWidth = objectWidth;
 }
 public float getObjectHeight() {
  return objectHeight;
 }
 public void setObjectHeight(float objectHeight) {
  this.objectHeight = objectHeight;
 }
 public void setParentDB(CIDER_DB parentDB){
  this.parentDB = parentDB;
 }
 public int isHover(float x, float y, float tolerance){
  if(parent == null){
   return -1;
  }else{
   PVector[] corners = getCorners();
   if((corners[0].x <= x && x<= corners[1].x) && (corners[0].y<= y && y<=corners[3].y)){
    return 1;
   }
   else{
    return 0;
   }
  } 
 }
 
 public PVector[] getCorners(){
  PVector pos = getPosition();
  float x1 = pos.x - objectWidth/2.0f;
  float x2 = pos.x + objectWidth/2.0f;
  float y1 = pos.y - objectHeight/2.0f;
  float y2 = pos.y + objectHeight/2.0f;
  return new PVector[]{new PVector(x1,y1), new PVector(x2,y1),new PVector(x2,y2),new PVector(x1,y2)};
 }
 public void draw(){
  
 }
 public void draw(PGraphics g){
  
 }
}
