package com.gmail.austintingwork.decorator;

import android.util.Log;

/**
 * Created by cellbody on 2016/7/27.
 */

public class Header1 extends TicketDecorator {

    public Header1(Component myComponent) {
        super(myComponent);
    }

    @Override
    public void prtTicket() {
        Log.d("debug", "Header1");
        super.callTrailer();
    }
}
