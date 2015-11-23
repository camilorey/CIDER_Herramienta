/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_CIDER_entityMarkers;

import CIDER_DB.CIDER_Centro;
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
public class CIDER_GUI_CentroClusterTreeMarker extends CIDER_GUI_EntityMarker{
 protected ArrayList<CIDER_GUI_CentroMarker> content;
 protected ArrayList<CIDER_GUI_CentroClusterTreeMarker> children;
 protected float clusterRadius;
 protected int[] filteredColor;
 protected boolean isInFilter;
 protected String clusterVarName;
 protected int zoomLevel;
 protected int desiredZoom;
 public CIDER_GUI_CentroClusterTreeMarker(CIDER_DB parentDB, PApplet parent) {
  super(parentDB, parent);
  setLists();
  setProperties();
  clusterRadius = 0;
 }
 public CIDER_GUI_CentroClusterTreeMarker(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth) {
  super(parentDB, parent, position, objectWidth);
  setLists();
  setProperties();
  clusterRadius = 0;
 }

 public CIDER_GUI_CentroClusterTreeMarker(CIDER_DB parentDB, PApplet parent, String name, PVector position, float objectWidth) {
  super(parentDB, parent, name, position, objectWidth);
  setLists();
  setProperties();
  clusterRadius = 0;
 }
 public CIDER_GUI_CentroClusterTreeMarker(CIDER_DB parentDB, PApplet parent, String name, PVector position, float objectWidth, float clusterRadius) {
  super(parentDB, parent, name, position, objectWidth);
  setLists();
  setProperties();
  this.clusterRadius = clusterRadius;
  clusterVarName = "";
 }
 public int getZoomLevel() {
  return zoomLevel;
 }

 public void setZoomLevel(int zoomLevel) {
  this.zoomLevel = zoomLevel;
 }

 public int getDesiredZoom() {
  return desiredZoom;
 }

 public void setDesiredZoom(int desiredZoom) {
  this.desiredZoom = desiredZoom;
  if(!isLeaf()){
   for(int i=0;i<children.size();i++){
    children.get(i).setDesiredZoom(desiredZoom);
   }
  }
 }
 @Override
 public void toggleSelection() {
  for(int i=0;i<content.size();i++){
   content.get(i).toggleSelection();
  }
  for(int i=0;i<children.size();i++){
   children.get(i).toggleSelection();
  }
 }
 public String getClusterVarName() {
  return clusterVarName;
 }

