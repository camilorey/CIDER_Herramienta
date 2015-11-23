/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_surfaces2;

import CIDER_DB.CIDER_DB;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_CentroMarker;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_ClusterTreeMarker;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_EntidadMarker;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_EntityMarker;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_Object;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_entityDisplaySurface extends CIDER_GUI_filterableSurface{
 protected CIDER_GUI_ClusterTreeMarker network;
 protected ArrayList<CIDER_GUI_EntityMarker> selectedContent;
 protected ArrayList<CIDER_GUI_EntityMarker> filteredContent;
 protected int currentZoom;
 protected int desiredZoom;
 protected int maxZoom;
 public CIDER_GUI_entityDisplaySurface(PApplet parent, PVector position, float objectWidth, float graphWidth) {
  super(parent, position, objectWidth, graphWidth);
  network = new CIDER_GUI_ClusterTreeMarker(surface.width*0.2f, parentDB,parent, "network", 
                                            new PVector(surface.width*0.5f,surface.height*0.5f), 
                                            objectWidth*0.2f);
  network.addParentSurface(this);
  network.setObjectColor(new int[]{0,255,0});
  currentZoom = 0;
  desiredZoom = currentZoom;
  maxZoom = -1;
  selectedContent = new ArrayList<CIDER_GUI_EntityMarker>();
  filteredContent = new ArrayList<CIDER_GUI_EntityMarker>();
 }
 public CIDER_GUI_entityDisplaySurface(PApplet parent, PVector position, float surfaceWidth, float surfaceHeight, float graphWidth, float graphHeight) {
  super(parent, position, surfaceWidth, surfaceHeight, graphWidth, graphHeight);
  network = new CIDER_GUI_ClusterTreeMarker(surface.width*0.2f, parentDB,parent, "network", 
                                            new PVector(surface.width*0.5f,surface.height*0.5f), 
                                            objectWidth*0.2f);
  network.addParentSurface(this);
  network.setObjectColor(new int[]{0,255,0});
  currentZoom = 0;
  desiredZoom = currentZoom;
  maxZoom = -1;
  selectedContent = new ArrayList<CIDER_GUI_EntityMarker>();
  filteredContent = new ArrayList<CIDER_GUI_EntityMarker>();
 }
 public CIDER_GUI_entityDisplaySurface(CIDER_DB parentDB, PVector position, float objectWidth, float graphWidth) {
  super(parentDB, position, objectWidth, graphWidth);
  network = new CIDER_GUI_ClusterTreeMarker(surface.width*0.2f, parentDB,parent, "network", 
                                            new PVector(surface.width*0.5f,surface.height*0.5f), 
                                            objectWidth*0.2f);
  network.addParentSurface(this);
  network.setObjectColor(new int[]{0,255,0});
  currentZoom = 0;
  desiredZoom = currentZoom;
  maxZoom = -1;
  selectedContent = new ArrayList<CIDER_GUI_EntityMarker>();
  filteredContent = new ArrayList<CIDER_GUI_EntityMarker>();
 }
 public CIDER_GUI_entityDisplaySurface(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth, float objectHeight, float graphWidth, float graphHeight) {
  super(parentDB, parent, position, objectWidth, objectHeight, graphWidth, graphHeight);
  network = new CIDER_GUI_ClusterTreeMarker(surface.width*0.8f, parentDB,parent, "network", 
                                            new PVector(surface.width*0.5f,surface.height*0.5f), 
                                            objectWidth*0.2f);
  network.addParentSurface(this);
  network.setObjectColor(new int[]{0,255,0});
  currentZoom = 0;
  desiredZoom = currentZoom;
  maxZoom = -1;
  selectedContent = new ArrayList<CIDER_GUI_EntityMarker>();
  filteredContent = new ArrayList<CIDER_GUI_EntityMarker>();
 }
 public int[] getClusterColor() {
  return network.getObjectColor();
 }
 public void setClusterColor(int[] clusterColor) {
  network.setObjectColor(clusterColor);
 }
 public int[] getClusterSelectedColor() {
  return network.getSelectedColor();
 }
 public void setClusterSelectedColor(int[] clusterSelectedColor) {
  network.setSelectedColor(clusterSelectedColor);
 }
 @Override
 public void addToContent(CIDER_GUI_Object object) {
  object.setParentDB(parentDB);
  if(object instanceof CIDER_GUI_CentroMarker){
   ((CIDER_GUI_CentroMarker)object).setObjectColor(network.getObjectColor());
   ((CIDER_GUI_CentroMarker)object).setSelectedColor(network.getSelectedColor());
   network.addMarkerObject((CIDER_GUI_CentroMarker)object);
  }else if(object instanceof CIDER_GUI_EntidadMarker){
   ((CIDER_GUI_EntidadMarker) object).addParentSurface(this);
   content.add((CIDER_GUI_EntidadMarker)object);
  }
 }
 public void clusterizeData(){
  network.clusterize();
  maxZoom = network.depth();
 }
 public String[] parseFilterString(String filter){
  String[] filterParts = new String[]{"",""};
  if(!filter.contains("--")){
   int indexOf = filter.indexOf("[");
   if(filter.contains("v:[")){
    filterParts[0] = filter.substring(indexOf+1,filter.length()-1);
    filterParts[1] = "";
   }else if(filter.contains("f:[")){
    filterParts[0] = "";
    filterParts[1] = filter.substring(indexOf+1,filter.length()-1);
   }
  }else{
    String[] tokens = filter.split("--");
    int indexOfVar = tokens[0].indexOf("[");
    int indexOfFilts = tokens[1].indexOf("[");
    filterParts[0] = tokens[0].substring(indexOfVar+1,tokens[0].length()-1);
    filterParts[1] = tokens[1].substring(indexOfFilts+1,tokens[1].length()-1);
  }
  return filterParts;
 }
 public void setZoomLevel(int zoomLevel){
  if(zoomLevel<maxZoom){
   currentZoom = zoomLevel;
   network.setDesiredZoom(zoomLevel);
  }
 }
 public void setSelectedContent(){
  selectedContent = new ArrayList<CIDER_GUI_EntityMarker>();
  for(int i=0;i<content.size();i++){
   if(content.get(i) instanceof CIDER_GUI_EntityMarker){
    CIDER_GUI_EntityMarker selected = (CIDER_GUI_EntityMarker) content.get(i); 
    if(selected.isSelected()){
     selectedContent.add(selected);
    }
   }
  }
 }
 
 public void applyFilterToContent(){
  String filter = createFilterString();
  if(!filter.equals("")){
   String[] filterTokens = parseFilterString(filter);
   if(!filterTokens[1].equals("")){
    for(int i=0;i<content.size();i++){
     if(content.get(i) instanceof CIDER_GUI_CentroMarker){
      CIDER_GUI_CentroMarker centro = (CIDER_GUI_CentroMarker) content.get(i);
      centro.setIsFilterableActivated(true);
      centro.setIsInFilter(false);
      centro.applyFilter(filterTokens[1]);
     }
    } 
   }
  }
 }
 @Override
 public void drawContent() {
  for(int i=0;i<content.size();i++){
   content.get(i).draw();
  }
  network.draw();
 }
 public void drawSelectedContent(){
  for(int i=0;i<selectedContent.size();i++){
   selectedContent.get(i).draw();
  }
 }
}
