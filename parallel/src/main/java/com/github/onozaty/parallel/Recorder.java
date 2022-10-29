package com.github.onozaty.parallel;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Recorder {

    private final AtomicInteger recodingIdProvider = new AtomicInteger();
    private final AtomicInteger eventIdProvider = new AtomicInteger();

    private final ConcurrentHashMap<Integer, Integer> startEventMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, Integer> endEventMap = new ConcurrentHashMap<>();

    public int start() {
        int recodingId = recodingIdProvider.incrementAndGet();
        int eventId = eventIdProvider.incrementAndGet();

        startEventMap.put(recodingId, eventId);
        return recodingId;
    }

    public void end(int recodingId) {
        int eventId = eventIdProvider.incrementAndGet();

        endEventMap.put(recodingId, eventId);
    }

    public void printByRecordingId(Appendable out) throws IOException {

        int maxRecordingId = recodingIdProvider.get();
        int maxRecordingIdLength = String.valueOf(maxRecordingId).length();
        String idFormat = "%" + maxRecordingIdLength + "d";

        int maxEventId = eventIdProvider.get();

        for (int recodingId = 1; recodingId <= maxRecordingId; recodingId++) {

            out.append(String.format(idFormat, recodingId));
            out.append(" : ");

            int startEventId = startEventMap.get(recodingId);
            int endEventId = endEventMap.get(recodingId);
            for (int eventId = 1; eventId <= maxEventId; eventId++) {
                if (eventId >= startEventId && eventId <= endEventId) {
                    out.append("+");
                } else {
                    out.append(" ");
                }
            }

            out.append("\n");
        }
    }

    public void printByActiveCount(Appendable out) throws IOException {

        int maxRecordingId = recodingIdProvider.get();
        int maxRecordingIdLength = String.valueOf(maxRecordingId).length();
        String idFormat = "%" + maxRecordingIdLength + "d";

        int maxEventId = eventIdProvider.get();

        // 各イベントタイミング毎にアクティブな数をカウント
        HashMap<Integer, Integer> activeCountByEvent = new HashMap<>();
        for (int recodingId = 1; recodingId <= maxRecordingId; recodingId++) {

            int startEventId = startEventMap.get(recodingId);
            int endEventId = endEventMap.get(recodingId);

            for (int eventId = 1; eventId <= maxEventId; eventId++) {
                if (eventId >= startEventId && eventId <= endEventId) {
                    activeCountByEvent.merge(eventId, 1, (v1, v2) -> v1 + 1);
                }
            }
        }

        int maxActiveCount = activeCountByEvent.values().stream().max(Integer::compare).get();

        // 棒グラフ形式で出力
        for (int currnetCount = maxActiveCount; currnetCount >= 1; currnetCount--) {

            out.append(String.format(idFormat, currnetCount));
            out.append(" : ");

            for (int eventId = 1; eventId <= maxEventId; eventId++) {

                int activeCount = activeCountByEvent.getOrDefault(eventId, 0);
                if (activeCount >= currnetCount) {
                    out.append("+");
                } else {
                    out.append(" ");
                }
            }
            out.append("\n");
        }
    }
}
