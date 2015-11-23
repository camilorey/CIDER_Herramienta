/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_surfaces2;

import CIDER_DB.CIDER_Centro;
import CIDER_DB.CIDER_DB;
import CIDER_DB.CIDER_Variable;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_CentroClusterTreeMarker;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_CentroMarker;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_ClusterTreeMapMarker;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_ClusterTreeMarker;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_EntidadMarker;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_EntityMarker;
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
public class CIDER_GUI_networkDisplaySurface2 extends CIDER_GUI_filterableSurface{
 protected int[] centroColor;
 protected int[] centroSelectedColor;
 protected int[] entidadColor;
 protected int[] entidadSelectedColor;
 protected int[] inFilterColor;
 protected int[] baseLineColor;
 protected int[] aliadoPrincipalColor;
 protected int[] aliadoSecundarioColor;
 protected boolean clusterizeByVariable;
 protected boolean isClusterized;
 protected int currentZoom;
 protected int desiredZoom;
 protected int maxZoom;
 ArrayList<CIDER_GUI_EntidadMarker> entidades;
 ArrayList<CIDER_GUI_CentroMarker> centros;
 ArrayList<CIDER_GUI_EntityMarker> selectedContent;
 CIDER_GUI_CentroClusterTreeMarker cluster;
 public CIDER_GUI_networkDisplaySurface2(PApplet parent, PVector position, float objectWidth, float graphWidth) {
  super(parent, position, objectWidth, graphWidth);
  setColors();
  currentZoom = 0;
  desiredZoom = currentZoom;
  maxZoom = -1;
 }
 public CIDER_GUI_networkDisplaySurface2(PApplet parent, PVector position, float surfaceWidth, float surfaceHeight, float graphWidth, float graphHeight) {
  super(parent, position, surfaceWidth, surfaceHeight, graphWidth, graphHeight);
  setColors();
  currentZoom = 0;
  desiredZoom = currentZoom;
  maxZoom = -1;
 }
 public CIDER_GUI_networkDisplaySurface2(CIDER_DB parentDB, PVector position, float objectWidth, float graphWidth) {
  super(parentDB, position, objectWidth, graphWidth);
  setColors();
  currentZoom = 0;
  desiredZoom = -1;
  maxZoom = -1;
 }
 public CIDER_GUI_networkDisplaySurface2(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth, float objectHeight, float graphWidth, float graphHeight) {
  super(parentDB, parent, position, objectWidth, objectHeight, graphWidth, graphHeight);
  setColors();
  currentZoom = 0;
  desiredZoom = currentZoom;
  maxZoom = -1;
 }
 public void setContent(float entidadSize, float centroSize){
  setEntidades(entidadSize);
  setCentros(centroSize);
  selectedContent = new ArrayList<CIDER_GUI_EntityMarker>();
 }
 public void setColors(){
  centroColor = new int[]{255,255,255};
  centroSelectedColor = new int[]{0,255,255};
  entidadColor = new int[]{200,200,200};
  entidadSelectedColor = new int[]{200,200,0};
  inFilterColor = new int[]{255,0,0};
  aliadoPrincipalColor = new int[]{255,0,0};
  aliadoSecundarioColor = new int[]{255,255,0};
  baseLineColor = new int[]{150,150,150};
 }
 public CIDER_GUI_CentroClusterTreeMarker setCluster(String varName){
   CIDER_Variable clusterVariable = parentDB.getVariable(varName);
   int numOptions = clusterVariable.getNumOptions();
   float clusterRadiusFactor= 0.0f;
   float clusterSizeFactor = 0.0f;
   if(numOptions<4){
    clusterRadiusFactor = 0.75f;
    clusterSizeFactor = 0.75f;
   }else if(numOptions >=4 && numOptions <8){
    clusterRadiusFactor = 0.75f;
    clusterSizeFactor = 0.75f;
   }else if(numOptions>=8 && numOptions <=12){
    clusterRadiusFactor = 0.5f;
    clusterSizeFactor = 0.5f;
   }else{
    clusterRadiusFactor = 0.1f;
    clusterSizeFactor = 0.1f;
   }
   PVector clusterPos = new PVector(surface.width*0.5f,surface.height*0.5f);
   float clusterWidth = clusterSizeFactor*surface.width;
   float clusterRadius = clusterRadiusFactor*surface.width;
   CIDER_GUI_CentroClusterTreeMarker newCluster = new CIDER_GUI_CentroClusterTreeMarker(parentDB, parent, varName, clusterPos, clusterWidth);
   newCluster.addParentSurface(this);
   newCluster.setZoomLevel(0);
   newCluster.setClusterRadius(clusterRadius);
   newCluster.setObjectColor(centroColor);
   newCluster.setSelectedColor(centroSelectedColor);
   newCluster.setFilteredColor(inFilterColor);
   newCluster.setContent(centros);
   newCluster.setClusterVarName(varName);
   newCluster.clusterize(clusterVariable);
   newCluster.setDesiredZoom(desiredZoom);
   return newCluster;
 }
 public int getDesiredZoom() {
  return desiredZoom;
 }

