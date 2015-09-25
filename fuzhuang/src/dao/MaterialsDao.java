package dao;

import java.util.List;

import model.materials.MaterialsVO;

public interface MaterialsDao{


	public void insertBean(MaterialsVO MaterialsVO);
	
	public void deleteBean(MaterialsVO MaterialsVO);
	
	public void updateBean(MaterialsVO MaterialsVO);

	public List<MaterialsVO> selectBeanList(String where);
	
	public MaterialsVO selectBean(String where);
	
	public List<MaterialsVO> selectBeanList(final int start, final int limit,final String where);
	
	public int selectBeanCount(final String where);
	
}
