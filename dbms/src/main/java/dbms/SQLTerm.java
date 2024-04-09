package dbms;

/** * @author Wael Abouelsaadat */

public class SQLTerm {

	public String _strTableName, _strColumnName, _strOperator;
	public Object _objValue;

	public SQLTerm() {

	}

	public boolean isNull() {
		return _strOperator == null || _objValue == null || _strColumnName == null
				|| _strTableName == null;
	}

}