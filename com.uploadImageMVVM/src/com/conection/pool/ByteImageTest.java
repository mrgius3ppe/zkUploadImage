package com.conection.pool;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ByteImageTest {

	public void insertByte(String file_name, InputStream stream)
			throws Exception {
		Connection conn = null;
		DataSource ds = (DataSource) new InitialContext()
				.lookup("java:jboss/datasource/NameDataSource");

		try {
			conn = ds.getConnection();
			if (stream != null) {
				PreparedStatement ps = conn
						.prepareStatement("INSERT INTO imagetest(name, image) VALUES(?, ?)");
				System.out.println(ps);
				ps.setString(1, file_name);

				ps.setBinaryStream(2, stream, stream.available());

				ps.executeUpdate();
				ps.close();
			} else {
				PreparedStatement ps = conn
						.prepareStatement("INSERT INTO imagetest(name, image) VALUES (?, empty_blob())");
				ps.setString(1, file_name);
				ps.execute();
				ps.close();

			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public byte[] writeBlob(String filename) throws Exception {
		Connection conn = null;
		DataSource ds = (DataSource) new InitialContext()
				.lookup("java:jboss/datasource/NameDataSource");
		byte[] data = null;
		try {

			conn = ds.getConnection();
			Statement st = conn.createStatement();
			String query = "SELECT image FROM imagetest where name='"
					+ filename + "'";
			ResultSet rs = st

			.executeQuery(query);
			System.out.println(filename);
			while (rs.next()) {
				Blob blob = rs.getBlob(1);
				data = blob.getBytes(1, (int) blob.length());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

}
