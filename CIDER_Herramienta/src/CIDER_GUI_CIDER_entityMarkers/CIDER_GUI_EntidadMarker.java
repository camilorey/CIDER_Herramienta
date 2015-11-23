/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_CIDER_entityMarkers;

import CIDER_DB.CIDER_DB;
import CIDER_DB.CIDER_Entidad;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_EntidadMarker extends CIDER_GUI_EntityMarker{
 public CIDER_GUI_EntidadMarker(CIDER_DB parentDB,PApplet parent) {
  super(parentDB, parent);
 }
 public CIDER_GUI_EntidadMarker(CIDER_DB parentDB,PApplet parent, PVector position, float objectWidth) {
  super(parentDB, parent, position, objectWidth);
 }
 public CIDER_GUI_EntidadMarker(CIDER_DB parentDB, PApplet parent, String name, PVector position, float objectWidth) {
  super(parentDB, parent, name, position, objectWidth);
 }

 @Override
 public int hasConnection(CIDER_GUI_EntityMarker ent) {
  if(ent instanceof CIDER_GUI_CentroMarker){
   return ((CIDER_GUI_CentroMarker) ent).hasConnection(this);
  }else if(ent instanceof CIDER_GUI_ClusterTreeMarker){
   return ((CIDER_GUI_ClusterTreeMarker) ent).hasConnection(this);
  }else{
   return -1;
  }
 }
 @Override
 public void drawObject() {
  if(parentSurface != null){
   parentSurface.getSurface().noStroke();
   parentSurface.getSurface().ellipse(position.x, position.y, objectWidth, objectWidth);
  }else{
   parent.noStroke();
   parent.ellipse(position.x, position.y, objectWidth, objectWidth);
  }
 }
 @Override
 public void drawObject(PGraphics g) {
  g.ellipse(position.x, position.y, objectWidth, objectWidth);
 }
}
