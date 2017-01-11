
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

public class SQLTableInfo
{
	public static SQLTableInfo forName(java.lang.String name) {
		return(new SQLTableInfo().setName(name));
	}

	public static SQLTableInfo forDetails(java.lang.String name, SQLTableColumnInfo[] columns, java.lang.String[] indexes, java.lang.String[] uniqueIndexes) {
		SQLTableInfo v = new SQLTableInfo();
		v.setName(name);
		if(columns != null) {
			int n = 0;
			int m = columns.length;
			for(n = 0 ; n < m ; n++) {
				SQLTableColumnInfo column = columns[n];
				if(column != null) {
					v.addColumn(column);
				}
			}
		}
		if(indexes != null) {
			int n2 = 0;
			int m2 = indexes.length;
			for(n2 = 0 ; n2 < m2 ; n2++) {
				java.lang.String index = indexes[n2];
				if(index != null) {
					v.addIndex(index);
				}
			}
		}
		if(uniqueIndexes != null) {
			int n3 = 0;
			int m3 = uniqueIndexes.length;
			for(n3 = 0 ; n3 < m3 ; n3++) {
				java.lang.String uniqueIndex = uniqueIndexes[n3];
				if(uniqueIndex != null) {
					v.addUniqueIndex(uniqueIndex);
				}
			}
		}
		return(v);
	}

	public static SQLTableInfo forDetails(java.lang.String name, SQLTableColumnInfo[] columns, java.lang.String[] indexes) {
		return(capex.SQLTableInfo.forDetails(name, columns, indexes, null));
	}

	public static SQLTableInfo forDetails(java.lang.String name, SQLTableColumnInfo[] columns) {
		return(capex.SQLTableInfo.forDetails(name, columns, null));
	}

	public static SQLTableInfo forDetails(java.lang.String name) {
		return(capex.SQLTableInfo.forDetails(name, null));
	}

	private java.lang.String name = null;
	private java.util.ArrayList<SQLTableColumnInfo> columns = null;
	private java.util.ArrayList<SQLTableColumnIndexInfo> indexes = null;

	public SQLTableInfo addColumn(SQLTableColumnInfo info) {
		if(info == null) {
			return(this);
		}
		if(columns == null) {
			columns = new java.util.ArrayList<SQLTableColumnInfo>();
		}
		columns.add(info);
		return(this);
	}

	public SQLTableInfo addIntegerColumn(java.lang.String name) {
		return(addColumn(capex.SQLTableColumnInfo.forInteger(name)));
	}

	public SQLTableInfo addStringColumn(java.lang.String name) {
		return(addColumn(capex.SQLTableColumnInfo.forString(name)));
	}

	public SQLTableInfo addStringKeyColumn(java.lang.String name) {
		return(addColumn(capex.SQLTableColumnInfo.forStringKey(name)));
	}

	public SQLTableInfo addTextColumn(java.lang.String name) {
		return(addColumn(capex.SQLTableColumnInfo.forText(name)));
	}

	public SQLTableInfo addIntegerKeyColumn(java.lang.String name) {
		return(addColumn(capex.SQLTableColumnInfo.forIntegerKey(name)));
	}

	public SQLTableInfo addDoubleColumn(java.lang.String name) {
		return(addColumn(capex.SQLTableColumnInfo.forDouble(name)));
	}

	public SQLTableInfo addBlobColumn(java.lang.String name) {
		return(addColumn(capex.SQLTableColumnInfo.forBlob(name)));
	}

	public SQLTableInfo addIndex(java.lang.String column) {
		if(cape.String.isEmpty(column) == false) {
			if(indexes == null) {
				indexes = new java.util.ArrayList<SQLTableColumnIndexInfo>();
			}
			indexes.add(new SQLTableColumnIndexInfo().setColumn(column).setUnique(false));
		}
		return(this);
	}

	public SQLTableInfo addUniqueIndex(java.lang.String column) {
		if(cape.String.isEmpty(column) == false) {
			if(indexes == null) {
				indexes = new java.util.ArrayList<SQLTableColumnIndexInfo>();
			}
			indexes.add(new SQLTableColumnIndexInfo().setColumn(column).setUnique(true));
		}
		return(this);
	}

	public java.lang.String getName() {
		return(name);
	}

	public SQLTableInfo setName(java.lang.String v) {
		name = v;
		return(this);
	}

	public java.util.ArrayList<SQLTableColumnInfo> getColumns() {
		return(columns);
	}

	public SQLTableInfo setColumns(java.util.ArrayList<SQLTableColumnInfo> v) {
		columns = v;
		return(this);
	}

	public java.util.ArrayList<SQLTableColumnIndexInfo> getIndexes() {
		return(indexes);
	}

	public SQLTableInfo setIndexes(java.util.ArrayList<SQLTableColumnIndexInfo> v) {
		indexes = v;
		return(this);
	}
}
