package com.mega.credicorp.riesgos.reportes;

import java.util.Date;
import java.util.Map;

import com.mega.modeling.api.MegaCollection;
import com.mega.modeling.api.MegaObject;
import com.mega.modeling.api.MegaRoot;
import com.mega.soho.erm.reports.riskidentification.IdentificationParameters;
import com.mega.soho.grcu.GRCDateUtility;
import com.mega.soho.grcu.assessment.AssessmentNode;
import com.mega.toolkit.errmngt.ErrorLogFormater;


/*
 * Esta clase es una de las mas densas en cuanto a contenido
 * se encarga de realizar la busqueda de los riesgos de acuerdo
 * a los parametros de contextos ingresados y a los rangos de fechas
 * adicionalmente se encarga de consultar y asignar por celda el 
 * numero de valores de contexto a visualizar, adicionalmente en esta 
 * se realiza el calculo de la criticiadad de los riesgos.
 */
public class NodesSearch
{
  private Map<String, HCell> inherentRiskMAP;
  private Map<String, HCell> netRiskMAP;
  private IdentificationParameters hMapIdentParameters;
  private AssessmentNode assessmentNode;
  private Criticidad criticidad;
  private PersistentLog log;
  private static String PERSON_SYSTEM = "~T20000000s10[Person <System>]";
  
  public NodesSearch()
  {
    try
    {
    	//Obtiene la instancia del log
      this.log = PersistentLog.getInstance();
    }
    catch (Exception e1)
    {
   
    	/* En caso de que ls instancia no exista se inicializa a través del metodo
         *  getInstance al cual se le suministra la ruta del fichero.
         */
      try
      {
        this.log = PersistentLog.getInstance("C:/ReportLog/HeatMap.txt");
      }
      catch (Exception e)
      {
        ErrorLogFormater err = new ErrorLogFormater();
        err.logError(e);
      }
    }
    
    //Asigna el objeto log obtenido al local privado log
    initLog(this.log);
  }
  
  
  /*Metodo que asigna un objeto log obtenido desde la clase PersistentLog 
   * al objeto local privado log
   */
  protected void initLog(PersistentLog log)
  {
    this.log = log;
  }
  
  public void setValueContexts(MegaRoot root, Hmap inherentRisk, Hmap netRisk, MegaCollection risks, IdentificationParameters identificationParameters)
  {
    this.hMapIdentParameters = identificationParameters;
    this.inherentRiskMAP = inherentRisk.getMavsMap();
    this.netRiskMAP = netRisk.getMavsMap();
    this.criticidad = new Criticidad();
    
    String readingAccess = "";
    String riskReadingAccess = "";
    
    //Obtiene el id del usuario actual a traves de una consulta del person system.
    MegaObject person = root.getCollection(PERSON_SYSTEM, new Object[0]).get(root.currentEnvironment().getCurrentUserID());
    //Obtiene información del area de acceso a lectura de la persona obtenida 
    MegaCollection raa = person.getCollection("~D10000008K40[Reading access area at creation]", new Object[0]);
    if (raa.size() > 0)
    {
      //Asigna al objeto mega readAccess el objeto retornado en la posición 1 de la coleccion obtenida
      MegaObject readAccess = person.getCollection("~D10000008K40[Reading access area at creation]", new Object[0]).get(Integer.valueOf(1));
      //Obtiene del identificador Absoluto del objeto previamente obtenido
      readingAccess = readAccess.getProp("~310000000D00[Absolute Identifier]");
    }
    
    
    for (MegaObject assessedObject : risks)
    {
    	
      //Asigna al objeto mega mgRisk el objeto assessedObject iterado en el ciclo
      MegaObject mgRisk = assessedObject;
      //Obtiene el identificador del area de acceso a lectura del objeto riesgo en cuestión
      riskReadingAccess = mgRisk.getProp("~)20000000z70[Reading access area identifier]");
      
      /*Compara el identificador del DRA del riesgo con el del usuario que consulta el reporte y con dos
       * identificadores directamente. 
       */
      
      if ((riskReadingAccess.equals(readingAccess)) || (readingAccess.equals("ygnVRydH3100")) || (readingAccess.equals("oPItoJEtKPpC")))
      {
    	  
    	 /*El metodo nodeToHeatMap se encarga de retorna una medida o evaludacion
    	  * relacionada al riesto en cuestión siempre y cuando este dentro de los rangos
    	  * de fechas especificadas, segun se entiende el riesgo puede tener muchas
    	  * evaluaciones pero solo se retorna la ultima.
    	  */
    	MegaObject temp = nodeToHeatMap(mgRisk, root, identificationParameters.getBeginDate(), identificationParameters.getEndDate());
    	
        if (temp != null)
        {
        	
        	/*Dependiendo de la validación de las fechas de la sesion y  los parametros del
        	 * reporte se determmina si se asignan los nodos absolutos o los nodos de contexto
        	 */
          this.assessmentNode = new AssessmentNode(temp);
          if (absoluteNodes().booleanValue()) {
        	//se encarga de asignar a las celdas el valor que le corresponde
            setAbsoluteNodes();
          } else {
            setContextualizedNodes();
          }
        }
      }
    }
  }
  
