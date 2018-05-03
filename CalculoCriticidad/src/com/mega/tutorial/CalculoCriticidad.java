package com.mega.tutorial;

// Importación de Paquetes
import java.io.IOException;
import com.mega.modeling.api.*;
import com.mega.modeling.api.jni.MegaObjectProxy;
import com.mega.vocabulary.*;
import javax.swing.JOptionPane;

/**
 * This class implements a MetaAttribute.
 */
public class CalculoCriticidad {

	// To Implement if you want to have a computed value for this attribute
	public void getAttributeValue(final MegaObject mgobjObject,
			final Double dAttributeID, final StringBuffer strValue) {

		try {
			// Declaramos la variables de tipo String que vamos a utilizar
			String disponibilidadAttr, integridadAttr, confidencialidadAttr, valorCriticidad = null;
			// Obtenemos los valores de los atributos del activo
			// (disponibilidad,
			// confidencialidad e integridad)
			disponibilidadAttr = mgobjObject
					.getProp(OCActivoInformacion.MA_OCActivoDisponibilidad);
			integridadAttr = mgobjObject
					.getProp(OCActivoInformacion.MA_OCActivoIntegridad);
			confidencialidadAttr = mgobjObject
					.getProp(OCActivoInformacion.MA_OCActivoConfidencialidad);
			// Obtenemos el nivel de criticidad
			valorCriticidad = nivelCriticidad(
					Integer.parseInt(disponibilidadAttr),
					Integer.parseInt(integridadAttr),
					Integer.parseInt(confidencialidadAttr));
			// Asignamos el valor de criticidad al valor del atributo Nivel de
			// Criticidad a traves de la variable del metodo
			strValue.append(valorCriticidad);
			JOptionPane.showMessageDialog(
					null,
					"El Nivel de Criticidad del Activo es: "
							+ valorCriticidad.toUpperCase());
		} catch (NumberFormatException e) {
			JOptionPane
					.showMessageDialog(
							null,
							"No se puede llevar a cabo el calculo del Nivel de Criticidad dado que no se han registrado los atributos de Confidencialidad, Integridad y Disponibilidad ");
		}
	}

	// Calcula el nivel de criticidad de acuerdo a la suma de los tres valores
	// que recibe
	public String nivelCriticidad(int disponibilidad, int integridad,
			int confidencialidad) {
		int sumaCriticidad = disponibilidad + integridad + confidencialidad;
		String Nivel = null;
		if (sumaCriticidad >= 3 && sumaCriticidad < 6) {
			Nivel = "Bajo";
		} else if (sumaCriticidad >= 6 && sumaCriticidad < 9) {
			Nivel = "Medio";
		} else if (sumaCriticidad >= 9 && sumaCriticidad < 12) {
			Nivel = "Alto";
		} else if (sumaCriticidad >= 12 && sumaCriticidad <= 15) {
			Nivel = "Muy Alto";
		}
		return Nivel;
	}

	// To Implement if you want to overload the update of this attribute
	public void setAttributeValue(final MegaObject mgobjObject,
			final Double dAttributeID, final StringBuffer strValue) {
	}

	// To Implement instead of SetAttributeValue if you want to manage a
	// specific display or extended value
	// Format : 0 internal, 1 external, 3 Display, 5 externalCode
	public void setExtendedAttributeValue(final int[] intFormat,
			final MegaObject mgobjObject, final Double dAttributeID,
			final StringBuffer strValue) {
	}

	// To Implement for a Text attribute to fix the text format of the given
	// text
	public void getTextFormat(final MegaObject mgobjObject,
			final Double dAttributeID, final Double[] dValue) {
	}

}