 public void setClusterVarName(String clusterVarName) {
  this.clusterVarName = clusterVarName;
 }
 public void setLists(){
  content = new ArrayList<CIDER_GUI_CentroMarker>();
  children = new ArrayList<CIDER_GUI_CentroClusterTreeMarker>();
 }
 public void setProperties(){
  isFilterable = false;
  isInFilter = false;
  zoomLevel = 0;
  desiredZoom = 0;
 }
 public ArrayList<CIDER_GUI_CentroMarker> getContentWithProperty(String option){
  ArrayList<CIDER_GUI_CentroMarker> subContent = new ArrayList<CIDER_GUI_CentroMarker>();
  for(int i=0;i<content.size();i++){
   if(content.get(i).satisfiesFilter(option)){
    CIDER_GUI_CentroMarker centro = content.get(i);
    subContent.add(centro);
   }
  }
  for(int i=0;i<subContent.size();i++){
   content.remove(subContent.get(i));
  }
  return subContent;
 }
 public PVector calculateCentroid(ArrayList<CIDER_GUI_CentroMarker> l){
  PVector centroid = new PVector();
  for(int i=0;i<l.size();i++){
   centroid.add(l.get(i).getPosition());
  }
  centroid.div((float) l.size());
  return centroid;
 }
 public ArrayList<CIDER_GUI_CentroClusterTreeMarker> createClusters(float radius){
  ArrayList<CIDER_GUI_CentroClusterTreeMarker> clusters = new ArrayList<CIDER_GUI_CentroClusterTreeMarker>();
  int i=0;
  String subClusterName = objectName;
  do{
   CIDER_GUI_CentroMarker clusterPivot = content.remove(0);
   PVector pivotPosition = clusterPivot.getPosition();
   ArrayList<CIDER_GUI_CentroMarker> subClusterContent = new ArrayList<CIDER_GUI_CentroMarker>();
   subClusterName += "_"+i;
   for(int j=1;j<content.size() && !content.isEmpty();j++){
    float memberDistance = clusterPivot.dist(content.get(j));
    if(memberDistance<=radius){
     CIDER_GUI_CentroMarker clusterMember = content.get(j);
     subClusterContent.add(clusterMember);
     content.remove(clusterMember);
    }
   }
   if(subClusterContent.size()!=0){
    subClusterContent.add(clusterPivot);
    PVector subClusterCenter = calculateCentroid(subClusterContent);
    float subClusterRadius = clusterRadius*0.5f;
    float subClusterWidth = objectWidth*0.5f;
    CIDER_GUI_CentroClusterTreeMarker subCluster = new CIDER_GUI_CentroClusterTreeMarker(parentDB, parent, subClusterName, subClusterCenter, subClusterWidth, subClusterRadius);
    subCluster.addParentSurface(parentSurface);
    subCluster.setObjectColor(objectColor);
    subCluster.setSelectedColor(selectedColor);
    subCluster.setFilteredColor(filteredColor);
    subCluster.setContent(subClusterContent);
    subCluster.setZoomLevel(zoomLevel+1);
    subCluster.clusterize();
   }else{
     content.add(clusterPivot);
   }
   i++;
  }while(!content.isEmpty() && i<content.size());
  return clusters;
 }
 public void clusterize(){
  if(content.size()>1){
   children = createClusters(clusterRadius);
  }
 }
 public void clusterize(CIDER_Variable var){
  if(content.size()>1){
   children = clusterizeWithVariable(var);
  }
 }
 public ArrayList<CIDER_GUI_CentroClusterTreeMarker> clusterizeWithVariable(CIDER_Variable var){
  ArrayList<String> varOptions = var.getVariableOptions();
  ArrayList<CIDER_GUI_CentroClusterTreeMarker> varClusters = new ArrayList<CIDER_GUI_CentroClusterTreeMarker>();
  int numOptions = var.getNumOptions();
  int initialContentSize = content.size();
  for(int i=0;i<numOptions;i++){
   float u = (float) i / (float) (numOptions);
   String varOption = varOptions.get(i);
   float a = 0.35f*parentSurface.getSurfaceWidth();
   float b = 0.35f*parentSurface.getSurfaceHeight();
   float theta = (-parent.PI/12.0f)*(1-u)+ u*(parent.TWO_PI+parent.PI/12.0f);
   float x = getPosition().x + a*parent.cos(theta);
   float y = getPosition().y + b*parent.sin(theta);
   PVector subClusterPos = new PVector(x,y);
   ArrayList<CIDER_GUI_CentroMarker> subClusterContent = getContentWithProperty(varOption);
   if(!subClusterContent.isEmpty()){
    float v = (float) subClusterContent.size() / (float) initialContentSize;
    float subClusterRadius = (1-v)*(clusterRadius*0.15f)+v*(clusterRadius*0.8f);
    float subClusterSize = (1-v)*(objectWidth*0.15f)+v*(objectWidth*0.8f);
    CIDER_GUI_CentroClusterTreeMarker subCluster = new CIDER_GUI_CentroClusterTreeMarker(parentDB, parent, varOption, subClusterPos, subClusterRadius, subClusterSize);
    subCluster.addParentSurface(parentSurface);
    subCluster.setObjectColor(objectColor);
    subCluster.setFilteredColor(filteredColor);
    subCluster.setSelectedColor(selectedColor);
    subCluster.setZoomLevel(zoomLevel+1);
    subCluster.setContent(subClusterContent);
    varClusters.add(subCluster);
   }
  }
  return varClusters;
 }
 public void plotConnectionsToEntity(CIDER_GUI_EntityMarker entity){
  if(isLeaf()){
   plotConnection(entity);
  }else{
   for(int i=0;i<children.size();i++){
    children.get(i).plotConnectionsToEntity(entity);
   }
  }
 }
 public void plotConnectionsToEntidades(ArrayList<CIDER_GUI_EntidadMarker> entidades){
  if(!isLeaf()){
   for(int i=0;i<entidades.size();i++){
    for(int j=0;j<children.size();j++){
     if(children.get(j).isIsFilterable()){
      if(children.get(j).isInFilter()){
       parentSurface.getSurface().strokeWeight(1);
       parentSurface.getSurface().stroke(filteredColor[0],filteredColor[1],filteredColor[2]);
       parentSurface.getSurface().textSize(10);
       parentSurface.getSurface().fill(0);
       children.get(j).plotConnectionsToEntity(entidades.get(i));
      }
     }else{
      parentSurface.getSurface().strokeWeight(1);
       parentSurface.getSurface().stroke(objectColor[0]*0.5f,objectColor[1]*0.5f,objectColor[2]*0.5f);
       parentSurface.getSurface().textSize(10);
       parentSurface.getSurface().fill(0);
       children.get(j).plotConnectionsToEntity(entidades.get(i));
     }
    }
   }
  }else{
   for(int i=0;i<entidades.size();i++){
    if(isIsFilterable()){
     parentSurface.getSurface().strokeWeight(1);
     parentSurface.getSurface().stroke(filteredColor[0],filteredColor[1],filteredColor[2]);
     parentSurface.getSurface().textSize(10);
     parentSurface.getSurface().fill(0);
    }else{
     parentSurface.getSurface().strokeWeight(1);
       parentSurface.getSurface().stroke(objectColor[0]*0.5f,objectColor[1]*0.5f,objectColor[2]*0.5f);
       parentSurface.getSurface().textSize(10);
       parentSurface.getSurface().fill(0);
    }
    plotConnectionsToEntity(entidades.get(i));
   }
  }
 }
 public void plotConnectionsAmongContentWithFilter(){
  for(int i=0;i<content.size();i++){
   for(int j=0;j<content.size();j++){
    if(i!=j){
     int connectionType = content.get(i).hasConnection(content.get(j));
     if(connectionType >0){
      System.out.println(connectionType);
      PVector p0 = content.get(i).getPosition();
      PVector p1 = content.get(j).getPosition();
      parentSurface.getSurface().strokeWeight(1);
      if(connectionType == 2){
        parentSurface.getSurface().stroke(255,0,0);
      }else if(connectionType ==1){
        parentSurface.getSurface().stroke(255,255,0);
      }
      if(content.get(i).isInFilter())
       parentSurface.getSurface().line(p0.x,p0.y,p1.x,p1.y);
     }
    }
   }
  }
 }
 public void plotConnectionsAmongContentWithoutFilter(){
  for(int i=0;i<content.size();i++){
   for(int j=0;j<content.size();j++){
    if(i!=j){
     int connectionType = content.get(i).hasConnection(content.get(j));
     if(connectionType >0){
      PVector p0 = content.get(i).getPosition();
      PVector p1 = content.get(j).getPosition();
      parentSurface.getSurface().strokeWeight(1);
      if(connectionType == 2){
        parentSurface.getSurface().stroke(255,0,0);
      }else if(connectionType ==1){
        parentSurface.getSurface().stroke(255,255,0);
      }
      parentSurface.getSurface().line(p0.x,p0.y,p1.x,p1.y);
     }
    }
   }
  }
 }
 public void plotConnectionsAmongContent(){
  if(isFilterable)
   plotConnectionsAmongContentWithFilter();
  else
   plotConnectionsAmongContentWithoutFilter();
 }
 public void plotConnectionsAmongClusters(){
  if(!isLeaf()){
   for(int i=0;i<children.size();i++){
    for(int j=i+1;j<children.size();j++){
     if(i!=j){
      if(isFilterable){
       if(isInFilter){
         parentSurface.getSurface().strokeWeight(3);
         parentSurface.getSurface().stroke(filteredColor[0],filteredColor[1],filteredColor[2]);
         parentSurface.getSurface().textSize(16);
         parentSurface.getSurface().fill(0);
         children.get(i).plotConnectionsToEntity(children.get(j));
       }else{
        parentSurface.getSurface().strokeWeight(3);
        parentSurface.getSurface().stroke(objectColor[0],objectColor[1],objectColor[2]);
        parentSurface.getSurface().textSize(16);
        parentSurface.getSurface().fill(0);
        children.get(i).plotConnectionsToEntity(children.get(j));
       }
      }else{
       parentSurface.getSurface().strokeWeight(3);
       parentSurface.getSurface().stroke(objectColor[0],objectColor[1],objectColor[2]);
       parentSurface.getSurface().textSize(16);
       parentSurface.getSurface().fill(0);
       children.get(i).plotConnectionsToEntity(children.get(j));
      }
     }
    }
   }
  }
 }
 public void plotConnection(CIDER_GUI_EntityMarker entity){
  int numPConnections = numberOfConnections(entity,'p');
  int numSConnections = numberOfConnections(entity,'s');
  String numConnections ="";
  if(numPConnections != 0 || numSConnections != 0){
   if(numPConnections !=0 && numSConnections != 0){
    numConnections = "P:"+numPConnections+", S:"+numSConnections;
   }else if(numPConnections ==0 && numSConnections != 0){
    numConnections = "S:"+numSConnections;
   }else{
    numConnections = "P:"+numPConnections;
   }
   plotConnectionTo(entity,numConnections);
  }
 }
 public void plotConnectionTo(CIDER_GUI_EntityMarker entity, String numConnections){
  PVector p0 = getPosition();
  PVector p1 = entity.getPosition();
  PVector midPoint = PVector.add(p0,p1);
  midPoint.div(2.0f);
  if(parentSurface != null){
   parentSurface.getSurface().line(p0.x,p0.y,p1.x,p1.y);
   parentSurface.getSurface().text(numConnections,midPoint.x+5,midPoint.y-5);
  }
 }
 public int numberOfConnections(CIDER_GUI_EntityMarker entity, char type){
  int numConnections = 0;
  if(entity instanceof CIDER_GUI_EntidadMarker || entity instanceof CIDER_GUI_CentroMarker){
   if(isLeaf()){
    for(int i=0;i<content.size();i++){
     int connectionType = content.get(i).hasConnection(entity);
     if(type == 'p' && connectionType == 2){
      if(isFilterable){
       if(content.get(i).isInFilter()){
        numConnections++;
       }
      }else{
       numConnections++;
      }
     }else if(type == 's' && connectionType == 1){
      if(isFilterable){
       if(content.get(i).isInFilter()){
        numConnections++;
       }
      }else{
       numConnections++;
      }
     }
    }
   }else{
    for(int i=0;i<children.size();i++){
     numConnections += children.get(i).numberOfConnections(entity, type);
    }
   }
  }else if(entity instanceof CIDER_GUI_CentroClusterTreeMarker){
   if(isLeaf()){
    for(int i=0;i<content.size();i++){
     numConnections += ((CIDER_GUI_CentroClusterTreeMarker) entity).numberOfConnections(content.get(i), type);
    }
   }else{
     for(int i=0;i<children.size();i++){
      numConnections += ((CIDER_GUI_CentroClusterTreeMarker) entity).numberOfConnections(children.get(i), type);
     }
   }
  }
  return numConnections;
 }
 public boolean isLeaf(){
  return children.isEmpty();
 }
 public boolean isIsInFilter() {
  return isInFilter;
 }
 public void setIsInFilter(boolean isInFilter) {
  this.isInFilter = isInFilter;
 }
 public boolean belongsToCluster(CIDER_GUI_CentroMarker centro){
  return getPosition().dist(centro.getPosition()) <= clusterRadius;
 }
 protected boolean isFilterable;

