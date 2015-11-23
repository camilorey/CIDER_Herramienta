/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_actionZones;

import CIDER_DB.CIDER_DB;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_Object;
import CIDER_GUI_surfaces2.CIDER_GUI_BarraSimplePlotSurface;
import CIDER_GUI_surfaces2.CIDER_GUI_singleVariableStatPlotDisplaySurface;
import CIDER_GUI_textComponents.CIDER_GUI_variableFilter;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_BarraSimpleActionZone extends CIDER_GUI_singleVariablePlotZone{

 public CIDER_GUI_BarraSimpleActionZone(CIDER_DB parentDB, PApplet parent) {
  super(parentDB, parent);
 }

 public CIDER_GUI_BarraSimpleActionZone(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth, PImage backgroundImage) {
  super(parentDB, parent, position, objectWidth, backgroundImage);
 }
 @Override
 public void onCollision(CIDER_GUI_Object object) {
  if(object instanceof CIDER_GUI_variableFilter){
   float baseGraphWidth = parent.width*0.25f;
   float baseGraphHeight = parent.height*0.25f;
   float surfaceWidth = parent.width;
   float surfaceHeight = parent.height;
   resultingPlot = new CIDER_GUI_BarraSimplePlotSurface(parentDB, parent, new PVector(position.x,position.y), baseGraphWidth, baseGraphHeight, surfaceWidth, surfaceHeight);
   resultingPlot.setBackgroundColor(new int[]{255,255,255});
   resultingPlot.addFilter((CIDER_GUI_variableFilter) object);
  }
 }
}
