package com.mega.credicorp.riesgos.reportes;

import com.mega.modeling.analysis.Analysis;
import com.mega.modeling.analysis.AnalysisParameter;
import com.mega.modeling.analysis.AnalysisRenderingToolbox;
import com.mega.modeling.analysis.AnalysisReportWithContext;
import com.mega.modeling.analysis.content.ReportContent;
import com.mega.modeling.analysis.content.View;
import com.mega.modeling.api.MegaCOMObject;
import com.mega.modeling.api.MegaCollection;
import com.mega.modeling.api.MegaRoot;
import com.mega.soho.erm.reports.riskidentification.IdentificationMethodsToolBox;
import com.mega.soho.erm.reports.riskidentification.IdentificationParameters;
import java.util.List;
import java.util.Map;

/*
 * Esta es la clase principal del reporte, es la encarga de orquestar
 * toda la creación de objetos e instancias que convergen en la 
 * generación del reporte adicionalmente es la que se accede desde mega
 * y la que recibe los parametros, el user data y el root de mega
 */
public class HeatMapReport
  implements AnalysisReportWithContext
{
  MegaRoot root;
  private MegaCollection risks;
  private ReportContent reportContent;
  private Hmap inherentRiskMap;
  private Hmap netRiskMap;
  private Boolean isExcel = Boolean.valueOf(false);
  private PersistentLog log;
  
  public HeatMapReport()
  {
    try
    {
      //Obtiene la instancia del log
      this.log = PersistentLog.getInstance();
    }
    catch (Exception e1)
    {
      /* En caso de que ls instancia no exista, se inicializa a través del metodo
       *  getInstance al cual se le suministra la ruta del fichero.
       */
      try
      {
        this.log = PersistentLog.getInstance("C:/ReportLog/HeatMap.txt");
      }
      catch (Exception localException1) {}
    }
    
    //Asigna el objeto log obtenido al local privado log
    initLog(this.log);
    try
    {
    	//Obtiene el nombre completo de la clase (incluye paquete) y lo escribe en bufer del log
      this.log.record(getClass().getCanonicalName() + " \r\n ");
    }
    catch (NullPointerException localNullPointerException) {}
  }
  
  /*Metodo que asigna un objeto log obtenido desde la clase PersistentLog 
   * al objeto local privado log
   */
  protected void initLog(PersistentLog log)
  {
    this.log = log;
  }
  
  
  /*
   * Recibe un conjunto de parametros que se reciben desde mega en el momento
   * que se accede al macro java estos nos dan acceso al objeto megaroot, a un 
   * mapa clave valor con los parametros relacionados al reporte, un objeto de tipo
   * Analysis que nos permite acceder algunas funciones del contexto de los reportes
   * y un objeto UserData.
   * 
   * Esta clase se encarga de orquestar el llamado y la instancia de los objetos necesarios
   * para llevara  acabo la creación del mapa de calor personalizado.
   * @see com.mega.modeling.analysis.AnalysisReportWithContext#getReportContent(com.mega.modeling.api.MegaRoot, java.util.Map, com.mega.modeling.analysis.Analysis, java.lang.Object)
   */
  public ReportContent getReportContent(MegaRoot megaRoot, Map<String, List<AnalysisParameter>> parameters, Analysis analysis, Object userData)
  {
    try
    {
     
      // Se asigna el objeto megaroot que se recibe como parametro al objeto privado root
      this.root = megaRoot;
      
      
      /* A través del objeto analysis se obtiene el contexto en el cual se genera el reporte
       * esto refiere a si es web o escritorio
       */
      MegaCOMObject oContext = analysis.getMegaContext();
      short iContext = AnalysisRenderingToolbox.getGenerationMode(oContext);
      
      /*Valida si una caracteristica del contexto contiene el string XLS para asignar un valor
       * al atributo de tipo bool isExcel
       */
      if (analysis.getDr().toString().contains("XLS")) {
        this.isExcel = Boolean.valueOf(true);
      }
      
      /*
       * Instancia de la clase IdentificationParameters que realiza la inicialización de
       * los parametros y la definición de los metodos get y set
       */
      IdentificationParameters hMapIdParameters = new IdentificationParameters(this.root);
      
      /*
       * La clase nativa IdentificationMethodsToolBox tiene todas las funciones relacionadas
       * a la consulta de riesgos de acuerdo a los parametros de entrada del reporte y a la
       * funcionalidad del mismo módulo, tiene metodos que permite consultar las colecciones
       * de riesgos evaluados, ordenadar las colecciones o filtrarlas entre otros. 
       */
      
      /*
       * El metodo setParameters se encarga de validar los valores asignados desde los parametros
       * validar su no nulidad y generar las esturcturar para asignarlas a una instancia de la clase
       *  IdentificationParameters y ofrecer el acceso a estos datos de forma transparente.
       */
      IdentificationMethodsToolBox.setParameters(parameters, hMapIdParameters, "6EC2C76F516D22DE", "6EC2C82A516D239E", "6EC2C8B7516D2498", "6EC2C892516D2448", "6EC2C8F1516D24F1", "6EC2C862516D23F8", "");
      
      
      /*
       *Este metodo estatico recibe el objeto root y el objeto IdentificationParameters que inicializa los 
       *parametros y retorna una coleccion de riesgos completa o sesgada de acuerdo a los parametros.
       */
      this.risks = HeatMapsMethodToolBox.getRisks(this.root, hMapIdParameters);
      
      
      /*
       * Se instancia el HeatMapsMethodToolBox, el cual tiene todos los metodos graficos que se encargan
       * de consolidad las vistas del reporte.
       */
      HeatMapsMethodToolBox hmap = new HeatMapsMethodToolBox();
      
      /*
       * Genera dos tablas de mapa de calor denominadas inherentRiskMap y netRiskMap, lo curioso es
       * que en ambos casos pasa los mismos parametros
       */
      this.inherentRiskMap = hmap.createHeatMapsCell(this.root, "~3CKVX(7vFn9L[ERM Likelihood]", "~kEKVUu7vFnyK[ERM Impact]", "~lFKVs28vF5NL[ERM Inherent Risk]", "Riesgo Inherente2");
      this.netRiskMap = hmap.createHeatMapsCell(this.root, "~3CKVX(7vFn9L[ERM Likelihood]", "~kEKVUu7vFnyK[ERM Impact]", "~lFKVs28vF5NL[ERM Inherent Risk]", "Riesgo Residual2");
      
      
      /*
       * Esta clase cuenta con ciertos metodos que estan orientados a determinar
       * los valores que van en cada una de las celdas del mapa de calor realizando
       * todas las validaciones pertinentes, tener en cuenta que el calculo de la 
       * criticidad se realiza en esta clase.
       */
      NodesSearch nSearch = new NodesSearch();
      nSearch.setValueContexts(this.root, this.inherentRiskMap, this.netRiskMap, this.risks, hMapIdParameters);
      
      /*
       * Con base en los valores almacenados en el objeto criticidad se llena
       * log.
       */
      
      try
      {
        this.log.record(" Get Criticidad \r\n ");
      }
      catch (NullPointerException localNullPointerException) {}
      Criticidad criticidad = nSearch.getCriticidad();
      try
      {
        this.log.record("criticidad.getNoRiesgosTotales()            " + criticidad.getNoRiesgosTotales() + " \r\n ");
      }
      catch (NullPointerException localNullPointerException1) {}
      try
      {
        this.log.record("criticidad.getUsTotales()                   " + criticidad.getUsTotales() + " \r\n ");
      }
      catch (NullPointerException localNullPointerException2) {}
      try
      {
        this.log.record("criticidad.getNoRiesgosTotalesResidual()    " + criticidad.getNoRiesgosTotalesResidual() + " \r\n ");
      }
      catch (NullPointerException localNullPointerException3) {}
      try
      {
        this.log.record("criticidad.getUsTotalesResidual()           " + criticidad.getUsTotalesResidual() + " \r\n ");
      }
      catch (NullPointerException localNullPointerException4) {}
      try
      {
        this.log.record("--------------------------------------------- \r\n ");
      }
      catch (NullPointerException localNullPointerException5) {}
      try
      {
        this.log.record("criticidad.getNoRiesgosCriticos()           " + criticidad.getNoRiesgosCriticos() + " \r\n ");
      }
      catch (NullPointerException localNullPointerException6) {}
      try
      {
        this.log.record("criticidad.getNoRiesgosCriticosResidual()   " + criticidad.getNoRiesgosCriticosResidual() + " \r\n ");
      }
      catch (NullPointerException localNullPointerException7) {}
      try
      {
        this.log.record("criticidad.getNoRiesgosAltos()              " + criticidad.getNoRiesgosAltos() + " \r\n ");
      }
      catch (NullPointerException localNullPointerException8) {}
      try
      {
        this.log.record("criticidad.getNoRiesgosAltosResidual()      " + criticidad.getNoRiesgosAltosResidual() + " \r\n ");
      }
      catch (NullPointerException localNullPointerException9) {}
      try
      {
        this.log.record("criticidad.getNoRiesgosRelevantes()         " + criticidad.getNoRiesgosRelevantes() + " \r\n ");
      }
      catch (NullPointerException localNullPointerException10) {}
      try
      {
        this.log.record("criticidad.getNoRiesgosRelevantesResidual() " + criticidad.getNoRiesgosRelevantesResidual() + " \r\n ");
      }
      catch (NullPointerException localNullPointerException11) {}
      try
      {
        this.log.record("criticidad.getNoRiesgosModerados()          " + criticidad.getNoRiesgosModerados() + " \r\n ");
      }
      catch (NullPointerException localNullPointerException12) {}
      try
      {
        this.log.record("criticidad.getNoRiesgosModeradosResidual()  " + criticidad.getNoRiesgosModeradosResidual() + " \r\n ");
      }
      catch (NullPointerException localNullPointerException13) {}
      try
      {
        this.log.record("criticidad.getNoRiesgosBajo()               " + criticidad.getNoRiesgosBajo() + " \r\n ");
      }
      catch (NullPointerException localNullPointerException14) {}
      try
      {
        this.log.record("criticidad.getNoRiesgosBajoResidual()       " + criticidad.getNoRiesgosBajoResidual() + " \r\n ");
      }
      catch (NullPointerException localNullPointerException15) {}
      
      
      /*
       * Se crea una nueva instancia del objeto ReportContent, el cual sera retornado
       * a Mega y sera el que permite ver el reporte.
       */
      this.reportContent = new ReportContent("");
      
      /*
       * Validan si el contexto es diferente de 2 para determinar el valor del isExcel
       */
      Boolean isHtml = Boolean.valueOf((iContext != 2) && (!this.isExcel.booleanValue()));
      
      /*
       * Este metodo se encarga de organizar todas las vistas del reporte, junto
       * con los mapa de calor generados (inherentRiskMap y netRiskMap )
       */
      
      hmap.setViews(this.root, this.reportContent, isHtml, this.inherentRiskMap, this.netRiskMap);
      
      //Crea una instancia del objeto TotalTable
      TotalTable totalTableView = new TotalTable();
      
      /*
       * El metodo getTotalTableView genera la tabla de total de riesgo
       * inherente y residual y la llena con la info del objeto criticidad 
       */
      View viewTotal = totalTableView.getTotalTableView(this.root, this.reportContent, criticidad, "~lFKVs28vF5NL[ERM Inherent Risk]");
      
      totalTableView.getReferenceView(this.root, this.reportContent, this.inherentRiskMap);
      
      this.reportContent.addView(viewTotal);
      
      this.log.flush();
    }
    catch (Exception e)
    {
      try
      {
        this.log.record(e.getMessage().toString() + " \r\n ");
      }
      catch (Exception localException1) {}
      try
      {
        throw new Exception(e);
      }
      catch (Exception localException2)
      {
        this.log.flush();
      }
    }
    return this.reportContent;
  }
}