 public boolean isIsFilterable() {
  return isFilterable;
 }
 public void setIsFilterable(boolean isFilterable) {
  this.isFilterable = isFilterable;
  if(!isLeaf()){
   for(int i=0;i<children.size();i++){
    children.get(i).setIsFilterable(isFilterable);
   }
  }
 }
 public float getClusterRadius() {
  return clusterRadius;
 }
 public void setClusterRadius(float clusterRadius) {
  this.clusterRadius = clusterRadius;
 }
 public int[] getFilteredColor() {
  return filteredColor;
 }
 public void setFilteredColor(int[] filteredColor) {
  this.filteredColor = filteredColor;
 }
 public ArrayList<CIDER_GUI_CentroMarker> getContent() {
  return content;
 }
 public ArrayList<CIDER_GUI_EntityMarker> getSelectedContent(){
  ArrayList<CIDER_GUI_EntityMarker> selected = new ArrayList<CIDER_GUI_EntityMarker>();
  if(!isLeaf()){
   for(int i=0;i<children.size();i++){
    if(children.get(i).isSelected()){
     selected.add(children.get(i));
    }
   }
  }
  for(int i=0;i<content.size();i++){
   if(content.get(i).isSelected()){
    selected.add(content.get(i));
   }
  }
  return selected;
 }
 public void setContent(ArrayList<CIDER_GUI_CentroMarker> cont) {
  int gridSize = parent.round(parent.sqrt(cont.size()))+1;
  int numContent = 0;
  for(int i=0;i<gridSize;i++){
   for(int j=0;j<gridSize;j++){
    float u = (float) i / (float) (gridSize-1);
    float v = (float) j / (float) (gridSize-1);
    float x = (1-u)*(-clusterRadius*0.3f)+u*(clusterRadius*0.3f);
    float y = (1-v)*(-clusterRadius*0.3f)+v*(clusterRadius*0.3f);
    if(numContent<cont.size()){
     PVector newPos = new PVector(getPosition().x+x,getPosition().y+y);
     CIDER_GUI_CentroMarker centroInCluster = new CIDER_GUI_CentroMarker(cont.get(numContent),newPos);
     centroInCluster.setObjectColor(objectColor);
     centroInCluster.setFilteredColor(filteredColor);
     centroInCluster.setSelectedColor(selectedColor);
     centroInCluster.addParentSurface(parentSurface);
     content.add(centroInCluster);
     numContent++;
    }
   }
  }
 }
 public void addToContent(CIDER_GUI_CentroMarker centro){
  if(isLeaf()){
   content.add(centro);
  }else{
   for(int i=0;i<children.size();i++){
    if(children.get(i).belongsToCluster(centro)){
     children.get(i).addToContent(centro);
    }
   }
  }
 }
 public void resetFilter(){
  isInFilter = false;
  for(int i=0;i<content.size();i++){
   content.get(i).setIsFilterableActivated(true);
   content.get(i).setIsInFilter(false);
  }
  for(int i=0;i<children.size();i++){
   children.get(i).setIsInFilter(false);
   children.get(i).resetFilter();
  }
 }
 public void applyFilter(String filter){
  for(int i=0;i<content.size();i++){
   content.get(i).applyFilter(filter);
  }
  if(!isLeaf()){
   for(int i=0;i<children.size();i++){
    children.get(i).applyFilter(filter);
   }
  }
  isInFilter = isInFilter();
 }
 public boolean isInFilter(){
  boolean isIn = false;
  for(int i=0;i<content.size();i++){
   isIn = isIn || content.get(i).isInFilter();
  }
  if(!isLeaf()){
   for(int i=0;i<children.size();i++){
    isIn = isIn || children.get(i).isInFilter();
   }
  }
  return isIn;
 }
 public int size(){
  if(isFilterable)
   return sizeWithFilter();
  else
   return sizeWithoutFilter();
 }
 public int sizeWithoutFilter(){
  int size = content.size();
  if(isLeaf()){
   return size;
  }else{
   for(int i=0;i<children.size();i++){
    size += children.get(i).sizeWithoutFilter();
   }
   return size;
  }
 }
 public int sizeWithFilter(){
  int size = 0;
  for(int i=0;i<content.size();i++){
    if(content.get(i).isInFilter()){
     size++;
    }
   }
  if(isLeaf()){
   return size;
  }else{
   for(int i=0;i<children.size();i++){
    size += children.get(i).sizeWithFilter();
   }
   return size;
  }
 }
 @Override
 public void drawName() {
  if(parentSurface != null){
   parentSurface.getSurface().textSize(12);
   parentSurface.getSurface().text(getObjectName(),getPosition().x-parent.textWidth(getObjectName())/2.0f,getPosition().y+objectHeight*0.55f);
  }else{
   parent.textSize(12);
   parent.text(getObjectName(),getPosition().x-parent.textWidth(getObjectName())/2.0f,getPosition().y+objectHeight*0.55f);
  }
 }
 @Override
 public void drawName(PGraphics g) {
   g.text(getObjectName(),getPosition().x-parent.textWidth(getObjectName())/2.0f,getPosition().y+objectHeight*0.55f);
 }
 @Override
 public void drawObject() {
  int size = size();
  if(parentSurface != null){
   parentSurface.getSurface().ellipse(getPosition().x,getPosition().y,objectWidth,objectHeight);
   parentSurface.getSurface().fill(0);
   parentSurface.getSurface().textSize(12);
   parentSurface.getSurface().text(size,getPosition().x,getPosition().y);
  }else{
   parent.ellipse(getPosition().x,getPosition().y,objectWidth,objectHeight);
   parent.fill(0);
   parent.textSize(12);
   parent.text(size,getPosition().x,getPosition().y);
  }
 }
 @Override
 public void drawObject(PGraphics g) {
  g.ellipse(getPosition().x,getPosition().y,objectWidth,objectHeight);
 }
 @Override
 public void drawOnParentSurface() {
  PVector mousePos = parentSurface.mouseToSurfaceCoordinates();
  parentSurface.getSurface().noStroke();
  if(isHover(mousePos.x,mousePos.y,objectWidth*0.5f)>0){
   if(parent.mousePressed){
    toggleSelection();
   }
   if(isFilterable){
    parentSurface.getSurface().fill(255-filteredColor[0]);
   }else{
    parentSurface.getSurface().fill(255-objectColor[0]);
   }
   drawName();
  }
  if(isFilterable){
   if(isInFilter){
    parentSurface.getSurface().fill(filteredColor[0],filteredColor[1],filteredColor[2]);
   }else{
    if(isSelected)
     parentSurface.getSurface().fill(selectedColor[0],selectedColor[1],selectedColor[2]);
    else
     parentSurface.getSurface().fill(objectColor[0],objectColor[1],objectColor[2]);
   }
  }else{
   if(isSelected){
    parentSurface.getSurface().fill(selectedColor[0],selectedColor[1],selectedColor[2]);
   }else
    parentSurface.getSurface().fill(objectColor[0],objectColor[1],objectColor[2]);
  }
  drawObject();
  if(isFilterable){
    parentSurface.getSurface().fill(255-filteredColor[0]);
   }else{
    parentSurface.getSurface().fill(255-objectColor[0]);
   }
   drawName();
 }
 @Override
 public void drawOnParent() {
  super.drawOnParent();
  if(isHover(parent.mouseX,parent.mouseY,objectWidth)>0){
   toggleSelection();
   if(isFilterable){
    parent.fill(255-filteredColor[0]);
   }else{
    parent.fill(255-objectColor[0]);
   }
   drawName();
  }
  if(isFilterable){
   if(isInFilter)
    parent.fill(filteredColor[0],filteredColor[1],filteredColor[2]);
   else
    parent.fill(objectColor[0],objectColor[1],objectColor[2]);
  }else{
   parent.fill(objectColor[0],objectColor[1],objectColor[2]);
  }
  drawObject();
 }
 public void drawConnections(){
  if(zoomLevel<desiredZoom){
   
  }else if(zoomLevel == desiredZoom){
   plotConnectionsAmongClusters();
  }
 }
 public void plotClusterShadow(){
  parentSurface.getSurface().stroke(0);
  parentSurface.getSurface().noFill();
  parentSurface.getSurface().ellipse(getPosition().x,getPosition().y,clusterRadius,clusterRadius);
  parentSurface.getSurface().fill(0);
  parentSurface.getSurface().text(getObjectName(),getPosition().x-parent.textWidth(getObjectName())/2.0f,getPosition().y+clusterRadius*0.51f);
 }
 @Override
 public void draw() {
  if(zoomLevel<=desiredZoom){
   if(isLeaf()){
    plotClusterShadow();
   }
   for(int i=0;i<content.size();i++){
    content.get(i).draw();
   }
   if(!isLeaf()){
    for(int i=0;i<children.size();i++){
    children.get(i).draw();
    }
   }
  }else{
    if(parentSurface != null){ 
     drawOnParentSurface();
    }else{
     drawOnParent();
    }
  }
 }
}
