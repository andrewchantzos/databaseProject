package db;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;

import org.junit.Test;

import util.DBUtil;

public class DbConnectionTest {

	@Test
	public void test() {
		Connection connection = DBUtil.getConnection();

		assertTrue(connection != null);
	}

}
