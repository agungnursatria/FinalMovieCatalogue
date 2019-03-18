package com.anb.finalmoviecatalogue.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class StackWidgetServices extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteFactory(this.getApplicationContext());
    }
}
