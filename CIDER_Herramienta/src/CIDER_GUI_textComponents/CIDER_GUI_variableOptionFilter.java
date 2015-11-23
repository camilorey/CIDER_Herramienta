/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_textComponents;

import processing.core.PApplet;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_variableOptionFilter extends CIDER_GUI_FilterComponent{
 public CIDER_GUI_variableOptionFilter(PApplet parent, String filterName) {
  super(new int[]{255,255,255},new int[]{255,0,0}, parent);
  content = filterName;
  objectWidth = 100;
  objectHeight = 20;
 }
 public CIDER_GUI_variableOptionFilter(PApplet parent, String filterName, PVector position) {
  super(new int[]{255,255,255},new int[]{255,0,0}, parent, position);
  content = filterName;
  objectWidth = 100;
  objectHeight = 20;
 }
}
