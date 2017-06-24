package dhbw.lan.lantalk.persistence.objects;

import java.util.List;

import javax.persistence.*;

/**
 * Represents a post from the database
 * 
 * @author Niklas Nikisch
 *
 */
@Entity
@Table(name = "Posts")
public class Post extends TextComponent {

	/**
	 * Represents a list of comments of the post
	 */
	@OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
	@OrderColumn(name = "orderIndex")
	private List<Comment> commentList;

	/**
	 * 
	 * @param commentList
	 *            Sets the comments in {@link Post#commentList} of the post
	 */
	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

	/**
	 * 
	 * @return the all commands {@link Post#commentList} of the post
	 */
	public List<Comment> getCommentList() {
		return this.commentList;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Post)) {
			return false;
		}
		
		Post post = (Post) obj;		
		if (!(post.getId() == this.getId())) {
			return false;
		}
		
		return true;
	}
}
