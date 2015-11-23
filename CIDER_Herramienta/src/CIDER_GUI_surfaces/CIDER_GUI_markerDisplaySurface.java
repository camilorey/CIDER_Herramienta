/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_surfaces;
import CIDER_DB.CIDER_DB;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_Object;
import CIDER_GUI_Behaviors.Collidable;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_EntityMarker;
import java.util.ArrayList;
import processing.core.*;
import CIDER_GUI_textComponents.CIDER_GUI_FilterComponent;
import CIDER_GUI_textComponents.CIDER_GUI_variableFilter;
import CIDER_GUI_textComponents.CIDER_GUI_variableOptionFilter;
/**
 *
 * @author laptop
 */
public class CIDER_GUI_markerDisplaySurface extends CIDER_GUI_displaySurface implements Collidable{
 protected ArrayList<CIDER_GUI_FilterComponent> filters;
 protected ArrayList<CIDER_GUI_EntityMarker> content;
 protected ArrayList<CIDER_GUI_EntityMarker> selectedContent;
 public CIDER_GUI_markerDisplaySurface(PApplet parent) {
  super(parent);
  filters = new ArrayList<CIDER_GUI_FilterComponent>();
  content = new ArrayList<CIDER_GUI_EntityMarker>();
  selectedContent = new ArrayList<CIDER_GUI_EntityMarker>();
 }
 public CIDER_GUI_markerDisplaySurface(PApplet parent, PVector position, float objectWidth, float objectHeight) {
  super(parent, position, objectWidth, objectHeight);
  filters = new ArrayList<CIDER_GUI_FilterComponent>();
  content = new ArrayList<CIDER_GUI_EntityMarker>();
  selectedContent = new ArrayList<CIDER_GUI_EntityMarker>();
 }
 public CIDER_GUI_markerDisplaySurface(CIDER_DB parentDB, PApplet parent) {
  super(parentDB, parent);
  filters = new ArrayList<CIDER_GUI_FilterComponent>();
  content = new ArrayList<CIDER_GUI_EntityMarker>();
  selectedContent = new ArrayList<CIDER_GUI_EntityMarker>();
 }
 public CIDER_GUI_markerDisplaySurface(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth, float objectHeight) {
  super(parentDB, parent, position, objectWidth, objectHeight);
  filters = new ArrayList<CIDER_GUI_FilterComponent>();
  content = new ArrayList<CIDER_GUI_EntityMarker>();
  selectedContent = new ArrayList<CIDER_GUI_EntityMarker>();
 }
 public ArrayList<CIDER_GUI_FilterComponent> getFilters() {
  return filters;
 }
 public void setFilters(ArrayList<CIDER_GUI_FilterComponent> filters) {
  this.filters = filters;
 }
 public void addFilter(CIDER_GUI_Object filter){
  float yOffset = getObjectHeight()*0.01f;
  float xOffset = getObjectWidth()*0.01f;
  float yHeight = filter.getObjectHeight()+yOffset;
  float assignedX = xOffset;
  for(int i=0;i<filters.size();i++){
   assignedX += (filters.get(i).getObjectWidth()+ xOffset);
  }
  filter.setPosition(new PVector(assignedX+filter.getObjectWidth()/2.0f,yHeight));
 }
 public void addToContent(CIDER_GUI_EntityMarker object){
  content.add(object);
 }
 public void drawFilters(){
   for(int i=0;i<filters.size();i++){
    filters.get(i).draw(surface);
   }
 }
 public void drawContent(){
  for(int i=0;i<content.size();i++){
   content.get(i).draw();
  }
 }
 public ArrayList<CIDER_GUI_EntityMarker> getSelected(){
  ArrayList<CIDER_GUI_EntityMarker> selected = new ArrayList<CIDER_GUI_EntityMarker>();
  for(int i=0;i<content.size();i++){
   if(content.get(i).isSelected()){
    selected.add(content.get(i));
   }
  }
  return selected;
 }
 @Override
 public void update(){
  surface.beginDraw();
   surface.background(backgroundColor[0],backgroundColor[1],backgroundColor[2]);
   drawContent();
   drawFilters();
  surface.endDraw();
 }
 @Override
 public boolean isCollision(CIDER_GUI_Object object){
  float a = position.x-objectWidth/2.0f;
  float b = position.x+objectWidth/2.0f;
  float c = position.y-objectHeight/2.0f;
  float d = position.y+objectHeight/2.0f;
  float x1 = object.getPosition().x-object.getObjectWidth()/2.0f;
  float x2 = object.getPosition().x+object.getObjectWidth()/2.0f;
  float y1 = object.getPosition().y-object.getObjectHeight()/2.0f;
  float y2 = object.getPosition().y-object.getObjectHeight()/2.0f;
  return ((a<=x1 && x1<=b) || (a<=x2 && x2<=b)) && ((c<=y1 && y1<=d) || (c<=y2 && y2<=d));
 }
 @Override
 public void onCollision(CIDER_GUI_Object object) {
  if(object instanceof CIDER_GUI_FilterComponent){
   if(isCollision(object)){
    addFilter((CIDER_GUI_FilterComponent) object);
   }
  }
 }
}
