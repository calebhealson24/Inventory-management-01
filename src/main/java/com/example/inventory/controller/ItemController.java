package com.example.inventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.inventory.model.Item;
import com.example.inventory.repository.ItemRepository;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class ItemController {

    private final ItemRepository repo;

    public ItemController(ItemRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String home(Model model) {
        model.addAttribute("items", repo.findAll());
        return "list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("item", new Item());
        return "form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Item item) {
        repo.save(item);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Optional<Item> opt = repo.findById(id);
        if (opt.isPresent()) {
            model.addAttribute("item", opt.get());
            return "form";
        }
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/";
    }
}
