/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_Behaviors;

import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_Object;

/**
 *
 * @author laptop
 */
public interface Collidable {
 
 public boolean isCollision(CIDER_GUI_Object object);
 public void onCollision(CIDER_GUI_Object object);
}
