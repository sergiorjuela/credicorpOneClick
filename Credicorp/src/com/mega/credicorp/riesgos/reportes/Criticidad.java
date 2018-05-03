package com.mega.credicorp.riesgos.reportes;


/*
 * La clase criticidad permite llevar a cabo la instancia de un
 * objeto que almacena dos caracteristicas a diferentes niveles
 * de los riesgos, riesgos residuales y valores en dolares que seran
 * calculados a traves de la evaluación de riesgos y presentados 
 * en una tabla
 */
public class Criticidad
{
	/*
	 * Definie un conjunto de atributos enteros y dobles que tiene como fin
	 * almacenar el numero de riesgos y riesgos residuales por nivel junto 
	 * con un valor en dolares. 
	 */
  Integer noRiesgosTotales = Integer.valueOf(0);
  Double usTotales = new Double(0.0D);
  Integer noRiesgosTotalesResidual = Integer.valueOf(0);
  Double usTotalesResidual = new Double(0.0D);
  Integer noRiesgosCriticos = Integer.valueOf(0);
  Double usCriticos = new Double(0.0D);
  Integer noRiesgosCriticosResidual = Integer.valueOf(0);
  Double usCriticosResidual = new Double(0.0D);
  Integer noRiesgosAltos = Integer.valueOf(0);
  Double usAltos = new Double(0.0D);
  Integer noRiesgosAltosResidual = Integer.valueOf(0);
  Double usAltosResidual = new Double(0.0D);
  Integer noRiesgosRelevantes = Integer.valueOf(0);
  Double usRelevantes = new Double(0.0D);
  Integer noRiesgosRelevantesResidual = Integer.valueOf(0);
  Double usRelevantesResidual = new Double(0.0D);
  Integer noRiesgosModerados = Integer.valueOf(0);
  Double usModerados = new Double(0.0D);
  Integer noRiesgosModeradosResidual = Integer.valueOf(0);
  Double usModeradosResidual = new Double(0.0D);
  Integer noRiesgosBajo = Integer.valueOf(0);
  Double usBajo = new Double(0.0D);
  Integer noRiesgosBajoResidual = Integer.valueOf(0);
  Double usBajoResidual = new Double(0.0D);
  public static Integer VALOR_UNO = Integer.valueOf(1);
  
  
  /*
   * Genera todos los metodos set y get de los atributos definidos previamente
   */
  
  public Integer getNoRiesgosTotales()
  {
    return this.noRiesgosTotales;
  }
  
  public void setNoRiesgosTotales()
  {
    this.noRiesgosTotales = Integer.valueOf(this.noRiesgosTotales.intValue() + 1);
  }
  
  public Double getUsTotales()
  {
    return this.usTotales;
  }
  
  public void setUsTotales(Double usTotales)
  {
    this.usTotales = Double.valueOf(this.usTotales.doubleValue() + usTotales.doubleValue());
  }
  
  public Integer getNoRiesgosTotalesResidual()
  {
    return this.noRiesgosTotalesResidual;
  }
  
  public void setNoRiesgosTotalesResidual()
  {
    this.noRiesgosTotalesResidual = Integer.valueOf(this.noRiesgosTotalesResidual.intValue() + 1);
  }
  
  public Double getUsTotalesResidual()
  {
    return this.usTotalesResidual;
  }
  
  public void setUsTotalesResidual(Double usTotalesResidual)
  {
    this.usTotalesResidual = Double.valueOf(this.usTotalesResidual.doubleValue() + usTotalesResidual.doubleValue());
  }
  
  public Integer getNoRiesgosCriticos()
  {
    return this.noRiesgosCriticos;
  }
  
  public void setNoRiesgosCriticos()
  {
    this.noRiesgosCriticos = Integer.valueOf(this.noRiesgosCriticos.intValue() + 1);
  }
  
  public Double getUsCriticos()
  {
    return this.usCriticos;
  }
  
  public void setUsCriticos(Double usCriticos)
  {
    this.usCriticos = Double.valueOf(this.usCriticos.doubleValue() + usCriticos.doubleValue());
  }
  
  public Integer getNoRiesgosCriticosResidual()
  {
    return this.noRiesgosCriticosResidual;
  }
  
  public void setNoRiesgosCriticosResidual()
  {
    this.noRiesgosCriticosResidual = Integer.valueOf(this.noRiesgosCriticosResidual.intValue() + 1);
  }
  
  public Double getUsCriticosResidual()
  {
    return this.usCriticosResidual;
  }
  
  public void setUsCriticosResidual(Double usCriticosResidual)
  {
    this.usCriticosResidual = Double.valueOf(this.usCriticosResidual.doubleValue() + usCriticosResidual.doubleValue());
  }
  