  public MegaObject nodeToHeatMap(MegaObject assessedObject, MegaRoot root, Date beginDate, Date endDate)
  {
    
	 /*
	  * Define una consulta que consulta las medidas de conexto donde el objeto evaluado
	  * sea igual al nombre del que entra como assessedObject y donde adicionalmente
	  * el contexto de evaluación sea nulo.
	  */
	  
	 String query = "Select ~)0Gb81(DEL3o[Measure Context] Where ~(GsYd9vKEziv[Assessed Object]" + 
      " = \"" + 
      assessedObject.megaField() + 
      "\"" + 
      " And [Assessment Context] Is Null ";
	 
	 //Almacena en el log el query generado
    try
    {
      this.log.record("get query " + query + " \r\n ");
    }
    catch (NullPointerException localNullPointerException) {}
    
    
    // Define una coleccion de medidas la cual va a llenar de acuerdo a la entrada de las fechas.
    MegaCollection measures;
    if ((beginDate == null) && (endDate == null))
    {
      /* Si ambos parametros de las fechas viene nulos se conulta la query con una fecha de
       * creación asignada en -1.
       */
    	
      measures = root.getSelection(query, new Object[] { Integer.valueOf(-1), "Creation Date" });
    }
    else
    {
    	/* En caso contrario se obtiene un coleccion similiar al caso anterior pero se obtienen
    	 * las medidas den un rango de fechas a través del método getMeasureInRange
         */
      measures = root.getSelection(query, new Object[] { Integer.valueOf(-1), "Creation Date" });
      try
      {
        this.log.record("Measures: " + measures.size() + " \r\n ");
      }
      catch (NullPointerException localNullPointerException1) {}
      measures = getMeasureInRange(root, beginDate, endDate, measures);
    }
    
    /*Define un objeto measure que tiene como fin almacenar el valor almacenado en la posicion 1 del objeto
     * el cual se almacenara en el log.
     */
    MegaObject measure;
    if (measures.size() > 0)
    {
      measure = measures.get(Integer.valueOf(1));
      try
      {
        this.log.record(" Measure creation date: " + measure.getProp("Creation Date") + " \r\n ");
      }
      catch (NullPointerException localNullPointerException2) {}
    }
    else
    {
      measure = null;
      try
      {
        this.log.record(" -- Sin evaluacion valida  \r\n ");
      }
      catch (NullPointerException localNullPointerException3) {}
    }
    // retorna el objeto measure
    return measure;
  }
   
  /*
   * Este metodo recibe una coleccion de medidas las cuales son contrastatadas con
   * los parametros fecha inicio y fecha fin, las que e encuentren en los rangos
   * indicados se agregan a una coleccion que se retorna. 
   */
  private static MegaCollection getMeasureInRange(MegaRoot root, Date beginDate, Date endDate, MegaCollection measures)
  {
    MegaCollection measuresInRange = root.getSelection("", new Object[0]);
    for (MegaObject measureObject : measures)
    {
      Date moAssessedObjectDate = GRCDateUtility.getDateFromMega(measureObject, "~510000000L00[Creation Date]");
      if (moAssessedObjectDate != null) {
        if (beginDate == null)
        {
          if ((moAssessedObjectDate.equals(endDate)) || (moAssessedObjectDate.before(endDate))) {
            measuresInRange.insert(measureObject);
          }
        }
        else if ((moAssessedObjectDate.equals(endDate)) || (moAssessedObjectDate.equals(beginDate)) || ((moAssessedObjectDate.before(endDate)) && (moAssessedObjectDate.after(beginDate)))) {
          measuresInRange.insert(measureObject);
        }
      }
      measureObject.release();
    }
    measures.release();
    
    return measuresInRange;
  }
  
  
  /*
   * Si el conjunto de parametros de tipo relacion que estan vinculados al reporte 
   * en cada una de sus colecciones es igual a 0 cero retorna true en caso contrario
   * false
   */
  private Boolean absoluteNodes()
  {
    if ((this.hMapIdentParameters.getObjectives().size() == 0) && (this.hMapIdentParameters.getOrgUnits().size() == 0) && (this.hMapIdentParameters.getProcesses().size() == 0) && (this.hMapIdentParameters.getRisktypes().size() == 0)) {
      return Boolean.valueOf(true);
    }
    return Boolean.valueOf(false);
  }
  
  
  
