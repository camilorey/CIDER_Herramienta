/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_actionZones;

import CIDER_DB.CIDER_DB;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_Object;
import CIDER_GUI_textComponents.CIDER_GUI_variableFilter;
import CIDER_GUI_textComponents.CIDER_GUI_variableOptionFilter;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_variableSplitActionZone extends CIDER_GUI_ActionZone{
 protected ArrayList<CIDER_GUI_variableOptionFilter> availableFilters;
 public void setAvailableFilters(ArrayList<CIDER_GUI_variableOptionFilter> availableFilters) {
  this.availableFilters = availableFilters;
 }
 public CIDER_GUI_variableSplitActionZone(CIDER_DB parentDB, PApplet parent) {
  super(parentDB, parent);
  availableFilters = new ArrayList<CIDER_GUI_variableOptionFilter>();
 }
 public CIDER_GUI_variableSplitActionZone(CIDER_DB parentDB, PApplet parent, PVector position, float objectWidth, PImage backgroundImage) {
  super(parentDB, parent, position, objectWidth, backgroundImage);
  availableFilters = new ArrayList<CIDER_GUI_variableOptionFilter>();
 }
 public ArrayList<CIDER_GUI_variableOptionFilter> getAvailableFilters() {
  return availableFilters;
 }
 @Override
 public void onCollision(CIDER_GUI_Object object) {
  availableFilters = new ArrayList<CIDER_GUI_variableOptionFilter>();
  if(object instanceof CIDER_GUI_variableFilter){
   availableFilters = ((CIDER_GUI_variableFilter) object).toVariableOptionFilters(objectWidth*0.5f, position);
  }
 }
}
