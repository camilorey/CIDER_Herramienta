/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_surfaces2;

import CIDER_DB.CIDER_DB;
import CIDER_GUI_Behaviors.Collidable;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_Object;
import CIDER_GUI_textComponents.CIDER_GUI_FilterComponent;
import CIDER_GUI_textComponents.CIDER_GUI_variableFilter;
import CIDER_GUI_textComponents.CIDER_GUI_variableOptionFilter;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_filterableSurface extends CIDER_GUI_Surface implements Collidable{
 protected ArrayList<CIDER_GUI_Object> content;
 protected ArrayList<CIDER_GUI_FilterComponent> filters;
 public CIDER_GUI_filterableSurface(PApplet parent, PVector position, float objectWidth, float graphWidth) {
  super(parent, position, objectWidth, graphWidth);
  content = new ArrayList<CIDER_GUI_Object>();
  filters = new ArrayList<CIDER_GUI_FilterComponent>();
 }
 public CIDER_GUI_filterableSurface(PApplet parent, PVector position, float surfaceWidth, float surfaceHeight, float graphWidth, float graphHeight) {
  super(parent, position, surfaceWidth, surfaceHeight, graphWidth, graphHeight);
  content = new ArrayList<CIDER_GUI_Object>();
  filters = new ArrayList<CIDER_GUI_FilterComponent>();
 }
 public CIDER_GUI_filterableSurface(CIDER_DB parentDB, PVector position, float objectWidth, float graphWidth) {
  super(parentDB, position, objectWidth, graphWidth);
  content = new ArrayList<CIDER_GUI_Object>();
  filters = new ArrayList<CIDER_GUI_FilterComponent>();
 }
 public CIDER_GUI_filterableSurface(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth, float objectHeight, float graphWidth, float graphHeight) {
  super(parentDB, parent, position, objectWidth, objectHeight, graphWidth, graphHeight);
  content = new ArrayList<CIDER_GUI_Object>();
  filters = new ArrayList<CIDER_GUI_FilterComponent>();
 }
 public void addToContent(CIDER_GUI_Object object){
  content.add(object);
 }
 public void addFilter(CIDER_GUI_FilterComponent filter){
  filters.add(filter);
 }
 public void applyFilter(){
  
 }
 public void drawContent(){
  for(int i=0;i<content.size();i++){
    content.get(i).draw(surface);
  }
 }
 public void drawFilters(){
  float yPosition = surface.height*0.05f;
  float xOffset = surface.width*0.01f;
  float assignedX = xOffset;
  int selectedFilter = -1;
  PVector mousePos = mouseToSurfaceCoordinates();
  for(int i=0;i<filters.size();i++){
   filters.get(i).setPosition(new PVector(assignedX+filters.get(i).getObjectWidth()*0.5f,yPosition));
   filters.get(i).draw(surface);
   assignedX += filters.get(i).getObjectWidth()+xOffset;
   if(filters.get(i).isHover(mousePos.x,mousePos.y,filters.get(i).objectWidth*0.5f)>0){
    if(parent.mousePressed && parent.mouseEvent.getClickCount()==2){
     selectedFilter = i;
    }
   }
  }
  if(selectedFilter!=-1){
   filters.remove(selectedFilter);
  }
 }
 public String createFilterString(){
  String filterString = "";
  String variableFilters = "";
  String variableOptionFilters = "";
  for(int i=0;i<filters.size();i++){
   if(filters.get(i) instanceof CIDER_GUI_variableFilter){
    variableFilters += filters.get(i).getContent() + ",";
   }else if(filters.get(i) instanceof CIDER_GUI_variableOptionFilter){
    variableOptionFilters += filters.get(i).getContent() +",";
   }
  }
  if(variableFilters.equals("") && variableOptionFilters.equals("")){
   return "";
  }else if(!variableFilters.equals("") && variableOptionFilters.equals("")){
   return "v:["+variableFilters+"]";
  }else if(variableFilters.equals("") && !variableOptionFilters.equals("")){
   return "f:["+variableOptionFilters+"]";
  }else{
   return "v:["+variableFilters+"]--f:["+variableOptionFilters+"]";
  }
 }
 public String[] parseFilterString(String filter){
  String[] filterParts = new String[]{"",""};
  if(!filter.contains("--")){
   int indexOf = filter.indexOf("[");
   if(filter.contains("v:[")){
    filterParts[0] = filter.substring(indexOf+1,filter.length()-2);
    filterParts[1] = "";
   }else if(filter.contains("f:[")){
    filterParts[0] = "";
    filterParts[1] = filter.substring(indexOf+1,filter.length()-1);
   }
  }else{
    String[] tokens = filter.split("--");
    int indexOfVar = tokens[0].indexOf("[");
    int indexOfFilts = tokens[1].indexOf("[");
    filterParts[0] = tokens[0].substring(indexOfVar+1,tokens[0].length()-2);
    filterParts[1] = tokens[1].substring(indexOfFilts+1,tokens[1].length()-1);
  }
  return filterParts;
 }
 @Override
 public void update() {
  surface.beginDraw();
   surface.background(backgroundColor[0],backgroundColor[1],backgroundColor[2]);
   drawContent();
   drawFilters();
  surface.endDraw();
 }
 @Override
 public boolean isCollision(CIDER_GUI_Object object) {
  float x1 = object.getPosition().x-object.getObjectWidth()/2.0f;
  float x2 = object.getPosition().x+object.getObjectWidth()/2.0f;
  float y1 = object.getPosition().y-object.getObjectHeight()/2.0f;
  float y2 = object.getPosition().y-object.getObjectHeight()/2.0f;
  return ((corners[0].x<=x1 && x1<=corners[1].x) || (corners[0].x<=x2 && x2<=corners[1].x)) 
      && ((corners[0].y<=y1 && y1<=corners[2].y) || (corners[0].y<=y2 && y2<=corners[2].y));
 }
 @Override
 public void onCollision(CIDER_GUI_Object object) {
  if(object instanceof CIDER_GUI_FilterComponent){
   if(isCollision(object)){
    addFilter((CIDER_GUI_FilterComponent) object);
    update();
   }
  }
 }
}
