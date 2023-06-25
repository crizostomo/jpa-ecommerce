package com.jpa.ecommerce.controller;

import com.jpa.ecommerce.model.Product;
import com.jpa.ecommerce.repository.Products;
import com.jpa.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private Products product;

    @Autowired
    private ProductService service;

    @PostMapping("/{id}/edit")
    public ModelAndView update(@PathVariable Integer id,
                                  @RequestParam Map<String, Object> product,
                                  RedirectAttributes redirectAttributes) {
        service.update(id, product);

        redirectAttributes.addFlashAttribute("message", "Update done successfully");

        return new ModelAndView("redirect:/products/{id}/edit");
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Integer id) {
        return newProduct(product.search(id));
    }

    @PostMapping("/new")
    public ModelAndView create(Product product,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        Product updatedProduct = service.create(product);

        redirectAttributes.addFlashAttribute("message", "Record done successfully");

        return new ModelAndView(
                "redirect:/products/{id}/edit", "id", updatedProduct.getId());
    }

    @GetMapping("/new")
    public ModelAndView newProduct(Product product) {
        ModelAndView mv = new ModelAndView("products/products-form");
        mv.addObject("product", product);
        return mv;
    }

    @GetMapping
    public ModelAndView list() {
        ModelAndView mv = new ModelAndView("products/products-list");
        mv.addObject("products", product.list());
        return mv;
    }
}