package com.mega.credicorp.riesgos.reportesro;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mega.modeling.analysis.Analysis;
import com.mega.modeling.analysis.AnalysisCallback;
import com.mega.modeling.analysis.AnalysisParameter;
import com.mega.modeling.analysis.AnalysisReportWithContext;
import com.mega.modeling.analysis.content.Dataset;
import com.mega.modeling.analysis.content.Dimension;
import com.mega.modeling.analysis.content.Item;
import com.mega.modeling.analysis.content.ReportContent;
import com.mega.modeling.analysis.content.Text;
import com.mega.modeling.analysis.content.View;
import com.mega.modeling.api.MegaCollection;
import com.mega.modeling.api.MegaObject;
import com.mega.modeling.api.MegaRoot;

public class BaseEventos
  implements AnalysisCallback, AnalysisReportWithContext
{
  private int mainIndex = 0;
  private Date paramFechaRegistro = null;
  private Date paramFechaTermino = null;
  private MegaRoot root;
  private ReportContent content = null;
  private Dimension dim01 = null;
  private Dimension dim02 = null;
  private Dataset dataset01 = null;
  private View view01 = null;
  private Item title01 = null;
  private Item title02 = null;
  private Item title03 = null;
  private Item title04 = null;
  private Item title05 = null;
  private Item title06 = null;
  private Item title07 = null;
  private Item title08 = null;
  private Item title09 = null;
  private Item title10 = null;
  private Item title11 = null;
  private Item title12 = null;
  private Item title13 = null;
  private Item title14 = null;
  private Item title15 = null;
  private Item title16 = null;
  private Item title17 = null;
  private Item title18 = null;
  private Item title19 = null;
  private Item title20 = null;
  private Item title21 = null;
  private Item title22 = null;
  private Item title23 = null;
  private Item title24 = null;
  private Item title25 = null;
  private Item title26 = null;
  private Item title27 = null;
  private Item title28 = null;
  private Item title29 = null;
  private Item title30 = null;
  private Item title31 = null;
  private Item title32 = null;
  private Item title33 = null;
  private Item title34 = null;
  private Item title35 = null;
  private Item title36 = null;
  private Item title37 = null;
  private Item title38 = null;
  private Item title39 = null;
  private Item title40 = null;
  private Item title41 = null;
  private Item title42 = null;
  private Item title43 = null;
  private Item title44 = null;
  private Item title45 = null;
  private Item title46 = null;
  private Item title47 = null;
  private Item title48 = null;
  private Item title49 = null;
  private Item title50 = null;
  private Item title51 = null;
  private Item title52 = null;
  private Item title53 = null;
  private Item title54 = null;
  private Item title55 = null;
  private Item title56 = null;
  private Item title57 = null;
  private Item title58 = null;
  private Item title59 = null;
  private Item title60 = null;
  private Item title61 = null;
  private Item title62 = null;
  private Item title63 = null;
  private Item title64 = null;
  private Item title65 = null;
  private Item title66 = null;
  private Item title67 = null;
  private Item title68 = null;
  private Item title69 = null;
  private Item title70 = null;
  private Item title71 = null;
  private Item title72 = null;
  private Item title73 = null;
  private Item title74 = null;
  private Item title75 = null;
  private Item title76 = null;
  private Item title77 = null;
  private Item title78 = null;
  private Item title79 = null;
  private Item title80 = null;
  private Item title81 = null;
  private Item title82 = null;
  private Item title83 = null;
  private Item title84 = null;
  private Item title85 = null;
  private Item title86 = null;
  private Item title87 = null;
  private Item title88 = null;
  
  public String Callback(MegaRoot root, String htmlCellContent, MegaCollection columnCollection, MegaCollection lineCollection, Object userData)
  {
    MegaCollection collection01 = null;
    collection01 = getCollectionFromCallback(root, (String)userData);
    if (collection01.size() > 0) {
      collection01.invokeMethod("Open", new Object[0]);
    }
    return htmlCellContent;
  }
  
  public ReportContent getReportContent(MegaRoot root, Map<String, List<AnalysisParameter>> params, Analysis analysis, Object obj)
  {
    MegaCollection selection01 = null;
    this.root = root;
    initParams(params);
    this.content = new ReportContent("");
    this.dim01 = new Dimension("");
    this.dim02 = new Dimension("");
    selection01 = this.root.getSelection(assembleQuery01().toString(), new Object[0]);
    if (selection01.size() > 0)
    {
      this.dataset01 = new Dataset("");
      initTitles();
      initIncidentRecords(selection01);
      this.dim01.setSize(this.mainIndex + 1);
      this.dim02.setSize(60);
    }
    else
    {
      this.dataset01 = new Dataset("~8RRwvyVYPfTD[No Hay Informaci�n para Mostrar]");
      this.dim01.setSize(1);
      this.dim02.setSize(1);
    }
    this.dataset01.addDimension(this.dim01);
    this.dataset01.addDimension(this.dim02);
    this.view01 = new View(this.content.addDataset(this.dataset01), true, false);
    this.view01.addRenderer("~WCgdfMQqBzc1[table]");
    this.content.addView(this.view01);
    return this.content;
  }
  
  private MegaCollection getCollectionFromCallback(MegaRoot root, String sComponentListIdabs)
  {
    MegaCollection colReturn = root.getSelection("", new Object[0]);
    if (sComponentListIdabs.length() > 0)
    {
      String sCurrentList = sComponentListIdabs;
      int iVirgule = sCurrentList.indexOf(",");
      while (iVirgule > 0)
      {
        String sBeg = sCurrentList.substring(0, iVirgule);
        sCurrentList = sCurrentList.substring(iVirgule + 1);
        iVirgule = sCurrentList.indexOf(",");
        colReturn.insert(root.getObjectFromID(sBeg));
      }
      colReturn.insert(root.getObjectFromID(sCurrentList));
    }
    return colReturn;
  }
  
  private void initParams(Map<String, List<AnalysisParameter>> params)
  {
    Iterator localIterator2;
    for (Iterator localIterator1 = params.keySet().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
    {
      String paramType = (String)localIterator1.next();
      localIterator2 = ((List)params.get(paramType)).iterator(); 
      AnalysisParameter paramValue = (AnalysisParameter)localIterator2.next();
      if (paramType.equals("933093A258383E03")) {
        for (AnalysisParameter.AnalysisSimpleTypeValue value : paramValue.getSimpleValues()) {
          this.paramFechaRegistro = ((Date)value.getValue());
        }
      } else if (paramType.equals("933093C358383E51")) {
        for (AnalysisParameter.AnalysisSimpleTypeValue value : paramValue.getSimpleValues()) {
          this.paramFechaTermino = ((Date)value.getValue());
        }
      }
    }
  }
  
  private void initIncidentRecords(MegaCollection selection01)
  {
    MegaObject incident = null;
    Set<MegaObject> losses = null;
    Set<MegaObject> gains = null;
    Set<MegaObject> recoveries = null;
    Set<MegaObject> provisions = null;
    Set<MegaObject> riskFactors = null;
    Set<MegaObject> riskConsequences = null;
    Set<MegaObject> actionPlans = null;
    StringBuffer strRiskFactors = new StringBuffer("");
    StringBuffer strRiskConsequences = new StringBuffer("");
    StringBuffer strActionPlans = new StringBuffer("");
    int size = 0;
    this.mainIndex = 0;
    for (int i = 1; i <= selection01.size(); i++)
    {
      strRiskFactors = new StringBuffer("");
      strRiskConsequences = new StringBuffer("");
      strActionPlans = new StringBuffer("");
      incident = selection01.get(Integer.valueOf(i));
      riskFactors = new HashSet();
      riskConsequences = new HashSet();
      actionPlans = new HashSet();
      losses = new HashSet();
      gains = new HashSet();
      recoveries = new HashSet();
      provisions = new HashSet();
      preloadCollection(incident.getCollection("~GjTql)zsHjpR[Risk Factor]", new Object[0]), riskFactors);
      setToString(riskFactors, strRiskFactors);
      preloadCollection(incident.getCollection("~akTqv)zsHLuR[Risk Consequence]", new Object[0]), riskConsequences);
      setToString(riskConsequences, strRiskConsequences);
      preloadCollection(incident.getCollection("~rIkRvtdfEb8S[Action Plan]", new Object[0]), actionPlans);
      setToString(actionPlans, strActionPlans);
      preloadCollection(incident, "~xMxTDpdsHHxP[Loss]", losses);
      preloadCollection(incident, "~()Sr8YjtHDC2[Gain]", gains);
      preloadCollection(incident, "~2KxTMpdsH9)P[Recovery]", recoveries);
      preloadCollection(incident, "~mMxTrqdsHz3Q[Provision]", provisions);
      size = biggestCollection(losses, gains, recoveries, provisions);
      processLines(incident, strRiskFactors.toString(), strRiskConsequences.toString(), strActionPlans.toString(), losses, gains, recoveries, provisions, size);
    }
  }
  
  private void initTitles()
  {
    this.title01 = new Text("~latXiTvPP5pE[C�digo Evento]", true);
    this.title01.setOrderable(true);
    this.dim02.addItem(this.title01);
    this.title02 = new Text("~FbtX(cvPPXFG[L�nea de Negocio Nivel 1]", true);
    this.title02.setOrderable(true);
    this.dim02.addItem(this.title02);
    this.title03 = new Text("~qSJl4zC4KbB4[L�nea de Negocio Nivel 2]", true);
    this.title03.setOrderable(true);
    this.dim02.addItem(this.title03);
    this.title04 = new Text("~GatXkUvPP1zE[Entidad del Declarador]", true);
    this.title04.setOrderable(true);
    this.dim02.addItem(this.title04);
    this.title05 = new Text("~bdtXa3vPPHOB[Gestor del Riesgo]", true);
    this.title05.setOrderable(true);
    this.dim02.addItem(this.title05);
    this.title06 = new Text("~sctXQdvPP9KG[Canal Afectado]", true);
    this.title06.setOrderable(true);
    this.dim02.addItem(this.title06);
    this.title07 = new Text("~AdtXvsuPPzP9[Procesos de Negocio]", true);
    this.title07.setOrderable(true);
    this.dim02.addItem(this.title07);
    this.title08 = new Text("~xdtX0tuPPbS9[Procesos Organizacionales]", true);
    this.title08.setOrderable(true);
    this.dim02.addItem(this.title08);
    this.title09 = new Text("~VqggwbhBLDzF[Fecha de Inicio]", true);
    this.title09.setOrderable(true);
    this.dim02.addItem(this.title09);
    this.title10 = new Text("~EqV0SqGVJHyK[Hora de Inicio]", true);
    this.title10.setOrderable(true);
    this.dim02.addItem(this.title10);
    this.title11 = new Text("~bnA90G3BLrwC[Fecha de T�rmino]", true);
    this.title11.setOrderable(true);
    this.dim02.addItem(this.title11);
    this.title12 = new Text("~actX5YvPPfOF[Hora de T�rmino]", true);
    this.title12.setOrderable(true);
    this.dim02.addItem(this.title12);
    this.title13 = new Text("~GdtXIXvPPfEF[Fecha de Detecci�n]", true);
    this.title13.setOrderable(true);
    this.dim02.addItem(this.title13);
    this.title14 = new Text("~matXdXvPPHJF[Hora de Detecci�n]", true);
    this.title14.setOrderable(true);
    this.dim02.addItem(this.title14);
    this.title15 = new Text("~actXq8vPPvEC[Factores de Riesgo]", true);
    this.title15.setOrderable(true);
    this.dim02.addItem(this.title15);
    this.title16 = new Text("~EctX1UvPPjtE[Nombre Evento]", true);
    this.title16.setOrderable(true);
    this.dim02.addItem(this.title16);
    this.title17 = new Text("~NatXn4vPPTcB[Descripci�n Detallada]", true);
    this.title17.setOrderable(true);
    this.dim02.addItem(this.title17);
    this.title18 = new Text("~enA9Iz2BLbMB[Tipo de P�rdida SF]", true);
    this.title18.setOrderable(true);
    this.dim02.addItem(this.title18);
    this.title19 = new Text("~iatXUfvPP9ZG[Nombre P�rdida]", true);
    this.title19.setOrderable(true);
    this.dim02.addItem(this.title19);
    this.title20 = new Text("~DctXrfvPPndG[Descripci�n P�rdida]", true);
    this.title20.setOrderable(true);
    this.dim02.addItem(this.title20);
    this.title21 = new Text("~DZw2S8ZbPHRA[Tipo de P�rdida]", true);
    this.title21.setOrderable(true);
    this.dim02.addItem(this.title21);
    this.title22 = new Text("~3dtX7hvPPzrG[Naturaleza P�rdida]", true);
    this.title22.setOrderable(true);
    this.dim02.addItem(this.title22);
    this.title23 = new Text("~iatX6ovPP90I[Fecha Efectiva P�rdida]", true);
    this.title23.setOrderable(true);
    this.dim02.addItem(this.title23);
    this.title24 = new Text("~4ctXqhvPPD)G[Monto P�rdida]", true);
    this.title24.setOrderable(true);
    this.dim02.addItem(this.title24);
    this.title25 = new Text("~idtXGivPPr3H[Monto(Local) P�rdida]", true);
    this.title25.setOrderable(true);
    this.dim02.addItem(this.title25);
    this.title26 = new Text("~rdtXEgvPPjiG[Fecha Contabilizaci�n P�rdida]", true);
    this.title26.setOrderable(true);
    this.dim02.addItem(this.title26);
    this.title27 = new Text("~YbtXmgvPPLnG[Hora Contabilizaci�n P�rdida]", true);
    this.title27.setOrderable(true);
    this.dim02.addItem(this.title27);
    this.title28 = new Text("~YatXShvPPbwG[Cuenta P�rdida]", true);
    this.title28.setOrderable(true);
    this.dim02.addItem(this.title28);
    this.title29 = new Text("~7ctXo7wPPTUJ[Documento Contable P�rdida]", true);
    this.title29.setOrderable(true);
    this.dim02.addItem(this.title29);
    this.title30 = new Text("~qQRwTJXYPXGE[Perdida Bruta]", true);
    this.title30.setOrderable(true);
    this.dim02.addItem(this.title30);
    this.title31 = new Text("~5RRwEHXYPbAE[Perdida Neta]", true);
    this.title31.setOrderable(true);
    this.dim02.addItem(this.title31);
    this.title32 = new Text("~h5eEUZlbPPOP[P�rdida Actual Bruta]", true);
    this.title32.setOrderable(true);
    this.dim02.addItem(this.title32);
    this.title33 = new Text("~k7eEzZlbPfUP[P�rdida Actual Neta]", true);
    this.title33.setOrderable(true);
    this.dim02.addItem(this.title33);
    this.title34 = new Text("~MbtXmivPPT8H[Nombre Ganancia]", true);
    this.title34.setOrderable(true);
    this.dim02.addItem(this.title34);
    this.title35 = new Text("~tctX7jvPP5DH[Descripci�n Ganancia]", true);
    this.title35.setOrderable(true);
    this.dim02.addItem(this.title35);
    this.title36 = new Text("~i4eEBSlbPH2N[Tipo de P�rdida Ganancia]", true);
    this.title36.setOrderable(true);
    this.dim02.addItem(this.title36);
    this.title37 = new Text("~sdtXxnvPPXzH[Fecha Efectiva Ganancia]", true);
    this.title37.setOrderable(true);
    this.dim02.addItem(this.title37);
    this.title38 = new Text("~NbtXvkvPPvVH[Monto Ganancia]", true);
    this.title38.setOrderable(true);
    this.dim02.addItem(this.title38);
    this.title39 = new Text("~2dtXPlvPPXaH[Monto(Local) Ganancia]", true);
    this.title39.setOrderable(true);
    this.dim02.addItem(this.title39);
    this.title40 = new Text("~catXhjvPPjHH[Fecha Contabilizaci�n Ganancia]", true);
    this.title40.setOrderable(true);
    this.dim02.addItem(this.title40);
    this.title41 = new Text("~GctXBkvPPLMH[Hora Contabilizaci�n Ganancia]", true);
    this.title41.setOrderable(true);
    this.dim02.addItem(this.title41);
    this.title42 = new Text("~rdtXckvPPzQH[Cuenta Ganancia]", true);
    this.title42.setOrderable(true);
    this.dim02.addItem(this.title42);
    this.title43 = new Text("~ndtX68wPPjZJ[Documento Contable Ganancia]", true);
    this.title43.setOrderable(true);
    this.dim02.addItem(this.title43);
    this.title44 = new Text("~fPRwkKXYPjME[Ganancia Bruta]", true);
    this.title44.setOrderable(true);
    this.dim02.addItem(this.title44);
    this.title45 = new Text("~ZatXmlvPP9fH[Nombre Recuperaci�n]", true);
    this.title45.setOrderable(true);
    this.dim02.addItem(this.title45);
    this.title46 = new Text("~DctXFmvPPnjH[Descripci�n Recuperaci�n]", true);
    this.title46.setOrderable(true);
    this.dim02.addItem(this.title46);
    this.title47 = new Text("~3atXDqvPP1LI[Tipo P�rdida Recuperaci�n]", true);
    this.title47.setOrderable(true);
    this.dim02.addItem(this.title47);
    this.title48 = new Text("~c7eE(HmbPDaT[Tipo Recuperaci�n]", true);
    this.title48.setOrderable(true);
    this.dim02.addItem(this.title48);
    this.title49 = new Text("~RDs7smIqJvGH[P�rdida Recuperada]", true);
    this.title49.setOrderable(true);
    this.dim02.addItem(this.title49);
    this.title50 = new Text("~HbtX(mvPP1tH[Fecha Efectiva Recuperaci�n]", true);
    this.title50.setOrderable(true);
    this.dim02.addItem(this.title50);
    this.title51 = new Text("~OdtXErvPPHUI[Monto Recuperaci�n]", true);
    this.title51.setOrderable(true);
    this.dim02.addItem(this.title51);
    this.title52 = new Text("~4btXlrvPPvYI[Monto(Local) Recuperaci�n]", true);
    this.title52.setOrderable(true);
    this.dim02.addItem(this.title52);
    this.title53 = new Text("~iatXHpvPPTBI[Fecha Contabilizaci�n Recuperaci�n]", true);
    this.title53.setOrderable(true);
    this.dim02.addItem(this.title53);
    this.title54 = new Text("~cXw2MIZbP1jA[Hora Recuperaci�n]", true);
    this.title54.setOrderable(true);
    this.dim02.addItem(this.title54);
    this.title55 = new Text("~dbtXdqvPPfPI[Cuenta Recuperaci�n]", true);
    this.title55.setOrderable(true);
    this.dim02.addItem(this.title55);
    this.title56 = new Text("~catXH8wPPLcJ[Documento Contable Recuperaci�n]", true);
    this.title56.setOrderable(true);
    this.dim02.addItem(this.title56);
    this.title57 = new Text("~C6eEvolbP1SR[Recuperaciones]", true);
    this.title57.setOrderable(true);
    this.dim02.addItem(this.title57);
    this.title58 = new Text("~WORw8MXYPXSE[Recuperaciones Por Seguros]", true);
    this.title58.setOrderable(true);
    this.dim02.addItem(this.title58);
    this.title59 = new Text("~ZbtXi3wPPnyI[Nombre Provisi�n]", true);
    this.title59.setOrderable(true);
    this.dim02.addItem(this.title59);
    this.title60 = new Text("~M3x78AobPjZC[Descripci�n Provisi�n]", true);
    this.title60.setOrderable(true);
    this.dim02.addItem(this.title60);
    this.title61 = new Text("~A7eEarlbP5bR[Tipo P�rdida Provisi�n]", true);
    this.title61.setOrderable(true);
    this.dim02.addItem(this.title61);
    this.title62 = new Text("~81UDdZLfLjwJ[Perdida Provisionada]", true);
    this.title62.setOrderable(true);
    this.dim02.addItem(this.title62);
    this.title63 = new Text("~abtXKovPPn2I[Fecha Efectiva Provisi�n]", true);
    this.title63.setOrderable(true);
    this.dim02.addItem(this.title63);
    this.title64 = new Text("~tbtXF5wPPj7J[Monto Provisi�n]", true);
    this.title64.setOrderable(true);
    this.dim02.addItem(this.title64);
    this.title65 = new Text("~fdtXt5wPPLCJ[Monto(Local) Provisi�n]", true);
    this.title65.setOrderable(true);
    this.dim02.addItem(this.title65);
    this.title66 = new Text("~7btXP6wPP1GJ[Fecha Contabilizaci�n Provisi�n]", true);
    this.title66.setOrderable(true);
    this.dim02.addItem(this.title66);
    this.title67 = new Text("~lctXt6wPPfKJ[Hora Contabilizaci�n Provisi�n]", true);
    this.title67.setOrderable(true);
    this.dim02.addItem(this.title67);
    this.title68 = new Text("~LatXJ7wPPHPJ[Cuenta Provisi�n]", true);
    this.title68.setOrderable(true);
    this.dim02.addItem(this.title68);
    this.title69 = new Text("~)ctX)7wPP5XJ[Documento Contable Provisi�n]", true);
    this.title69.setOrderable(true);
    this.dim02.addItem(this.title69);
    this.title70 = new Text("~WpgYWlubP1nF[Provisiones]", true);
    this.title70.setOrderable(true);
    this.dim02.addItem(this.title70);
    this.title71 = new Text("~6btXDevPPvPG[Zona Geogr�fica]", true);
    this.title71.setOrderable(true);
    this.dim02.addItem(this.title71);
    this.title72 = new Text("~ioA9F83BLrUB[Tipo de Evento]", true);
    this.title72.setOrderable(true);
    this.dim02.addItem(this.title72);
    this.title73 = new Text("~GctXEbvPPfwF[Riesgo Materializado]", true);
    this.title73.setOrderable(true);
    this.dim02.addItem(this.title73);
    this.title74 = new Text("~0atX59vPPXJC[Consecuencias de Riesgo]", true);
    this.title74.setOrderable(true);
    this.dim02.addItem(this.title74);
    this.title75 = new Text("~wctXZVvPPH4F[Fecha de Registro de Evento]", true);
    this.title75.setOrderable(true);
    this.dim02.addItem(this.title75);
    this.title76 = new Text("~AatXXYvPPHTF[Fecha para Reportes]", true);
    this.title76.setOrderable(true);
    this.dim02.addItem(this.title76);
    this.title77 = new Text("~OdtXQZvPPXcF[Eventos Cercanos a la P�rdida]", true);
    this.title77.setOrderable(true);
    this.dim02.addItem(this.title77);
    this.title78 = new Text("~YbtXnGvPPbAD[Nombre Plan de Acci�n]", true);
    this.title78.setOrderable(true);
    this.dim02.addItem(this.title78);
    this.title79 = new Text("~NbtX1avPPzhF[Estado Evento]", true);
    this.title79.setOrderable(true);
    this.dim02.addItem(this.title79);
    this.title80 = new Text("~CbtX0VvPPf)E[Compa��a Afectada]", true);
    this.title80.setOrderable(true);
    this.dim02.addItem(this.title80);
    this.title81 = new Text("~cctXWuuPP5n9[Requerimientos]", true);
    this.title81.setOrderable(true);
    this.dim02.addItem(this.title81);
    this.title82 = new Text("~obtX(YvPPvXF[Macro Eventos]", true);
    this.title82.setOrderable(true);
    this.dim02.addItem(this.title82);
    this.title83 = new Text("~XbtX5cvPPD4G[Naturaleza del Negocio Fiduciario]", true);
    this.title83.setOrderable(true);
    this.dim02.addItem(this.title83);
    this.title84 = new Text("~uctX4JvPPfcD[Origen]", true);
    this.title84.setOrderable(true);
    this.dim02.addItem(this.title84);
    this.title85 = new Text("~rdtXabvPPb)F[Control Fallido]", true);
    this.title85.setOrderable(true);
    this.dim02.addItem(this.title85);
    this.title86 = new Text("~1dtXRcvPPr8G[Entidad Afectada]", true);
    this.title86.setOrderable(true);
    this.dim02.addItem(this.title86);
    this.title87 = new Text("~HdtXX5vPP5lB[Productos]", true);
    this.title87.setOrderable(true);
    this.dim02.addItem(this.title87);
    this.title88 = new Text("~adtXFxuPPz9A[Aplicaciones]", true);
    this.title88.setOrderable(true);
    this.dim02.addItem(this.title88);
  }
  
  private StringBuffer assembleQuery01()
  {
    StringBuffer query01 = new StringBuffer("");
    query01.append("Select ~iKxTxodsHTpP[Eventos] ");
    if ((this.paramFechaRegistro != null) && (this.paramFechaTermino == null)) {
      query01.append(" where ~LLxTvzhsHjrQ[Detection Date] >= '").append(dateToStringMDYYYY(this.paramFechaRegistro)).append("'");
    }
    if ((this.paramFechaTermino != null) && (this.paramFechaRegistro == null)) {
      query01.append(" where ~LLxTvzhsHjrQ[Detection Date] <= '").append(dateToStringMDYYYY(this.paramFechaTermino)).append("'");
    }
    if ((this.paramFechaTermino != null) && (this.paramFechaRegistro != null))
    {
      query01.append(" where ~LLxTvzhsHjrQ[Detection Date] >= '").append(dateToStringMDYYYY(this.paramFechaRegistro)).append("'");
      query01.append(" and ~LLxTvzhsHjrQ[Detection Date] <= '").append(dateToStringMDYYYY(this.paramFechaTermino)).append("'");
    }
    return query01;
  }
  
  private StringBuffer assembleQuery02(MegaObject incident, String role)
  {
    StringBuffer query02 = new StringBuffer("");
    query02.append("Select ~030000000240[Person Assignment] ")
      .append("Where ~hCr81RIpEvMH[Assigned Object]:~iKxTxodsHTpP[Eventos].~H20000000550[_HexaIdAbs] = '")
      .append(incident.getProp("~H20000000550[_HexaIdAbs]")).append("' And ~M2000000Ce80[Business Role] = '")
      .append(role).append("'");
    return query02;
  }
  
  private StringBuffer assembleQuery03(MegaObject incident, String incidentFinancialElement)
  {
    StringBuffer query03 = new StringBuffer("");
    query03.append("Select ").append(incidentFinancialElement)
      .append(" Where ~IMxT6HisHHSR[Incident].~H20000000550[_HexaIdAbs] = '")
      .append(incident.getProp("~H20000000550[_HexaIdAbs]")).append("'");
    return query03;
  }
  
  private StringBuffer assembleQuery04(MegaObject incident, String object, String objectMAE)
  {
    StringBuffer query04 = new StringBuffer("");
    query04.append("Select ").append(object).append(" Where ").append(objectMAE).append(".~H20000000550[_HexaIdAbs] = '")
      .append(incident.getProp("~H20000000550[_HexaIdAbs]")).append("'");
    return query04;
  }
  
  private MegaObject findGestor(MegaObject incident)
  {
    MegaObject person = null;
    MegaCollection selection02 = null;
    selection02 = this.root.getSelection(assembleQuery02(incident, "~fecV31u5JXEB[Gestor de Riesgos]").toString(), new Object[0]);
    if ((selection02 != null) && (selection02.size() > 0)) {
      person = selection02.get(Integer.valueOf(1)).getCollection("~L2000000Ca80[Assigned Person]", new Object[0]).get(Integer.valueOf(1));
    }
    return person;
  }
  
  private void setToString(Set<MegaObject> objSet, StringBuffer strObj)
  {
    if (objSet.size() > 0) {
      for (MegaObject obj : objSet)
      {
        if (strObj.length() > 0) {
          strObj.append(" | ");
        }
        strObj.append(obj.getProp("~Z20000000D60[Short Name]"));
      }
    }
  }
  
  private void preloadCollection(MegaCollection collection, Set<MegaObject> set)
  {
    if (collection.size() > 0) {
      for (int collectionIndex = 1; collectionIndex <= collection.size(); collectionIndex++) {
        set.add(collection.get(Integer.valueOf(collectionIndex)));
      }
    }
  }
  
  private void preloadCollection(MegaObject incident, String objectType, Set<MegaObject> set)
  {
    MegaCollection selection03 = null;
    selection03 = this.root.getSelection(assembleQuery03(incident, objectType).toString(), new Object[0]);
    if (selection03.size() > 0) {
      for (int j = 1; j <= selection03.size(); j++) {
        set.add(selection03.get(Integer.valueOf(j)));
      }
    }
  }
  
  private int biggestCollection(Set<MegaObject> losses, Set<MegaObject> gains, Set<MegaObject> recoveries, Set<MegaObject> provisions)
  {
    int size = 0;
    if ((losses != null) && (losses.size() > size)) {
      size = losses.size();
    }
    if ((gains != null) && (gains.size() > size)) {
      size = gains.size();
    }
    if ((recoveries != null) && (recoveries.size() > size)) {
      size = recoveries.size();
    }
    if ((provisions != null) && (provisions.size() > size)) {
      size = provisions.size();
    }
    return size;
  }
  
  private void processLines(MegaObject incident, String riskFactors, String riskConsequences, String actionPlans, Set<MegaObject> lossesSet, Set<MegaObject> gainsSet, Set<MegaObject> recoveriesSet, Set<MegaObject> provisionsSet, int size)
  {
    MegaObject lossAccount = null;
    MegaObject gainAccount = null;
    MegaObject recoveryAccount = null;
    MegaObject provisionAccount = null;
    MegaObject loss = null;
    MegaObject gain = null;
    MegaObject recovery = null;
    MegaObject provision = null;
    List<MegaObject> losses = null;
    List<MegaObject> gains = null;
    List<MegaObject> recoveries = null;
    List<MegaObject> provisions = null;
    if (size > 0)
    {
      losses = new ArrayList(lossesSet);
      gains = new ArrayList(gainsSet);
      recoveries = new ArrayList(recoveriesSet);
      provisions = new ArrayList(provisionsSet);
      for (int i = 0; i < size; i++)
      {
        this.mainIndex += 1;
        incidentData(incident);
        if (losses.size() > i)
        {
          loss = (MegaObject)losses.get(i);
          this.dataset01.addItem(new Text(loss.getProp("~g20000000f60[Generic Local name]"), false), this.mainIndex + ",19");
          this.dataset01.addItem(new Text(String.valueOf(loss.getProp("~f10000000b20[Comment]", "DISPLAY")), false), this.mainIndex + ",20");
          this.dataset01.addItem(new Text(String.valueOf(loss.getProp("~z0Pi1ShuJ5hN[Tipo de P�rdida]", "EXTERNAL")), false), this.mainIndex + ",21");
          this.dataset01.addItem(new Text(String.valueOf(loss.getProp("~eMxTVgisHbtR[Nature \\Loss]", "EXTERNAL")), false), this.mainIndex + ",22");
          this.dataset01.addItem(new Text(dateToStringMDYYYY((Date)loss.getProp("~FKxTicisHLjR[Effective date]", "INTERNAL")), false), this.mainIndex + ",23");
          this.dataset01.addItem(new Text(String.valueOf(loss.getProp("~bKxT)KisHnbR[Amount]", "EXTERNAL")), false), this.mainIndex + ",24");
          this.dataset01.addItem(new Text(String.valueOf(loss.getProp("~cTMFOzJJIvYN[Amount (local)]", "EXTERNAL")), false), this.mainIndex + ",25");
          this.dataset01.addItem(new Text(dateToStringMDYYYY((Date)loss.getProp("~RE67pdZVJzCK[Fecha de Contabilizaci�nn]", "INTERNAL")), false), this.mainIndex + ",26");
          this.dataset01.addItem(new Text(loss.getProp("~vadwohU6K9pM[Hora de Contabilizaci�n]"), false), this.mainIndex + ",27");
          if (loss.getCollection("~QXPMqfr0IzmD[Account]", new Object[0]).size() > 0)
          {
            lossAccount = loss.getCollection("~QXPMqfr0IzmD[Account]", new Object[0]).get(Integer.valueOf(1));
            this.dataset01.addItem(new Text(lossAccount.getProp("~Z20000000D60[Short Name]"), false), this.mainIndex + ",28");
          }
          this.dataset01.addItem(new Text(loss.getProp("~PF67OfZVJPQK[Documento Contable]"), false), this.mainIndex + ",29");
        }
        if (gains.size() > i)
        {
          gain = (MegaObject)gains.get(i);
          this.dataset01.addItem(new Text(gain.getProp("~g20000000f60[Generic Local name]"), false), this.mainIndex + ",34");
          this.dataset01.addItem(new Text(String.valueOf(gain.getProp("~f10000000b20[Comment]", "DISPLAY")), false), this.mainIndex + ",35");
          this.dataset01.addItem(new Text(String.valueOf(gain.getProp("~z0Pi1ShuJ5hN[Tipo de P�rdida]", "EXTERNAL")), false), this.mainIndex + ",36");
          this.dataset01.addItem(new Text(dateToStringMDYYYY((Date)gain.getProp("~FKxTicisHLjR[Effective date]", "INTERNAL")), false), this.mainIndex + ",37");
          this.dataset01.addItem(new Text(String.valueOf(gain.getProp("~bKxT)KisHnbR[Amount]", "EXTERNAL")), false), this.mainIndex + ",38");
          this.dataset01.addItem(new Text(String.valueOf(gain.getProp("~cTMFOzJJIvYN[Amount (local)]", "EXTERNAL")), false), this.mainIndex + ",39");
          this.dataset01.addItem(new Text(dateToStringMDYYYY((Date)gain.getProp("~RE67pdZVJzCK[Fecha de Contabilizaci�nn]", "INTERNAL")), false), this.mainIndex + ",40");
          this.dataset01.addItem(new Text(gain.getProp("~vadwohU6K9pM[Hora de Contabilizaci�n]"), false), this.mainIndex + ",41");
          if (gain.getCollection("~QXPMqfr0IzmD[Account]", new Object[0]).size() > 0)
          {
            gainAccount = gain.getCollection("~QXPMqfr0IzmD[Account]", new Object[0]).get(Integer.valueOf(1));
            this.dataset01.addItem(new Text(gainAccount.getProp("~Z20000000D60[Short Name]"), false), this.mainIndex + ",42");
          }
          this.dataset01.addItem(new Text(gain.getProp("~PF67OfZVJPQK[Documento Contable]"), false), this.mainIndex + ",43");
        }
        if (recoveries.size() > i)
        {
          recovery = (MegaObject)recoveries.get(i);
          this.dataset01.addItem(new Text(recovery.getProp("~g20000000f60[Generic Local name]"), false), this.mainIndex + ",45");
          this.dataset01.addItem(new Text(String.valueOf(recovery.getProp("~f10000000b20[Comment]", "DISPLAY")), false), this.mainIndex + ",46");
          this.dataset01.addItem(new Text(String.valueOf(recovery.getProp("~z0Pi1ShuJ5hN[Tipo de P�rdida]", "EXTERNAL")), false), this.mainIndex + ",47");
          this.dataset01.addItem(new Text(String.valueOf(recovery.getProp("~FD67TYbVJPBL[Tipo de Recuperaci�n]", "EXTERNAL")), false), this.mainIndex + ",48");
          this.dataset01.addItem(new Text(String.valueOf(recovery.getProp("~YLbOFFMfLbc5[Perdida Recuperada]", "EXTERNAL")), false), this.mainIndex + ",49");
          this.dataset01.addItem(new Text(dateToStringMDYYYY((Date)recovery.getProp("~FKxTicisHLjR[Effective date]", "INTERNAL")), false), this.mainIndex + ",50");
          this.dataset01.addItem(new Text(String.valueOf(recovery.getProp("~bKxT)KisHnbR[Amount]", "EXTERNAL")), false), this.mainIndex + ",51");
          this.dataset01.addItem(new Text(String.valueOf(recovery.getProp("~cTMFOzJJIvYN[Amount (local)]", "EXTERNAL")), false), this.mainIndex + ",52");
          this.dataset01.addItem(new Text(dateToStringMDYYYY((Date)recovery.getProp("~RE67pdZVJzCK[Fecha de Contabilizaci�nn]", "INTERNAL")), false), this.mainIndex + ",53");
          this.dataset01.addItem(new Text(recovery.getProp("~vadwohU6K9pM[Hora de Contabilizaci�n]"), false), this.mainIndex + ",54");
          if (recovery.getCollection("~QXPMqfr0IzmD[Account]", new Object[0]).size() > 0)
          {
            recoveryAccount = recovery.getCollection("~QXPMqfr0IzmD[Account]", new Object[0]).get(Integer.valueOf(1));
            this.dataset01.addItem(new Text(recoveryAccount.getProp("~Z20000000D60[Short Name]"), false), this.mainIndex + ",55");
          }
          this.dataset01.addItem(new Text(recovery.getProp("~PF67OfZVJPQK[Documento Contable]"), false), this.mainIndex + ",56");
        }
        if (provisions.size() > i)
        {
          provision = (MegaObject)provisions.get(i);
          this.dataset01.addItem(new Text(provision.getProp("~g20000000f60[Generic Local name]"), false), this.mainIndex + ",59");
          this.dataset01.addItem(new Text(String.valueOf(provision.getProp("~f10000000b20[Comment]", "DISPLAY")), false), this.mainIndex + ",60");
          this.dataset01.addItem(new Text(String.valueOf(provision.getProp("~z0Pi1ShuJ5hN[Tipo de P�rdida]", "EXTERNAL")), false), this.mainIndex + ",61");
          this.dataset01.addItem(new Text(String.valueOf(provision.getProp("~rLbOhHMfLDj8[Perdida Provisionada]", "EXTERNAL")), false), this.mainIndex + ",62");
          this.dataset01.addItem(new Text(dateToStringMDYYYY((Date)provision.getProp("~FKxTicisHLjR[Effective date]", "INTERNAL")), false), this.mainIndex + ",63");
          this.dataset01.addItem(new Text(String.valueOf(provision.getProp("~bKxT)KisHnbR[Amount]", "EXTERNAL")), false), this.mainIndex + ",64");
          this.dataset01.addItem(new Text(String.valueOf(provision.getProp("~cTMFOzJJIvYN[Amount <local>]", "EXTERNAL")), false), this.mainIndex + ",65");
          this.dataset01.addItem(new Text(dateToStringMDYYYY((Date)provision.getProp("~RE67pdZVJzCK[Fecha de Contabilizaci�nn]", "INTERNAL")), false), this.mainIndex + ",66");
          this.dataset01.addItem(new Text(String.valueOf(provision.getProp("~vadwohU6K9pM[Hora de Contabilizaci�n]", "INTERNAL")), false), this.mainIndex + ",67");
          if (provision.getCollection("~QXPMqfr0IzmD[Account]", new Object[0]).size() > 0)
          {
            provisionAccount = provision.getCollection("~QXPMqfr0IzmD[Account]", new Object[0]).get(Integer.valueOf(1));
            this.dataset01.addItem(new Text(provisionAccount.getProp("~Z20000000D60[Short Name]"), false), this.mainIndex + ",68");
          }
          this.dataset01.addItem(new Text(provision.getProp("~PF67OfZVJPQK[Documento Contable]"), false), this.mainIndex + ",69");
        }
        this.dataset01.addItem(new Text(riskFactors, false), this.mainIndex + ",15");
        this.dataset01.addItem(new Text(riskConsequences, false), this.mainIndex + ",74");
        this.dataset01.addItem(new Text(actionPlans, false), this.mainIndex + ",78");
      }
    }
    else
    {
      this.mainIndex += 1;
      incidentData(incident);
      this.dataset01.addItem(new Text(riskFactors, false), this.mainIndex + ",15");
      this.dataset01.addItem(new Text(riskConsequences, false), this.mainIndex + ",74");
      this.dataset01.addItem(new Text(actionPlans, false), this.mainIndex + ",78");
    }
  }
  
  private void incidentData(MegaObject incident)
  {
    this.dataset01.addItem(new Text(incident.getProp("~WtUi89B5iSG0[Code]"), false), this.mainIndex + ",1");
    if (incident.getCollection("~IlTqqWzsHLOQ[Business Line]", new Object[0]).size() > 0)
    {
      MegaObject businessLine = incident.getCollection("~IlTqqWzsHLOQ[Business Line]", new Object[0]).get(Integer.valueOf(1));
      this.dataset01.addItem(new Text(businessLine.getProp("~Z20000000D60[Short Name]"), false), this.mainIndex + ",2");
    }
    if (incident.getCollection("~)SJlxuC4Kzy3[Business Line-2]", new Object[0]).size() > 0)
    {
      MegaObject businessLine = incident.getCollection("~)SJlxuC4Kzy3[Business Line-2]", new Object[0]).get(Integer.valueOf(1));
      this.dataset01.addItem(new Text(businessLine.getProp("~Z20000000D60[Short Name]"), false), this.mainIndex + ",3");
    }
    if (incident.getCollection("~p0b9Go4tH9eC[Declarant's Entity]", new Object[0]).size() > 0)
    {
      MegaObject declarantEntity = incident.getCollection("~p0b9Go4tH9eC[Declarant's Entity]", new Object[0]).get(Integer.valueOf(1));
      this.dataset01.addItem(new Text(declarantEntity.getProp("~Z20000000D60[Short Name]"), false), this.mainIndex + ",4");
    }
    MegaObject gestor = findGestor(incident);
    if (gestor != null) {
      this.dataset01.addItem(new Text(gestor.getProp("~Z20000000D60[Short Name]"), false), this.mainIndex + ",5");
    }
    this.dataset01.addItem(new Text(String.valueOf(incident.getProp("~lqD1R9p9JD0H[Canal Afectado]]", "EXTERNAL")), false), this.mainIndex + ",6");
    if (incident.getCollection("~pjTq0XzsHbXQ[Business Process]", new Object[0]).size() > 0)
    {
      MegaObject businessProcess = incident.getCollection("~pjTq0XzsHbXQ[Business Process]", new Object[0]).get(Integer.valueOf(1));
      this.dataset01.addItem(new Text(businessProcess.getProp("~Z20000000D60[Short Name]"), false), this.mainIndex + ",7");
    }
    if (incident.getCollection("~4lTq7XzsHDcQ[Organizational Process]", new Object[0]).size() > 0)
    {
      MegaObject organizationalProcess = incident.getCollection("~4lTq7XzsHDcQ[Organizational Process]", new Object[0]).get(Integer.valueOf(1));
      this.dataset01.addItem(new Text(organizationalProcess.getProp("~Z20000000D60[Short Name]"), false), this.mainIndex + ",8");
    }
    this.dataset01.addItem(new Text(dateToStringMDYYYY((Date)incident.getProp("~KMxT3(hsH1vQ[Occurrence Date]", "INTERNAL")), false), this.mainIndex + ",9");
    this.dataset01.addItem(new Text(incident.getProp("~4BW9XfIwJr7M[Hora de Inicio]"), false), this.mainIndex + ",10");
    this.dataset01.addItem(new Text(dateToStringMDYYYY((Date)incident.getProp("~HKLYplv2Jj0F[Fecha T�rmino]", "INTERNAL")), false), this.mainIndex + ",11");
    this.dataset01.addItem(new Text(incident.getProp("~5AW9NfIwJX4M[Hora de T�rmino]"), false), this.mainIndex + ",12");
    this.dataset01.addItem(new Text(dateToStringMDYYYY((Date)incident.getProp("~LLxTvzhsHjrQ[Detection Date]", "INTERNAL")), false), this.mainIndex + ",13");
    this.dataset01.addItem(new Text(incident.getProp("~QBW9CbIwJfwL[Hora de Detecci�n]"), false), this.mainIndex + ",14");
    this.dataset01.addItem(new Text(incident.getProp("~Z20000000D60[Short Name]"), false), this.mainIndex + ",16");
    this.dataset01.addItem(new Text(String.valueOf(incident.getProp("~f10000000b20[Comment]", "DISPLAY")), false), this.mainIndex + ",17");
    this.dataset01.addItem(new Text(String.valueOf(incident.getProp("~qKxTv(hsHf)Q[Nature \\Incident]", "EXTERNAL")), false), this.mainIndex + ",18");
    this.dataset01.addItem(new Text(String.valueOf(incident.getProp("~)NxTDrhsHLJQ[Gross Loss]", "EXTERNAL")), false), this.mainIndex + ",30");
    this.dataset01.addItem(new Text(String.valueOf(incident.getProp("~0LxTPrhsHfMQ[Net Loss]", "EXTERNAL")), false), this.mainIndex + ",31");
    this.dataset01.addItem(new Text(String.valueOf(incident.getProp("~VLxTevhsHXdQ[Gross Actual Loss]", "EXTERNAL")), false), this.mainIndex + ",32");
    this.dataset01.addItem(new Text(String.valueOf(incident.getProp("~QNxTvvhsH9kQ[Net Actual Loss]", "EXTERNAL")), false), this.mainIndex + ",33");
    this.dataset01.addItem(new Text(String.valueOf(incident.getProp("~FRjFlhLJJTuI[Ganancia Bruta]", "EXTERNAL")), false), this.mainIndex + ",44");
    this.dataset01.addItem(new Text(String.valueOf(incident.getProp("~UMxTovhsHrgQ[Recoveries]", "EXTERNAL")), false), this.mainIndex + ",57");
    this.dataset01.addItem(new Text(String.valueOf(incident.getProp("~jRjFobLJJDoI[Recuperaciones Por Seguro]", "EXTERNAL")), false), this.mainIndex + ",58");
    this.dataset01.addItem(new Text(String.valueOf(incident.getProp("~JQjFdiLJJ51J[Provisiones]", "EXTERNAL")), false), this.mainIndex + ",70");
    if (incident.getCollection("~NtD1xFp9JXWH[Sitio]", new Object[0]).size() > 0)
    {
      MegaObject site = incident.getCollection("~NtD1xFp9JXWH[Sitio]", new Object[0]).get(Integer.valueOf(1));
      this.dataset01.addItem(new Text(site.getProp("~Z20000000D60[Short Name]"), false), this.mainIndex + ",71");
    }
    if (incident.getCollection("~yjTqeWzsHjJQ[Risk Type]", new Object[0]).size() > 0)
    {
      MegaObject riskType = incident.getCollection("~yjTqeWzsHjJQ[Risk Type]", new Object[0]).get(Integer.valueOf(1));
      this.dataset01.addItem(new Text(riskType.getProp("~Z20000000D60[Short Name]"), false), this.mainIndex + ",72");
    }
    MegaCollection risks = this.root.getSelection(assembleQuery04(incident, "~W5faeGPxySL0[Risk]", "~uiTqb(zsHvcR[Incident]").toString(), new Object[0]);
    if ((risks != null) && (risks.size() > 0))
    {
      MegaObject risk = risks.get(Integer.valueOf(1));
      this.dataset01.addItem(new Text(risk.getProp("~Z20000000D60[Short Name]"), false), this.mainIndex + ",73");
    }
    this.dataset01.addItem(new Text(dateToStringMDYYYY((Date)incident.getProp("~C0Xc(KhNJrMK[Fecha de Registro de Evento]", "INTERNAL")), false), this.mainIndex + ",75");
    this.dataset01.addItem(new Text(dateToStringMDYYYY((Date)incident.getProp("~1iA0xnn8JLEP[Fecha Para Reportes]", "INTERNAL")), false), this.mainIndex + ",76");
    this.dataset01.addItem(new Text(CercanoPerdidaToText(incident.getProp("~kNxTe(hsHLyQ[Near Miss]")), true), this.mainIndex + ",77");
    this.dataset01.addItem(new Text(String.valueOf(incident.getProp("~G5ZTs(dtHDlK[Status \\Incident]", "EXTERNAL")), false), this.mainIndex + ",79");
    if (incident.getCollection("~IloP4QNFJP7G[Compa��a Afectada]", new Object[0]).size() > 0)
    {
      MegaObject company = incident.getCollection("~IloP4QNFJP7G[Compa��a Afectada]", new Object[0]).get(Integer.valueOf(1));
      this.dataset01.addItem(new Text(company.getProp("~Z20000000D60[Short Name]"), false), this.mainIndex + ",80");
    }
    if (incident.getCollection("~qkTqOXzsH5qQ[Requirement]", new Object[0]).size() > 0)
    {
      MegaObject product = incident.getCollection("~qkTqOXzsH5qQ[Requirement]", new Object[0]).get(Integer.valueOf(1));
      this.dataset01.addItem(new Text(product.getProp("~Z20000000D60[Short Name]"), false), this.mainIndex + ",81");
    }
    if (incident.getCollection("~JNxTxuhsHbXQ[Macro Incident]", new Object[0]).size() > 0)
    {
      MegaObject macroIncident = incident.getCollection("~JNxTxuhsHbXQ[Macro Incident]", new Object[0]).get(Integer.valueOf(1));
      this.dataset01.addItem(new Text(macroIncident.getProp("~Z20000000D60[Short Name]"), false), this.mainIndex + ",82");
    }
    this.dataset01.addItem(new Text(String.valueOf(incident.getProp("~WcTadGAaJLrC[Naturaleza del Negocio Fiduciario]", "EXTERNAL")), false), this.mainIndex + ",83");
    this.dataset01.addItem(new Text(String.valueOf(incident.getProp("~EBM4Cvn5K5JL[Origen]]", "EXTERNAL")), false), this.mainIndex + ",84");
    MegaCollection controls = this.root.getSelection(assembleQuery04(incident, "~wUzyFBI84Hf1[Control]", "~HlTq7)zsHLkR[Incident]").toString(), new Object[0]);
    if ((controls != null) && (controls.size() > 0))
    {
      MegaObject control = controls.get(Integer.valueOf(1));
      this.dataset01.addItem(new Text(control.getProp("~Z20000000D60[Short Name]"), false), this.mainIndex + ",85");
    }
    if (incident.getCollection("~YiTqwWzsHzSQ[Entity]", new Object[0]).size() > 0)
    {
      MegaObject entity = incident.getCollection("~YiTqwWzsHzSQ[Entity]", new Object[0]).get(Integer.valueOf(1));
      this.dataset01.addItem(new Text(entity.getProp("~Z20000000D60[Short Name]"), false), this.mainIndex + ",86");
    }
    if (incident.getCollection("~JiTqCXzsHrgQ[Product]", new Object[0]).size() > 0)
    {
      MegaObject product = incident.getCollection("~JiTqCXzsHrgQ[Product]", new Object[0]).get(Integer.valueOf(1));
      this.dataset01.addItem(new Text(product.getProp("~Z20000000D60[Short Name]"), false), this.mainIndex + ",87");
    }
    if (incident.getCollection("~ZjTqIXzsHTlQ[Application]", new Object[0]).size() > 0)
    {
      MegaObject product = incident.getCollection("~ZjTqIXzsHTlQ[Application]", new Object[0]).get(Integer.valueOf(1));
      this.dataset01.addItem(new Text(product.getProp("~Z20000000D60[Short Name]"), false), this.mainIndex + ",88");
    }
  }
  
  private String CercanoPerdidaToText(String NearMiss)
  {
    String text = "";
    if (NearMiss.trim().equals("1")) {
      text = "~B0Ls5jzaPXfF[Opci�n Respuesta-S�]";
    } else {
      text = "~J2LskjzaPTlF[Opci�n Respuesta-No]";
    }
    return text;
  }
  
  private String dateToStringMDYYYY(Date date)
  {
    String rtn = "";
    Calendar calendar = GregorianCalendar.getInstance();
    calendar.setTime(date);
    if (calendar.get(1) > 1970) {
      if (this.root.currentEnvironment().getCurrentLanguage().getName().equals("English")) {
        rtn = calendar.get(2) + 1 + "/" + calendar.get(5) + "/" + calendar.get(1);
      } else {
        rtn = calendar.get(5) + "/" + (calendar.get(2) + 1) + "/" + calendar.get(1);
      }
    }
    return rtn;
  }
}
