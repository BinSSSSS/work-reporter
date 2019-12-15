package cn.tblack.work.reporter.page;

import java.util.List;

import org.springframework.data.domain.Page;

/**
 * @-==-返回一个LaYui前端框架中table数据要求的格式
 * @author TD唐登
 * @Date:2019年11月25日
 * @Version: 1.0(测试版)
 */
public class LaYuiPage<T> {

	private Page<T> pageImpl;

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
	private List<?> result;
	/** 分页代码 */
	private String paginate;

	private Integer totalpages;

	public LaYuiPage() {
		super();
	}

	public LaYuiPage(Page<T> pageImpl) {
		this.pageImpl = pageImpl;
		this.data = pageImpl.getContent();
		this.count = data.size();

		this.startIndex = 0;
		this.curPage = pageImpl.getPageable().getPageNumber();
		this.totals = count;
		this.pageSize = pageImpl.getPageable().getPageSize();
		this.result = data;

		this.totalpages = this.count % this.pageSize == 0 ? this.count / this.pageSize : this.count / this.pageSize + 1;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 获取任一页第一条数据在数据集的位置.
	 * 
	 * @param pageNo   从1开始的页号
	 * @param pageSize 每页记录条数
	 * @return 该页第一条数据
	 */
	public static Integer getStartOfPage(Integer pageNo, Integer pageSize) {
		return (pageNo - 1) * pageSize;
	}

	public Integer getCurPage() {
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		if (curPage == null || curPage < 1) {
			this.curPage = 1;
			return;
		}
		this.curPage = curPage;
		Integer start = getPageSize() * (curPage - 1) + 1;
		setStartIndex(start);
	}

	public String getPaginate() {
		return paginate;
	}

	public void setPaginate(String paginate) {
		this.paginate = paginate;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/** 返回分页数 **/
	public Integer getTotalPages() {

		if (totals / this.pageSize < 1) {

			return 1;
		} else {
			Integer totalPages = this.totals / this.pageSize;
			if (this.totals % this.pageSize != 0) {
				totalPages += 1;
			}
			return totalPages;
		}
	}

	public List<?> getResult() {

		return result;
	}

	public void setResult(List<?> result) {
		this.result = result;
	}

	public Integer getStartIndex() {
		return startIndex;
	}

	/**
	 * !_!_!查询的开始位置总是从1开始.
	 * 
	 * @param startIndex
	 */
	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
		if (this.startIndex <= 0) {
			this.startIndex = 1;
		}
	}

	public Integer getTotals() {
		return totals;
	}

	public void setTotals(Integer totals) {
		this.totals = totals;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Page<T> getPageImpl() {
		return pageImpl;
	}

	public void setPageImpl(Page<T> pageImpl) {
		this.pageImpl = pageImpl;
	}

	public Integer getTotalpages() {
		return totalpages;
	}

	public void setTotalpages(Integer totalpages) {
		this.totalpages = totalpages;
	}

	@Override
	public String toString() {
		return "LaYuiPage [pageImpl=" + pageImpl + ", count=" + count + ", msg=" + msg + ", code=" + code + ", data="
				+ data + ", startIndex=" + startIndex + ", pageSize=" + pageSize + ", totals=" + totals + ", curPage="
				+ curPage + ", result=" + result + ", paginate=" + paginate + ", totalpages=" + totalpages + "]";
	}

}
