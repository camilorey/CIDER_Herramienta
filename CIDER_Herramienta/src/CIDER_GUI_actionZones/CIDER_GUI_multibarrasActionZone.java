/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_actionZones;

import CIDER_DB.CIDER_DB;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_Object;
import CIDER_GUI_surfaces2.CIDER_GUI_MultiBarrasPlotSurface;
import CIDER_GUI_surfaces2.CIDER_GUI_twoVariableStatPlotDisplaySurface;
import CIDER_GUI_textComponents.CIDER_GUI_variableFilter;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_multibarrasActionZone extends CIDER_GUI_twoVariablePlotZone{

 public CIDER_GUI_multibarrasActionZone(CIDER_DB parentDB, PApplet parent) {
  super(parentDB, parent);
 }

 public CIDER_GUI_multibarrasActionZone(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth, PImage backgroundImage) {
  super(parentDB, parent, position, objectWidth, backgroundImage);
 }
 @Override
 public void createStatPlot() {
  resultingPlot = new CIDER_GUI_MultiBarrasPlotSurface(parentDB, parent, new PVector(position.x,position.y), parent.width*0.25f, parent.height*0.25f, parent.width, parent.height);
  resultingPlot.setBackgroundColor(new int[]{255,255,255});
  resultingPlot.addFilter(vars.get(0));
  resultingPlot.addFilter(vars.get(1));
  resultingPlot.applyFilter();
 }
}
