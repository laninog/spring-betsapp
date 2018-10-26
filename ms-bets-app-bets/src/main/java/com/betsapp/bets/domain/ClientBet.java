package com.betsapp.bets.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.betsapp.domain.MatchResult;

@Entity
@Table(name = "BET_CLIENT_BETS")
public class ClientBet implements Serializable {

	private static final long serialVersionUID = -2016153288171257071L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "client_id")
	private Long client;

	@Column(name = "match_id")
	private Long match;

	@NotNull
	@Enumerated(EnumType.ORDINAL)
	private MatchResult result;

	@NotNull
	@Min(value = 1)
	private Double amount;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime created;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getClient() {
		return client;
	}

	public void setClient(Long client) {
		this.client = client;
	}

	public Long getMatch() {
		return match;
	}

	public void setMatch(Long match) {
		this.match = match;
	}

	public MatchResult getResult() {
		return result;
	}

	public void setResult(MatchResult result) {
		this.result = result;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

}
