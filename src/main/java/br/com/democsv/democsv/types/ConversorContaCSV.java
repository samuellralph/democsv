package br.com.democsv.democsv.types;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvConstraintViolationException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;

@SuppressWarnings("rawtypes")
public class ConversorContaCSV extends AbstractBeanField {
	
	@Override
	protected Object convert(String value) throws CsvDataTypeMismatchException, CsvConstraintViolationException {
		if(value.isEmpty()) {
			return null;
		} else {
			return value.replace("-", "");
		}
	}
}