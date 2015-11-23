/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cider_herramienta;

import CIDER_DB.CIDER_DB;
import CIDER_GUI.CIDER_GUI;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.Google;
import java.util.HashMap;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

/**
 *
 * @author laptop
 */
public class CIDER_Herramienta extends PApplet{
 UnfoldingMap baseMap;
 HashMap<String, PImage> actionZoneImages;
 HashMap<String, PImage> buttonImages;
 PFont myFont;
 CIDER_DB dataBase;
 CIDER_GUI ciderGUI;
 float initialRadius;
 int buttonPressed;
 int currentZoom = 0;
 int maxZoom = 5;
 boolean showMap = false;
 boolean showNetwork = false;
 boolean showStat = false;
 float mapCornerX;
 float mapCornerY;
 float mapWidth;
 float mapHeight;
 boolean onOption;
 public void setup(){
  size(900,600);
  myFont = loadFont("Mangal-18.vlw");
  textFont(myFont,18);
  createActionZoneImages();
  createButtonImages();
  dataBase = new CIDER_DB(dataPath(""), "baseDeDatos_emailInstitucional.xlsx");
  ciderGUI = new CIDER_GUI(this, dataBase,myFont,actionZoneImages,buttonImages);
  setMap();
  buttonPressed = -1;
  onOption = false;
 }
 public void keyPressed(){
  if(keyCode == UP){
   if(currentZoom < maxZoom){
    System.out.println("zooming in");
    currentZoom++;
   }
  }
  if(keyCode == DOWN){
   if(currentZoom >=0){
    System.out.println("zooming out");
    currentZoom--;
   }
  }
  if(key == ' '){
   ciderGUI.resetButtons();
  }
 }
 public void mousePressed(){
  if(buttonPressed == 0){
   ciderGUI.mousePressedOnMapLayer();
  }
 }
 public void mouseDragged(){
  if(buttonPressed == 0){
   ciderGUI.mouseDraggedOnMapLayer();
  }
 }
 public void drawOptions(int buttonOption){
  switch(buttonOption){
   case 0: 
    ciderGUI.setZoom(currentZoom);
    ciderGUI.drawMapLayer();
    ciderGUI.drawVariableLayer(); 
    break;
   case 1:
    ciderGUI.drawVariableLayer();
    ciderGUI.drawStatPlotLayer();
    break;
   case 2:
    ciderGUI.setZoom(currentZoom);
    ciderGUI.drawVariableLayer(); 
    ciderGUI.drawNetworkLayer();
    break;
  }
 }
 public void draw(){
  background(200);
  buttonPressed = ciderGUI.buttonMenuPressed();
  if(buttonPressed == -1){
   ciderGUI.drawButtonLayer();
   ciderGUI.resetMapLayer();
  }else{
   drawOptions(buttonPressed);
  }
 }
 void setMap(){
  mapCornerX = width*0.01f;
  mapCornerY = height*0.2f;
  mapWidth = width*0.98f;
  mapHeight = height*0.79f;
  baseMap = new UnfoldingMap(this, new Google.GoogleMapProvider());
  baseMap.setBackgroundColor(255);
  baseMap.setZoomRange(1,17);
  resetMap();
 }
 void resetMap(){
  Location bogotaLocation = new Location(4.609866,-74.08205);
  baseMap.zoomAndPanTo(bogotaLocation,7);
 }
 void createActionZoneImages(){
  actionZoneImages = new HashMap<String, PImage>();
  actionZoneImages.put("baseAction", loadImage("actionZoneImage.png"));
  actionZoneImages.put("variableSplit", loadImage("variableSplitActionZone.png"));
  actionZoneImages.put("tortaZone", loadImage("tortaActionZone.png"));
  actionZoneImages.put("barrasSimplesZone", loadImage("barraSimpleActionZone.png"));
  actionZoneImages.put("multiBarrasZone", loadImage("multiBarrasActionZone.png"));
  actionZoneImages.put("comparativoZone", loadImage("comparativoActionZone.png"));
 }
 void createButtonImages(){
  buttonImages = new HashMap<String, PImage>();
  buttonImages.put("ficheroButton", loadImage("ficheroButton.png"));
  buttonImages.put("estadisticosButton", loadImage("estadisticosButton.png"));
  buttonImages.put("mapasButton", loadImage("mapasButton.png"));
  buttonImages.put("plusZoom", loadImage("plusZoom.png"));
  buttonImages.put("minusZoom", loadImage("minusZoom.png"));
  
 }
 /**
  * @param args the command line arguments
  */
 public static void main(String[] args) {
  // TODO code application logic here
  PApplet.main(new String[]{cider_herramienta.CIDER_Herramienta.class.getName()});
 }
}
