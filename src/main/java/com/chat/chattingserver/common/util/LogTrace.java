package com.chat.chattingserver.common.util;

import com.chat.chattingserver.common.dto.TraceStatus;

public interface LogTrace {
    TraceStatus begin(String message);
    void end(TraceStatus status);
    void exception(TraceStatus status, Exception e);
}
