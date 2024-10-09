package vn.iotstar.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.iotstar.entity.Category;
import vn.iotstar.entity.Video;
import vn.iotstar.services.ICategoryService;
import vn.iotstar.services.IVideoService;
import vn.iotstar.services.impl.CategoryService;
import vn.iotstar.services.impl.VideoService;
import vn.iotstar.utils.Constant;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(urlPatterns = {"/admin/videos", "/admin/video/add",
		"/admin/video/insert", "/admin/video/edit", "/admin/video/update",
		"/admin/video/delete", "/admin/video/search"})
public class VideoController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public IVideoService videoService = new VideoService();
	public ICategoryService cateService = new CategoryService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String url = req.getRequestURI();
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		if (url.contains("videos")) {
			List<Video> list = new ArrayList<>();
			String cateidString = req.getParameter("id");
			int id = 0;
		    if (cateidString != null && !cateidString.trim().isEmpty()) {
		        try {
		            id = Integer.parseInt(cateidString);
		        } catch (NumberFormatException e) {
		            e.printStackTrace();
		        }
		    }
			if (id != 0) {
				list = videoService.findByCategoryId(id);
			} else {
				list = videoService.findAll();
			}
			req.setAttribute("listvideo", list);
			req.getRequestDispatcher("/views/admin/video-list.jsp").forward(req, resp);
		} 
		else if (url.contains("add")) {
			req.getRequestDispatcher("/views/admin/video-add.jsp").forward(req, resp);
		} 
		else if (url.contains("edit")) {
			String id = req.getParameter("id");
			Video video = videoService.findById(id);
			req.setAttribute("video", video);
			req.getRequestDispatcher("/views/admin/video-edit.jsp").forward(req, resp);
		} 
		else if (url.contains("delete")) {
			String id = req.getParameter("id");
			try {
				videoService.delete(id);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resp.sendRedirect(req.getContextPath() + "/admin/videos");
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String url = req.getRequestURI();
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		if (url.contains("insert")) {
			
			String videoid = req.getParameter("videoid");
			String title = req.getParameter("videotitle");
			String des = req.getParameter("description");
		    int views = Integer.parseInt(req.getParameter("views"));
		    String statu = req.getParameter("status");
		    System.out.println(statu);
		    int status = Integer.parseInt(statu);
		    String cateidString = req.getParameter("categoryid");
		    int cateid = 0;
		    if (cateidString != null && !cateidString.trim().isEmpty()) {
		        try {
		            cateid = Integer.parseInt(cateidString);
		        } catch (NumberFormatException e) {
		            e.printStackTrace();
		        }
		    }
		    Category category = cateService.findById(cateid);
			String images = req.getParameter("images1");
			
			Video video = new Video();
			video.setVideoId(videoid);
			video.setTitle(title);
			video.setActive(status);
			video.setDescription(des);
			video.setViews(views);
			video.setCategory(category);
			
			// Upload Images
			String fname = "";
			String uploadPath = Constant.DIR;
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			try {
				Part part = req.getPart("images");
				
				if (part.getSize() > 0) {
					String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
					// rename file
					int index = filename.lastIndexOf(".");
					String ext = filename.substring(index+1);
					fname = System.currentTimeMillis() + "." + ext;
					// upload file
					part.write(uploadPath + "/" + fname);
					// ghi ten file vao data
					video.setPoster(fname);
				} 
				else if(images != null) {
					video.setPoster(images);
				}
				else {
					video.setPoster("avatar.png");
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			videoService.insert(video);
			resp.sendRedirect(req.getContextPath() + "/admin/videos");
			
		} else if (url.contains("update")) {
			String videoid = req.getParameter("videoid");
			String title = req.getParameter("videotitle");
			String des = req.getParameter("description");
			int views = Integer.parseInt(req.getParameter("views"));
			int status = Integer.parseInt(req.getParameter("status"));
			int cateid = Integer.parseInt(req.getParameter("categoryid"));
			Category category = cateService.findById(cateid);
			
			Video video = new Video();
			video.setVideoId(videoid);
			video.setTitle(title);
			video.setActive(status);
			video.setDescription(des);
			video.setViews(views);
			video.setCategory(category);
			
			// Giu hinh cu neu khong update
			Video videoold = videoService.findById(videoid);
			String fileold = videoold.getPoster();
			// Upload Images
			String fname = "";
			String uploadPath = Constant.DIR;
			File uploadDir = new File(uploadPath);
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			try {
				Part part = req.getPart("images");
				if (part.getSize() > 0) {
					String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
					// rename file
					int index = filename.lastIndexOf(".");
					String ext = filename.substring(index+1);
					fname = System.currentTimeMillis() + "." + ext;
					// upload file
					part.write(uploadPath + "/" + fname);
					// ghi ten file vao data
					video.setPoster(fname);
				} else {
					video.setPoster(fileold);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			videoService.update(video);
			resp.sendRedirect(req.getContextPath() + "/admin/videos");
		}
	}

}
