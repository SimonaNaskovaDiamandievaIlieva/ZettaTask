package pages;

import core.UserActions;

public class BasePage {

    protected final UserActions actions;

    protected BasePage(UserActions actions) {
        this.actions = actions;
    }
}
