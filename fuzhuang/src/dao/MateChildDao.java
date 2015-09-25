package dao;

import java.util.List;

import model.materials.MateChildVO;


public interface MateChildDao{


	public void insertBean(MateChildVO MateChildVO);
	
	public void deleteBean(MateChildVO MateChildVO);
	
	public void updateBean(MateChildVO MateChildVO);

	public List<MateChildVO> selectBeanList(String where);
	
	public MateChildVO selectBean(String where);
	
	public List<MateChildVO> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
}
