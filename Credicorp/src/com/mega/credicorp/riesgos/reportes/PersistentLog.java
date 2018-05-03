package com.mega.credicorp.riesgos.reportes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;



/*
 * La clase PersistentLog define los metodos que permite realizar el registro de un log
 * en un archivo plano de los diferentes calculos que lleva a cabo el reporte y lo actualiza
 * cada vez que el reporte se accede o se actualiza.
 */

public class PersistentLog
{
	
//Define las variables estaticas de la clase
  protected static PersistentLog log = null;
  private static File file;
  private static StringBuffer debug;
  
  //Inicializa el log tomando como parametro de entrada la ruta donde se generara el txt

  private static void Init(String filename)
  {
    log = new PersistentLog();
    file = new File(filename);
    debug = new StringBuffer("");
  }
  
  /*Valida que la instancia del log exista, si no la inicializa. Adicioalmente valida el
   * el atributo filename si este es null se debe ir al metodo getInstance. 
   */
  
  public static PersistentLog getInstance(String filename)
    throws Exception
  {
    if (log == null) {
      Init(filename);
    } else if (filename == null) {
      return getInstance();
    }
    return log;
  }
  
  /*Este metodo valida si el objeto esta instanciado en este caso
  *lo retorno o si no genera una excpción de no inicialización
  */
  public static PersistentLog getInstance()
    throws Exception
  {
    if (log == null) {
      throw new Exception("No inicializado");
    }
    return log;
  }
  
  /*
   * Se encarga de crear un objeto de escritura de archivos viculado a un fiechero 
   * y escribir en el objeto archivo vinculado al log la información del stringBuffer debug
   */
  public void flush()
  {
    try
    {
      FileWriter fw = new FileWriter(file.getAbsoluteFile());
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write(debug.toString());
      bw.close();
      debug = new StringBuffer("");
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  /*
   * Reinicia el log y el archivo.
   */
  
  public static void reset(String filename)
  {
    Init(filename);
  }
  
  /*
   * Agrega string al stringbuffer
   */
  public void record(String str)
  {
    if (!debug.toString().equals(str)) {
      debug.append(str);
    }
  }
}
