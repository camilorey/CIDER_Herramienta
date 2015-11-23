/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_DB;

/**
 *
 * @author laptop
 */
public class CIDER_DB_Entity {
 
 protected String entityName; 
 public CIDER_DB_Entity() {
 }
 public CIDER_DB_Entity(String entityName) {
  this.entityName = entityName;
 }
 public String getEntityName() {
  return entityName;
 }
 public void setEntityName(String entityName) {
  this.entityName = entityName;
 }
 boolean isEqual(CIDER_DB_Entity entity){
  return entityName.equals(entity.getEntityName());
 }
}
