package com.fintrack.cli;

import java.util.Scanner;

public abstract class Menu {
    protected final Scanner scanner;

    public Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    public abstract void show();
}
