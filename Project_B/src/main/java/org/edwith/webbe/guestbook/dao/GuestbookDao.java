package org.edwith.webbe.guestbook.dao;

import org.edwith.webbe.guestbook.dto.Guestbook;
import org.edwith.webbe.guestbook.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GuestbookDao {
    public List<Guestbook> getGuestbooks(){
        List<Guestbook> list = new ArrayList<>();
        Connection conn = DBUtil.getConnection();
        PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT * FROM guestbook";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong("id");
				String name = rs.getString("name");
				String content = rs.getString("content");
				Date regdate = rs.getDate("regdate");
				list.add(new Guestbook(id, name, content, regdate));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
        return list;
    }

    public void addGuestbook(Guestbook guestbook){
		String sql = "INSERT INTO guestbook (name, content, regdate) VALUES ( ?, ?, ? )";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, guestbook.getName());
			ps.setString(2, guestbook.getContent());
			ps.setDate(3, new java.sql.Date(guestbook.getRegdate().getTime()));
			ps.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }
}
