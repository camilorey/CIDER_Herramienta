/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_Behaviors;

/**
 *
 * @author laptop
 */
public interface Resizable {
 public void onResize(float dx, float dy);
 public void resize(float dx, float dy);
 public int isOnCorner(float x, float y);
}