  /*
   * Este metodo recibe un objeto mega como parametros que representa la session 
   * y a este ultimo le consulta la fecha de finalización y la contrasta con 
   * las fechas de inicio y fin de los parametros, lo extraño es que retorna true
   * en ambos casos.
   */
  private Boolean checkSessionCharacteristics(MegaObject session)
  {
    if ((session != null) && 
      (session.getProp("~rlhJ(GrnFfXA[Assessment Session Status]").equals("CL")))
    {
      Date sessionEndDate = GRCDateUtility.getDateFromMega(session, "~1MFDsrsyE9lG[Assessment End Date]");
      if ((sessionEndDate != null) && ((sessionEndDate.before(this.hMapIdentParameters.getEndDate())) || (sessionEndDate.equals(this.hMapIdentParameters.getEndDate()))) && ((sessionEndDate.after(this.hMapIdentParameters.getBeginDate())) || (sessionEndDate.equals(this.hMapIdentParameters.getBeginDate())))) {
        return Boolean.valueOf(true);
      }
    }
    return Boolean.valueOf(true);
  }
  
  
  /*
   * Obtiene la coleccion de caracteristcas de evaluación a través del root, de esta 
   * obtiene la que especifica el parametro de entrada AssessedCharac, porteriormente 
   * del nodo evuado obtiene el valor para la caracteristica en cuestión.
   */
  
  private MegaObject getAssessedValue(String AssessedCharac)
  {
    MegaObject assessedValueFirstCharac = null;
    try
    {
      MegaRoot root = this.assessmentNode.getNode().getRoot();
      MegaObject mgFirstAssessedCharac = root.getCollection("~8IsYL)ELEfKw[Assessed Characteristic]", new Object[0]).get(AssessedCharac);
      assessedValueFirstCharac = this.assessmentNode.getValue(mgFirstAssessedCharac.megaUnnamedField());
      mgFirstAssessedCharac.release();
    }
    catch (Exception e)
    {
      try
      {
        this.log.record(" -- Valores de Evaluaciï¿½n con Error " + e.toString() + " \r\n ");
      }
      catch (NullPointerException localNullPointerException) {}
    }
    return assessedValueFirstCharac;
  }
  
  private void setAbsoluteNodes()
  {
    if (checkSessionCharacteristics(this.assessmentNode.getAssessment()).booleanValue()) {
      setValueContextToHcell();
    }
  }
  
  private void setContextualizedNodes()
  {
    MegaCollection contextsnodes = this.assessmentNode.getContexts();
    if (((contextsnodes == null) || (contextsnodes.size() == 0)) && 
      (checkSessionCharacteristics(this.assessmentNode.getAssessment()).booleanValue()))
    {
      try
      {
        this.log.record(" checkSessionCharacteristics   \r\n ");
      }
      catch (NullPointerException localNullPointerException) {}
      try
      {
        this.log.record(" checkParametersAsContexts   \r\n ");
      }
      catch (NullPointerException localNullPointerException1) {}
      setValueContextToHcell();
    }
  }
  
