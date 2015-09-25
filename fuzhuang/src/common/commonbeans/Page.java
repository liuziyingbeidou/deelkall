package commonbeans;

import java.util.ArrayList;
import java.util.List;

import config.Constant;



public class Page {

	public Page(int start,int limit,int total, List<?> items){
		this.start = start;
		this.total = total;
		this.items = items;
		this.limit = limit;
	}
	
	public Page() {
		limit = Constant.PAGE_SIZE;
		start = 0;
		total = 0;
		items = new ArrayList();
	}

	private int limit;
	private int start;
	private int total;
	
	private List items;
	
	
	public List getItems() {
		return items;
	}
	public void setItems(List items) {
		this.items = items;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	/**
	 * 计算总页数.
	 */
	public int getTotalPages() {
		if (total == -1)
			return -1;

		int count = total / limit;
		if (total % limit > 0) {
			count++;
		}
		return count;
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean hasNextPage() {
		return getTotalPages() > start / limit + 1;
	}
	public int getPageNumer() {
		
		if (hasNextPage())
			return start / limit + 1;
		else{
			return start / limit + 1;
		}
			
		
	}
	/**
	 * 获取页数,从1开始计数
	 * @return
	 */
	public int getNextPage() {
		if (hasNextPage())
			return start / limit + 2;
		else
			return start / limit + 1;
	}

	/**
	 * 是否还有上一页. 
	 */
	public boolean hasPrePage() {
		return start > 0;
	}

	/**
	 * 返回上页的页号,序号从1开始.
	 */
	public int getPrePage() {
		if (hasPrePage())
			return start / limit;
		else
			return 1;
	}


	public int getFirstPage() {
		return 1;
	}

	public int getLastPage() {
		return getTotalPages();
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
}
