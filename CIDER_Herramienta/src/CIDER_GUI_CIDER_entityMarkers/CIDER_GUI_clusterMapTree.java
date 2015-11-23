/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_CIDER_entityMarkers;

import CIDER_DB.CIDER_DB;
import CIDER_GUI_surfaces2.CIDER_GUI_mapDisplaySurface;
import CIDER_GUI_surfaces2.CIDER_GUI_mapDisplaySurface2;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_clusterMapTree extends CIDER_GUI_EntityMarker{
 protected CIDER_GUI_mapDisplaySurface2 parentSurface;
 protected CIDER_GUI_CentroMapMarker selectedMarker;
 protected PVector gpsLocation;
 protected UnfoldingMap baseMap;
 protected int clusterSize;
 protected int desiredZoomLevel;
 protected int zoomLevel;
 protected float clusterRadius;
 protected int desiredZoom;
 protected int[] filteredColor;
 protected boolean isInFilter;
 protected ArrayList<CIDER_GUI_CentroMapMarker> content;
 protected ArrayList<CIDER_GUI_clusterMapTree> children;
 public CIDER_GUI_clusterMapTree(CIDER_DB parentDB, PApplet parent) {
  super(parentDB, parent);
  this.baseMap = null;
  this.content = new ArrayList<CIDER_GUI_CentroMapMarker>();
  this.children = new ArrayList<CIDER_GUI_clusterMapTree>();
  this.clusterSize = 0;
  this.zoomLevel = 0;
  this.desiredZoom = 0;
  this.clusterRadius = -1.0f;
  this.filteredColor = new int[]{255,0,0};
 }
 public CIDER_GUI_clusterMapTree(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth) {
  super(parentDB, parent, position, objectWidth);
  this.baseMap = null;
  this.content = new ArrayList<CIDER_GUI_CentroMapMarker>();
  this.children = new ArrayList<CIDER_GUI_clusterMapTree>();
  this.clusterSize = 0;
  this.zoomLevel = 0;
  this.desiredZoom = 0;
  this.clusterRadius = -1.0f;
  this.filteredColor = new int[]{255,0,0};
 }
 public CIDER_GUI_clusterMapTree(CIDER_DB parentDB, PApplet parent, String name, PVector position, float objectWidth) {
  super(parentDB, parent, name, position, objectWidth);
  this.baseMap = null;
  this.content = new ArrayList<CIDER_GUI_CentroMapMarker>();
  this.children = new ArrayList<CIDER_GUI_clusterMapTree>();
  this.clusterSize = 0;
  this.zoomLevel = 0;
  this.desiredZoom = 0;
  this.clusterRadius = -1.0f;
  this.filteredColor = new int[]{255,0,0};
 }
 public CIDER_GUI_clusterMapTree(CIDER_DB parentDB, PApplet parent, UnfoldingMap baseMap,String name, PVector position, float objectWidth) {
  super(parentDB, parent, name, position, objectWidth);
  this.clusterRadius = objectWidth;
  this.baseMap = baseMap;
  this.content = new ArrayList<CIDER_GUI_CentroMapMarker>();
  this.children = new ArrayList<CIDER_GUI_clusterMapTree>();
  this.clusterSize = 0;
  this.zoomLevel = 0;
  this.desiredZoom = 0;
  this.filteredColor = new int[]{255,0,0};
  isInFilter = false;
 }
 public ArrayList<CIDER_GUI_CentroMapMarker> getContent() {
  return content;
 }

 public void setContent(ArrayList<CIDER_GUI_CentroMapMarker> content) {
  this.content = content;
 }
 public CIDER_GUI_mapDisplaySurface2 getParentSurface() {
  return parentSurface;
 }
 
 public CIDER_GUI_CentroMapMarker getSelectedMarker() {
  if(children.isEmpty()){
   return selectedMarker;
  }else{
    CIDER_GUI_CentroMapMarker inChild = null;
    for(int i=0;i<children.size() && inChild == null;i++){
     inChild = children.get(i).getSelectedMarker();
     if(inChild != null){
      selectedMarker = inChild;
     }
    }
    return selectedMarker;
  }
 }
 public void setSelectedMarker(CIDER_GUI_CentroMapMarker selectedMarker) {
  this.selectedMarker = selectedMarker;
 }
 public boolean isInRadius(CIDER_GUI_CentroMapMarker centroMapMarker){
  return getPosition().dist(centroMapMarker.getPosition()) <= clusterRadius;
 }
 public void addToContent(CIDER_GUI_CentroMapMarker centroMapMarker){
  centroMapMarker.setObjectColor(objectColor);
  centroMapMarker.setFilteredColor(filteredColor);
  centroMapMarker.addParentSurface(parentSurface);
  if(!children.isEmpty()){
   boolean isChosen = false;
   for(int i=0;i<children.size() && !isChosen;i++){
    if(children.get(i).isInRadius(centroMapMarker)){
     children.get(i).addToContent(centroMapMarker);
     isChosen = true;
    } 
   }
   if(!isChosen){
    content.add(centroMapMarker);
   }
  }else{
    content.add(centroMapMarker);
  }
 }
 public void setParentSurface(CIDER_GUI_mapDisplaySurface2 parentSurface) {
  this.parentSurface = parentSurface;
 }
 public PVector getGpsLocation() {
  return gpsLocation;
 }
 public void setGpsLocation(PVector gpsLocation) {
  this.gpsLocation = gpsLocation;
 }
 public PVector gpsPositionToParentPosition(){
  return baseMap.getScreenPosition(new Location(gpsLocation.x,gpsLocation.y));
 }
 public int getClusterSize() {
  return clusterSize;
 }
 public void setClusterSize(int clusterSize) {
  this.clusterSize = clusterSize;
 }
 public int getZoomLevel() {
  return zoomLevel;
 }
 public void setZoomLevel(int zoomLevel) {
  this.zoomLevel = zoomLevel;
 }
 public int getDesiredZoomLevel() {
  return desiredZoomLevel;
 }
 public void setDesiredZoomLevel(int desiredZoomLevel) {
  this.desiredZoomLevel = desiredZoomLevel;
  for(int i=0;i<children.size();i++){
   children.get(i).setDesiredZoomLevel(desiredZoomLevel);
  }
 }
 public float getClusterRadius() {
  return clusterRadius;
 }
 public void setClusterRadius(float clusterRadius) {
  this.clusterRadius = clusterRadius;
 }
 public int getDesiredZoom() {
  return desiredZoom;
 }
 public void setDesiredZoom(int desiredZoom) {
  this.desiredZoom = desiredZoom;
 }
 public int[] getFilteredColor() {
  return filteredColor;
 }
 public void resetFilter(){
  isInFilter = false;
  for(int i=0;i<content.size();i++){
   content.get(i).setIsFilterableActivated(true);
   content.get(i).setIsInFilter(false);
  }
  for(int i=0;i<children.size();i++){
   children.get(i).resetFilter();
  }
 }
 public void resetSelected(){
  for(int i=0;i<content.size();i++){
   content.get(i).setIsSelected(false);
  }
  for(int i=0;i<children.size();i++){
   children.get(i).resetSelected();
  }
 }
 public void applyFilter(String filter){
  resetFilter();
  for(int i=0;i<content.size();i++){
   content.get(i).applyFilter(filter);
   if(content.get(i).isInFilter()){
    isInFilter = true;
   }
  }
  for(int i=0;i<children.size();i++){
   children.get(i).applyFilter(filter);
  }
 }
 public PVector gpsToScreenCoordinates(PVector p){
  return baseMap.getScreenPosition(new Location(p.x,p.y));
 }
 public PVector calculateCentroid(ArrayList<CIDER_GUI_CentroMapMarker> l){
  PVector centroid = new PVector();
  for(int i=0;i<l.size();i++){
   centroid.add(l.get(i).getGpsPos());
  }
  centroid.div((float) l.size());
  return centroid;
 }
 public ArrayList<CIDER_GUI_clusterMapTree> createClusters(float subClusterRadius){
  ArrayList<CIDER_GUI_clusterMapTree> clusters = new ArrayList<CIDER_GUI_clusterMapTree>();
  int i=0;
  do{
   CIDER_GUI_CentroMapMarker clusterPivot = content.remove(0);
   PVector pivotPosition = clusterPivot.getPosition();
   ArrayList<CIDER_GUI_CentroMapMarker> subClusterContent = new ArrayList<CIDER_GUI_CentroMapMarker>();
   String clusterName = getObjectName()+"_"+i;
   for(int j=1;j<content.size() && !content.isEmpty();j++){
    float memberDistance = clusterPivot.dist(content.get(j));
    if(memberDistance<subClusterRadius){
     CIDER_GUI_CentroMapMarker clusterMember = content.get(j);
     subClusterContent.add(clusterMember);
     content.remove(clusterMember);
    }
   }
   if(!subClusterContent.isEmpty()){
    subClusterContent.add(clusterPivot);
    PVector subClusterGPSPos = calculateCentroid(subClusterContent);
    PVector subClusterCenter = gpsToScreenCoordinates(subClusterGPSPos);
    float childClusterRadius = clusterRadius*0.5f;
    float childClusterWidth = objectWidth*0.5f;
    CIDER_GUI_clusterMapTree child = new CIDER_GUI_clusterMapTree(parentDB, parent, baseMap, clusterName, subClusterCenter, childClusterWidth);
    child.setParentSurface(parentSurface);
    child.setObjectColor(objectColor);
    child.setFilteredColor(filteredColor);
    child.setGpsLocation(subClusterGPSPos);
    child.setClusterRadius(childClusterRadius);
    child.setZoomLevel(getZoomLevel()+1);
    child.setContent(subClusterContent);
    child.clusterize();
    clusters.add(child);
   }else{
    content.add(clusterPivot);
   }
   i++;
  }while(!content.isEmpty() && i<content.size());
  return clusters;
 }
 public void clusterize(){
  if(content.size()>1){
   children = createClusters(clusterRadius*0.5f);
   clusterSize = size();
  }
  clusterSize = size();
 }
 public int size(){
  if(children.isEmpty())
   return content.size();
  else{
   int size = 0;
   for(int i=0;i<children.size();i++){
    size += children.get(i).size();
   }
   return size + content.size();
  }
 }
 public int depth(){
  int depth = 0;
  if(!children.isEmpty()){
   for(int i=0;i<children.size();i++){
    depth = PApplet.max(depth,children.get(i).depth());
   }
  }
  return 1+depth;
 }
 public void setFilteredColor(int[] filteredColor) {
  this.filteredColor = filteredColor;
 }
 @Override
 public void drawObject() {
  PVector pos = gpsPositionToParentPosition();
  if(pos.x>=parentSurface.getCorners()[0].x && pos.x <= parentSurface.getCorners()[0].x+parentSurface.getObjectWidth() 
   &&pos.y>=parentSurface.getCorners()[0].y && pos.y <= parentSurface.getCorners()[0].y+parentSurface.getObjectHeight()){
   if(isInFilter){
    parent.fill(filteredColor[0],filteredColor[1],filteredColor[2]);
   }else{
    parent.fill(objectColor[0],objectColor[1],objectColor[2]);
   }
   parent.ellipse(pos.x, pos.y, objectWidth, objectWidth);
   parent.fill(0);
   parent.text(size(),pos.x-parent.textWidth(clusterSize+"")/2.0f,pos.y+parent.textAscent()/2.0f);
  }
 }
 @Override
 public void draw() {
  if(zoomLevel <= desiredZoomLevel){
  selectedMarker = null;
  for(int i=0;i<content.size();i++){
   content.get(i).draw();
   if(content.get(i).isSelected()){
    selectedMarker = content.get(i);
   }
  }
  for(int i=0;i<children.size();i++){
   children.get(i).draw();
  }
 }else{
   drawObject();
  }
 }
}
