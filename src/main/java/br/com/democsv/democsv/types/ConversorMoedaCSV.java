package br.com.democsv.democsv.types;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

@SuppressWarnings("rawtypes")
public class ConversorMoedaCSV extends AbstractBeanField {
	
	@Override
	protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		if(value.isEmpty()) {
			return null;
		} else {
			String newValue = value.replace(",", ".");
			return new BigDecimal(newValue).setScale(2, RoundingMode.HALF_UP);
		}
	}
}