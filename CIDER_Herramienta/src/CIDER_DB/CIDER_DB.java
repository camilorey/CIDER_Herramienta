/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_DB;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author laptop
 */
public class CIDER_DB{
 protected CIDER_CentrosHandler centros;
 protected CIDER_VariableHandler variables;
 protected CIDER_ExcelHandler excelHandler;
 public CIDER_DB(){
  centros = null;
  variables = null;
  excelHandler = null;
 }
 public CIDER_DB(String filePath, String fileName){
  excelHandler = new CIDER_ExcelHandler(filePath, fileName);
  excelHandler.createVariableToDBMap();
  variables = excelHandler.createVariableSet();
  centros = excelHandler.createCentrosHandler(variables);
  System.out.println("==========CIDER DATABASE REPORT==========================");
  System.out.println("centros registrados: "+centros.getNumCentros());
  System.out.println("entidades registradas: "+centros.getNumEntidades());
 }
 public CIDER_Variable getVariable(String varName){
  return variables.getVariable(varName);
 }
 public CIDER_DB_Entity get(String entName){
  return centros.get(entName);
 }
 
 public ArrayList<CIDER_DB_Entity> getFilterQueryResult(CIDER_Variable var, String filter){
  return centros.getFilterQueryResult(var, filter);
 }
 public HashMap<String,ArrayList<CIDER_DB_Entity>> getSingleVariableQueryResult(CIDER_Variable var){
  return centros.getSingleVariableQueryResult(var);
 }
 public HashMap<String, HashMap<String, ArrayList<CIDER_DB_Entity>>> getDoubleVariableQueryResult(CIDER_Variable var1, CIDER_Variable var2){
  return centros.getDoubleVariableQueryResult(var1, var2);
 }
 public HashMap<String,Float> makeSingleVariableStatPercentQuery(CIDER_Variable var,ArrayList<String> filters){
  return centros.makeSingleVariableStatPercentQuery(var,filters);
 }
 public HashMap<String, Integer> makeSingleVariableStatNumberQuery(CIDER_Variable var, ArrayList<String> filters){
  return centros.makeSingleVariableStatNumberQuery(var,filters);
 }
 public HashMap<String, HashMap<String,Float>> makeDoubleVariableStatPercentQuery(CIDER_Variable var1, CIDER_Variable var2, ArrayList<String> filters){
  return centros.makeDoubleVariableStatPercentQuery(var1, var2, filters);
 }
 public HashMap<String, HashMap<String,Integer>> makeDoubleVariableStatNumberQuery(CIDER_Variable var1, CIDER_Variable var2, ArrayList<String> filters){
  return centros.makeDoubleVariableStatNumberQuery(var1, var2, filters);
 }
 public void replace(CIDER_DB_Entity entity, CIDER_DB_Entity newEntity){
  centros.replace(entity, newEntity);
 }
 public boolean isIn(String entName){
  return centros.isIn(entName);
 }
 public boolean isIn(CIDER_DB_Entity ent){
  return centros.isIn(ent);
 }
 public int numCentros(){
  return centros.getNumCentros();
 }
 public int numEntidades(){
  return centros.getNumEntidades();
 }
 public int connectionBetweenEntities(String entity1Name, String entity2Name){
  return centros.connectionBetweenEntities(entity1Name, entity2Name);
 }
 public int entitySatisfiesQuery(String entityName, String query){
  return centros.entitySatisfiesQuery(entityName, query);
 }
 
 public ArrayList<String> getCentrosRegistrados(){
  return centros.getCentroNames();
 }
 public ArrayList<String> getEntidadesRegistradas(){
  return centros.getEntidadNames();
 }
 public ArrayList<String> getVariablesRegistradas(){
  return variables.getVariableNames();
 }
 public ArrayList<String> getVariableOptions(String varName){
  return variables.getVariableOptions(varName);
 }
}
