package es.us.dp1.lx_xy_24_25.your_game_name.user;

import es.us.dp1.lx_xy_24_25.your_game_name.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "authorities")
@EqualsAndHashCode(callSuper = true)
public class Authorities extends BaseEntity{
	
//	@ManyToOne
//	@JoinColumn(name = "username")
//	User user;
	
//	@Enumerated(EnumType.STRING)
	@Column(length = 20, unique = true, nullable = false)
	String authority;
	
	
}
