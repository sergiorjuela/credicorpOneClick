package com.mega.credicorp.riesgos.reportes;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;



import com.mega.modeling.analysis.content.Dataset;
import com.mega.modeling.analysis.content.Dimension;
import com.mega.modeling.analysis.content.ReportContent;
import com.mega.modeling.analysis.content.Text;
import com.mega.modeling.analysis.content.View;
import com.mega.modeling.api.MegaObject;
import com.mega.modeling.api.MegaRoot;
import com.mega.soho.grcu.GRCDataProcessing;
import com.mega.soho.grcu.GRCReportViewUtility;


/*
 * Esta clase se encarga de generar la tabla del mapa de calor y
 * llenarla con los valores almacenados en las instacias de las clases
 * hmap y cell, tiene dos metodos de llenado uno para los mapa de
 * calor convencionales y otro para un mapa de referencia.
 */

public class TablePresentation
{
  private MegaRoot mgRoot;
  private Dataset heatMapDataset;
  private Dimension dimFirstMaAttribute;
  private Dimension dimSecondMaAttribute;
  private ReportContent rContent;
  private Hmap hMap;
  public static final String PATTERN = "###,###.##";
  
  public View createReportTable(MegaRoot root, ReportContent reportContent, Hmap heatMap)
  {
	  
	  /*
	   * Inicializa los atributos locales definiendo la estrutura de un data set con
	   * dimensiones y tamaños  
	   */
	
    this.mgRoot = root;
    this.rContent = reportContent;
    this.hMap = heatMap;
    this.heatMapDataset = new Dataset("");
    this.dimFirstMaAttribute = new Dimension("");
    this.dimSecondMaAttribute = new Dimension("");
    this.dimFirstMaAttribute.setSize(this.hMap.getMavFirstMaAttribute().size() + 1);
    this.dimSecondMaAttribute.setSize(this.hMap.getMavSecondMaAttribute().size() + 1);
    this.heatMapDataset.addDimension(this.dimFirstMaAttribute);
    this.heatMapDataset.addDimension(this.dimSecondMaAttribute);
    
    
    //Llenar mapa de calor
    fillHeatMap();
    
    //Genera una nueva vista cin el dataset generado previamente
    View vTable = new View(reportContent.addDataset(this.heatMapDataset));
    //La renderiza como una tabla
    vTable.addRenderer("~WCgdfMQqBzc1[table]");
    vTable.getItemRenderer().addParameter("class", "charttable");
    
    return vTable;
  }
  
  
  /*
   * Se encarga de crear un tabla de un mapa de calor 
   */
  public View createReferenceTable(MegaRoot root, ReportContent reportContent, Hmap heatMap)
  {
    this.mgRoot = root;
    this.rContent = reportContent;
    this.hMap = heatMap;
    this.heatMapDataset = new Dataset("");
    this.dimFirstMaAttribute = new Dimension("");
    this.dimSecondMaAttribute = new Dimension("");
    this.dimFirstMaAttribute.setSize(this.hMap.getMavFirstMaAttribute().size() + 1);
    this.dimSecondMaAttribute.setSize(this.hMap.getMavSecondMaAttribute().size() + 1);
    this.heatMapDataset.addDimension(this.dimFirstMaAttribute);
    this.heatMapDataset.addDimension(this.dimSecondMaAttribute);
    
    
    //Realiza al calculo y llenado de la tabla
    fillReferenceHeatMap();
    
    //Genera la vista la renderiza y la retorna
    View vTable = new View(reportContent.addDataset(this.heatMapDataset));
    vTable.addRenderer("~WCgdfMQqBzc1[table]");
    vTable.getItemRenderer().addParameter("class", "charttable");
    
    return vTable;
  }
  
  
  /*
   * Este metodo realiza el llenado de la tabla y el calculo de los totales
   * del mapa de valor convencional
   */
  private void fillHeatMap()
  {
	  
	//Obtiene el map de celdas vinculado al hmap
    Map<String, HCell> mavsMap = this.hMap.getMavsMap();
    
    /*
     * Define las variables ligne, column y total value contexts con 1,1 y 0
     * y setTitles con false
     */
    int ligne = 1;
    int column = 1;
    int totalValueContexts = 0;
    boolean setTitles = false;
    
    
    for (MegaObject mavFirstMetAttribute : this.hMap.getMavFirstMaAttribute())
    {
      //Obtiene por cada matv un titulo y lo agrega en la coordenada X.
      Text tex_mavName = getTitlesTexts(mavFirstMetAttribute.getProp("~n97OO26RrO00[Espaï¿½ol]"));
      this.dimFirstMaAttribute.addItem(tex_mavName);
      
      /*
       * La variable total line se inicializa en 0 y se va incrementando a medida
       * que se llenan la celdas de la fila
       */
      int totalLine = 0;
      for (MegaObject mavSecondMetAttribute : this.hMap.getMavSecondMaAttribute())
      {
        if (!setTitles)
        {
        //Obtiene por cada matv un titulo y lo agrega en la coordenada Y.
          tex_mavName = getTitlesTexts(mavSecondMetAttribute.getProp("~n97OO26RrO00[Espaï¿½ol]"));
          tex_mavName.getItemRenderer().addParameter("columnwidth", "60px");
          this.dimSecondMaAttribute.addItem(tex_mavName);
        }
        
        //Arma el valor de la llave de la celda
        String heatMapCellKey = Double.valueOf(mavFirstMetAttribute.getProp("~L20000000L50[Internal Value]")).toString() + "," + 
          Double.valueOf(mavSecondMetAttribute.getProp("~L20000000L50[Internal Value]")).toString();
        
        /*
         * Define un objeto de tipo celca y le asigna el objeto celda almacenado
         * en el map vinculado a la llave previamente estructurada  
         */
       
        HCell hcell = (HCell)mavsMap.get(heatMapCellKey);
        
        /*
         * Incrementa el valor  de la linea de acuerdo al tamaño del valores
         * de contexto de la celda
         */
        totalLine += hcell.getValueContexts().size();
        
        /*
         * Genera un objeto de tipo texto en donde le pasa el tamaño del arreglo
         * este tamaño representa en nuero de coincidencias por nodo.
         */
        Text value = new Text("<div  style=\"text-align:center;color:#00457E;font-size:11px;font-family:arial;font-weight:bold\"> <b>" + hcell.getValueContexts().size() + " </b></div>", false);
        value.isHtml(true);
        /*
         * Le pone el color a la celda y le agrega la propiedad
         * drilldown que genera un evento cuando se selecciona la casilla
         */
        value.getItemRenderer().addParameter("color", hcell.getColor());
        value.getItemRenderer().addParameter("drilldown", getDrillDown(hcell.getValueContexts()).toString());
        
        /*
         * Agrega al data set el objeto text en la posición ligne (1) y column (1) 
         * y posteriormente aumenta el contador de columna. 
         */
        this.heatMapDataset.addItem(value, ligne + "," + column);
        column++;
      }
      
      /*
       * Genera un titulo con el valor del totalline el cual es el acumulado
       * de toda la linea y lo coloca en la ultima posición en la cual se 
       * totaliza
       */
      Text ittotal = getTitlesTexts(String.valueOf(totalLine));
      this.heatMapDataset.addItem(ittotal, ligne + "," + column);
      
      /*
       * a la variable totalValueContexts se suma el valor de la linea 
       * para obtener un acumulado del valor total.
       */
      totalValueContexts += totalLine;
      
      /*
       * Incrementa la linea y la columna la inicializa en 1 para reiniciar
       * el recorrido de izquierda a derecha en la segunda fila.
       */
      ligne++;
      column = 1;
      
      //Cambia el valor de setTitles a true para que los titulos ya no se generen
      setTitles = true;
    }
    
    //Agrega un texto vacio que se ubica en la parte superior derecha (se asume)
    this.dimSecondMaAttribute.addItem(new Text(" ", false));
    //Agrega un texto que se ubica en la parte inferior izquierda "Total"
    Text totalTitle = getTitlesTexts(GRCDataProcessing.getCodeTemplate("~5ARhLtwZGr5Q[total]", this.mgRoot));
    this.dimFirstMaAttribute.addItem(totalTitle);
    //Calcula el total de la columnas y lo agrega a la tabla 
    setTotalColumns();
    //Genera un objeto text con el total calculado y lo agrega a la tabla
    Text totalAll = getTitlesTexts(String.valueOf(totalValueContexts));
    this.heatMapDataset.addItem(totalAll, this.hMap.getMavFirstMaAttribute().size() + 1 + "," + (this.hMap.getMavSecondMaAttribute().size() + 1));
    
    //GEnera la vista con el data set y la renderiza
    View heatMapView = new View(this.rContent.addDataset(this.heatMapDataset));
    heatMapView.addRenderer("~WCgdfMQqBzc1[table]");
    heatMapView.getItemRenderer().addParameter("class", "charttable");
  }
  
  
  /*
   * Este metodo realiza el llenado de la tabla  de referencia
   */
  private void fillReferenceHeatMap()
  {
	  
	/*
	 * Obtiene el mapa de mtav e inicializa las variables ligne y column en 1 
	 * y set Titel en false
	 */
    Map<String, HCell> mavsMap = this.hMap.getMavsMap();
    int ligne = 1;
    int column = 1;
    boolean setTitles = false;
 
    
    for (MegaObject mavFirstMetAttribute : this.hMap.getMavFirstMaAttribute())
    {
    	
    	//Genera los titulos del primer valor de meta atributo y los agrega a la tabla
      Text tex_mavName = getTitlesTexts(mavFirstMetAttribute.getProp("~n97OO26RrO00[Espaï¿½ol]"));
      this.dimFirstMaAttribute.addItem(tex_mavName);
      
      for (MegaObject mavSecondMetAttribute : this.hMap.getMavSecondMaAttribute())
      {
        if (!setTitles)
        	
        {
       //Genera los titulos del segundo valor de meta atributo y los agrega a la tabla
          tex_mavName = getTitlesTexts(mavSecondMetAttribute.getProp("~n97OO26RrO00[Espaï¿½ol]"));
          tex_mavName.getItemRenderer().addParameter("columnwidth", "60px");
          this.dimSecondMaAttribute.addItem(tex_mavName);
        }
        
        //Arma la llave de la celda del mapa 
        String heatMapCellKey = Double.valueOf(mavFirstMetAttribute.getProp("~L20000000L50[Internal Value]")).toString() + "," + 
          Double.valueOf(mavSecondMetAttribute.getProp("~L20000000L50[Internal Value]")).toString();
        
        /*
         * Obtiene la probabilidad el impacto y con el producto de estos dos la criticidad
         * de referencia, le da formato y genera un objeto text con la información que luego
         * se agrega a la tabla.
         */
        
        Double probabilidad = Double.valueOf(mavFirstMetAttribute.getProp("~L20000000L50[Internal Value]"));
        Double impacto = Double.valueOf(mavSecondMetAttribute.getProp("~L20000000L50[Internal Value]"));
        Double criticidad = Double.valueOf(probabilidad.doubleValue() * impacto.doubleValue());
        DecimalFormat myFormatter = new DecimalFormat("###,###.##");
        
        //Se obtiene la celda con la llave previamente generada           
        HCell hcell = (HCell)mavsMap.get(heatMapCellKey);
        
        Text value = new Text("<div  style=\"text-align:center;color:#00457E;font-size:11px;font-family:arial;font-weight:bold\"> <b>" + myFormatter.format(criticidad) + " </b></div>", false);
        value.isHtml(true);
        
        //se agrega color y valor a la tabla 
        value.getItemRenderer().addParameter("color", hcell.getColor());
        this.heatMapDataset.addItem(value, ligne + "," + column);
        column++;
      }
      ligne++;
      column = 1;
      setTitles = true;
    }
    //Se aggrega un titulo en blanco
    this.dimSecondMaAttribute.addItem(new Text(" ", false));
    
    // Se genera la vista y se renderiza.
    
    View heatMapView = new View(this.rContent.addDataset(this.heatMapDataset));
    heatMapView.addRenderer("~WCgdfMQqBzc1[table]");
    heatMapView.getItemRenderer().addParameter("class", "charttable");
  }
  
