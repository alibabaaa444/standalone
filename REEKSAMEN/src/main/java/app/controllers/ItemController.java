package app.controllers;

import app.entities.Item;
import app.persistence.ItemMapper;
import io.javalin.http.Context;

import java.util.List;

public class ItemController {
    private final ItemMapper itemMapper;

    public ItemController(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    public void getAllItems(Context ctx) {
        List<Item> items = itemMapper.getAllItems();
        ctx.attribute("itemList", items);
        ctx.render("items.html");
    }
}
