package com.matheus.catalogo.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import com.matheus.catalogo.service.CatalogoService;
import com.matheus.catalogo.model.Musica;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class CatalogoController {
    
    @Autowired
    CatalogoService catalogosService;
    
    @RequestMapping(value="/musicas", method=RequestMethod.GET)
    public ModelAndView getMusicas() {
        ModelAndView mv = new ModelAndView("musicas");
        List<Musica> musicas = catalogosService.findAll();
        mv.addObject("musicas", musicas);
        return mv;
    }

    @RequestMapping(value="/musicas/{id}", method=RequestMethod.GET)
    public ModelAndView getMusicaDetalhes(@PathVariable("id") long id) {
        ModelAndView mv = new ModelAndView("musicaDetalhes");
        Musica musica = catalogosService.findById(id);
        mv.addObject("musica", musica);
        return mv;
    }


    @RequestMapping(value="/addMusica", method=RequestMethod.GET)
    public String getMusicaForm() {
        return "musicaForm";
    }

    
    @RequestMapping(value="/addMusica", method=RequestMethod.POST)
    public String salvarMusica(@Valid Musica musica, BindingResult result, RedirectAttributes attributes) {

        if (result.hasErrors()){
            attributes.addFlashAttribute("mensagem","Campos obrigatórios não foram preenchidos!!!");
            return "redirect:/addMusica";
        }      
        musica.setData(LocalDate.now());  
        catalogosService.save(musica);
        return "redirect:/musicas";
    }


    @RequestMapping(value="/atualizarMusica/{id}", method=RequestMethod.GET)
    public ModelAndView getMusicaAtualizar(@PathVariable("id") long id) {

        Musica musica = catalogosService.findById(id);
        ModelAndView mv = new ModelAndView("musicaAtualizar");
        mv.addObject("musica", musica);
        return mv;
    }

    @RequestMapping(value="/atualizarMusica/{id}", method=RequestMethod.POST)
    public String atualizarMusica(@Valid Musica musica, BindingResult result, RedirectAttributes attributes, @PathVariable("id") long id) {

        if (result.hasErrors()){
            attributes.addFlashAttribute("mensagem","Campos obrigatórios não foram preenchidos!!!");
            return "redirect:/atualizarMusica";
        }      

        musica.setData(LocalDate.now());  
        catalogosService.save(musica);
        String retorno = "redirect:/musicas/" + id;
        return retorno;
    }

    @RequestMapping(value="/excluirMusica/{id}", method=RequestMethod.GET)
    public ModelAndView confirmaExcluirMusica(@PathVariable("id") long id) {
        ModelAndView mv = new ModelAndView("musicaExcluirForm");
        Musica musica = catalogosService.findById(id);
        mv.addObject("musica", musica);
        return mv;
    }

    @DeleteMapping
    @RequestMapping(value="/excluirMusica/{id}", method=RequestMethod.POST)
    public String excluirMusica(@PathVariable("id") long id) {
        catalogosService.excluir(id);
        return "redirect:/musicas";
    }
}
