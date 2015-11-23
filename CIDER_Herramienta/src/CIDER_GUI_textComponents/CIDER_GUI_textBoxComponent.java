/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_textComponents;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_Object;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;
/**
 *
 * @author laptop
 */
public class CIDER_GUI_textBoxComponent extends CIDER_GUI_Object{
 protected int textSize;
 protected String content;
 protected int[] foregroundColor;
 protected int[] backgroundColor;
 public CIDER_GUI_textBoxComponent(int[] foregroundColor, int[] backgroundColor, PApplet parent) {
  super(parent);
  this.foregroundColor = foregroundColor;
  this.backgroundColor = backgroundColor;
 }
 public CIDER_GUI_textBoxComponent(int[] foregroundColor, int[] backgroundColor, PApplet parent, PVector position, float objectWidth, float objectHeight) {
  super(parent, position, objectWidth, objectHeight);
  this.foregroundColor = foregroundColor;
  this.backgroundColor = backgroundColor;
 }
 public int getTextSize() {
  return textSize;
 }
 public void setTextSize(int textSize) {
  this.textSize = textSize;
 }
 public int[] getForegroundColor() {
  return foregroundColor;
 }
 public void setForegroundColor(int[] foregroundColor) {
  this.foregroundColor = foregroundColor;
 }
 public int[] getBackgroundColor() {
  return backgroundColor;
 }
 public void setBackgroundColor(int[] backgroundColor) {
  this.backgroundColor = backgroundColor;
 }
 public String getContent() {
  return content;
 }
 public void setContent(String content) {
  this.content = content;
 }
 @Override
 public void draw() {
  PVector[] corners = getCorners();
  parent.noStroke();
  parent.fill(backgroundColor[0],backgroundColor[1],backgroundColor[2]);
  parent.beginShape();
   for(int i=0;i<corners.length;i++){
    parent.vertex(corners[i].x,corners[i].y);
   }
  parent.endShape(PApplet.CLOSE);
  float x = corners[0].x+objectWidth*0.01f;
  float y = corners[1].y+objectHeight*0.01f;
  float w = objectWidth*0.98f;
  float h = objectHeight*0.98f;
  parent.fill(foregroundColor[0],foregroundColor[1],foregroundColor[2]);
  parent.textSize(textSize);
  parent.text(content,x,y,w,h);
 }
 @Override
 public void draw(PGraphics g) {
  PVector[] corners = getCorners();
  g.noStroke();
  g.fill(backgroundColor[0],backgroundColor[1],backgroundColor[2]);
  g.beginShape();
   for(int i=0;i<corners.length;i++){
    g.vertex(corners[i].x,corners[i].y);
   }
  g.endShape(PApplet.CLOSE);
  float x = corners[0].x+objectWidth*0.01f;
  float y = corners[0].y+objectWidth*0.01f;
  float w = objectWidth*0.98f;
  float h = objectHeight*0.98f;
  g.fill(foregroundColor[0],foregroundColor[1],foregroundColor[2]);
  g.textSize(textSize);
  g.text(content,x,y,w,h);
 }
}
