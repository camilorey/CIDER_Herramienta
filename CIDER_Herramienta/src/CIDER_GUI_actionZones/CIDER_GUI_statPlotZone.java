/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_actionZones;

import CIDER_DB.CIDER_DB;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_Object;
import CIDER_GUI_surfaces2.CIDER_GUI_StatPlotDisplaySurface;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_statPlotZone extends CIDER_GUI_ActionZone{
 CIDER_GUI_StatPlotDisplaySurface resultingPlot;
 public CIDER_GUI_statPlotZone(CIDER_DB parentDB, PApplet parent) {
  super(parentDB, parent);
  resultingPlot = null;
 }

 public CIDER_GUI_statPlotZone(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth, PImage backgroundImage) {
  super(parentDB, parent, position, objectWidth, backgroundImage);
  resultingPlot = null;
 }
 public CIDER_GUI_StatPlotDisplaySurface getResultingPlot() {
  return resultingPlot;
 }
 @Override
 public boolean isCollision(CIDER_GUI_Object object) {
  return object.getPosition().dist(getPosition())<objectWidth/3.0f;
 }
}
