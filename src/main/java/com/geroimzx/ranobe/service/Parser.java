package com.geroimzx.ranobe.service;

import java.io.IOException;

public interface Parser {
    public <T> T parse(final String url, String...ignoreWords) throws IOException;

    public <T> T parseWithNextPageUrl(int delay, final String url, int pageCount, String...ignoreWords) throws IOException, InterruptedException;
}
