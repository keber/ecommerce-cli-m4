package com.unicornt.store;

import com.unicornt.store.service.StoreService;
import com.unicornt.store.ui.Console;

public class Main {

    public static void main(String[] args) {
        new Console(new StoreService()).run();
    }
}
