package cn.tblack.work.reporter.sys.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cn.tblack.work.reporter.encoder.CustomMD5PasswordEncoder;
import cn.tblack.work.reporter.sys.dao.SysUserDao;
import cn.tblack.work.reporter.sys.entity.SysRole;
import cn.tblack.work.reporter.sys.entity.SysUser;

@Service
public class WRUserDetailService implements UserDetailsService {

	@Autowired
	private SysUserDao sysUserDao;

	@Autowired
	private CustomMD5PasswordEncoder passworEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		SysUser user = sysUserDao.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("用户不存在");
		}

		Set<SysRole> roles = user.getRoles();

		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
			
		// 添加当前用户拥有的角色信息
		for (SysRole role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRoleKey()));
		}
		String password = passworEncoder.encode(user.getPassword());

		return new org.springframework.security.core.userdetails.User(username, password, authorities);
	
}

}
