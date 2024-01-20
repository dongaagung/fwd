package com.example.fwd;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TicTacToeController {

    private TicTacToe game;

    @GetMapping("/")
    public String index(Model model, @RequestParam(name = "size", defaultValue = "3") int size) {
        
        if (ObjectUtils.isEmpty(game)) {
            game = new TicTacToe(size);
        }
        model.addAttribute("board", game.getBoard())
             .addAttribute("currentPlayer", game.getCurrentPlayer());
        return "index";
    }

    @PostMapping("/makeMove")
    public String makeMove(@RequestParam int row, @RequestParam int col, Model model) {
        if (game.makeMove(row, col)) {
            model.addAttribute("winner", game.getCurrentPlayer());
            game = null; // Reset the game after a win
            return "winner";
        }
        return "redirect:/";
    }

    @GetMapping("/resetGame")
    public String resetGame(@RequestParam(name = "size", defaultValue = "3") int size) {
        game = null;
        game = new TicTacToe(size);
        return "redirect:/";
    }
}
