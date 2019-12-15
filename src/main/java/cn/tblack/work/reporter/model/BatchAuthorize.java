package cn.tblack.work.reporter.model;

import java.util.List;

/**
 * @_~_~保存批量授权中的 资源id链表和角色id链表
 * @author TD唐登
 * @Date:2019年11月27日
 * @Version: 1.0(测试版)
 */
public class BatchAuthorize {

	private List<String> resIdList;
	private List<String> roleIdList;

	public List<String> getResIdList() {
		return resIdList;
	}

	public void setResIdList(List<String> resIdList) {
		this.resIdList = resIdList;
	}

	public List<String> getRoleIdList() {
		return roleIdList;
	}

	public void setRoleIdList(List<String> roleIdList) {
		this.roleIdList = roleIdList;
	}

	@Override
	public String toString() {
		return "BatchAuthorize [resIdList=" + resIdList + ", roleIdList=" + roleIdList + "]";
	}
	/**
	 * @~_@_检查批量对象内的资源链表和角色链表是否为空，当有一个为空的时候，返回true，否则返回false
	 * @return
	 */
	public boolean isEmpty() {
		return resIdList.isEmpty() || roleIdList.isEmpty();
	}
}