  private void setTotalColumns()
  {
    /*
     * Inicializa el objeto ligne en el tamaño mas uno para que la linea
     * sea la ultima de la tabla.
     */
	int ligne = this.hMap.getMavSecondMaAttribute().size() + 1;
	
	//Inicializa la columna en uno
    int column = 1;
    for (MegaObject mavSecondMetAttribute : this.hMap.getMavSecondMaAttribute())
    {
    	//Define contador de total por columna
    	int totalColumn = 0;
      for (MegaObject mavFirstMetAttribute : this.hMap.getMavFirstMaAttribute())
      {
    	//Arma la llave de la celda   
        String heatMapCellKey = Double.valueOf(mavFirstMetAttribute.getProp("~L20000000L50[Internal Value]")).toString() + "," + 
          Double.valueOf(mavSecondMetAttribute.getProp("~L20000000L50[Internal Value]")).toString();
        
        //Obtiene el objeto celda con la llave generada
        HCell hcell = (HCell)this.hMap.getMavsMap().get(heatMapCellKey);
        //Aumenta el valor total con el valor del tamaño del arreglo de la celda
        totalColumn += hcell.getValueContexts().size();
        
        // define el objeto text y lo adiciona al final de la columna
        Text ittotal = getTitlesTexts(String.valueOf(totalColumn));
        this.heatMapDataset.addItem(ittotal, ligne + "," + column);
      }
      column++;
    }
  }
  
  
  /*
   * Recibe un string, el cual a traves de un objeto Text lo convierte en un titulo
   * y le asigna un color predeterminado.
   */
  private Text getTitlesTexts(String str)
  {
    String html_str = "<div  style=\"text-align: left;font-size:9px;font-family:arial\"> <b>" + str + "</b> </div>";
    Text tex_str = new Text(html_str, false);
    tex_str.isHtml(true);
    tex_str.getItemRenderer().addParameter("color", "E8E6DE");
    return tex_str;
  }
  
  /*
   *Recibe una lista de string que representa los nodos almacenados en esa posicion
   *genera un stringbuffer el cual llena de forma consecuente con cada string nodo de 
   *la lista 
   */
  
  private StringBuffer getDrillDown(ArrayList<String> nodes)
  {
    StringBuffer objectsId = new StringBuffer("~)0Gb81(DEL3o[Assessment Value Context]:");
    for (String nodeMegaField : nodes) {
      objectsId.append(nodeMegaField + ",");
    }
    GRCReportViewUtility.deleteSemiColon(objectsId);
    return objectsId;
  }
}
 