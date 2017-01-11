
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

public class TemplateStorageUsingSQLDatabase implements TemplateStorage
{
	public static TemplateStorageUsingSQLDatabase forDatabase(SQLDatabase db, java.lang.String table, java.lang.String idColumn, java.lang.String contentColumn) {
		TemplateStorageUsingSQLDatabase v = new TemplateStorageUsingSQLDatabase();
		v.setDatabase(db);
		if(!(android.text.TextUtils.equals(table, null))) {
			v.setTable(table);
		}
		if(!(android.text.TextUtils.equals(idColumn, null))) {
			v.setIdColumn(idColumn);
		}
		if(!(android.text.TextUtils.equals(contentColumn, null))) {
			v.setContentColumn(contentColumn);
		}
		return(v);
	}

	public static TemplateStorageUsingSQLDatabase forDatabase(SQLDatabase db, java.lang.String table, java.lang.String idColumn) {
		return(capex.TemplateStorageUsingSQLDatabase.forDatabase(db, table, idColumn, null));
	}

	public static TemplateStorageUsingSQLDatabase forDatabase(SQLDatabase db, java.lang.String table) {
		return(capex.TemplateStorageUsingSQLDatabase.forDatabase(db, table, null));
	}

	public static TemplateStorageUsingSQLDatabase forDatabase(SQLDatabase db) {
		return(capex.TemplateStorageUsingSQLDatabase.forDatabase(db, null));
	}

	private SQLDatabase database = null;
	private java.lang.String table = null;
	private java.lang.String idColumn = null;
	private java.lang.String contentColumn = null;

	public TemplateStorageUsingSQLDatabase() {
		table = "templates";
		idColumn = "id";
		contentColumn = "content";
	}

	public void getTemplate(java.lang.String id, samx.function.Procedure1<java.lang.String> callback) {
		if(callback == null) {
			return;
		}
		if(((database == null) || cape.String.isEmpty(table)) || cape.String.isEmpty(id)) {
			callback.execute(null);
			return;
		}
		SQLStatement stmt = database.prepare(((("SELECT content FROM " + table) + " WHERE ") + idColumn) + " = ?;");
		if(stmt == null) {
			callback.execute(null);
			return;
		}
		stmt.addParamString(id);
		final samx.function.Procedure1<java.lang.String> cb = callback;
		database.querySingleRow(stmt, new samx.function.Procedure1<cape.DynamicMap>() {
			public void execute(cape.DynamicMap data) {
				if(data == null) {
					cb.execute(null);
					return;
				}
				cb.execute(data.getString("content"));
			}
		});
	}

	public SQLDatabase getDatabase() {
		return(database);
	}

	public TemplateStorageUsingSQLDatabase setDatabase(SQLDatabase v) {
		database = v;
		return(this);
	}

	public java.lang.String getTable() {
		return(table);
	}

	public TemplateStorageUsingSQLDatabase setTable(java.lang.String v) {
		table = v;
		return(this);
	}

	public java.lang.String getIdColumn() {
		return(idColumn);
	}

	public TemplateStorageUsingSQLDatabase setIdColumn(java.lang.String v) {
		idColumn = v;
		return(this);
	}

	public java.lang.String getContentColumn() {
		return(contentColumn);
	}

	public TemplateStorageUsingSQLDatabase setContentColumn(java.lang.String v) {
		contentColumn = v;
		return(this);
	}
}
