/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_DB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_Centro extends CIDER_DB_Entity{
 
 protected HashMap<String,String> singleVariables;
 protected HashMap<String,ArrayList<String>> multiVariables;
 protected ArrayList<CIDER_DB_Entity> aliadosPrincipales;
 protected ArrayList<CIDER_DB_Entity> aliadosSecundarios;
 public CIDER_Centro() {
  aliadosPrincipales = new ArrayList<CIDER_DB_Entity>();
  aliadosSecundarios = new ArrayList<CIDER_DB_Entity>();
  singleVariables = new HashMap<String,String>();
  multiVariables = new HashMap<String,ArrayList<String>>();
 }

 public CIDER_Centro(String entityName) {
  super(entityName);
  singleVariables = new HashMap<String,String>();
  multiVariables = new HashMap<String,ArrayList<String>>();
  aliadosPrincipales = new ArrayList<CIDER_DB_Entity>();
  aliadosSecundarios = new ArrayList<CIDER_DB_Entity>();
 }
 public void makeSingleVariable(String variableName, String variableContent){
  singleVariables.put(variableName,variableContent);
 }
 public void makeMultivariable(String variableName, ArrayList<String> variableContent){
  multiVariables.put(variableName, variableContent);
 }
 public PVector getPosGPS(){
  String posGPS = singleVariables.get("COORDENADAS DE UBICACIÓN");
  if(posGPS.contains(",")){
   String[] posGPSTokens = posGPS.split(",");
   float x = Float.valueOf(posGPSTokens[0]);
   float y = Float.valueOf(posGPSTokens[1]);
   return new PVector(x,y);
  }else{
   return new PVector();
  }
 }
 public ArrayList<String> getFicheroInformation(){
  ArrayList<String> ficheroInfo = new ArrayList<String>();
  ficheroInfo.add(singleVariables.get("ENTIDAD ADSCRITA A"));
  ficheroInfo.add(singleVariables.get("CONTACTO TOP"));
  ficheroInfo.add(singleVariables.get("E-MAIL CONTACTO TOP"));
  ficheroInfo.add(singleVariables.get("DIRECCIÓN"));
  ficheroInfo.add(singleVariables.get("CIUDAD/MUNICIPIO"));
  ficheroInfo.add(singleVariables.get("DEPARTAMENTO"));
  ficheroInfo.add(singleVariables.get("TELÉFONO 1"));
  ficheroInfo.add(singleVariables.get("E-MAIL INSTITUCIONAL"));
  ficheroInfo.add(singleVariables.get("PÁGINA(S) WEB"));
  ficheroInfo.add(singleVariables.get("OFERTA ACADÉMICA"));
  return ficheroInfo;
 }
 public String getTamano(){
  return singleVariables.get("TAMAÑO");
 }
 public String getMultiVariableAsString(String multiVarName){
  String varAsString = "";
  ArrayList<String> values = multiVariables.get(multiVarName);
  for(int i=0;i<values.size();i++){
   varAsString += values.get(i);
   if(i<values.size()-1){
    varAsString +=", ";
   }
  }
  return varAsString;
 }
 public String getCentroBaseInformation(){
  String centroInformation = "";
  return centroInformation;
 }
 public void reportMultiVariables(){
  for(String multiVarName: multiVariables.keySet()){
   boolean repeated = repeatedValuesInMultivariable(multiVarName);
   if(repeated){
    System.out.println("repeated values in variable: "+multiVarName);
   }
  }
 }
 public boolean repeatedValuesInMultivariable(String multiVarName){
  boolean hasRepeated = false;
  ArrayList<String> var = multiVariables.get(multiVarName);
  for(int i=0;i<var.size();i++){
   String val = var.get(i);
   for(int j=0;j<var.size();j++){
    if(i!=j){
     hasRepeated = hasRepeated || val.equals(var.get(j));
    }
   }
   return hasRepeated;
  }
  return hasRepeated;
 }
 public boolean isSingleVariable(String variableName){
  return singleVariables.containsKey(variableName);
 }
 public boolean isMultiVariable(String variableName){
  return multiVariables.containsKey(variableName);
 }
 
 public boolean respondToFilters(ArrayList<String> filters){
  boolean isInFilter = true;
  for(int i=0;i<filters.size();i++){
   isInFilter = isInFilter && respondToQuery(filters.get(i));
  }
  return isInFilter;
 }
 public boolean respondToQuery(String query){
  boolean response = false;
  //search for string in individual variables
  response = singleVariables.containsValue(query);
  if(response){
   return response;
  }else{
   //now search for string in multivariables
   for(Map.Entry multiVarEntry: multiVariables.entrySet()){
    ArrayList<String> multiVar = (ArrayList<String>) multiVarEntry.getValue();
    for(int i=0;i<multiVar.size();i++){
     response = response || multiVar.get(i).equals(query);
    }
   }
   return response;
  }
 }
 public boolean respondToQuery(CIDER_Variable var){
  if(isSingleVariable(var.getVariableName())){
   return true;
  }else if(isMultiVariable(var.getVariableName())){
   return true;
  }
  return false;
 }
 public String getVariableValue(CIDER_Variable var){
  if(isSingleVariable(var.getVariableName())){
   return singleVariables.get(var.getVariableName());
  }else if(isMultiVariable(var.getVariableName())){
   return "";
  }
  return "";
 }
 public boolean respondToQuery(String variableName, String query){
  if(isSingleVariable(variableName)){
   return singleVariables.get(variableName).equals(query);
  }else if(isMultiVariable(variableName)){
    ArrayList<String> varOptions = multiVariables.get(variableName);
    boolean satisfies = false;
    for(int i=0;i<varOptions.size();i++){
     satisfies = satisfies || varOptions.get(i).equals(query);
    }
    return satisfies;
  }else{
   return false;
  }
 }
 public String getAliadosAsString(char type){
  String aliadosAsString = "";
  ArrayList<CIDER_DB_Entity> aliados;
  if(type == 'p'){
   aliados = getAliadosPrincipales();
  }else{
   aliados = getAliadosSecundarios();
  }
  for(int i=0;i<aliados.size();i++){
   aliadosAsString += aliados.get(i).getEntityName();
   if(i<aliados.size()-1){
    aliadosAsString +=", ";
   }
  }
  return aliadosAsString;
 }
 public boolean isInSingleVariable(String variableName, String query){
  if(isSingleVariable(variableName)){
   return singleVariables.get(variableName).equals(query);
  }
  return singleVariables.containsValue(query);
 }
 public ArrayList<CIDER_DB_Entity> getAliadosPrincipales() {
  return aliadosPrincipales;
 }
 public ArrayList<CIDER_DB_Entity> getAliadosSecundarios() {
  return aliadosSecundarios;
 }
 public void addToAliados(CIDER_DB_Entity entity, char type){
  if(type == 'p'){
   aliadosPrincipales.add(entity);
  }else{
   aliadosSecundarios.add(entity);
  }
 }
 public boolean isAliado(CIDER_DB_Entity entity, char type){
  boolean isAliado = singleVariables.get("ENTIDAD ADSCRITA A").equals(entity.getEntityName());
  if(type == 'p'){
   for(int i=0;i<aliadosPrincipales.size();i++){
    isAliado = isAliado || aliadosPrincipales.get(i).equals(entity);
   }
  }else{
   for(int i=0;i<aliadosSecundarios.size();i++){
    isAliado = isAliado || aliadosSecundarios.get(i).equals(entity);
   }
  }
  return isAliado;
 }
}
