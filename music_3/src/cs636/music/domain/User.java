package cs636.music.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;

/**
 * User: we are only using a subset of the database attributes
 * here, for simplicity, and to show this is a normal thing to do.
 *
 */

@Entity
@Table(name="SITE_USER",
	uniqueConstraints = @UniqueConstraint(columnNames="EMAIL_ADDRESS"))

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator(name="UserIdGen",
			table = "music_id_gen",
			pkColumnName = "GEN_NAME",
			valueColumnName = "GEN_VAL",
			pkColumnValue = "userid_gen")
	@GeneratedValue(generator="UserIdGen")
	@Column(name="USER_ID")
	private long userId;

	@Column(name="EMAIL_ADDRESS", nullable=false, length=50)
	private String emailAddress;

	@Column(nullable=false, length=50)
	private String firstname;

	@Column(nullable=false, length=50)
	private String lastname;

	public long getId() {
		return userId;
	}
	
	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}