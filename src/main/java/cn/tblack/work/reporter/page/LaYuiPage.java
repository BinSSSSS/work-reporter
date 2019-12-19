package cn.tblack.work.reporter.page;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;


/**
 * @-==-返回一个LaYui前端框架中table数据要求的格式
 * @author TD唐登
 * @Date:2019年11月25日
 * @Version: 1.0(测试版)
 */
public class LaYuiPage<T> implements Serializable{

	/** 默认每一页的分页大小，如果需要自己定义 使用page.setpageSize(分页大小)即可 **/
	public static Integer DEFAULT_PAGE_SIZE = 10;

	private static final long serialVersionUID = 1L;
	
	/**数据量大小*/
	private Integer count;

	private String msg = "";

	private Integer code = 0;
	
	/**查询分页结果数据*/
	private List<T> data;

	/** 对象获取的开始位置，应该总是从1开始 */
	private Integer startIndex;
	
	/** 每页获取的页大小，默认是10 **/
	private Integer limit = DEFAULT_PAGE_SIZE;
	
	/** 当前的页数下标 */
	private Integer page = 1;
	
	/**总页数*/
	private Integer totalPages;

	
	public LaYuiPage() {
		super();
	}
	
	public LaYuiPage(Page<T> pageImpl) {
		this.data = pageImpl.getContent();
		this.count = data.size();
		this.startIndex = 0;
		this.page = pageImpl.getPageable().getPageNumber();
		this.limit = pageImpl.getPageable().getPageSize();
		

		this.totalPages = this.count % this.limit == 0 ? this.count / this.limit : this.count / this.limit + 1;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	@Override
	public String toString() {
		return "LaYuiPage [count=" + count + ", msg=" + msg + ", code=" + code + ", data=" + data + ", startIndex="
				+ startIndex + ", limit=" + limit + ", page=" + page + ", totalPages=" + totalPages + "]";
	}
	
	
}
