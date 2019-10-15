package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity
public class Post extends Model {

	public String title;
	public Date postedAt;

	@Lob // ← 大きな文字列型DBをしようすることを宣言
	public String content;

	@ManyToOne // ← UserClassとの関連を宣言
	public User author;

	public Post(User author, String title, String content) {
		this.author = author;
		this.title = title;
		this.content = content;
		this.postedAt = new Date();
	}
}