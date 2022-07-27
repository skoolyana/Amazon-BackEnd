package com.dailycodebuffer.springboot.tutorial.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.dailycodebuffer.springboot.tutorial.constants.AppConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "verification_tokens")
@NoArgsConstructor
@AllArgsConstructor 
@EqualsAndHashCode(callSuper = true, exclude = {"credential"})
@Data
@Builder
final public class VerificationToken extends AbstractMappedEntity implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Column(name = "verification_token_id", unique = true, nullable = false, updatable = false)
	private Integer verificationTokenId;
	
	
	@Column(name = "verif_token")
	private String token;
	
	
	@JsonFormat(pattern = AppConstant.LOCAL_DATE_FORMAT, shape = Shape.STRING)
	@DateTimeFormat(pattern = AppConstant.LOCAL_DATE_FORMAT)
	@Column(name = "expire_date")
	private LocalDate expireDate;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "credential_id")
	private Credential credential;
	
	
}
