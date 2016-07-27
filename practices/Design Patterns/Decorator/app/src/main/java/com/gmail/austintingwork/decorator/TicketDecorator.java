package com.gmail.austintingwork.decorator;

import android.util.Log;

/**
 * Created by cellbody on 2016/7/26.
 */

public abstract class TicketDecorator extends Component {
    private Component myTrailer;


    public TicketDecorator(Component myComponent) {
        this.myTrailer = myComponent;
    }

    public void callTrailer() {
        if (myTrailer != null) {
            myTrailer.prtTicket();
        }
    }
}
