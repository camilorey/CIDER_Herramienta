/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_surfaces2;

import CIDER_DB.CIDER_Centro;
import CIDER_DB.CIDER_DB;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_CentroMapMarker;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_CentroMarker;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_Object;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_clusterMapTree;
import CIDER_GUI_textComponents.CIDER_GUI_FicheroComponent;
import CIDER_GUI_textComponents.CIDER_GUI_FilterComponent;
import CIDER_GUI_textComponents.CIDER_GUI_variableOptionFilter;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.Google;
import java.util.HashMap;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_mapDisplaySurface2 extends CIDER_GUI_filterableSurface{
 int minClusterZoomLevel;
 int maxClusterZoomLevel;
 int minMapZoomLevel;
 int maxMapZoomLevel;
 int currentContentZoomLevel;
 int currentMapZoomLevel;
 CIDER_GUI_CentroMapMarker centroForFichero;
 int selectedCentro;
 protected PFont ficheroFont;
 protected UnfoldingMap baseMap;
 protected CIDER_GUI_FicheroComponent fichero;
 protected CIDER_GUI_clusterMapTree mapContent;
 public CIDER_GUI_mapDisplaySurface2(PApplet parent, PVector position, float objectWidth, float graphWidth) {
  super(parent, position, objectWidth, graphWidth);
  this.minMapZoomLevel = 0;
  this.maxMapZoomLevel = 0;
  this.minClusterZoomLevel = 0;
  this.maxClusterZoomLevel = 0;
  this.currentContentZoomLevel = 0;
  fichero = new CIDER_GUI_FicheroComponent(parent, null);
  centroForFichero = null;
  setBaseMap();
  setFichero();
 }
 public CIDER_GUI_mapDisplaySurface2(PApplet parent, PVector position, float surfaceWidth, float surfaceHeight, float graphWidth, float graphHeight) {
  super(parent, position, surfaceWidth, surfaceHeight, graphWidth, graphHeight);
  setBaseMap();
  this.minMapZoomLevel = 0;
  this.maxMapZoomLevel = 0;
  this.minClusterZoomLevel = 0;
  this.maxClusterZoomLevel = 0;
  this.currentContentZoomLevel = 0;
  centroForFichero = null;
  this.selectedCentro = -1;
  fichero = new CIDER_GUI_FicheroComponent(parent, null);
  this.mapContent = null;
 }
 public CIDER_GUI_mapDisplaySurface2(CIDER_DB parentDB, PVector position, float objectWidth, float graphWidth) {
  super(parentDB, position, objectWidth, graphWidth);
  this.minMapZoomLevel = 0;
  this.maxMapZoomLevel = 0;
  this.minClusterZoomLevel = 0;
  this.maxClusterZoomLevel = 0;
  this.currentContentZoomLevel = 0;
  centroForFichero = null;
  this.selectedCentro = -1;
  fichero = new CIDER_GUI_FicheroComponent(parent, null);
  this.mapContent = null;
  setBaseMap();
  setFichero();
 }
 public CIDER_GUI_mapDisplaySurface2(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth, float objectHeight, float graphWidth, float graphHeight,PFont ficheroFont) {
  super(parentDB, parent, position, objectWidth, objectHeight, graphWidth, graphHeight);
  this.ficheroFont = ficheroFont;
  this.selectedCentro = -1;  
  setBaseMap();
  setFichero();
  setMapContent();
 }
 public PFont getFicheroFont() {
  return ficheroFont;
 }
 public void setMapContent(){
  float x = corners[0].x+objectWidth*0.5f;
  float y = corners[0].y+objectHeight*0.5f;
  this.mapContent = new CIDER_GUI_clusterMapTree(parentDB, parent, baseMap, "map content", new PVector(x,y), objectWidth*0.05f);
  mapContent.setClusterRadius(400);
  mapContent.setParentSurface(this);
  mapContent.setGpsLocation(new PVector(4.609866f,-74.08205f)); //set cluster center in Bogota
  mapContent.setSelectedColor(new int[]{255,0,0});
  mapContent.setObjectColor(new int[]{255,255,255});
 }
 public void clusterizeData(){
  mapContent.clusterize();
  minClusterZoomLevel = 1;
  maxClusterZoomLevel = mapContent.depth();
  System.out.println("cluster depth: "+mapContent.depth());
  System.out.println("cluster size: "+ mapContent.size());
 }
 public void setZoomLevel(int zoomLevel){
  currentMapZoomLevel = zoomLevel;
  mapContent.setDesiredZoomLevel(currentMapZoomLevel);
 }
 public void zoomInMapContent(){
  currentContentZoomLevel++;
  if(currentContentZoomLevel<maxClusterZoomLevel){
   mapContent.setDesiredZoomLevel(currentContentZoomLevel);
  }
 }
 public void zoomOutMapContent(){
  currentContentZoomLevel--;
  if(currentContentZoomLevel>minClusterZoomLevel){
   mapContent.setDesiredZoomLevel(currentContentZoomLevel);
  }
 }
 public void panBaseMap(float dx, float dy){
  baseMap.panBy(dx,dy);
 }
 public void zoomInBaseMap(){
  currentMapZoomLevel = baseMap.getZoomLevel()+1;
  if(currentMapZoomLevel<maxMapZoomLevel){
   baseMap.zoomToLevel(currentMapZoomLevel);
  }
 }
 public void zoomOutBaseMap(){
  currentMapZoomLevel = baseMap.getZoomLevel()-1;
  if(currentMapZoomLevel>minMapZoomLevel){
   baseMap.zoomToLevel(currentMapZoomLevel);
  }
 }
 public void zoomInAndPanByBaseMap(){
  currentMapZoomLevel = baseMap.getZoomLevel()+1;
  if(currentMapZoomLevel<maxMapZoomLevel){
   baseMap.zoomOut();
  }
 }
 public void zoomOutAndPanByBaseMap(){
  currentMapZoomLevel = baseMap.getZoomLevel()-1;
  if(currentMapZoomLevel>minMapZoomLevel){
   baseMap.zoomAndPanTo(parent.mouseX,parent.mouseY, currentMapZoomLevel);
  }
 }
 @Override
 public void addToContent(CIDER_GUI_Object object) {
  if(object instanceof CIDER_GUI_CentroMarker){
   CIDER_GUI_CentroMarker centroMarker = (CIDER_GUI_CentroMarker) object;
   CIDER_Centro centro = (CIDER_Centro)parentDB.get(centroMarker.getObjectName());
   PVector gpsCentroPos = centro.getPosGPS();
   CIDER_GUI_CentroMapMarker mapMarker = new CIDER_GUI_CentroMapMarker(parentDB, parent, centro.getEntityName(), centroMarker.getPosition(), centroMarker.objectWidth);
   mapMarker.setBaseMap(baseMap);
   mapMarker.setGpsPos(gpsCentroPos);
   mapMarker.setObjectColor(mapContent.getObjectColor());
   mapMarker.setSelectedColor(mapContent.getSelectedColor());
   mapMarker.setPretendCorners(corners);
   mapMarker.setPretendSurfaceWidth(objectWidth);
   mapMarker.setPretendSurfaceHeight(objectHeight);
   mapContent.addToContent(mapMarker);
  }
 }
 public void setFicheroFont(PFont ficheroFont) {
  this.ficheroFont = ficheroFont;
 }
 public void setBaseMap(){
  minMapZoomLevel = 1;
  maxMapZoomLevel = 17;
  currentMapZoomLevel = 7;
  baseMap = new UnfoldingMap(parent, new Google.GoogleMapProvider());
  baseMap.setBackgroundColor(255);
  baseMap.setZoomRange(minMapZoomLevel,maxMapZoomLevel);
  Location bogotaLocation = new Location(4.609866,-74.08205);
  baseMap.zoomAndPanTo(bogotaLocation,currentMapZoomLevel);
 }
 public void mouseDragged(){
  if(parent.mouseX>=corners[0].x && parent.mouseX<=objectWidth+corners[0].x 
    && parent.mouseY>=corners[0].y && parent.mouseY<=objectHeight+corners[0].y){
   if(parent.mouseButton == PApplet.LEFT){
     panBaseMap(parent.mouseX-parent.pmouseX, parent.mouseY-parent.pmouseY);
   }
  }
 }
 public void mousePressed(){
  if(parent.mouseX>=corners[0].x && parent.mouseX<=objectWidth+corners[0].x 
    && parent.mouseY>=corners[0].y && parent.mouseY<=objectHeight+corners[0].y){
   if(parent.mouseEvent.getClickCount() ==2){
    if(parent.mouseButton == PApplet.LEFT){
      zoomInAndPanByBaseMap();
    }else if(parent.mouseButton == PApplet.RIGHT){
      zoomOutAndPanByBaseMap(); 
    }
   }
  }
 }
 public void setFichero(){
  fichero = new CIDER_GUI_FicheroComponent(parent,ficheroFont);
  float xFichero = corners[0].x + surface.width*0.22f;
  float yFichero = corners[0].y + surface.height*0.4f;
  fichero.setPosition(new PVector(xFichero,yFichero));
  fichero.setParentDB(parentDB);
 }
 public void updateFicheroObject(CIDER_GUI_CentroMapMarker centro){
  fichero.setFicheroInformation(centro);
 }
 @Override
 public void applyFilter(){
  String filter = createFilterString();
  if(!filter.equals("")){
   String[] filterTokens = parseFilterString(filter);
   if(!filterTokens[1].equals("")){
     mapContent.applyFilter(filterTokens[1]);
   }
  }else{
   mapContent.resetFilter();
  }
 }
 @Override
 public void drawContent() {
  applyFilter();
  mapContent.draw();
 }
 @Override
 public void drawFilters() {
  float yPosition = surface.height*0.05f;
  float xOffset = surface.width*0.01f;
  float assignedX = xOffset;
  int selectedFilter = -1;
  PVector mousePos = mouseToSurfaceCoordinates();
  for(int i=0;i<filters.size();i++){
   filters.get(i).setPosition(new PVector(assignedX+filters.get(i).getObjectWidth()*0.5f+corners[0].x,yPosition+corners[0].y));
   filters.get(i).draw(parent.g);
   assignedX += filters.get(i).getObjectWidth()+xOffset;
   if(filters.get(i).isHover(parent.mouseX,parent.mouseY,filters.get(i).objectWidth)>0){
    if(parent.mousePressed && parent.mouseButton == PApplet.RIGHT){
     selectedFilter = i;
    }
   }
  }
  if(selectedFilter!=-1){
   filters.remove(selectedFilter);
  }
 }
 public void drawFichero(){
  centroForFichero = mapContent.getSelectedMarker();
  if(centroForFichero != null){
   updateFicheroObject(centroForFichero);
   fichero.draw();
  }
 }
 @Override
 public void update() {
  surface.beginDraw();
   surface.background(150);
  surface.endDraw();
  centroForFichero = mapContent.getSelectedMarker();
  if(parent.mousePressed && parent.mouseButton == PApplet.RIGHT){
   mapContent.resetSelected();
  }
 }
 public void resetBaseMap(){
  Location bogotaLocation = new Location(4.609866,-74.08205);
  baseMap.zoomAndPanTo(bogotaLocation,7);
 }
 public void resetMapContent(){
  mapContent.setDesiredZoom(1);
  mapContent.setZoomLevel(1);
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
 @Override
 public void onCollision(CIDER_GUI_Object object) {
  if(object instanceof CIDER_GUI_variableOptionFilter){
   if(isCollision(object)){
    addFilter((CIDER_GUI_FilterComponent) object);
   }
  }
 }
}
