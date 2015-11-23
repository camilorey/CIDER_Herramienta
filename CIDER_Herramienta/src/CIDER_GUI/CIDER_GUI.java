/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI;

import CIDER_DB.CIDER_Centro;
import CIDER_DB.CIDER_DB;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_CentroMarker;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_ClusterTreeMarker;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_EntidadMarker;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_Object;
import CIDER_GUI_actionZones.CIDER_GUI_BarraSimpleActionZone;
import CIDER_GUI_actionZones.CIDER_GUI_ComparativoActionZone;
import CIDER_GUI_actionZones.CIDER_GUI_multibarrasActionZone;
import CIDER_GUI_actionZones.CIDER_GUI_singleVariablePlotZone;
import CIDER_GUI_actionZones.CIDER_GUI_statPlotZone;
import CIDER_GUI_actionZones.CIDER_GUI_tortaActionZone;
import CIDER_GUI_actionZones.CIDER_GUI_twoVariablePlotZone;
import CIDER_GUI_buttonGroups.CIDER_GUI_Button;
import CIDER_GUI_surfaces2.CIDER_GUI_ComparativoPlotSurface;
import CIDER_GUI_surfaces2.CIDER_GUI_StatPlotDisplaySurface;
import CIDER_GUI_surfaces2.CIDER_GUI_networkDisplaySurface2;
import CIDER_GUI_surfaces2.CIDER_GUI_entityDisplaySurface;
import CIDER_GUI_surfaces2.CIDER_GUI_filterableSurface;
import CIDER_GUI_surfaces2.CIDER_GUI_mapDisplaySurface;
import CIDER_GUI_surfaces2.CIDER_GUI_mapDisplaySurface2;
import CIDER_GUI_surfaces2.CIDER_GUI_networkDisplaySurface;
import CIDER_GUI_surfaces2.CIDER_GUI_twoVariableStatPlotDisplaySurface;
import CIDER_GUI_textComponents.CIDER_GUI_FilterComponent;
import CIDER_GUI_textComponents.CIDER_GUI_variableFilter;
import CIDER_GUI_textComponents.CIDER_GUI_variableOptionFilter;
import java.util.ArrayList;
import java.util.HashMap;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI {
 protected int zoomLevel;
 protected PApplet parent;
 protected CIDER_DB parentDB;
 protected ArrayList<CIDER_GUI_Button> menuButtons;
 protected CIDER_GUI_Button plusZoomButton;
 protected CIDER_GUI_Button minusZoomButton;
 protected CIDER_GUI_variableLayer variableLayer;
 protected ArrayList<CIDER_GUI_Object> guiContent;
 protected ArrayList<CIDER_GUI_FilterComponent> variables;
 protected ArrayList<CIDER_GUI_FilterComponent> variablesSplit;
 protected HashMap<String, ArrayList<CIDER_GUI_variableOptionFilter>> selectedVariableOptions;
 protected CIDER_GUI_networkDisplaySurface2 networkLayer;
 protected CIDER_GUI_mapDisplaySurface2 mapLayer;
 protected ArrayList<CIDER_GUI_StatPlotDisplaySurface> statPlots;
 protected ArrayList<CIDER_GUI_statPlotZone> statCreationZones;
 protected CIDER_GUI_singleVariablePlotZone singleVarPlotZone;
 public CIDER_GUI(PApplet parent, CIDER_DB parentDB,PFont textFont) {
  this.parent = parent;
  this.parentDB = parentDB;
  guiContent = new ArrayList<CIDER_GUI_Object>();
  variableLayer = new CIDER_GUI_variableLayer(parent, parentDB);
  setNetworkLayer();
  setMapLayer(textFont);
  createCentrosAndEntidades();
 }
 public CIDER_GUI(PApplet parent, CIDER_DB parentDB,PFont textFont, HashMap<String, PImage> actionZoneImages, HashMap<String,PImage> buttonImages) {
  this.parent = parent;
  this.parentDB = parentDB;
  guiContent = new ArrayList<CIDER_GUI_Object>();
  variableLayer = new CIDER_GUI_variableLayer(parent, parentDB, actionZoneImages.get("variableSplit"));
  setButtons(buttonImages);
  setNetworkLayer();
  setStatLayer(actionZoneImages);
  setMapLayer(textFont);
  createCentrosAndEntidades();
 }
 public void setButtons(HashMap<String, PImage> buttonImages){
  menuButtons = new ArrayList<CIDER_GUI_Button>();
  int numButtons = 3;
  for(int i=0;i<numButtons;i++){
   float u = (float) i / (float) numButtons;
   float x = (1-u)*(parent.width*0.16f)+u*(parent.width*0.83f);
   float y = parent.height*0.5f;
   menuButtons.add(new CIDER_GUI_Button(parent,new PVector(x,y), parent.width*0.15f, parent.height*0.15f));
  }
  menuButtons.get(0).setButtonImage(buttonImages.get("ficheroButton"));
  menuButtons.get(1).setButtonImage(buttonImages.get("estadisticosButton"));
  menuButtons.get(2).setButtonImage(buttonImages.get("mapasButton"));
  plusZoomButton = new CIDER_GUI_Button(parent, buttonImages.get("plusZoom"), new PVector(), 20, 20);
  minusZoomButton = new CIDER_GUI_Button(parent, buttonImages.get("minusZoom"), new PVector(), 20, 20);
 }
 public void setMapLayer(PFont textFont){
  float mapCornerX = parent.width*0.01f;
  float mapCornerY = parent.height*0.2f;
  float mapWidth = parent.width*0.98f;
  float mapHeight = parent.height*0.79f;
  PVector mapLayerCenter = new PVector(mapCornerX+mapWidth*0.5f,mapCornerY+mapHeight*0.5f);
  mapLayer = new CIDER_GUI_mapDisplaySurface2(parentDB, parent, mapLayerCenter, mapWidth, mapHeight, mapWidth, mapHeight,textFont);
  //create plus and minus zoom buttons
  float x = mapCornerX+mapWidth-plusZoomButton.getObjectWidth();
  float yPlus = mapCornerY+plusZoomButton.getObjectHeight();
  float yMinus = yPlus+2*plusZoomButton.getObjectHeight();
  plusZoomButton.setPosition(new PVector(x,yPlus));
  minusZoomButton.setPosition(new PVector(x,yMinus));
 }
 public void setNetworkLayer(){
  networkLayer = new CIDER_GUI_networkDisplaySurface2(parentDB, parent, new PVector(parent.width*0.5f,parent.height*0.6f), parent.width*0.98f, parent.height*0.75f,  parent.width*0.98f, parent.height*0.75f);
  networkLayer.setColors();
  networkLayer.setContent(8.0f, 10.0f);
 }
 public void setStatLayer(HashMap<String,PImage> actionZoneImages){
  statCreationZones = new ArrayList<CIDER_GUI_statPlotZone>();
  float y = parent.height*0.85f;
  statCreationZones.add(new CIDER_GUI_tortaActionZone(parentDB, parent, new PVector(parent.width*0.2f,y), parent.width*0.15f, actionZoneImages.get("tortaZone")));
  statCreationZones.add(new CIDER_GUI_BarraSimpleActionZone(parentDB, parent, new PVector(parent.width*0.4f,y), parent.width*0.15f, actionZoneImages.get("barrasSimplesZone")));
  statCreationZones.add(new CIDER_GUI_multibarrasActionZone(parentDB, parent, new PVector(parent.width*0.6f,y), parent.width*0.15f, actionZoneImages.get("multiBarrasZone")));
  statCreationZones.add(new CIDER_GUI_ComparativoActionZone(parentDB, parent, new PVector(parent.width*0.8f,y), parent.width*0.15f, actionZoneImages.get("comparativoZone")));
  statPlots = new ArrayList<CIDER_GUI_StatPlotDisplaySurface>();
 }
 public void createCentrosAndEntidades(){
  ArrayList<String> centroNames = parentDB.getCentrosRegistrados();
  ArrayList<String> entidadesNames = parentDB.getEntidadesRegistradas();
  int numCentros = 0;
  int numEntidades = 0;
  for(int i=0;i<centroNames.size();i++){
   float x = (float) parent.random(networkLayer.getSurfaceWidth()*0.02f,networkLayer.getSurfaceWidth()*0.98f);
   float y = (float) parent.random(networkLayer.getSurfaceHeight()*0.02f,networkLayer.getSurfaceHeight()*0.98f);
   PVector centroPosition = new PVector(x,y);
   String tamano = ((CIDER_Centro) parentDB.get(centroNames.get(i))).getTamano();
   float centroSize = 8.0f;
   if(tamano.equals("Mediana"))
     centroSize *= 1.5;
   else if(tamano.equals("Grande"))
     centroSize *= 1.9;
   CIDER_GUI_CentroMarker centro = new CIDER_GUI_CentroMarker(parentDB, parent, centroNames.get(i), centroPosition, centroSize);
   centro.setObjectColor(new int[]{150,0,0});
   centro.setSelectedColor(new int[]{255,0,0});
   centro.setFilteredColor(new int[]{255,0,0});
   centro.setIsFilterableActivated(false);
   centro.setIsSelected(false);
   networkLayer.addToContent(centro);
   mapLayer.addToContent(centro);
   numCentros++;
  }
  for(int i=0;i<entidadesNames.size();i++){
   float x = (float) parent.random(networkLayer.getSurfaceWidth()*0.02f,networkLayer.getSurfaceWidth()*0.98f);
   float y = (float) parent.random(networkLayer.getSurfaceHeight()*0.02f,networkLayer.getSurfaceHeight()*0.98f);
   PVector entidadPosition = new PVector(x,y);
   CIDER_GUI_EntidadMarker entidad = new CIDER_GUI_EntidadMarker(parentDB, parent, entidadesNames.get(i), entidadPosition, 3.0f);
   entidad.setObjectColor(new int[]{100,100,100});
   entidad.setSelectedColor(new int[]{150,150,150});
   networkLayer.addToContent(entidad);
   numEntidades++;
  }
  mapLayer.clusterizeData();
 }
 public void setZoom(int zoomLevel){
  this.zoomLevel = zoomLevel;
 }
 public void drawVariableLayer(){
  variableLayer.draw();
 }
 public void drawButtonLayer(){
  for(int i=0;i<menuButtons.size();i++){
   menuButtons.get(i).draw();
   if(menuButtons.get(i).isClicked(parent.mouseX, parent.mouseY)){
    menuButtons.get(i).setIsActivated(true);
   }
  }
 }
 public void resetButtons(){
  for(int i=0;i<menuButtons.size();i++){
   menuButtons.get(i).setIsActivated(false);
  }
 }
 public int buttonMenuPressed(){
  int numButton = -1;
  for(int i=0;i<menuButtons.size();i++){
   if(menuButtons.get(i).isIsActivated())
    numButton = i;
  }
  return numButton;
 }
 public void drawStatPlotLayer(){
  for(int i=0;i<statCreationZones.size();i++){
   statCreationZones.get(i).draw();
   CIDER_GUI_StatPlotDisplaySurface statPlot = variableLayer.interactionWithStatZone(statCreationZones.get(i));
   if(statPlot != null){
    statPlot.update();
    statPlots.add(statPlot);
   } 
  }
  for(int i=0;i<statPlots.size();i++){
   statPlots.get(i).draw();
   statPlots.get(i).move();
   variableLayer.collisionWithSurface2(statPlots.get(i));
   if(parent.mousePressed){
    statPlots.get(i).onResize(parent.mouseX-parent.pmouseX, parent.mouseY-parent.pmouseY);
   }
  }
  for(int i=0;i<statPlots.size();i++){
   if(statPlots.get(i).isToDelete()){
    statPlots.remove(i);
   }
  }
 }
 public void drawNetworkLayer(){
  networkLayer.setDesiredZoom(zoomLevel);
  networkLayer.draw();
  variableLayer.collisionWithSurface2(networkLayer);
 }
 public void resetMapLayer(){
  mapLayer.resetBaseMap();
  mapLayer.resetMapContent();
 }
 public void drawMapLayer(){
  //mapLayer.setZoomLevel(zoomLevel);
  mapLayer.draw();
  plusZoomButton.draw();
  minusZoomButton.draw();
  variableLayer.collisionWithSurface2(mapLayer);
 }
 public void mouseDraggedOnMapLayer(){
  mapLayer.mouseDragged();
 }
 public void mousePressedOnMapLayer(){
  mapLayer.mousePressed();
  if(plusZoomButton.isClicked(parent.mouseX, parent.mouseY)){
   System.out.println("zooming in");
   mapLayer.zoomInBaseMap();
   mapLayer.zoomInMapContent();
   zoomLevel++;
  }
  if(minusZoomButton.isClicked(parent.mouseX, parent.mouseY)){
   System.out.println("zooming out");
   mapLayer.zoomOutBaseMap();
   mapLayer.zoomOutMapContent();
   zoomLevel--;
  }
 }
 public void draw(){
  for(int i=0;i<guiContent.size();i++){
   guiContent.get(i).draw();
  }
 }
}
