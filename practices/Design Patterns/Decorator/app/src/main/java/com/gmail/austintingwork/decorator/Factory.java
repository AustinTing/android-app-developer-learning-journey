package com.gmail.austintingwork.decorator;

/**
 * Created by cellbody on 2016/7/26.
 */

public class Factory {
    public Component getComponent() {
        Component myComponent;
        myComponent = new SalesTicket();
        myComponent = new Footer1(myComponent);
        myComponent = new Header1(myComponent);
        return myComponent;
    }
}
