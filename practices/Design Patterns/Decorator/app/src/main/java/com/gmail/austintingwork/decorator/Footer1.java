package com.gmail.austintingwork.decorator;

import android.util.Log;

/**
 * Created by cellbody on 2016/7/26.
 */

public class Footer1 extends TicketDecorator {

    public Footer1(Component myComponent) {
        super(myComponent);
    }

    @Override
    public void prtTicket() {
        super.callTrailer();
        Log.d("debug", "Footer1");
    }
}
