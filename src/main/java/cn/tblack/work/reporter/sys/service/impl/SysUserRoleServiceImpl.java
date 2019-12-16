package cn.tblack.work.reporter.sys.service.impl;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import cn.tblack.work.reporter.sys.dao.SysUserRoleDao;
import cn.tblack.work.reporter.sys.entity.SysRole;
import cn.tblack.work.reporter.sys.entity.SysUser;
import cn.tblack.work.reporter.sys.entity.SysUserRole;
import cn.tblack.work.reporter.sys.service.SysRoleService;
import cn.tblack.work.reporter.sys.service.SysUserRoleService;
import static cn.tblack.work.reporter.constant.RoleNameConstant.*;

@Transactional
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService{

	@Autowired
	private SysUserRoleDao userRoleDao;
	@Autowired
	private SysRoleService roleService;
	
	@Override
	public List<SysUserRole> findAll() {
		return userRoleDao.findAll();
	}

	@Override
	public List<SysUserRole> findAll(Sort sort) {
		return userRoleDao.findAll(sort);
	}

	@Override
	public List<SysUserRole> findAllById(Iterable<Integer> ids) {
		return userRoleDao.findAllById(ids);
	}

	@Override
	public List<SysUserRole> saveAll(Iterable<SysUserRole> entities) {
		return userRoleDao.saveAll(entities);
	}

	@Override
	public void flush() {
		userRoleDao.flush();
	}

	@Override
	public SysUserRole saveAndFlush(SysUserRole entity) {
		return userRoleDao.saveAndFlush(entity);
	}

	@Override
	public void deleteInBatch(Iterable<SysUserRole> entities) {
		userRoleDao.deleteInBatch(entities);
	}

	@Override
	public void deleteAllInBatch() {
		userRoleDao.deleteAllInBatch();
	}

	@Override
	public SysUserRole getOne(Integer id) {
		return userRoleDao.getOne(id);
	}

	@Override
	public SysUserRole save(SysUserRole entity) {
		return userRoleDao.save(entity);
	}

	@Override
	public SysUserRole findById(Integer id) {
		return userRoleDao.findById(id).get();
	}

	@Override
	public boolean existsById(Integer id) {
		return userRoleDao.existsById(id);
	}

	@Override
	public long count() {
		return userRoleDao.count();
	}

	@Override
	public void deleteById(Integer id) {
		userRoleDao.deleteById(id);
	}

	@Override
	public void delete(SysUserRole entity) {
		userRoleDao.delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends SysUserRole> entities) {
		userRoleDao.deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		userRoleDao.deleteAll();
	}

	@Override
	public void revokeUserRole(String userId, String roleId) {
		userRoleDao.revokeUserRole(userId,roleId);
	}

	@Override
	public boolean grantUserRole(SysUser user) {
//		userRoleDao.grantUserRole(user.getId());
		SysRole role = roleService.findByRoleName(ROLE_USER);
		return grantRole(user, role);
	}

	@Override
	public boolean grantAdminRole(SysUser user) {

		SysRole role = roleService.findByRoleName(ROLE_ADMIN);
		return grantRole(user, role);
	}

	@Transactional
	private boolean grantRole(SysUser user, SysRole role) {
		SysUserRole userRoles = new SysUserRole();

		userRoles.setUserId(user.getId());
		userRoles.setRoleId(role.getId());

		if (userRoleDao.save(userRoles) == null) {
			return false;
		}
		return true;
	}

	@Override
	public Set<SysRole> findUserRoleByUserId(String userId) {
		return userRoleDao.findUserRoleByUserId(userId);
	}
}
