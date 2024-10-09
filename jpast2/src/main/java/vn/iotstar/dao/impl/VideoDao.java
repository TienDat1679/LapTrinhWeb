package vn.iotstar.dao.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import vn.iotstar.configs.JPAConfig;
import vn.iotstar.dao.IVideoDao;
import vn.iotstar.entity.Video;

public class VideoDao implements IVideoDao {
	
	@Override
	public void insert(Video video) {
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			enma.persist(video);
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
			throw e;
		} finally {
			enma.close();
		}
	}
	@Override
	public void update(Video video) {
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			enma.merge(video);
			trans.commit();
		} catch (Exception e) {
			e.printStackTrace();
			trans.rollback();
			throw e;
		} finally {
			enma.close();
		}
	}
	@Override
	public void delete(String videoid) throws Exception {
		EntityManager enma = JPAConfig.getEntityManager();
		EntityTransaction trans = enma.getTransaction();
		try {
			trans.begin();
			Video video = enma.find(Video.class, videoid);
			if(video != null) {
				enma.remove(video);
			}else {
				throw new Exception("Không tìm thấy");
			}
			trans.commit();
		} catch (Exception e) {	
			e.printStackTrace();
			trans.rollback();
			throw e;
		} finally {
			enma.close();
		}
	}
	@Override
	public Video findById(String videoid) {
	    EntityManager enma = JPAConfig.getEntityManager();
	    Video video = enma.find(Video.class, videoid);
	    return video;
	}

	@Override
	public List<Video> findAll(){
		EntityManager enma = JPAConfig.getEntityManager();
		TypedQuery<Video> query = enma.createNamedQuery("Video.findAll", Video.class);
		return query.getResultList();
	}
	@Override
	public List<Video> findByVideoTitle(String vidtitle) {
		 EntityManager enma = JPAConfig.getEntityManager();
		 String jpql = "SELECT v FROM Video v WHERE v.videotitle like :vidtitle";
		 TypedQuery<Video> query= enma.createQuery(jpql, Video.class);
		 query.setParameter("videotitle", "%" + vidtitle + "%");
		 return query.getResultList();
	}
	@Override
	public List<Video> findAll(int page, int pagesize) {
		EntityManager enma = JPAConfig.getEntityManager();
	 	TypedQuery<Video> query= enma.createNamedQuery("Video.findAll", Video.class);
	 	query.setFirstResult(page*pagesize);
	 	query.setMaxResults(pagesize);
	 	return query.getResultList();
	 }
	@Override
	public int count() {
		EntityManager enma = JPAConfig.getEntityManager();
		String jpql = "SELECT count(v) FROM Video v";
		Query query = enma.createQuery(jpql);
		return ((Long)query.getSingleResult()).intValue();
	 }
	@Override
	public List<Video> findByCategoryId(int cateid) {
	    EntityManager enma = JPAConfig.getEntityManager();
	    
	    // Câu truy vấn JPQL tìm các video theo CategoryId
	    String jpql = "SELECT v FROM Video v WHERE v.category.categoryid = :cateid";
	    
	    // Tạo truy vấn với câu JPQL và kiểu trả về là Video
	    TypedQuery<Video> query = enma.createQuery(jpql, Video.class);
	    
	    // Truyền tham số categoryId vào truy vấn
	    query.setParameter("cateid", cateid);
	    
	    // Trả về danh sách kết quả
	    return query.getResultList();
	}

}
