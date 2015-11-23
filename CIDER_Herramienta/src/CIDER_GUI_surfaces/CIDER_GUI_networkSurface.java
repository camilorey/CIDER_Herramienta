/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_surfaces;

import CIDER_DB.CIDER_Entidad;
import CIDER_DB.CIDER_Centro;
import CIDER_DB.CIDER_DB;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_ClusterTreeMarker;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_CentroMarker;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_EntidadMarker;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_EntityMarker;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_networkSurface extends CIDER_GUI_markerDisplaySurface{
 protected CIDER_GUI_ClusterTreeMarker network;
 protected int currentZoom;
 protected int desiredZoom;
 protected int maxZoom;
 
 public CIDER_GUI_networkSurface(PApplet parent, PVector position, float objectWidth, float objectHeight) {
  super(parent, position, objectWidth, objectHeight);
  network = null;
  //network.addParentSurface(this);
  network.setObjectColor(new int[]{0,255,0});
  currentZoom = 0;
  desiredZoom = currentZoom;
  maxZoom = -1;
 }
 public CIDER_GUI_networkSurface(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth, float objectHeight) {
  super(parentDB, parent, position, objectWidth, objectHeight);
  network = new CIDER_GUI_ClusterTreeMarker(objectWidth*0.2f, parentDB,parent, "objectNetwork", new PVector(surface.width*0.5f,surface.height*0.5f), objectWidth*0.2f);
  //network.addParentSurface(this);
  network.setObjectColor(new int[]{0,255,0});
  currentZoom = 0;
  desiredZoom = currentZoom;
  maxZoom = -1;
 }
 public void addEntidad(CIDER_Entidad entidad){
  float u = (float) parent.random(0.05f,0.95f);
  float v = (float) parent.random(0.05f,0.95f);
  PVector entidadPos = new PVector(surface.width*u,surface.height*v);
  CIDER_GUI_EntidadMarker entidadMarker = new CIDER_GUI_EntidadMarker(parentDB, parent, entidad.getEntityName(),entidadPos, 10.0f);
  //entidadMarker.addParentSurface(this);
  entidadMarker.setObjectColor(new int[]{150,150,150});
  addToContent(entidadMarker);
 }
 public void addCentroToNetwork(CIDER_Centro centro){
  float u = (float) parent.random(0.05f,0.95f);
   float v = (float) parent.random(0.05f,0.95f);
   PVector centroPos = new PVector(surface.width*u,surface.height*v);
   CIDER_GUI_CentroMarker centroMarker = new CIDER_GUI_CentroMarker(parentDB, parent, centro.getEntityName(),centroPos, 10.f);
   //centroMarker.addParentSurface(this);
   centroMarker.setObjectColor(new int[]{255,0,0});
   network.addMarkerObject(centroMarker);
 }
 @Override
 public ArrayList<CIDER_GUI_EntityMarker> getSelected() {
  ArrayList<CIDER_GUI_EntityMarker> selected =  super.getSelected();
  ArrayList<CIDER_GUI_EntityMarker> selectedInNetwork =  network.getSelected();
  for(int i=0;i<selectedInNetwork.size();i++){
   selected.add(selectedInNetwork.get(i));
  }
  return selected;
 }
 public void setCluster(){
  network.clusterize();
  maxZoom = network.getMaxZoomLevel();
  System.out.println("network depth: "+maxZoom);
  System.out.println("network size: "+network.size());
  System.out.println("current zoom:" +network.zoomLevel);
  System.out.println("desired zoom: "+network.desiredZoom); 
 }
 public CIDER_GUI_ClusterTreeMarker getCluster(){
  return network;
 }
 public void setZoomLevel(int zoomLevel){
  if(zoomLevel<maxZoom){
   currentZoom = zoomLevel;
   network.setDesiredZoom(zoomLevel);
  }
 }
 @Override
 public void drawContent() {
  surface.strokeWeight(2);
  drawConnections();
  surface.strokeWeight(1);
  for(int i=0;i<content.size();i++){
   content.get(i).draw();
  }
  network.draw();
 }
 public void drawConnections(){
  ArrayList<CIDER_GUI_EntityMarker> selected = getSelected();
  if(!selected.isEmpty()){
   for(int i=0;i<selected.size();i++){
    for(int j=0;j<selected.size();j++){
     if(i!=j){
      if(selected.get(i).hasConnection(selected.get(j))== 1){
       surface.stroke(255,255,0);
       selected.get(i).drawConnection(selected.get(j));
      }else if(selected.get(i).hasConnection(selected.get(j))== 2){
       surface.stroke(255,0,0);
       selected.get(i).drawConnection(selected.get(j));
      }
     }
    }
   }
  }
 }
}
