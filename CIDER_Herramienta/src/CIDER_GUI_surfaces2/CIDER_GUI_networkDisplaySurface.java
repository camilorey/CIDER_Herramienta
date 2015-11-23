/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_surfaces2;

import CIDER_DB.CIDER_DB;
import CIDER_DB.CIDER_Variable;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_CentroMarker;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_ClusterTreeMarker;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_EntidadMarker;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_EntityMarker;
import CIDER_GUI_textComponents.CIDER_GUI_variableFilter;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_networkDisplaySurface extends CIDER_GUI_entityDisplaySurface{
 protected int[] clusterColor;
 protected int[] entidadColor;
 protected int[] inFilterColor;
 protected int[] aliadoPrincipalColor;
 protected int[] aliadoSecundarioColor;
 public CIDER_GUI_networkDisplaySurface(PApplet parent, PVector position, float objectWidth, float graphWidth) {
  super(parent, position, objectWidth, graphWidth);
 }
 public CIDER_GUI_networkDisplaySurface(PApplet parent, PVector position, float surfaceWidth, float surfaceHeight, float graphWidth, float graphHeight) {
  super(parent, position, surfaceWidth, surfaceHeight, graphWidth, graphHeight);
 }
 public CIDER_GUI_networkDisplaySurface(CIDER_DB parentDB, PVector position, float objectWidth, float graphWidth) {
  super(parentDB, position, objectWidth, graphWidth);
 }
 public CIDER_GUI_networkDisplaySurface(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth, float objectHeight, float graphWidth, float graphHeight) {
  super(parentDB, parent, position, objectWidth, objectHeight, graphWidth, graphHeight);
 }
 @Override
 public int[] getClusterColor(){
  return clusterColor;
 }
 @Override
 public void setClusterColor(int[] clusterColor){
  this.clusterColor = clusterColor;
 }
 public int[] getEntidadColor() {
  return entidadColor;
 }
 public void setEntidadColor(int[] entidadColor) {
  this.entidadColor = entidadColor;
 }
 public int[] getInFilterColor() {
  return network.getFilteredColor();
 }
 public void setInFilterColor(int[] inFilterColor) {
  network.setFilteredColor(inFilterColor);
 }
 public int[] getAliadoPrincipalColor() {
  return aliadoPrincipalColor;
 }
 public void setAliadoPrincipalColor(int[] aliadoPrincipalColor) {
  this.aliadoPrincipalColor = aliadoPrincipalColor;
 }
 public int[] getAliadoSecundarioColor() {
  return aliadoSecundarioColor;
 }
 public void setAliadoSecundarioColor(int[] aliadoSecundarioColor) {
  this.aliadoSecundarioColor = aliadoSecundarioColor;
 }
 @Override
 public void applyFilterToContent() {
  String filter = createFilterString();
  if(!filter.equals("")){
   String[] filterParts = parseFilterString(filter);
   if(!filterParts[1].equals("")){
    for(int i=0;i<content.size();i++){
     if(content.get(i) instanceof CIDER_GUI_CentroMarker){
      CIDER_GUI_CentroMarker centro = (CIDER_GUI_CentroMarker) content.get(i);
      centro.setIsFilterableActivated(true);
      centro.setIsInFilter(false);
      centro.applyFilter(filterParts[1]);
      centro.setFilteredColor(network.getFilteredColor());
     }
    }
    network.resetFilter();
    network.applyFilter(filterParts[1]);
   }
  }else{
    network.resetFilter();
    for(int i=0;i<content.size();i++){
     if(content.get(i) instanceof CIDER_GUI_CentroMarker){
      CIDER_GUI_CentroMarker centro = (CIDER_GUI_CentroMarker) content.get(i);
      centro.setIsFilterableActivated(true);
      centro.setIsInFilter(false);
     }
    }
  }
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
 public String getClusteringFilter(){
  String clusteringFilter = "";
  for(int i=0;i<filters.size();i++){
   if(filters.get(i) instanceof CIDER_GUI_variableFilter){
    clusteringFilter = ((CIDER_GUI_variableFilter) filters.get(i)).getContent();
   }
  }
  return clusteringFilter;
 }
 public void setFilteredContent2(){
  filteredContent = network.getFilteredContent2();
  for(int i=0;i<filteredContent.size();i++){
   if(filteredContent.get(i) instanceof CIDER_GUI_CentroMarker){
    ((CIDER_GUI_CentroMarker) filteredContent.get(i)).setFilteredColor(inFilterColor);
   }else{
    filteredContent.get(i).setObjectColor(inFilterColor);
   }
  }
 }
 public void setFilteredContent(){
  filteredContent = new ArrayList<CIDER_GUI_EntityMarker>();
  for(int i=0;i<content.size();i++){
   if(content.get(i) instanceof CIDER_GUI_CentroMarker){
    CIDER_GUI_CentroMarker contAsCentro = (CIDER_GUI_CentroMarker) content.get(i);
    if(contAsCentro.isInFilter()){
     contAsCentro.setFilteredColor(inFilterColor);
     filteredContent.add(contAsCentro);
    }else{
     if(content.get(i) instanceof CIDER_GUI_EntidadMarker){
      ((CIDER_GUI_EntidadMarker) content.get(i)).setObjectColor(entidadColor);
     }
     if(content.get(i) instanceof CIDER_GUI_CentroMarker){
      ((CIDER_GUI_CentroMarker) content.get(i)).setObjectColor(network.getObjectColor());
     }
    }
   }
  }
  ArrayList<CIDER_GUI_EntityMarker> filteredInTree = network.getFilteredContent();
  for(int i=0;i<filteredInTree.size();i++){
   filteredContent.add(filteredInTree.get(i));
  }
 }
 public void resetColors(){
  network.setObjectColor(clusterColor);
  for(int i=0;i<content.size();i++){
   if(content.get(i) instanceof CIDER_GUI_CentroMarker){
    ((CIDER_GUI_CentroMarker) content.get(i)).setObjectColor(network.getObjectColor());
   }
   if(content.get(i) instanceof CIDER_GUI_EntidadMarker){
    ((CIDER_GUI_EntidadMarker) content.get(i)).setObjectColor(entidadColor);
   }
  }
 }
 @Override
 public void setSelectedContent() {
  selectedContent = new ArrayList<CIDER_GUI_EntityMarker>();
  for(int i=0;i<content.size();i++){
   if(content.get(i) instanceof CIDER_GUI_EntityMarker){
    CIDER_GUI_EntityMarker selected = (CIDER_GUI_EntityMarker) content.get(i); 
    if(selected.isSelected()){
     if(selected instanceof CIDER_GUI_EntidadMarker){
      selected.setObjectColor(new int[]{255,255,255});
      selectedContent.add(selected);
     }else if(selected instanceof CIDER_GUI_CentroMarker){
      CIDER_GUI_CentroMarker asCentro = (CIDER_GUI_CentroMarker) selected;
      if(asCentro.isInFilter()){
       selectedContent.add(selected);
      }
     }
    }
   }
  }
  ArrayList<CIDER_GUI_EntityMarker> selectedFromTree = network.getSelected();
  for(int i=0;i<selectedFromTree.size();i++){
   if(selectedFromTree.get(i) instanceof CIDER_GUI_CentroMarker){
     selectedFromTree.get(i).setObjectColor(new int[]{0,255,0});
     selectedContent.add(selectedFromTree.get(i));
   }
  }
 } 
 public void drawConnectionsBetweenEntities(ArrayList<CIDER_GUI_EntityMarker> list){
  for(int i=0;i<list.size();i++){
   for(int j=0;j<list.size();j++){
    if(i!=j){
     CIDER_GUI_EntityMarker ent1 = list.get(i);
     CIDER_GUI_EntityMarker ent2 = list.get(j);
     int connectionType = ent1.hasConnection(ent2);
     if(connectionType == 1){
      surface.stroke(255,0,255);
      surface.strokeWeight(3);
      ent1.drawConnection(ent2);
      ent2.setObjectColor(new int[]{255,0,255});
      //ent2.drawName();
     }else if(connectionType == 2){
      surface.stroke(0,255,255);
      ent1.drawConnection(ent2);
      surface.strokeWeight(3);
      ent2.setObjectColor(new int[]{0,255,255});
      //ent2.drawName();
     }
    }
   }
  }
 }
 public void drawConnectionsAmongEntities(ArrayList<CIDER_GUI_EntityMarker> list){
  for(int i=0;i<list.size();i++){
   CIDER_GUI_EntityMarker ent = list.get(i);
   for(int j=0;j<content.size();j++){
    CIDER_GUI_EntityMarker otherEnt = (CIDER_GUI_EntityMarker) content.get(j);
    int connectionType = ent.hasConnection(otherEnt);
    if(connectionType == 1){
      surface.stroke(aliadoPrincipalColor[0],aliadoPrincipalColor[1],aliadoPrincipalColor[2]);
      surface.strokeWeight(1);
      ent.drawConnection(otherEnt);
      otherEnt.setObjectColor(aliadoPrincipalColor);
      //otherEnt.drawName();
     }else if(connectionType == 2){
      surface.stroke(aliadoSecundarioColor[0],aliadoSecundarioColor[1],aliadoSecundarioColor[2]);
      surface.strokeWeight(1);
      ent.drawConnection(otherEnt);
      otherEnt.setObjectColor(aliadoSecundarioColor);
      //otherEnt.drawName();
     }
   }
  }
 }
 public void drawConnectionsBetweenFilteredContent(){
  drawConnectionsBetweenEntities(filteredContent);
 }
 public void drawConnectionsBetweenSelectedContent(){
  drawConnectionsBetweenEntities(selectedContent);
 }
 public void drawConnectionsAmongSelectedContent(){
  drawConnectionsAmongEntities(selectedContent);
 }
 public void drawConnectionsAmongFilteredContent(){
  drawConnectionsAmongEntities(filteredContent);
 }
 @Override
 public void update() {
  resetColors();
  setFilteredContent();
  setSelectedContent();
  surface.beginDraw();
   surface.background(backgroundColor[0],backgroundColor[1],backgroundColor[2]);
   drawConnectionsBetweenFilteredContent();
   drawConnectionsAmongFilteredContent();
   drawContent();
   drawFilters();
  surface.endDraw();
 }
}
