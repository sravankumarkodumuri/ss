package com.app.ss.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.app.ss.domain.Authorities;
import com.app.ss.domain.Users;

@Repository
public class UserDao implements UserDetailsService{
	
	@Autowired
	private JdbcTemplate jt;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		String sql = "select * from users where username = ?";
		Users user = null;
		List<Users> query = jt.query(sql, new Object[]{username}, new RowMapper<Users>(){

			@Override
			public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
				Users user = new Users();
				if(user.getUsername() == null)
					user.setUsername(rs.getString("username"));
				if(user.getPassword() == null)
					user.setPassword(rs.getString("password"));
				return user;
			}
		});
		
		if(query.size() == 0)
			throw new UsernameNotFoundException("No user found with given username");
		else
			user=query.get(0);
		
		sql ="select authority from authorities where username = ?";
		List<String> authorities = jt.queryForList(sql, new Object[]{username}, String.class);
		
		Set<Authorities> userAuths = new HashSet<Authorities>();
		for (String authority : authorities) {
			userAuths.add(new Authorities(username, authority));
		}
		user.setAuthorities(userAuths);
		return user;
	}

}
