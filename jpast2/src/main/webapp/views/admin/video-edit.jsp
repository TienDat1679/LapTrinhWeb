<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglib.jsp" %>

<form action="${pageContext.request.contextPath }/admin/video/update" method="post" enctype="multipart/form-data">

	<input type="text" id="videoid" name="videoid" value="${video.videoId }" hidden="hidden"><br> 

	<label for="videotitle">Video Title: </label><br> 
	<input type="text" id="videotitle" name="videotitle" value="${video.title }"><br> 
	
	<label for="categoryid">Category ID: </label><br> 
	<input type="text" id="categoryid" name="categoryid" value="${video.category.categoryid }"><br> 
	
	<label for="views">View Count:</label><br> 
	<input type="text" id="views" name="views" value="${video.views }"><br> 
	
	<label for="description">Description:</label><br> 
	<input type="text" id="description" name="description" value="${video.description }"><br> 
	
	<label for="images">Poster:</label><br> 
	
		<c:if test="${video.poster.substring(0,5) != 'https'}">
			<c:url value="/image?fname=${video.poster }" var="imgUrl"></c:url>
		</c:if>			
		<c:if test="${video.poster.substring(0,5) == 'https'}">
			<c:url value="${video.poster }" var="imgUrl"></c:url>
		</c:if>	
		<img id="imagess" height="150" width="200" src="${imgUrl}" />
		
	<input type="file" onchange="chooseFile(this)" id="images" name="images" value="${video.poster }"><br>
	
	<label for="status">Active: </label>
	<input id="statuson" type="radio" name="status" value="1" ${video.active==1?'checked': ''}>
	<label for="statuson">Hoạt động</label>
	<input id="statusoff" type="radio" name="status" value="0" ${video.active==0?'checked': ''}>
	<label for="statusoff">Khóa</label>
	
	<br> <input type="submit" value="Submit">
</form>
  