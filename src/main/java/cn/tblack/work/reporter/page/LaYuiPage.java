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


	private static final long serialVersionUID = 1L;

	private Integer count;

	private String msg = "";

	private Integer code = 0;

	private List<T> data;

	/** 默认每一页的分页大小，如果需要自己定义 使用page.setpageSize(分页大小)即可 **/
	public static Integer DEFAULT_PAGE_SIZE = 10;
	/** 对象获取的开始位置，应该总是从1开始 */
	private Integer startIndex;
	/** 每页获取的页大小，默认是10 **/
	private Integer pageSize = DEFAULT_PAGE_SIZE;
	/** 总记录数 记录一共有多少数据 **/
	private Integer totals;
	/** 应该总是从1开始 */
	private Integer curPage = 1;
	/** 查询的结果 查询后的每一页的分页结果 */
	private List<T> result;
	/** 分页代码 */
	private String paginate;

	private Integer totalPages;

	public LaYuiPage() {
		super();
	}
	
	public LaYuiPage(Page<T> pageImpl) {
		this.data = pageImpl.getContent();
		this.count = data.size();

		this.startIndex = 0;
		this.curPage = pageImpl.getPageable().getPageNumber();
		this.totals = count;
		this.pageSize = pageImpl.getPageable().getPageSize();
		this.result = data;

		this.totalPages = this.count % this.pageSize == 0 ? this.count / this.pageSize : this.count / this.pageSize + 1;
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

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotals() {
		return totals;
	}

	public void setTotals(Integer totals) {
		this.totals = totals;
	}

	public Integer getCurPage() {
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public String getPaginate() {
		return paginate;
	}

	public void setPaginate(String paginate) {
		this.paginate = paginate;
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
				+ startIndex + ", pageSize=" + pageSize + ", totals=" + totals + ", curPage=" + curPage + ", result="
				+ result + ", paginate=" + paginate + ", totalPages=" + totalPages + "]";
	}

	
}