  private void setValueContextToHcell()
  {
	  
	 
    if (checkSessionCharacteristics(this.assessmentNode.getAssessment()).booleanValue()) {
      try
      {
        this.log.record("setValueContextToHcell V2 \r\n ");
        
        //Obtiene todos los valores de evalución de cada uno de los atributos
        MegaObject aValueImpact = getAssessedValue("~OvMqdWTjJ904[Impacto Abs]");
        MegaObject aValueLikeLihood = getAssessedValue("~XuMqTWTjJPz3[Probabilidad Abs]");
        MegaObject aValueImpactRes = getAssessedValue("~ovMq6cTjJbx4[Impacto Residual Abs]");
        MegaObject aValueLikeLihoodRes = getAssessedValue("~zuMqxbTjJzu4[Probabilidad Residual Abs]");
        
        // Valida que los valores no sean nulos ni vengan vacios 
        if ((aValueImpact != null) && (aValueLikeLihood != null)) {
          if ((!aValueLikeLihood.getProp("~ebwhMB3iDLiH[Computed Value]").isEmpty()) && (!aValueImpact.getProp("~ebwhMB3iDLiH[Computed Value]").isEmpty()))
          {
            if ((aValueLikeLihood.getProp("~ebwhMB3iDLiH[Computed Value]") != "") && (aValueImpact.getProp("~ebwhMB3iDLiH[Computed Value]") != ""))
            {
              
            //A los double probabilidad e impacto les asigna el valor computado de cada objeto
              Double probabilidad = Double.valueOf(aValueLikeLihood.getProp("~ebwhMB3iDLiH[Computed Value]"));
              Double impacto = Double.valueOf(aValueImpact.getProp("~ebwhMB3iDLiH[Computed Value]"));
              //Arma la llave de la celda
              String cellKey = probabilidad.toString() + "," + impacto.toString();
             
              //Almacena en el log la llave de la celda
              try
              {
                this.log.record(" cellKey " + cellKey + "  \r\n ");
              }
              catch (NullPointerException localNullPointerException) {}
              // valida que el valor obtenido en la celda sea diferente de nulo
              if (this.inherentRiskMAP.get(cellKey) != null)
              {
            	  //Asigna a la celda el valor obtenido por el campo
                ((HCell)this.inherentRiskMAP.get(cellKey)).getValueContexts().add(this.assessmentNode.getAssessed().megaUnnamedField());
                
                /*Utiliza el metodo validarCriticidad, este metodo con base en los valores de probabilidad 
                 * e impacto a traves de un conjunto de validaciones acumula un numero de riesgos y un valor
                 * dependiendo las escalas definidas 
                 * */
                validarCriticidad(probabilidad, impacto);
              }
            }
          }
          else {
            try
            {
              this.log.record(" -- Caracterï¿½stica sin valores \r\n ");
            }
            catch (NullPointerException localNullPointerException1) {}
          }
        }
        // Valida que los valores no sean nulos ni vengan vacios 
        if ((aValueImpactRes != null) && (aValueLikeLihoodRes != null)) {
          if ((!aValueImpactRes.getProp("~ebwhMB3iDLiH[Computed Value]").isEmpty()) && (!aValueLikeLihoodRes.getProp("~ebwhMB3iDLiH[Computed Value]").isEmpty()))
          {
            if ((aValueImpactRes.getProp("~ebwhMB3iDLiH[Computed Value]") != "") && (aValueLikeLihoodRes.getProp("~ebwhMB3iDLiH[Computed Value]") != ""))
            {
            	
            	//A los bouble probabilidad e impacto residual les asigna el valor computado de cada objeto
              Double probabilidadResidual = Double.valueOf(aValueLikeLihoodRes.getProp("~ebwhMB3iDLiH[Computed Value]"));
              Double impactoResidual = Double.valueOf(aValueImpactRes.getProp("~ebwhMB3iDLiH[Computed Value]"));
            //Arma la llave de la celda
              String cellKey = probabilidadResidual.toString() + "," + impactoResidual.toString();
              try
              {
                this.log.record(" cellKey net " + cellKey + "  \r\n ");
              }
              catch (NullPointerException localNullPointerException2) {}
              if (this.netRiskMAP.get(cellKey) != null)
              {
            	//Asigna a la celda el valor obtenido por el campo
                ((HCell)this.netRiskMAP.get(cellKey)).getValueContexts().add(this.assessmentNode.getAssessed().megaUnnamedField());
                
                /*Utiliza el metodo validavalidarCriticidadResidualrCriticidad, este metodo con base en los valores 
                 * de probabilidad e impacto residual  a traves de un conjunto de validaciones acumula un numero de riesgos 
                 * y un valor dependiendo las escalas definidas 
                 * */
                validarCriticidadResidual(probabilidadResidual, impactoResidual);
              }
            }
          }
          else {
            try
            {
              this.log.record(" -- Caracterï¿½stica sin valores \r\n ");
            }
            catch (NullPointerException localNullPointerException3) {}
          }
        }
        this.log.record("exit setValueContextToHcell V2 \r\n ");
      }
      catch (Exception e)
      {
        try
        {
          this.log.record(" setValueContextToHcell Error " + e.toString() + "  \r\n ");
        }
        catch (NullPointerException localNullPointerException4) {}
      }
    }
  }
  /*
   * Recibe la probabilidad y el impacto, los multiplica y de acuerdo
   * al resultado del producto a traves de condicionales definidos 
   * en donde aplique se acumulan el numero de riesgos y el valor en dolares.
   */
  public Criticidad validarCriticidad(Double probabilidad, Double impacto)
  {
    Double usCrit = Double.valueOf(probabilidad.doubleValue() * impacto.doubleValue());
    if (usCrit.doubleValue() >= 50000.0D)
    {
      this.criticidad.setNoRiesgosCriticos();
      this.criticidad.setUsCriticos(usCrit);
    }
    else if ((usCrit.doubleValue() >= 18000.0D) && (usCrit.doubleValue() < 50000.0D))
    {
      this.criticidad.setNoRiesgosAltos();
      this.criticidad.setUsAltos(usCrit);
    }
    else if ((usCrit.doubleValue() >= 4050.0D) && (usCrit.doubleValue() < 18000.0D))
    {
      this.criticidad.setNoRiesgosRelevantes();
      this.criticidad.setUsRelevantes(usCrit);
    }
    else if ((usCrit.doubleValue() >= 1800.0D) && (usCrit.doubleValue() < 4050.0D))
    {
      this.criticidad.setNoRiesgosModerados();
      this.criticidad.setUsModerados(usCrit);
    }
    else if (usCrit.doubleValue() <= 1000.0D)
    {
      this.criticidad.setNoRiesgosBajo();
      this.criticidad.setUsBajo(usCrit);
    }
    this.criticidad.setNoRiesgosTotales();
    this.criticidad.setUsTotales(usCrit);
    return this.criticidad;
  }
  
  
  /*
   * Recibe la probabilidad y el impacto residual, los multiplica y de acuerdo
   * al resultado del producto a traves de condicionales definidos 
   * en donde aplique se acumulan el numero de riesgos y el valor en dolares residual
   */
  public Criticidad validarCriticidadResidual(Double probabilidad, Double impacto)
  {
    Double usCrit = Double.valueOf(probabilidad.doubleValue() * impacto.doubleValue());
    if (usCrit.doubleValue() >= 50000.0D)
    {
      this.criticidad.setNoRiesgosCriticosResidual();
      this.criticidad.setUsCriticosResidual(usCrit);
    }
    else if ((usCrit.doubleValue() >= 18000.0D) && (usCrit.doubleValue() < 50000.0D))
    {
      this.criticidad.setNoRiesgosAltosResidual();
      this.criticidad.setUsAltosResidual(usCrit);
    }
    else if ((usCrit.doubleValue() >= 4050.0D) && (usCrit.doubleValue() < 18000.0D))
    {
      this.criticidad.setNoRiesgosRelevantesResidual();
      this.criticidad.setUsRelevantesResidual(usCrit);
    }
    else if ((usCrit.doubleValue() >= 1800.0D) && (usCrit.doubleValue() < 4050.0D))
    {
      this.criticidad.setNoRiesgosModeradosResidual();
      this.criticidad.setUsModeradosResidual(usCrit);
    }
    else if (usCrit.doubleValue() <= 1000.0D)
    {
      this.criticidad.setNoRiesgosBajoResidual();
      this.criticidad.setUsBajoResidual(usCrit);
    }
    this.criticidad.setNoRiesgosTotalesResidual();
    this.criticidad.setUsTotalesResidual(usCrit);
    return this.criticidad;
  }
  
  //Permite acceder a la instancia de la clase criticidad
  public Criticidad getCriticidad()
  {
    return this.criticidad;
  }
  // Asigna una instancia a la clase criticidad
  public void setCriticidad(Criticidad criticidad)
  {
    this.criticidad = criticidad;
  }
}
