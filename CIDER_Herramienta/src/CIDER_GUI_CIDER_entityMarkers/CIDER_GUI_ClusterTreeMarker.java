/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_CIDER_entityMarkers;

import CIDER_DB.CIDER_DB;
import CIDER_DB.CIDER_Variable;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_ClusterTreeMarker extends CIDER_GUI_EntityMarker{
 public ArrayList<CIDER_GUI_EntityMarker> content;
 public ArrayList<CIDER_GUI_ClusterTreeMarker> children;
 public int clusterSize;
 protected float clusterRadius;
 public int zoomLevel;
 public int desiredZoom;
 public int[] filteredColor;
 boolean isFilterable;
 public CIDER_GUI_ClusterTreeMarker(CIDER_DB parentDB, PApplet parent, String name, PVector position, float objectWidth) {
  super(parentDB, parent, name, position, objectWidth);
  content = new ArrayList<CIDER_GUI_EntityMarker>();
  children = new ArrayList<CIDER_GUI_ClusterTreeMarker>();
 }
 public CIDER_GUI_ClusterTreeMarker(ArrayList<CIDER_GUI_EntityMarker> content, CIDER_DB parentDB, PApplet parent, String name, PVector position, float objectWidth) {
  super(parentDB, parent, name, position, objectWidth);
  this.content = content;
  children = new ArrayList<CIDER_GUI_ClusterTreeMarker>();
 }
 public CIDER_GUI_ClusterTreeMarker(float clusterRadius, CIDER_DB parentDB, PApplet parent, String name, PVector position, float objectWidth) {
  super(parentDB,parent, name, position, objectWidth);
  this.clusterRadius = clusterRadius;
  content = new ArrayList<CIDER_GUI_EntityMarker>();
  children = new ArrayList<CIDER_GUI_ClusterTreeMarker>();
 }
 public CIDER_GUI_ClusterTreeMarker(ArrayList<CIDER_GUI_EntityMarker> content, float clusterRadius, CIDER_DB parentDB, PApplet parent, String name, PVector position, float objectWidth) {
  super(parentDB, parent, name, position, objectWidth);
  this.content = content;
  this.clusterRadius = clusterRadius;
  children = new ArrayList<CIDER_GUI_ClusterTreeMarker>();
 }
 public ArrayList<CIDER_GUI_EntityMarker> getContent() {
  return content;
 }
 public ArrayList<CIDER_GUI_CentroMarker> getCentrosFromTree(){
  ArrayList<CIDER_GUI_CentroMarker> centros = new ArrayList<CIDER_GUI_CentroMarker>();
  for(int i=0;i<content.size();i++){
   if(content.get(i) instanceof CIDER_GUI_CentroMarker){
    
   }
  }
  return centros;
 }
 public ArrayList<CIDER_GUI_EntityMarker> getContentWithFilter(String filter){
  ArrayList<CIDER_GUI_EntityMarker> filtered = new ArrayList<CIDER_GUI_EntityMarker>();
  for(int i=0;i<content.size();i++){
   if(((CIDER_GUI_CentroMarker) content.get(i)).satisfiesFilter(filter)){
    CIDER_GUI_EntityMarker cont = content.get(i);
    filtered.add(cont);
    content.remove(cont);
   }
  }
  return filtered;
 }
 public ArrayList<CIDER_GUI_EntityMarker> getFilteredContent2(){
  ArrayList<CIDER_GUI_EntityMarker> filtered = new ArrayList<CIDER_GUI_EntityMarker>();
  for(int i=0;i<content.size();i++){
   CIDER_GUI_CentroMarker asCentro = (CIDER_GUI_CentroMarker) content.get(i);
   if(asCentro.isInFilter()){
    filtered.add(asCentro);
   }
  }
  for(int i=0;i<children.size();i++){
   ArrayList<CIDER_GUI_EntityMarker> filteredInChildren = children.get(i).getFilteredContent2();
   if(!filteredInChildren.isEmpty()){
    filtered.add(children.get(i));
   }
  }
  return filtered;
 }
 public ArrayList<CIDER_GUI_EntityMarker> getFilteredContent(){
  ArrayList<CIDER_GUI_EntityMarker> filtered = new ArrayList<CIDER_GUI_EntityMarker>();
  for(int i=0;i<content.size();i++){
   if(content.get(i) instanceof CIDER_GUI_CentroMarker){
    CIDER_GUI_CentroMarker centro = (CIDER_GUI_CentroMarker) content.get(i);
    if(centro.isInFilter()){
     filtered.add(centro);
    }
   }
  }
  for(int i=0;i<children.size();i++){
   ArrayList<CIDER_GUI_EntityMarker> filteredInChildren = children.get(i).getFilteredContent();
   for(int j=0;j<filteredInChildren.size();j++){
    filtered.add(filteredInChildren.get(j));
   }
  }
  return filtered;
 }
 public boolean isIsFilterable() {
  return isFilterable;
 }

 public void setIsFilterable(boolean isFilterable) {
  this.isFilterable = isFilterable;
 }
 public void resetFilter(){
  isFilterable = false;
  for(int i=0;i<content.size();i++){
   if(content.get(i) instanceof CIDER_GUI_CentroMarker){
    CIDER_GUI_CentroMarker centro = (CIDER_GUI_CentroMarker) content.get(i);
    centro.setIsFilterableActivated(true);
    centro.setIsInFilter(false);
   }
  }
  for(int i=0;i<children.size();i++){
   children.get(i).resetFilter();
  }
 }
 public void applyFilter(String filter){
  resetFilter();
  isFilterable = true;
  for(int i=0;i<content.size();i++){
   if(content.get(i) instanceof CIDER_GUI_CentroMarker){
    CIDER_GUI_CentroMarker centro = (CIDER_GUI_CentroMarker) content.get(i);
    centro.applyFilter(filter);
   }
  }
  for(int i=0;i<children.size();i++){
   children.get(i).applyFilter(filter);
  }
 }
 public void setContent(ArrayList<CIDER_GUI_EntityMarker> content) {
  this.content = content;
 }
 public float getClusterRadius() {
  return clusterRadius;
 }
 public void setClusterRadius(float clusterRadius) {
  this.clusterRadius = clusterRadius;
 }
 public int getMaxZoomLevel() {
  return depth();
 }
 public void setZoomLevel(int zoomLevel) {
  this.zoomLevel = zoomLevel;
 }
 public void setDesiredZoom(int desiredZoom) {
  this.desiredZoom = desiredZoom;
  for(int i=0;i<children.size();i++){
    children.get(i).setDesiredZoom(desiredZoom);
  }
 }
 public ArrayList<CIDER_GUI_EntityMarker> getSelected(){
  ArrayList<CIDER_GUI_EntityMarker> selected = new ArrayList<CIDER_GUI_EntityMarker>();
  for(int i=0;i<content.size();i++){
   if(content.get(i).isSelected()){
    selected.add(content.get(i));
   }
  }
  for(int i=0;i<children.size();i++){
   ArrayList<CIDER_GUI_EntityMarker> selectedInChild = children.get(i).getSelected();
   for(int j=0;j<selectedInChild.size();j++){
    selected.add(selectedInChild.get(j));
   }
  }
  return selected;
 }
 public void addMarkerObject(CIDER_GUI_EntityMarker object){
  object.setObjectColor(objectColor);
  object.addParentSurface(parentSurface);
  if(!children.isEmpty()){
   boolean isChosen = false;
   for(int i=0;i<children.size() && !isChosen;i++){
    if(children.get(i).isInRadius(object)){
     children.get(i).addMarkerObject(object);
     isChosen = true;
    } 
   }
   if(!isChosen){
    content.add(object);
   }
  }else{
    content.add(object);
  }
 }
 public int searchForConnectionInContent(CIDER_GUI_EntityMarker entity){
  int searchResult = -1;
  for(int i=0;i<content.size() && searchResult <0;i++){
   searchResult = content.get(i).hasConnection(entity);
  }
  return searchResult;
 }
 public int searchForConnection(CIDER_GUI_EntityMarker entity){
  if(children.isEmpty()){
   int searchResult = -1;
   for(int i=0;i<children.size() && searchResult <0;i++){
    searchResult = children.get(i).hasConnection(entity);
   }
   return searchResult;
  }else{
   return searchForConnectionInContent(entity);
  }
 }
 @Override
 public int hasConnection(CIDER_GUI_EntityMarker entity) {
  if(entity instanceof CIDER_GUI_CentroMarker){
   return searchForConnection(entity);
  }else if(entity instanceof CIDER_GUI_EntidadMarker){
   return searchForConnection(entity);
  }else{
   return -4;
  }
 }
 public boolean isInRadius(CIDER_GUI_EntityMarker object){
  return position.dist(object.getPosition())<=clusterRadius;
 }
 public PVector calculateCentroid(ArrayList<CIDER_GUI_EntityMarker> l){
  PVector centroid = new PVector();
  for(int i=0;i<l.size();i++){
   centroid.add(l.get(i).getPosition());
  }
  centroid.div((float) l.size());
  return centroid;
 }
 public ArrayList<CIDER_GUI_ClusterTreeMarker> createClusters(float radius){
  ArrayList<CIDER_GUI_ClusterTreeMarker> clusters = new ArrayList<CIDER_GUI_ClusterTreeMarker>();
  int i=0;
  String subClusterName = objectName;
  do{
   CIDER_GUI_EntityMarker clusterPivot = content.remove(0);
   PVector pivotPosition = clusterPivot.getPosition();
   ArrayList<CIDER_GUI_EntityMarker> subClusterContent = new ArrayList<CIDER_GUI_EntityMarker>();
   subClusterName += "_"+i;
   for(int j=1;j<content.size() && !content.isEmpty();j++){
    float memberDistance = ((CIDER_GUI_CentroMarker) clusterPivot).dist((CIDER_GUI_CentroMarker) content.get(j));
    if(memberDistance<radius){
     CIDER_GUI_EntityMarker clusterMember = content.get(j);
     subClusterContent.add(clusterMember);
     content.remove(clusterMember);
    }
   }
   if(subClusterContent.size()!=0){
    subClusterContent.add(clusterPivot);
    PVector subClusterCenter = calculateCentroid(subClusterContent);
    float subClusterRadius = clusterRadius*0.5f;
    float subClusterWidth = objectWidth*0.5f;
    CIDER_GUI_ClusterTreeMarker subCluster = new CIDER_GUI_ClusterTreeMarker(subClusterContent, subClusterRadius, parentDB,parent, subClusterName, subClusterCenter, subClusterWidth);
    subCluster.addParentSurface(parentSurface);
    subCluster.setObjectColor(objectColor);
    subCluster.setSelectedColor(selectedColor);
    subCluster.setContent(subClusterContent);
    subCluster.setZoomLevel(zoomLevel+1);
    subCluster.clusterize();
    clusters.add(subCluster);
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
 public ArrayList<CIDER_GUI_ClusterTreeMarker> createClusters(ArrayList<String> clusterNames){
  ArrayList<CIDER_GUI_ClusterTreeMarker> clusters = new ArrayList<CIDER_GUI_ClusterTreeMarker>();
  int gridX = parent.round(parent.sqrt(clusterNames.size()))+1;
  int gridY = parent.round(parent.sqrt(clusterNames.size()))+1;
  int numOption = 0;
  float w = parentSurface.getSurface().width;
  float h = parentSurface.getSurface().height;
  for(int i=1;i<gridX-1;i++){
   for(int j=1;j<gridY-1;j++){
    float u = (float) i / (float) gridX;
    float v = (float) j / (float) gridY;
    float x = u*w;
    float y = v*h;
    if(numOption < clusterNames.size()){
     PVector clusterPos = new PVector(x,y);
     CIDER_GUI_ClusterTreeMarker child = new CIDER_GUI_ClusterTreeMarker(clusterRadius*0.5f, parentDB, parent, clusterNames.get(i), clusterPos, objectWidth*0.75f);
     child.setParentDB(parentDB);
     clusters.add(child);
     numOption++;
    }
   }
  }
  return clusters;
 }
 public void clusterize(CIDER_Variable var){
  ArrayList<String> varOptions = var.getVariableOptions();
  children = createClusters(varOptions);
  for(int i=0;i<children.size();i++){
   ArrayList<CIDER_GUI_EntityMarker> childContent = getContentWithFilter(varOptions.get(i));
   children.get(i).setContent(childContent);
   children.get(i).clusterize();
  }
 }
 public ArrayList<PVector> createClusterCenters(ArrayList<String> clusterNames){
  ArrayList<PVector> centers = new ArrayList<PVector>();
  int gridX = parent.round(parent.sqrt(clusterNames.size()))+1;
  int gridY = parent.round(parent.sqrt(clusterNames.size()))+1;
  int numOption = 0;
  float w = parentSurface.getSurface().width;
  float h = parentSurface.getSurface().height;
  for(int i=1;i<gridX;i++){
   for(int j=1;j<gridY;j++){
    float u = (float) i / (float) gridX;
    float v = (float) j / (float) gridY;
    float x = u*w;
    float y = v*h;
    if(numOption<clusterNames.size()){
     PVector clusterCenter = new PVector(x,y);
     centers.add(clusterCenter);
     numOption++;
    }
   }
  }
  return centers;
 }
 public ArrayList<CIDER_GUI_ClusterTreeMarker> clusterizeWithVariable(String variableName){
  ArrayList<CIDER_GUI_ClusterTreeMarker> variableClusters = new ArrayList<CIDER_GUI_ClusterTreeMarker>();
  CIDER_Variable var = parentDB.getVariable(variableName);
  ArrayList<String> varOptions = var.getVariableOptions();
  ArrayList<PVector> clusterCenters = createClusterCenters(varOptions);
  float subClusterRadius = clusterRadius/(float) clusterCenters.size();
  float subClusterSize = objectWidth / (float) clusterCenters.size();
  for(int i=0;i<clusterCenters.size();i++){
   float u = (float) i / (float) clusterCenters.size();
   CIDER_GUI_ClusterTreeMarker cluster = new CIDER_GUI_ClusterTreeMarker(subClusterRadius, parentDB, parent, varOptions.get(i), clusterCenters.get(i), subClusterSize);
   cluster.addParentSurface(parentSurface);
   cluster.setObjectColor(new int[]{parent.round(255*u),255,255});
   cluster.setFilteredColor(filteredColor);
   cluster.setSelectedColor(selectedColor);
   cluster.setZoomLevel(zoomLevel+1);
   cluster.setDesiredZoom(desiredZoom);
   
  }
  return variableClusters;
 }

 public int[] getFilteredColor() {
  return filteredColor;
 }
 public void setFilteredColor(int[] filteredColor) {
  this.filteredColor = filteredColor;
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
 @Override
 public void toggleSelection(){
  if(isSelected){
   isSelected = false;
  }else{
   isSelected = true;
  }
  for(int i=0;i<children.size();i++){
   children.get(i).toggleSelection();
  }
  for(int i=0;i<content.size();i++){
   content.get(i).toggleSelection();
  }
 }
 public int size(){
  if(isFilterable)
   return sizeWithFilter();
  else
   return sizeWithoutFilter();
 }
 public int sizeWithFilter(){
  int size = 0;
  return size;
 }
 public int sizeWithoutFilter(){
  int size = 0;
  if(!children.isEmpty()){
   for(int i=0;i<children.size();i++){
    int childSize = children.get(i).sizeWithoutFilter();
    size += childSize;
   }
  }
  return size+content.size();
 }
 @Override
 public void drawObject() {
  PVector pos = getPosition();
  clusterSize = size();
  if(parentSurface != null){
   parentSurface.getSurface().noStroke();
   if(isSelected){
    parentSurface.getSurface().fill(selectedColor[0],selectedColor[1],selectedColor[2]);
   }else{
    parentSurface.getSurface().fill(objectColor[0],objectColor[1],objectColor[2]);
   }
   parentSurface.getSurface().ellipse(pos.x, pos.y, 2*objectWidth, 2*objectWidth);
   parentSurface.getSurface().fill(255-objectColor[0]);
   parentSurface.getSurface().text(clusterSize,pos.x-parent.textWidth(clusterSize+"")/2.0f,pos.y+parent.textAscent()/2.0f);
  }else{
   if(isSelected){
    parent.fill(selectedColor[0],selectedColor[1],selectedColor[2]);
   }else{
    parent.fill(objectColor[0],objectColor[1],objectColor[2]);
   }
   parent.ellipse(position.x, position.y, 2*objectWidth, 2*objectWidth);
   parent.fill(255-objectColor[0]);
   parent.text(clusterSize,pos.x-parent.textWidth(clusterSize+"")/2.0f,pos.y+parent.textAscent()/2.0f);
  }
 }
 @Override
 public void drawObject(PGraphics g) {
  PVector pos = getPosition();
  for(int i=0;i<content.size();i++){
   content.get(i).draw(g);
  }
  g.fill(objectColor[0],objectColor[1],objectColor[2]);
  g.ellipse(pos.x, pos.y, objectWidth, objectWidth);
  g.fill(255);
  g.text(clusterSize,pos.x-parent.textWidth(clusterSize+"")/2.0f,pos.y+parent.textAscent()/2.0f);
 }
 @Override
 public void draw() {
  if(zoomLevel<=desiredZoom){
   for(int i=0;i<content.size();i++){
    content.get(i).draw();
   }
   for(int i=0;i<children.size();i++){
    children.get(i).draw();
   }
  }else{
    if(parentSurface != null){
     PVector relativeMousePos = parentSurface.mouseToSurfaceCoordinates();
     if(isHover(relativeMousePos.x,relativeMousePos.y,objectWidth*0.5f)>0){
      drawName();
      if(parent.mousePressed){
       toggleSelection();
      }
     }
     drawObject();
    }else{
     if(isHover(parent.mouseX,parent.mouseY,objectWidth*0.5f)>0){
      drawName();
      if(parent.mousePressed){
       toggleSelection();
      }
     }
     drawObject();
    }
  }
 }
}
