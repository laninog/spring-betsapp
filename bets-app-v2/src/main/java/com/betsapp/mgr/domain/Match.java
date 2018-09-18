package com.betsapp.mgr.domain;

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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="MGR_MATCHES")
public class Match implements Serializable {

	private static final long serialVersionUID = -5993767229492265208L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    @NotEmpty
	private String local;
    
    @NotEmpty
	private String visitor;
    
    @Min(value=1)
    @Column(name="rel_local")
	private Integer relLocal = 1;
    
    @Min(value=1)
    @Column(name="rel_visitor")
	private Integer relVisitor = 1;

    @Min(value=1)
    @Column(name="rel_draw")
	private Integer relDraw = 1;
    
    @Enumerated(EnumType.ORDINAL)
    private MatchResult result;
    
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime open;
    
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime close;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getVisitor() {
		return visitor;
	}

	public void setVisitor(String visitor) {
		this.visitor = visitor;
	}

	public Integer getRelLocal() {
		return relLocal;
	}

	public void setRelLocal(Integer relLocal) {
		this.relLocal = relLocal;
	}

	public Integer getRelVisitor() {
		return relVisitor;
	}

	public void setRelVisitor(Integer relVisitor) {
		this.relVisitor = relVisitor;
	}
	
	public Integer getRelDraw() {
		return relDraw;
	}

	public void setRelDraw(Integer relDraw) {
		this.relDraw = relDraw;
	}
	
	public MatchResult getResult() {
		return result;
	}
	
	public void setResult(MatchResult result) {
		this.result = result;
	}

	public void setRelDraw(MatchResult result) {
		this.result = result;
	}

	public LocalDateTime getOpen() {
		return open;
	}

	public void setOpen(LocalDateTime open) {
		this.open = open;
	}

	public LocalDateTime getClose() {
		return close;
	}

	public void setClose(LocalDateTime close) {
		this.close = close;
	}

	@Override
	public String toString() {
		return "Match [id=" + id + ", local=" + local + ", visitor=" + visitor + ", relLocal=" + relLocal
				+ ", relVisitor=" + relVisitor + ", relDraw=" + relDraw + ", open=" + open + ", close=" + close + "]";
	}
	
}
