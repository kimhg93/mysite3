package kr.co.itcen.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.itcen.mysite.service.GuestbookService;
import kr.co.itcen.mysite.vo.GuestBookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping(value={"","/list"}, method=RequestMethod.GET)
	public String list(Model model) {
		List<GuestBookVo> list = guestbookService.getList();
		model.addAttribute("list", list);
		return "guestbook/list";
	}
	
	@RequestMapping(value="/list", method=RequestMethod.POST)
	public String list(GuestBookVo vo) {
		guestbookService.insert(vo);
		return "redirect:/guestbook";
	}
	
	@RequestMapping(value="/delete/{no}", method=RequestMethod.GET)
	public String delete(@PathVariable("no") Long no, Model model) {
		model.addAttribute("no", no);
		return "guestbook/delete";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(GuestBookVo vo) {
		guestbookService.delete(vo);
		return "redirect:/guestbook";
	}	
}
