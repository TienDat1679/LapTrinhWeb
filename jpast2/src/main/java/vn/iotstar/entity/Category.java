package vn.iotstar.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="categories")
@AllArgsConstructor
@NoArgsConstructor
@Data
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CategoryId")
	private int categoryid;
	
	@Column(name="CategoryName", columnDefinition = "NVARCHAR(200) NOT NULL")
	@NotEmpty(message = "Khong duoc bo trong")
	private String categoryname;
	
	@Column(name="Images", columnDefinition = "NVARCHAR(500)")
	private String images;
	
	@Column(name="Status")
	private int status;
	
	@OneToMany(mappedBy = "category")
	private List<Video> videos;
	
	public Video addVideo(Video video) {
		getVideos().add(video);
		video.setCategory(this);
		return video;
	}
	
	public Video removeVideo(Video video) {
		getVideos().remove(video);
		video.setCategory(null);
		return video;
	}
}
