/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_surfaces2;

import CIDER_DB.CIDER_Centro;
import CIDER_DB.CIDER_DB;
import CIDER_DB.CIDER_Variable;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_CentroMapMarker;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_CentroMarker;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_ClusterTreeMapMarker;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_ClusterTreeMarker;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_EntityMarker;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_Object;
import CIDER_GUI_textComponents.CIDER_GUI_FicheroComponent;
import CIDER_GUI_textComponents.CIDER_GUI_FilterComponent;
import CIDER_GUI_textComponents.CIDER_GUI_variableFilter;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.Google;
import java.util.ArrayList;
import java.util.HashMap;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_mapDisplaySurface extends CIDER_GUI_entityDisplaySurface{
 int minZoomLevel;
 int currentZoomLevel;
 int maxZoomLevel;
 boolean showingFichero;
 int selectedCentro;
 protected UnfoldingMap baseMap;
 protected HashMap<String, PVector> gpsPositions;
 protected CIDER_GUI_FicheroComponent fichero;
 public CIDER_GUI_mapDisplaySurface(PApplet parent, PVector position, float objectWidth, float graphWidth) {
  super(parent, position, objectWidth, graphWidth);
  fichero = new CIDER_GUI_FicheroComponent(parent, null);
  fichero.setParentDB(parentDB);
  showingFichero = false;
  selectedCentro = -1;
 }
 public CIDER_GUI_mapDisplaySurface(PApplet parent, PVector position, float surfaceWidth, float surfaceHeight, float graphWidth, float graphHeight) {
  super(parent, position, surfaceWidth, surfaceHeight, graphWidth, graphHeight);
  fichero = new CIDER_GUI_FicheroComponent(parent, null);
  showingFichero = false;
  selectedCentro = -1;
 }
 public CIDER_GUI_mapDisplaySurface(CIDER_DB parentDB, PVector position, float objectWidth, float graphWidth) {
  super(parentDB, position, objectWidth, graphWidth);
  fichero = new CIDER_GUI_FicheroComponent(parent, null);
  showingFichero = false;
  selectedCentro = -1;
 }
 public CIDER_GUI_mapDisplaySurface(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth, float objectHeight, float graphWidth, float graphHeight,PFont font) {
  super(parentDB, parent, position, objectWidth, objectHeight, graphWidth, graphHeight);
  gpsPositions = new HashMap<String,PVector>();
  setMap();
  setFichero(font);
  showingFichero = false;
  selectedCentro = -1;
  network.addParentSurface(null);
  network.setClusterRadius(objectWidth*0.05f);
  network.setZoomLevel(0);
 }
 public void setFichero(PFont textFont){
  fichero = new CIDER_GUI_FicheroComponent(parent,textFont);
  float xFichero = corners[0].x + surface.width*0.22f;
  float yFichero = corners[0].y + surface.height*0.4f;
  fichero.setPosition(new PVector(xFichero,yFichero));
  fichero.setParentDB(parentDB);
 }
 @Override
 public void addToContent(CIDER_GUI_Object object) {
  if(object instanceof CIDER_GUI_CentroMarker){
   CIDER_GUI_CentroMarker centroMarker = (CIDER_GUI_CentroMarker) object;
   CIDER_Centro centro = (CIDER_Centro)parentDB.get(centroMarker.getObjectName());
   PVector gpsCentroPos = centro.getPosGPS();
   gpsPositions.put(centro.getEntityName(), gpsCentroPos);
   CIDER_GUI_CentroMapMarker mapMarker = new CIDER_GUI_CentroMapMarker(parentDB, parent, centro.getEntityName(), centroMarker.getPosition(), centroMarker.objectWidth);
   mapMarker.setBaseMap(baseMap);
   mapMarker.setGpsPos(gpsCentroPos);
   mapMarker.setObjectColor(centroMarker.getObjectColor());
   mapMarker.setFilteredColor(centroMarker.getFilteredColor());
   mapMarker.setPretendCorners(corners);
   mapMarker.setPretendSurfaceWidth(objectWidth);
   mapMarker.setPretendSurfaceHeight(objectHeight);
   network.addMarkerObject(mapMarker);
   content.add(mapMarker);
  }
 }
 public String getClusteringFilter(){
  String clusteringFilter = "";
  for(int i=0;i<filters.size();i++){
   if(filters.get(i) instanceof CIDER_GUI_variableFilter){
    clusteringFilter = ((CIDER_GUI_variableFilter) filters.get(i)).getContent();
   }
  }
  return clusteringFilter;
 }
 @Override
 public void clusterizeData() {
  String clusteringFilter = getClusteringFilter();
  if(clusteringFilter.equals("")){
   super.clusterizeData();
  }else{
   CIDER_Variable clusteringVariable = parentDB.getVariable(clusteringFilter);
   network.clusterize(clusteringVariable);
  }
 }
 public PVector GPSToSurfaceCoordinates(PVector gpsPos){
  float[] screenPos = baseMap.getScreenPositionFromLocation(new Location(gpsPos.x, gpsPos.y));
  return new PVector(screenPos[0],screenPos[1]);
 }
 @Override
 public void addFilter(CIDER_GUI_FilterComponent filter) {
  float yOffset = corners[0].y+surface.height*0.01f;
  float xOffset = surface.width*0.01f;
  float yHeight = filter.getObjectHeight()+yOffset;
  float assignedX = corners[0].x+xOffset;
  for(int i=0;i<filters.size();i++){
   assignedX += (filters.get(i).getObjectWidth()+ xOffset);
  }
  filter.setPosition(new PVector(assignedX+filter.getObjectWidth()/2.0f,yHeight));
  filters.add(filter);
 }
 public void setMap(){
  minZoomLevel = 1;
  currentZoomLevel = 7;
  maxZoomLevel = 17;
  baseMap = new UnfoldingMap(parent, new Google.GoogleMapProvider());
  baseMap.setBackgroundColor(255);
  baseMap.setZoomRange(minZoomLevel,maxZoomLevel);
  Location bogotaLocation = new Location(4.609866,-74.08205);
  baseMap.zoomAndPanTo(bogotaLocation,currentZoomLevel);
  network = new CIDER_GUI_ClusterTreeMapMarker(objectWidth*0.05f, parentDB, parent, "map display", position, objectWidth*0.05f);
  network.setObjectColor(new int[]{0,255,0});
  network.setFilteredColor(new int[]{255,0,0});
  ((CIDER_GUI_ClusterTreeMapMarker) network).setPretendCorners(corners);
  ((CIDER_GUI_ClusterTreeMapMarker) network).setPretendSurfaceWidth(objectWidth);
  ((CIDER_GUI_ClusterTreeMapMarker) network).setPretendSurfaceHeight(objectHeight);
  ((CIDER_GUI_ClusterTreeMapMarker) network).setBaseMap(baseMap);
  ((CIDER_GUI_ClusterTreeMapMarker) network).setGpsPos(new PVector(4.609866f,-74.08205f));
 }
 public void mouseDragged(){
  if(parent.mouseX>=corners[0].x && parent.mouseX<=objectWidth+corners[0].x 
    && parent.mouseY>=corners[0].y && parent.mouseY<=objectHeight+corners[0].y){
   if(parent.mouseButton == PApplet.LEFT){
     baseMap.panBy(parent.mouseX-parent.pmouseX, parent.mouseY-parent.pmouseY);
   }
  }
 }
 public void mousePressed(){
  if(parent.mouseX>=corners[0].x && parent.mouseX<=objectWidth+corners[0].x 
    && parent.mouseY>=corners[0].y && parent.mouseY<=objectHeight+corners[0].y){
   if(parent.mouseEvent.getClickCount() ==2){
    if(parent.mouseButton == PApplet.LEFT){
     currentZoomLevel = baseMap.getZoomLevel()+1;
     if(currentZoomLevel<maxZoomLevel){
       baseMap.zoomAndPanTo(parent.mouseX,parent.mouseY, currentZoomLevel);
     }
    }else if(parent.mouseButton == PApplet.RIGHT){
      currentZoomLevel = baseMap.getZoomLevel()-1;
      if(currentZoomLevel>minZoomLevel){
       baseMap.zoomAndPanTo(parent.mouseX,parent.mouseY, currentZoomLevel);
      }
    }
   }
  }
 }
 public void updateFicheroObject(CIDER_GUI_CentroMarker entity){
  fichero.setFicheroInformation(entity);
 }
 @Override
 public void drawFilters() {
  int selectedFilter = -1;
  PVector mousePos = mouseToSurfaceCoordinates();
  for(int i=0;i<filters.size();i++){
   filters.get(i).draw(parent.g);
   if(filters.get(i).isHover(mousePos.x, mousePos.y, filters.get(i).objectWidth*0.5f)>0){
    if(parent.mousePressed){
     if(parent.mouseEvent.getClickCount()==2)
      selectedFilter = i;
    }
   }
  }
  if(selectedFilter != -1){
   filters.remove(selectedFilter);
  }
 }
 public void resetFilter(){
  for(int i=0;i<content.size();i++){
   ((CIDER_GUI_CentroMarker) content.get(i)).setIsInFilter(false);
  }
 }
 public void drawFichero(){
  if(selectedCentro != -1){
   updateFicheroObject((CIDER_GUI_CentroMarker) content.get(selectedCentro));
   fichero.draw();
  }
 }
 @Override
 public void drawContent() {
  network.draw();
 }
 @Override
 public void update() {
  surface.beginDraw();
   surface.background(150); 
  surface.endDraw();
  if(parent.mousePressed && parent.mouseButton == PApplet.RIGHT){
   selectedCentro = -1;
  }
 }
 @Override
 public void draw() {
  parent.tint(255);
  baseMap.draw();
  update();
  parent.tint(150,150);
  parent.image(surface,corners[0].x,corners[0].y,objectWidth,objectHeight);
  drawContent();
  drawFilters();
  parent.tint(255);
  drawFichero();
 }
}
