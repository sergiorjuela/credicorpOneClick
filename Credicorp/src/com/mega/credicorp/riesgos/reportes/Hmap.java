package com.mega.credicorp.riesgos.reportes;

import com.mega.modeling.api.MegaCollection;
import java.util.Map;


/*
 * Clase Hmap define un objeto que contiene los atributos de un 
 * mapa de calor, el nombre de la tabla los dos meta atributos 
 * de contraste y la celdas del mapa
 */
public class Hmap

{
	//Declaración de atributos
  private String tableName;
  private MegaCollection mavFirstMaAttribute;
  private MegaCollection mavSecondMaAttribute;
  private Map<String, HCell> mavsMap;
  
  //Definición de metodos de set y get
  
  public String getTableName()
  {
    return this.tableName;
  }
  
  public void setTableName(String tableName)
  {
    this.tableName = tableName;
  }
  
  public MegaCollection getMavFirstMaAttribute()
  {
    return this.mavFirstMaAttribute;
  }
  
  public void setMavFirstMaAttribute(MegaCollection mavFirstMaAttribute)
  {
    this.mavFirstMaAttribute = mavFirstMaAttribute;
  }
  
  public MegaCollection getMavSecondMaAttribute()
  {
    return this.mavSecondMaAttribute;
  }
  
  public void setMavSecondMaAttribute(MegaCollection mavSecondMaAttribute)
  {
    this.mavSecondMaAttribute = mavSecondMaAttribute;
  }
  
  public Map<String, HCell> getMavsMap()
  {
    return this.mavsMap;
  }
  
  public void setMavsMap(Map<String, HCell> mavsMap)
  {
    this.mavsMap = mavsMap;
  }
}
