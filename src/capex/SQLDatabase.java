
/*
 * This file is part of Jkop for Android
 * Copyright (c) 2016-2017 Job and Esther Technologies, Inc.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package capex;

abstract public class SQLDatabase
{
	private cape.LoggingContext logger = null;
	public abstract SQLStatement prepare(java.lang.String sql);
	public abstract java.lang.String getDatabaseTypeId();
	public abstract SQLStatement prepareCreateTableStatement(java.lang.String table, java.util.ArrayList<SQLTableColumnInfo> columns);
	public abstract SQLStatement prepareDeleteTableStatement(java.lang.String table);
	public abstract SQLStatement prepareCreateIndexStatement(java.lang.String table, java.lang.String column, boolean unique);
	public abstract void close(samx.function.Procedure0 callback);
	public abstract void execute(SQLStatement stmt, samx.function.Procedure1<java.lang.Boolean> callback);
	public abstract void query(SQLStatement stmt, samx.function.Procedure1<SQLResultSetIterator> callback);
	public abstract void querySingleRow(SQLStatement stmt, samx.function.Procedure1<cape.DynamicMap> callback);
	public abstract void tableExists(java.lang.String table, samx.function.Procedure1<java.lang.Boolean> callback);
	public abstract void queryAllTableNames(samx.function.Procedure1<java.util.ArrayList<java.lang.Object>> callback);
	public abstract void close();
	public abstract boolean execute(SQLStatement stmt);
	public abstract SQLResultSetIterator query(SQLStatement stmt);
	public abstract cape.DynamicMap querySingleRow(SQLStatement stmt);
	public abstract boolean tableExists(java.lang.String table);
	public abstract java.util.ArrayList<java.lang.Object> queryAllTableNames();

	public boolean ensureTableExists(SQLTableInfo table) {
		if(table == null) {
			return(false);
		}
		java.lang.String name = table.getName();
		if(cape.String.isEmpty(name)) {
			return(false);
		}
		if(tableExists(name)) {
			return(true);
		}
		if(execute(prepareCreateTableStatement(name, table.getColumns())) == false) {
			return(false);
		}
		java.util.ArrayList<SQLTableColumnIndexInfo> array = table.getIndexes();
		if(array != null) {
			int n = 0;
			int m = array.size();
			for(n = 0 ; n < m ; n++) {
				SQLTableColumnIndexInfo cii = array.get(n);
				if(cii != null) {
					if(execute(prepareCreateIndexStatement(name, cii.getColumn(), cii.getUnique())) == false) {
						execute(prepareDeleteTableStatement(name));
					}
				}
			}
		}
		return(true);
	}

	public void ensureTableExists(SQLTableInfo table, samx.function.Procedure1<java.lang.Boolean> callback) {
		boolean v = ensureTableExists(table);
		if(callback != null) {
			callback.execute(v);
		}
	}

	private java.lang.String createColumnSelectionString(java.lang.String[] columns) {
		if((columns == null) || (columns.length < 1)) {
			return("*");
		}
		cape.StringBuilder sb = new cape.StringBuilder();
		boolean first = true;
		if(columns != null) {
			int n = 0;
			int m = columns.length;
			for(n = 0 ; n < m ; n++) {
				java.lang.String column = columns[n];
				if(column != null) {
					if(first == false) {
						sb.append(", ");
					}
					sb.append(column);
					first = false;
				}
			}
		}
		return(sb.toString());
	}

	private java.lang.String createOrderByString(SQLOrderingRule[] order) {
		if((order == null) || (order.length < 1)) {
			return(null);
		}
		cape.StringBuilder sb = new cape.StringBuilder();
		sb.append(" ORDER BY ");
		boolean first = true;
		if(order != null) {
			int n = 0;
			int m = order.length;
			for(n = 0 ; n < m ; n++) {
				SQLOrderingRule rule = order[n];
				if(rule != null) {
					if(first == false) {
						sb.append(", ");
					}
					sb.append(rule.getColumn());
					sb.append(' ');
					if(rule.getDescending()) {
						sb.append("DESC");
					}
					else {
						sb.append("ASC");
					}
					first = false;
				}
			}
		}
		return(sb.toString());
	}

	public SQLStatement prepareQueryAllStatement(java.lang.String table) {
		return(prepareQueryAllStatement(table, null, null));
	}

	public SQLStatement prepareQueryAllStatement(java.lang.String table, java.lang.String[] columns) {
		return(prepareQueryAllStatement(table, columns, null));
	}

	public SQLStatement prepareQueryAllStatement(java.lang.String table, java.lang.String[] columns, SQLOrderingRule[] order) {
		cape.StringBuilder sb = new cape.StringBuilder();
		sb.append("SELECT ");
		sb.append(createColumnSelectionString(columns));
		sb.append(" FROM ");
		sb.append(table);
		sb.append(createOrderByString(order));
		sb.append(";");
		return(prepare(sb.toString()));
	}

	static public java.util.ArrayList<java.lang.String> objectAsNativeVectorDataTypeNode(java.lang.Object o) {
		if(o instanceof java.util.ArrayList) {
			return((java.util.ArrayList<java.lang.String>)o);
		}
		return(null);
	}

	public SQLStatement prepareCountRecordsStatement(java.lang.String table, java.util.HashMap<java.lang.String,java.lang.String> criteria) {
		cape.StringBuilder sb = new cape.StringBuilder();
		sb.append("SELECT COUNT(*) AS count FROM ");
		sb.append(table);
		boolean first = true;
		java.util.ArrayList<java.lang.String> keys = null;
		if(criteria != null) {
			keys = objectAsNativeVectorDataTypeNode((java.lang.Object)cape.Map.getKeys(criteria));
			if(keys != null) {
				int n = 0;
				int m = keys.size();
				for(n = 0 ; n < m ; n++) {
					java.lang.String key = keys.get(n);
					if(key != null) {
						if(first) {
							sb.append(" WHERE ");
							first = false;
						}
						else {
							sb.append(" AND ");
						}
						sb.append((java.lang.String)((key instanceof java.lang.String) ? key : null));
						sb.append(" = ?");
					}
				}
			}
		}
		sb.append(';');
		java.lang.String sql = sb.toString();
		SQLStatement stmt = prepare(sql);
		if(stmt == null) {
			return(null);
		}
		if(keys != null) {
			if(keys != null) {
				int n2 = 0;
				int m2 = keys.size();
				for(n2 = 0 ; n2 < m2 ; n2++) {
					java.lang.String key = keys.get(n2);
					if(key != null) {
						java.lang.String val = cape.Map.get(criteria, key);
						if(android.text.TextUtils.equals(val, null)) {
							val = null;
						}
						stmt.addParamString((java.lang.String)((val instanceof java.lang.String) ? val : null));
					}
				}
			}
		}
		return(stmt);
	}

	public SQLStatement prepareQueryWithCriteriaStatement(java.lang.String table, java.util.HashMap<java.lang.String,java.lang.String> criteria) {
		return(prepareQueryWithCriteriaStatement(table, criteria, 0, 0, null, null));
	}

	public SQLStatement prepareQueryWithCriteriaStatement(java.lang.String table, java.util.HashMap<java.lang.String,java.lang.String> criteria, int limit) {
		return(prepareQueryWithCriteriaStatement(table, criteria, limit, 0, null, null));
	}

	public SQLStatement prepareQueryWithCriteriaStatement(java.lang.String table, java.util.HashMap<java.lang.String,java.lang.String> criteria, int limit, int offset) {
		return(prepareQueryWithCriteriaStatement(table, criteria, limit, offset, null, null));
	}

	public SQLStatement prepareQueryWithCriteriaStatement(java.lang.String table, java.util.HashMap<java.lang.String,java.lang.String> criteria, int limit, int offset, java.lang.String[] columns) {
		return(prepareQueryWithCriteriaStatement(table, criteria, limit, offset, columns, null));
	}

	public SQLStatement prepareQueryWithCriteriaStatement(java.lang.String table, java.util.HashMap<java.lang.String,java.lang.String> criteria, int limit, int offset, java.lang.String[] columns, SQLOrderingRule[] order) {
		cape.StringBuilder sb = new cape.StringBuilder();
		sb.append("SELECT ");
		sb.append(createColumnSelectionString(columns));
		sb.append(" FROM ");
		sb.append(table);
		boolean first = true;
		java.util.ArrayList<java.lang.String> keys = null;
		if(criteria != null) {
			keys = objectAsNativeVectorDataTypeNode((java.lang.Object)cape.Map.getKeys(criteria));
			if(keys != null) {
				int n = 0;
				int m = keys.size();
				for(n = 0 ; n < m ; n++) {
					java.lang.String key = keys.get(n);
					if(key != null) {
						if(first) {
							sb.append(" WHERE ");
							first = false;
						}
						else {
							sb.append(" AND ");
						}
						sb.append((java.lang.String)((key instanceof java.lang.String) ? key : null));
						sb.append(" = ?");
					}
				}
			}
		}
		sb.append(createOrderByString(order));
		if(limit > 0) {
			sb.append(" LIMIT ");
			sb.append(cape.String.forInteger(limit));
		}
		if(offset > 0) {
			sb.append(" OFFSET ");
			sb.append(cape.String.forInteger(offset));
		}
		sb.append(';');
		java.lang.String sql = sb.toString();
		SQLStatement stmt = prepare(sql);
		if(stmt == null) {
			return(null);
		}
		if(keys != null) {
			if(keys != null) {
				int n2 = 0;
				int m2 = keys.size();
				for(n2 = 0 ; n2 < m2 ; n2++) {
					java.lang.String key = keys.get(n2);
					if(key != null) {
						java.lang.String val = cape.Map.get(criteria, key);
						if(android.text.TextUtils.equals(val, null)) {
							val = null;
						}
						stmt.addParamString((java.lang.String)((val instanceof java.lang.String) ? val : null));
					}
				}
			}
		}
		return(stmt);
	}

	public SQLStatement prepareQueryDistinctValuesStatement(java.lang.String table, java.lang.String column) {
		if(cape.String.isEmpty(table) || cape.String.isEmpty(column)) {
			return(null);
		}
		cape.StringBuilder sb = new cape.StringBuilder();
		sb.append("SELECT DISTINCT ");
		sb.append(column);
		sb.append(" FROM ");
		sb.append(table);
		sb.append(";");
		return(prepare(sb.toString()));
	}

	public SQLStatement prepareInsertStatement(java.lang.String table, cape.DynamicMap data) {
		if((cape.String.isEmpty(table) || (data == null)) || (data.getCount() < 1)) {
			return(null);
		}
		cape.StringBuilder sb = new cape.StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(table);
		sb.append(" ( ");
		boolean first = true;
		java.util.ArrayList<java.lang.String> keys = data.getKeys();
		if(keys != null) {
			int n = 0;
			int m = keys.size();
			for(n = 0 ; n < m ; n++) {
				java.lang.String key = keys.get(n);
				if(key != null) {
					if(first == false) {
						sb.append(',');
					}
					sb.append((java.lang.String)((key instanceof java.lang.String) ? key : null));
					first = false;
				}
			}
		}
		sb.append(" ) VALUES ( ");
		first = true;
		if(keys != null) {
			int n2 = 0;
			int m2 = keys.size();
			for(n2 = 0 ; n2 < m2 ; n2++) {
				java.lang.String key = keys.get(n2);
				if(key != null) {
					if(first == false) {
						sb.append(',');
					}
					sb.append('?');
					first = false;
				}
			}
		}
		sb.append(" );");
		SQLStatement stmt = prepare(sb.toString());
		if(stmt == null) {
			return(null);
		}
		if(keys != null) {
			int n3 = 0;
			int m3 = keys.size();
			for(n3 = 0 ; n3 < m3 ; n3++) {
				java.lang.String key = keys.get(n3);
				if(key != null) {
					java.lang.Object o = data.get(key);
					if((o instanceof java.lang.String) || (o instanceof cape.StringObject)) {
						stmt.addParamString(cape.String.asString(o));
					}
					else if(o instanceof cape.IntegerObject) {
						stmt.addParamInteger(cape.Integer.asInteger(o));
					}
					else if(o instanceof cape.DoubleObject) {
						stmt.addParamDouble(cape.Double.asDouble(o));
					}
					else if(o instanceof cape.BufferObject) {
						stmt.addParamBlob(((cape.BufferObject)o).toBuffer());
					}
					else if(o instanceof byte[]) {
						stmt.addParamBlob((byte[])((o instanceof byte[]) ? o : null));
					}
					else {
						java.lang.String s = (java.lang.String)((o instanceof java.lang.String) ? o : null);
						if(android.text.TextUtils.equals(s, null)) {
							s = "";
						}
						stmt.addParamString(s);
					}
				}
			}
		}
		return(stmt);
	}

	public SQLStatement prepareUpdateStatement(java.lang.String table, cape.DynamicMap criteria, cape.DynamicMap data) {
		if((cape.String.isEmpty(table) || (data == null)) || (data.getCount() < 1)) {
			return(null);
		}
		cape.StringBuilder sb = new cape.StringBuilder();
		sb.append("UPDATE ");
		sb.append(table);
		sb.append(" SET ");
		java.util.ArrayList<java.lang.Object> params = new java.util.ArrayList<java.lang.Object>();
		boolean first = true;
		cape.Iterator<java.lang.String> keys = data.iterateKeys();
		while(keys != null) {
			java.lang.String key = keys.next();
			if(android.text.TextUtils.equals(key, null)) {
				break;
			}
			if(first == false) {
				sb.append(", ");
			}
			sb.append(key);
			sb.append(" = ?");
			first = false;
			params.add(data.get(key));
		}
		if((criteria != null) && (criteria.getCount() > 0)) {
			sb.append(" WHERE ");
			first = true;
			cape.Iterator<java.lang.String> criterias = criteria.iterateKeys();
			while(criterias != null) {
				java.lang.String criterium = criterias.next();
				if(android.text.TextUtils.equals(criterium, null)) {
					break;
				}
				if(first == false) {
					sb.append(" AND ");
				}
				sb.append(criterium);
				sb.append(" = ?");
				first = false;
				params.add(criteria.get(criterium));
			}
		}
		sb.append(';');
		SQLStatement stmt = prepare(sb.toString());
		if(stmt == null) {
			return(null);
		}
		if(params != null) {
			int n = 0;
			int m = params.size();
			for(n = 0 ; n < m ; n++) {
				java.lang.Object o = params.get(n);
				if(o != null) {
					if(o instanceof byte[]) {
						stmt.addParamBlob(cape.Buffer.asBuffer(o));
					}
					else {
						java.lang.String s = cape.String.asString(o);
						if(android.text.TextUtils.equals(s, null)) {
							s = "";
						}
						stmt.addParamString(s);
					}
				}
			}
		}
		return(stmt);
	}

	public SQLStatement prepareDeleteStatement(java.lang.String table, cape.DynamicMap criteria) {
		if(cape.String.isEmpty(table)) {
			return(null);
		}
		cape.StringBuilder sb = new cape.StringBuilder();
		sb.append("DELETE FROM ");
		sb.append(table);
		java.util.ArrayList<java.lang.Object> params = new java.util.ArrayList<java.lang.Object>();
		if((criteria != null) && (criteria.getCount() > 0)) {
			sb.append(" WHERE ");
			boolean first = true;
			cape.Iterator<java.lang.String> criterias = criteria.iterateKeys();
			while(criterias != null) {
				java.lang.String criterium = criterias.next();
				if(android.text.TextUtils.equals(criterium, null)) {
					break;
				}
				if(first == false) {
					sb.append(" AND ");
				}
				sb.append(criterium);
				sb.append(" = ?");
				first = false;
				params.add(criteria.get(criterium));
			}
		}
		sb.append(';');
		SQLStatement stmt = prepare(sb.toString());
		if(stmt == null) {
			return(null);
		}
		if(params != null) {
			int n = 0;
			int m = params.size();
			for(n = 0 ; n < m ; n++) {
				java.lang.Object o = params.get(n);
				if(o != null) {
					if(o instanceof byte[]) {
						stmt.addParamBlob(cape.Buffer.asBuffer(o));
					}
					else {
						java.lang.String s = cape.String.asString(o);
						if(android.text.TextUtils.equals(s, null)) {
							s = "";
						}
						stmt.addParamString(s);
					}
				}
			}
		}
		return(stmt);
	}

	public cape.LoggingContext getLogger() {
		return(logger);
	}

	public SQLDatabase setLogger(cape.LoggingContext v) {
		logger = v;
		return(this);
	}
}
