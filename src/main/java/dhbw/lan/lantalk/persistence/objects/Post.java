package dhbw.lan.lantalk.persistence.objects;

import java.util.List;

import javax.persistence.*;

/**
 * Represents a comment from the database
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

}
