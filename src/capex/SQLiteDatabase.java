
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

abstract public class SQLiteDatabase extends SQLDatabase
{
	static public SQLiteDatabase instance() {
		System.out.println("[capex.SQLiteDatabase.instance] (SQLiteDatabase.sling:40:2): Not implemented");
		return(null);
	}

	static public cape.LoggingContext objectAsCapeLoggingContext(java.lang.Object o) {
		if(o instanceof cape.LoggingContext) {
			return((cape.LoggingContext)o);
		}
		return(null);
	}

	public static SQLiteDatabase forFile(cape.File file, boolean allowCreate, cape.LoggingContext logger) {
		if(file == null) {
			return(null);
		}
		SQLiteDatabase v = instance();
		if(v == null) {
			return(null);
		}
		if(logger != null) {
			v.setLogger(logger);
		}
		if(file.isFile() == false) {
			if(allowCreate == false) {
				return(null);
			}
			cape.File pp = file.getParent();
			if(pp.isDirectory() == false) {
				if(pp.createDirectoryRecursive() == false) {
					cape.Log.error(objectAsCapeLoggingContext((java.lang.Object)v.getLogger()), "Failed to create directory: " + pp.getPath());
				}
			}
			if(v.initialize(file, true) == false) {
				v = null;
			}
		}
		else if(v.initialize(file, false) == false) {
			v = null;
		}
		return(v);
	}

	public static SQLiteDatabase forFile(cape.File file, boolean allowCreate) {
		return(capex.SQLiteDatabase.forFile(file, allowCreate, null));
	}

	public static SQLiteDatabase forFile(cape.File file) {
		return(capex.SQLiteDatabase.forFile(file, true));
	}

	@Override
	public java.lang.String getDatabaseTypeId() {
		return("sqlite");
	}

	public boolean initialize(cape.File file, boolean create) {
		return(true);
	}

	@Override
	public cape.DynamicMap querySingleRow(SQLStatement stmt) {
		SQLResultSetIterator it = query(stmt);
		if(it == null) {
			return(null);
		}
		cape.DynamicMap v = it.next();
		return(v);
	}

	@Override
	public boolean tableExists(java.lang.String table) {
		if(android.text.TextUtils.equals(table, null)) {
			return(false);
		}
		SQLStatement stmt = prepare("SELECT name FROM sqlite_master WHERE type='table' AND name=?;");
		if(stmt == null) {
			return(false);
		}
		stmt.addParamString(table);
		cape.DynamicMap sr = querySingleRow(stmt);
		if(sr == null) {
			return(false);
		}
		if(cape.String.equals(table, sr.getString("name")) == false) {
			return(false);
		}
		return(true);
	}

	@Override
	public void queryAllTableNames(samx.function.Procedure1<java.util.ArrayList<java.lang.Object>> callback) {
		java.util.ArrayList<java.lang.Object> v = queryAllTableNames();
		if(callback != null) {
			callback.execute(v);
		}
	}

	@Override
	public java.util.ArrayList<java.lang.Object> queryAllTableNames() {
		SQLStatement stmt = prepare("SELECT name FROM sqlite_master WHERE type='table';");
		if(stmt == null) {
			return(null);
		}
		SQLResultSetIterator it = query(stmt);
		if(it == null) {
			return(null);
		}
		java.util.ArrayList<java.lang.Object> v = new java.util.ArrayList<java.lang.Object>();
		while(true) {
			cape.DynamicMap o = it.next();
			if(o == null) {
				break;
			}
			java.lang.String name = o.getString("name");
			if(cape.String.isEmpty(name) == false) {
				v.add(name);
			}
		}
		return(v);
	}

	public java.lang.String columnToCreateString(SQLTableColumnInfo cc) {
		cape.StringBuilder sb = new cape.StringBuilder();
		sb.append(cc.getName());
		sb.append(' ');
		int tt = cc.getType();
		if(tt == capex.SQLTableColumnInfo.TYPE_INTEGER_KEY) {
			sb.append("INTEGER PRIMARY KEY AUTOINCREMENT");
		}
		else if(tt == capex.SQLTableColumnInfo.TYPE_STRING_KEY) {
			sb.append("TEXT PRIMARY KEY");
		}
		else if(tt == capex.SQLTableColumnInfo.TYPE_INTEGER) {
			sb.append("INTEGER");
		}
		else if(tt == capex.SQLTableColumnInfo.TYPE_STRING) {
			sb.append("VARCHAR(255)");
		}
		else if(tt == capex.SQLTableColumnInfo.TYPE_TEXT) {
			sb.append("TEXT");
		}
		else if(tt == capex.SQLTableColumnInfo.TYPE_BLOB) {
			sb.append("BLOB");
		}
		else if(tt == capex.SQLTableColumnInfo.TYPE_DOUBLE) {
			sb.append("REAL");
		}
		else {
			cape.Log.error(getLogger(), "Unknown column type: " + cape.String.forInteger(tt));
			sb.append("UNKNOWN");
		}
		return(sb.toString());
	}

	@Override
	public SQLStatement prepareCreateTableStatement(java.lang.String table, java.util.ArrayList<SQLTableColumnInfo> columns) {
		if((android.text.TextUtils.equals(table, null)) || (columns == null)) {
			return(null);
		}
		cape.StringBuilder sb = new cape.StringBuilder();
		sb.append("CREATE TABLE ");
		sb.append(table);
		sb.append(" (");
		boolean first = true;
		if(columns != null) {
			int n = 0;
			int m = columns.size();
			for(n = 0 ; n < m ; n++) {
				SQLTableColumnInfo column = columns.get(n);
				if(column != null) {
					if(first == false) {
						sb.append(',');
					}
					sb.append(' ');
					sb.append(columnToCreateString(column));
					first = false;
				}
			}
		}
		sb.append(" );");
		return(prepare(sb.toString()));
	}

	@Override
	public SQLStatement prepareDeleteTableStatement(java.lang.String table) {
		if(android.text.TextUtils.equals(table, null)) {
			return(null);
		}
		cape.StringBuilder sb = new cape.StringBuilder();
		sb.append("DROP TABLE ");
		sb.append(table);
		sb.append(";");
		return(prepare(sb.toString()));
	}

	@Override
	public SQLStatement prepareCreateIndexStatement(java.lang.String table, java.lang.String column, boolean unique) {
		if((android.text.TextUtils.equals(table, null)) || (android.text.TextUtils.equals(column, null))) {
			return(null);
		}
		java.lang.String unq = "";
		if(unique) {
			unq = "UNIQUE ";
		}
		java.lang.String sql = ((((((((("CREATE " + unq) + "INDEX ") + table) + "_") + column) + " ON ") + table) + " (") + column) + ")";
		return(prepare(sql));
	}
}
