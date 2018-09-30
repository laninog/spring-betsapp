package com.betsapp.mgr.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.betsapp.mgr.domain.Match;
import com.betsapp.mgr.domain.MatchResult;
import com.betsapp.mgr.repositories.MatchRepository;

@Controller
public class MatchController {

	private static final Logger log = LoggerFactory.getLogger(MatchController.class);
	
	@Autowired
	private MatchRepository repository;

	@Secured({"ROLE_USER"})
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String findAll(Model model) {
		model.addAttribute("matches", repository.findAll());
		return "index";
	}

	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/form")
	public String add(Model model) {
		model.addAttribute("match", new Match());
		return "form";
	}

	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/local/{id}")
	public String local(@PathVariable("id") Long id) {
		setWinner(id, MatchResult.LOCAL);
		return "redirect:/";
	}

	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/visitor/{id}")
	public String visitor(@PathVariable("id") Long id) {
		setWinner(id, MatchResult.VISITOR);
		return "redirect:/";
	}

	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/draw/{id}")
	public String draw(@PathVariable("id") Long id) {
		setWinner(id, MatchResult.DRAW);
		return "redirect:/";
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@RequestMapping(value = "/matches", method = RequestMethod.POST)
	public String save(@Valid Match match, BindingResult result, RedirectAttributes flash) {
		
		log.info("Match -> " + match);
		
		if(result.hasErrors()) {
			return "form";
		}
		
		SecurityContext ctx = SecurityContextHolder.getContext();
		
		if(ctx != null && ctx.getAuthentication() != null) {
			Authentication auth = ctx.getAuthentication();
			flash.addFlashAttribute("success", auth.getName().concat(" has created a Match!"));
		}
		
		repository.saveOrUpdate(match);
		return "redirect:/";
	}
	
	private void setWinner(Long id, MatchResult winner) {
		if(id == null)
			return;
		Match match = repository.findById(id);
		if(match == null)
			return;
		match.setResult(winner);
		repository.saveOrUpdate(match);
	}
	
}