 public void setDesiredZoom(int desiredZoom) {
  this.desiredZoom = desiredZoom;
  if(cluster != null){
   cluster.setDesiredZoom(desiredZoom);
  }
 }
 public void setCentros(float centroSize){
  centros = new ArrayList<CIDER_GUI_CentroMarker>();
  ArrayList<String> centroNames = parentDB.getCentrosRegistrados();
  boolean[] chosen = new boolean[centroNames.size()];
  int gridX = parent.round(parent.sqrt(centroNames.size()))+1;
  int gridY = parent.round(parent.sqrt(centroNames.size()))+1;
  int numCentro = 0;
  for(int i=1;i<gridX;i++){
   for(int j=1;j<gridY;j++){
    float u0 = (float) i / (float) (gridX);
    float v0 = (float) j / (float) (gridY);
    float u1 = (float) (i+1) / (float) (gridX);
    float v1 = (float) (j+1) / (float) (gridY);
    float x0 = (1-u0)*(surface.width*0.01f)+u0*(surface.width*0.98f);
    float x1 = (1-u1)*(surface.width*0.01f)+u1*(surface.width*0.98f);
    float y0 = (1-v0)*(surface.height*0.01f)+v0*(surface.height*0.98f);
    float y1 = (1-v1)*(surface.height*0.01f)+v1*(surface.height*0.98f);
    float x = (float) parent.random(x0,x1);
    float y = (float) parent.random(y0,y1);
    if(numCentro<centroNames.size()){
     PVector pos = new PVector(x,y);
     String tamano = ((CIDER_Centro) parentDB.get(centroNames.get(i))).getTamano();
     float markerSize = centroSize;
     if(tamano.equals("Pequeña")){
      markerSize *= 0.8f;
     }else if(tamano.equals("Grande")){
      markerSize *= 1.6f;
     }
     CIDER_GUI_CentroMarker centro = new CIDER_GUI_CentroMarker(parentDB, parent, centroNames.get(numCentro), pos, markerSize);
     centro.setObjectColor(centroColor);
     centro.setSelectedColor(centroSelectedColor);
     centro.setFilteredColor(inFilterColor);
     centro.addParentSurface(this);
     centros.add(centro);
     numCentro++;
    }
   }
  }
 }
 public void plotAliados(CIDER_GUI_EntityMarker entity){
  for(int i=0;i<centros.size();i++){
   int connectionType = centros.get(i).hasConnection(entity);
   if(connectionType == 1){
    surface.stroke(aliadoPrincipalColor[0],aliadoPrincipalColor[1],aliadoPrincipalColor[2]);
    centros.get(i).drawConnection(entity);  
    centros.get(i).drawName();
   }else if(connectionType == 2){
    surface.stroke(aliadoSecundarioColor[0],aliadoSecundarioColor[1],aliadoSecundarioColor[2]);
    centros.get(i).drawConnection(entity);
    centros.get(i).drawName();
   }
  }
  if(entity instanceof CIDER_GUI_CentroMarker){
   for(int i=0;i<entidades.size();i++){
    int connectionType = ((CIDER_GUI_CentroMarker) entity).hasConnection(entidades.get(i));
    if(connectionType == 1){
     surface.stroke(aliadoPrincipalColor[0],aliadoPrincipalColor[1],aliadoPrincipalColor[2]);
     ((CIDER_GUI_CentroMarker) entity).drawConnection(entidades.get(i));    
     entidades.get(i).drawName();
    }else if(connectionType == 2){
     surface.stroke(aliadoSecundarioColor[0],aliadoSecundarioColor[1],aliadoSecundarioColor[2]);
     ((CIDER_GUI_CentroMarker) entity).drawConnection(entidades.get(i));
     entidades.get(i).drawName();
    }
   }
  }
 }
 public void setEntidades(float entidadSize){
  entidades = new ArrayList<CIDER_GUI_EntidadMarker>();
  ArrayList<String> entidadesName = parentDB.getEntidadesRegistradas();
  int gridX = parent.round(parent.sqrt(entidadesName.size()))+1;
  int gridY = parent.round(parent.sqrt(entidadesName.size()))+1;
  int numEntidad = 0;
  for(int i=1;i<gridX;i++){
   for(int j=1;j<gridY;j++){
    float u0 = (float) i / (float) (gridX);
    float v0 = (float) j / (float) (gridY);
    float u1 = (float) (i+1) / (float) (gridX);
    float v1 = (float) (j+1) / (float) (gridY);
    float x0 = (1-u0)*(surface.width*0.01f)+u0*(surface.width*0.98f);
    float x1 = (1-u1)*(surface.width*0.01f)+u1*(surface.width*0.98f);
    float y0 = (1-v0)*(surface.height*0.01f)+v0*(surface.height*0.98f);
    float y1 = (1-v1)*(surface.height*0.01f)+v1*(surface.height*0.98f);
    float x = (float) parent.random(x0,x1);
    float y = (float) parent.random(y0,y1);
    if(numEntidad<entidadesName.size()){ 
     PVector pos = new PVector(x,y);
     CIDER_GUI_EntidadMarker entidad = new CIDER_GUI_EntidadMarker(parentDB, parent, entidadesName.get(numEntidad), pos, entidadSize);
     entidad.setObjectColor(entidadColor);
     entidad.setSelectedColor(entidadSelectedColor);
     entidad.addParentSurface(this);
     entidades.add(entidad);
     numEntidad++;
    }
   }
  }
  System.out.println("entidades dibujadas: "+numEntidad);
 }
 public void setSelectedContent(){
  selectedContent = new ArrayList<CIDER_GUI_EntityMarker>();
  for(int i=0;i<entidades.size();i++){
   if(entidades.get(i).isSelected()){
    selectedContent.add(entidades.get(i));
   }
  }
  for(int i=0;i<centros.size();i++){
   if(centros.get(i).isSelected()){
    selectedContent.add(centros.get(i));
   }
  }
  if(cluster != null){
   ArrayList<CIDER_GUI_EntityMarker> selectedFromCluster = cluster.getSelectedContent();
   if(!selectedFromCluster.isEmpty()){
    for(int i=0;i<selectedFromCluster.size();i++){
     selectedContent.add(selectedFromCluster.get(i));
    }
   }
  }
 }
 public void plotConnectionsOnSelectedContent(){
  for(int i=0;i<selectedContent.size();i++){
   if(selectedContent.get(i) instanceof CIDER_GUI_CentroClusterTreeMarker){
    ((CIDER_GUI_CentroClusterTreeMarker) selectedContent.get(i)).plotConnectionsToEntidades(entidades);
   }else{
    if(cluster == null)
     plotAliados(selectedContent.get(i));
    else{
     if(selectedContent.get(i) instanceof CIDER_GUI_EntidadMarker || selectedContent.get(i) instanceof CIDER_GUI_CentroMarker){
      surface.strokeWeight(3);
      surface.stroke(255,100,100);
      surface.fill(255,100,100);
      cluster.plotConnectionsToEntity(selectedContent.get(i));
     }
    }
   }
  }
  if(cluster == null){
   for(int i=0;i<selectedContent.size();i++){
    plotAliados(selectedContent.get(i));
   }
  }else{
   for(int i=0;i<selectedContent.size();i++){
    cluster.plotConnectionsToEntity(selectedContent.get(i));
   }
  }
 }
 public void resetFilter(){
  for(int i=0;i<centros.size();i++){
   centros.get(i).setIsFilterableActivated(true);
   centros.get(i).setIsInFilter(false);
  }
 }
 public void applyFilterToCentros(String centroFilter){
  resetFilter();
  for(int i=0;i<centros.size();i++){
   centros.get(i).applyFilter(centroFilter);
  }
  if(cluster!=null){
   cluster.setIsFilterable(true);
   cluster.resetFilter();
   cluster.applyFilter(centroFilter);
  }
 }
 @Override
 public void applyFilter(){
  String filterString = createFilterString();
  if(!filterString.equals("")){
   String[] filterParts = parseFilterString(filterString);
   if(!filterParts[0].equals("")){
    if(cluster == null){
     cluster = setCluster(filterParts[0]);
    }else{ 
     if(!cluster.getClusterVarName().equals(filterParts[0])){
      cluster = setCluster(filterParts[0]);
     }
    }
   }else{
     cluster = null;
   }
   if(!filterParts[1].equals("")){
     applyFilterToCentros(filterParts[1]); 
   }else{
     cluster.setIsFilterable(false);
     cluster.resetFilter();
   }
  }else{
   cluster = null;
  }
 }
 @Override
 public void drawContent() {
  for(int i=0;i<entidades.size();i++){
   entidades.get(i).draw();
  }
  if(cluster != null){
   cluster.drawConnections();
   cluster.draw();
  }else{
   for(int i=0;i<centros.size();i++){
    centros.get(i).draw();
   }
  }
 }
 @Override
 public void update() {
  setSelectedContent();
  applyFilter();
  surface.beginDraw();
   surface.background(backgroundColor[0],backgroundColor[1],backgroundColor[2]);
   drawContent();
   drawFilters();
   plotConnectionsOnSelectedContent();
  surface.endDraw();
 }
 @Override
 public void onCollision(CIDER_GUI_Object object) {
  if(object instanceof CIDER_GUI_variableFilter){
   boolean containsVarFilter = false;
   for(int i=0;i<filters.size();i++){
    containsVarFilter = containsVarFilter || filters.get(i) instanceof CIDER_GUI_variableFilter;
   }
   if(!containsVarFilter){
    if(isCollision(object)){
     addFilter((CIDER_GUI_FilterComponent) object);
    }
   }
  }
  if(object instanceof CIDER_GUI_variableOptionFilter){
   if(isCollision(object)){
    addFilter((CIDER_GUI_FilterComponent) object);
   }
  }
 }
}
