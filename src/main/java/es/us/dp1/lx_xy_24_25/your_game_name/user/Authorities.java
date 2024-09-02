package es.us.dp1.lx_xy_24_25.your_game_name.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import es.us.dp1.lx_xy_24_25.your_game_name.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "authorities")
public class Authorities extends BaseEntity{
	
//	@ManyToOne
//	@JoinColumn(name = "username")
//	User user;
	
//	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	String authority;
	
	
}
