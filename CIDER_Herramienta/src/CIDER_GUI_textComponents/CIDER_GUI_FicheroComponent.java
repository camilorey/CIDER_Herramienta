/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_GUI_textComponents;

import CIDER_DB.CIDER_Centro;
import CIDER_GUI_Behaviors.Draggable;
import CIDER_GUI_CIDER_entityMarkers.CIDER_GUI_CentroMarker;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PVector;

/**
 *
 * @author laptop
 */
public class CIDER_GUI_FicheroComponent extends CIDER_GUI_richTextComponent{
 protected String ficheroTitle;
 protected ArrayList<String> ficheroFields;
 protected ArrayList<String> ficheroInformation;
 protected boolean displayingPrivateInformation;
 protected float xFieldBoxAlignment;
 protected float informationLayerWidth;
 protected float informationLayerHeight;
 
 public CIDER_GUI_FicheroComponent(PApplet parent,PFont font) {
  super(new int[]{0,0,0}, new int[]{255,255,255}, parent,parent.width*0.4f,parent.height*0.5f);
  this.position = new PVector(parent.width*0.3f,parent.height*0.3f);
  ficheroTitle = "CENTRO DE INVESTIGACIÓN";
  ficheroFields = new ArrayList<String>();
  informationLayer.textFont(font);
  informationLayer.textSize(12);
  setFields();
 }
 public void setFields(){
  ficheroFields.add("Entidad adscrita a: ");
  ficheroFields.add("Contacto top: ");
  ficheroFields.add("E-Mail Contacto Top;");
  ficheroFields.add("Dirección: ");
  ficheroFields.add("Ciudad/Municipio: ");
  ficheroFields.add("Departamento: ");
  ficheroFields.add("Teléfono 1: ");
  ficheroFields.add("E-mail institucional: ");
  ficheroFields.add("Página web: ");
  ficheroFields.add("Oferta Académica: ");
  for(int i=0;i<ficheroFields.size();i++){
   xFieldBoxAlignment = parent.max(xFieldBoxAlignment,informationLayer.textWidth(ficheroFields.get(i)));
  }
 }
 public void setFicheroInformation(CIDER_GUI_CentroMarker centro){
  CIDER_Centro cent = (CIDER_Centro) parentDB.get(centro.getObjectName());
  ficheroTitle = cent.getEntityName();
  ficheroInformation = cent.getFicheroInformation();
 }
 @Override
 public void updateInformationLayer(){
  float xAlignment = informationLayer.width*0.01f;
  float ficheroTitleWidth = informationLayer.width*0.9f;
  float ficheroTitleHeight = informationLayer.height*0.15f;
  float ficheroFieldWidth = informationLayer.width*0.7f-xFieldBoxAlignment*0.25f;
  float ficheroFieldHeight = informationLayer.height*0.08f;
  float currentY = informationLayer.height*0.01f;
  informationLayer.beginDraw();
   informationLayer.background(backgroundColor[0],backgroundColor[1],backgroundColor[2]);
   informationLayer.fill(foregroundColor[0],foregroundColor[1],foregroundColor[2]);
   informationLayer.textSize(16);
   informationLayer.textAlign(PApplet.CENTER,PApplet.TOP);
   informationLayer.text(ficheroTitle,xAlignment,currentY,ficheroTitleWidth,ficheroTitleHeight);
   currentY += ficheroTitleHeight;
   informationLayer.textSize(12);
   for(int i=0;i<ficheroFields.size();i++){
    if(!ficheroInformation.get(i).equals("") && !ficheroInformation.get(i).equals("no hay información disponible")){
     informationLayer.textAlign(PApplet.LEFT,PApplet.BOTTOM);
     informationLayer.fill(foregroundColor[0],foregroundColor[1],foregroundColor[2]);
     informationLayer.textSize(10);
     informationLayer.text(ficheroFields.get(i),xAlignment,currentY);
     informationLayer.textAlign(PApplet.LEFT,PApplet.TOP);
     informationLayer.noFill();
     informationLayer.fill(foregroundColor[0],foregroundColor[1],foregroundColor[2]);
     informationLayer.textSize(8);
     informationLayer.text(ficheroInformation.get(i),xAlignment+xFieldBoxAlignment,currentY-ficheroFieldHeight/2.0f,ficheroFieldWidth,ficheroFieldHeight);
     currentY+= ficheroFieldHeight;
    }
   }
  informationLayer.endDraw();
 }
}
