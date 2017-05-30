package dhbw.lan.lantalk.persistence.objects;

import javax.persistence.*;

/**
 * Represents a comment from the database
 * 
 * @author Niklas Nikisch
 *
 */
@Entity
@Table(name = "Comments")
public class Comment extends TextComponent {

	/**
	 * Represents the article of the comment
	 */
	@ManyToOne
	@JoinColumn
	private Post post;

	/**
	 *
	 * @param user
	 *            set the {@link Comment#post} of the comment
	 */
	public void setPost(Post post) {
		this.post = post;
	}

	/**
	 * @return the {@link Comment#post} of the comment
	 */
	public Post getPost() {
		return this.post;
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
		Comment comment = (Comment) obj;
		if (!post.equals(comment.post)) {
			return false;
		}
		return true;
	}
}