  public Integer getNoRiesgosAltos()
  {
    return this.noRiesgosAltos;
  }
  
  public void setNoRiesgosAltos()
  {
    this.noRiesgosAltos = Integer.valueOf(this.noRiesgosAltos.intValue() + 1);
  }
  
  public Double getUsAltos()
  {
    return this.usAltos;
  }
  
  public void setUsAltos(Double usAltos)
  {
    this.usAltos = Double.valueOf(this.usAltos.doubleValue() + usAltos.doubleValue());
  }
  
  public Integer getNoRiesgosAltosResidual()
  {
    return this.noRiesgosAltosResidual;
  }
  
  public void setNoRiesgosAltosResidual()
  {
    this.noRiesgosAltosResidual = Integer.valueOf(this.noRiesgosAltosResidual.intValue() + 1);
  }
  
  public Double getUsAltosResidual()
  {
    return this.usAltosResidual;
  }
  
  public void setUsAltosResidual(Double usAltosResidual)
  {
    this.usAltosResidual = Double.valueOf(this.usAltosResidual.doubleValue() + usAltosResidual.doubleValue());
  }
  
  public Integer getNoRiesgosModerados()
  {
    return this.noRiesgosModerados;
  }
  
  public void setNoRiesgosModerados()
  {
    this.noRiesgosModerados = Integer.valueOf(this.noRiesgosModerados.intValue() + 1);
  }
  
  public Double getUsModerados()
  {
    return this.usModerados;
  }
  
  public void setUsModerados(Double usModerados)
  {
    this.usModerados = Double.valueOf(this.usModerados.doubleValue() + usModerados.doubleValue());
  }
  
  public Integer getNoRiesgosModeradosResidual()
  {
    return this.noRiesgosModeradosResidual;
  }
  
  public void setNoRiesgosModeradosResidual()
  {
    this.noRiesgosModeradosResidual = Integer.valueOf(this.noRiesgosModeradosResidual.intValue() + 1);
  }
  
  public Double getUsModeradosResidual()
  {
    return this.usModeradosResidual;
  }
  
  public void setUsModeradosResidual(Double usModeradosResidual)
  {
    this.usModeradosResidual = Double.valueOf(this.usModeradosResidual.doubleValue() + usModeradosResidual.doubleValue());
  }
  
  public Integer getNoRiesgosBajo()
  {
    return this.noRiesgosBajo;
  }
  
  public void setNoRiesgosBajo()
  {
    this.noRiesgosBajo = Integer.valueOf(this.noRiesgosBajo.intValue() + 1);
  }
  
  public Double getUsBajo()
  {
    return this.usBajo;
  }
  
  public void setUsBajo(Double usBajo)
  {
    this.usBajo = Double.valueOf(this.usBajo.doubleValue() + usBajo.doubleValue());
  }
  
  public Integer getNoRiesgosBajoResidual()
  {
    return this.noRiesgosBajoResidual;
  }
  
  public void setNoRiesgosBajoResidual()
  {
    this.noRiesgosBajoResidual = Integer.valueOf(this.noRiesgosBajoResidual.intValue() + 1);
  }
  
  public Double getUsBajoResidual()
  {
    return this.usBajoResidual;
  }
  
  public void setUsBajoResidual(Double usBajoResidual)
  {
    this.usBajoResidual = Double.valueOf(this.usBajoResidual.doubleValue() + usBajoResidual.doubleValue());
  }
  
  public Integer getNoRiesgosRelevantes()
  {
    return this.noRiesgosRelevantes;
  }
  
  public void setNoRiesgosRelevantes()
  {
    this.noRiesgosRelevantes = Integer.valueOf(this.noRiesgosRelevantes.intValue() + 1);
  }
  
  public Double getUsRelevantes()
  {
    return this.usRelevantes;
  }
  
  public void setUsRelevantes(Double usRelevantes)
  {
    this.usRelevantes = Double.valueOf(this.usRelevantes.doubleValue() + usRelevantes.doubleValue());
  }
  
  public Integer getNoRiesgosRelevantesResidual()
  {
    return this.noRiesgosRelevantesResidual;
  }
  
  public void setNoRiesgosRelevantesResidual()
  {
    this.noRiesgosRelevantesResidual = Integer.valueOf(this.noRiesgosRelevantesResidual.intValue() + 1);
  }
  
  public Double getUsRelevantesResidual()
  {
    return this.usRelevantesResidual;
  }
  
  public void setUsRelevantesResidual(Double usRelevantesResidual)
  {
    this.usRelevantesResidual = Double.valueOf(this.usRelevantesResidual.doubleValue() + usRelevantesResidual.doubleValue());
  }
}
